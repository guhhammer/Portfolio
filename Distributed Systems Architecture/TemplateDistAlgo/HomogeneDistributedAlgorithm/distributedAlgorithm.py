import pika, sys, json


# #### ##    ## #### ######## 
#  ##  ###   ##  ##     ##    
#  ##  ####  ##  ##     ##    
#  ##  ## ## ##  ##     ##    
#  ##  ##  ####  ##     ##    
#  ##  ##   ###  ##     ##    
# #### ##    ## ####    ##    

if len(sys.argv) < 2:

	print("\n\tYou should run it like this: \n\n\t\t>> "+
		    __file__.split("\\")[-1]+
		    " [id of your program] [neighbours' ids one-by-one]\n")
	
	sys.exit()


my_exchange, my_id, neighbour_ids = '', sys.argv[1], sys.argv[2:]

connection = pika.BlockingConnection()

channel = connection.channel()

### Body of the messages sent is composed of: 
###
###   "origin id; destiny; broadcast flags or options; message content".
###


# ######## ##     ## ##    ##  ######  ######## ####  #######  ##    ##  ######  
# ##       ##     ## ###   ## ##    ##    ##     ##  ##     ## ###   ## ##    ## 
# ##       ##     ## ####  ## ##          ##     ##  ##     ## ####  ## ##       
# ######   ##     ## ## ## ## ##          ##     ##  ##     ## ## ## ##  ######  
# ##       ##     ## ##  #### ##          ##     ##  ##     ## ##  ####       ## 
# ##       ##     ## ##   ### ##    ##    ##     ##  ##     ## ##   ### ##    ## 
# ##        #######  ##    ##  ######     ##    ####  #######  ##    ##  ###### 

sep = lambda pattern: print(pattern * 60)


def onBroadcast(my_exchange, route, msg, onCallbackTriggered=False) -> None:

	global neighbour_ids, my_id

	send_to = neighbour_ids

	if onCallbackTriggered:

		send_to = list( set(neighbour_ids) - set(json.loads(msg.split(";")[2])['broadcasted_to']) )

	for i in send_to:

		appender = msg.split(";")

		appender[2] = list( set(json.loads(msg.split(";")[2])['broadcasted_to']) | set(neighbour_ids) | set(my_id) )

		appender[2] = json.dumps({'broadcasted_to': appender[2]})

		onUnicast(my_exchange, i, ";".join(appender))


def onCallback(channel, method, properties, body) -> None:

	global neighbour_ids, my_exchange

	msg = body.decode().split(";")

	if msg[1] == 'b' or msg[1] == 'broadcast':

		forwd = list( set(neighbour_ids) - set(json.loads(msg[2])['broadcasted_to']) )

		print(f"> Received for broadcast from {msg[0]} (forwarded to {forwd}): ", msg[3])

		onBroadcast(my_exchange, "b", body.decode(), onCallbackTriggered=True)

	print(f"> Received from {msg[0]}: ", msg[3])


def onConsume() -> None:

	global channel, my_id, onCallback
	
	channel.basic_consume(queue=str(my_id), on_message_callback=onCallback, auto_ack=True)

	try:

		print("\n"); sep("-")
		print("\nWaiting for messages... [Press ctrl+c to quit consuming.]")
		channel.start_consuming()

	except KeyboardInterrupt:

		channel.stop_consuming()

	print("\n"); sep("-")


def onPublish() -> None:

	global channel, sep, my_id, my_exchange

	sep("-"); print("\nPublish:\n")

	route, broadcast_options = "", json.dumps({'broadcasted_to': my_id})
	while route.lower() not in [*neighbour_ids, 'b', 'broadcast']:

		try:
		
			route = input(f"> Type a neighbour id ({neighbour_ids}) or broadcast(b): ")
			break

		except KeyboardInterrupt: print("\nMessage unpublished.\n"); sep("-"); return

	msg = ""
	while msg == "":

		try:

			msg = input("> Type a message: ")
			msg = my_id+";"+route+";"+broadcast_options+";"+msg

			break

		except KeyboardInterrupt: print("\nMessage unpublished.\n"); sep("-"); return

	if route not in ['b', 'broadcast']:

		onUnicast(my_exchange, route, msg)

	else:

		onBroadcast(my_exchange, route, msg)

	print("\nMessage published.\n"); sep("-")


def onUnicast(my_exchange, route, msg) -> None:

	global channel

	channel.basic_publish(exchange=my_exchange, routing_key=route, body=msg)


# ##     ##    ###    #### ##    ## 
# ###   ###   ## ##    ##  ###   ## 
# #### ####  ##   ##   ##  ####  ## 
# ## ### ## ##     ##  ##  ## ## ## 
# ##     ## #########  ##  ##  #### 
# ##     ## ##     ##  ##  ##   ### 
# ##     ## ##     ## #### ##    ## 

if __name__=="__main__":


	## Declare my id queue. 
	channel.queue_declare(queue=str(my_id))

	## Declare my neighbour ids' queues.
	[channel.queue_declare(queue=str(_)) for _ in neighbour_ids]


	print("\n"); sep("=")
	while True:

		try:

			action = input("\nChoose an action: Publish(1)/Consume(2)/End(3) >> ")

			if str(action).lower() in ["1", "publish"]: onPublish(); continue
			
			if str(action).lower() in ["2", "consume"]: onConsume(); continue

			if str(action).lower() in ["3", "end"]: break

		except KeyboardInterrupt: break
	
	channel.close()

	print("\nExecution ended.\n\n"); sep("=")
