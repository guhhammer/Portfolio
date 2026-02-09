import pika, sys, json, time, datetime, enum

#############################################
#############################################
## INIT

#   States = { INICIATOR, IDLE, VISITED, OK }
#	Sstart = { INICIATOR, IDLE }
#	Sfinish = { OK }

# Restrictions: { CN, TR, BL, UI }

class States(enum.Enum):

	INICIATOR = 1
	IDLE = 2
	VISITED = 3
	OK = 4

if len(sys.argv) < 2:

	print("\n\tYou should run it like this: \n\n\t\t>> "+
		    __file__.split("\\")[-1]+
		    " [id of your program] [neighbours' ids one-by-one]\n")
	
	sys.exit()

my_exchange, my_id, neighbour_ids = '', sys.argv[1], sys.argv[2:]

connection = pika.BlockingConnection()

channel = connection.channel()

MY_STATE = States.IDLE

not_visited = neighbour_ids[:]

_iniciator = False

_entry = my_id

### Body of the messages sent is composed of: 
###
###   "origin id:COMMAND"
###

#############################################
#############################################
## FUNCTIONS

sep = lambda pattern: print(pattern * 60)


def onCallback(channel, method, properties, body) -> None:

	global neighbour_ids, my_exchange, MY_STATE, not_visited, _iniciator, _entry

	msg = body.decode().split(":")
	origin = msg[0]

	if msg[0].upper() == "NULL": # INICIADOR.

		not_visited = neighbour_ids[:]

		_iniciator = True

		print(f"\n{datetime.datetime.now()} {my_id} is INICIATOR.")

		onVisita()

	if MY_STATE == States.IDLE:

		if msg[1].upper() == "T":

			_entry = origin

			if origin in not_visited: not_visited.remove(origin)

			_iniciator = False

			print(f"\n{datetime.datetime.now()} {my_id} is IDLE, received message from {origin}.")

			onVisita()

	if MY_STATE == States.VISITED:

		if msg[1].upper() == "T":

			if origin in not_visited: not_visited.remove(origin)
			
			print(f"\n{datetime.datetime.now()} {my_id} is VISITED, received message from {origin}. Sending B to origin.");			

			onUnicast(my_exchange, origin, f"{my_id}:B")

		if msg[1].upper() in ["R", "B"]:

			print(f"\n{datetime.datetime.now()} {my_id} is VISITED, received message from {origin}.");			

			onVisita()


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


def onUnicast(my_exchange, route, msg) -> None:

	global channel

	channel.basic_publish(exchange=my_exchange, routing_key=route, body=msg)


def onVisita() -> None:

	global MY_STATE, not_visited

	time.sleep(2)

	if len(not_visited) > 0:

		_next = not_visited[0]

		not_visited.remove(_next)

		MY_STATE = States.VISITED

		print(f"\n{datetime.datetime.now()} {my_id} is sending T to {_next}.")

		onUnicast(my_exchange, _next, f"{my_id}:T")

	else:

		MY_STATE = States.OK

		if not _iniciator: 

			print(f"\n{datetime.datetime.now()} {my_id} is sending R to {_entry}.")

			onUnicast(my_exchange, _entry, f"{my_id}:R")

		print(f"\n{datetime.datetime.now()} {my_id} is OK.")


#############################################
#############################################
## MAIN

if __name__ == "__main__":


	## Declare my id queue. 
	channel.queue_declare(queue=str(my_id))

	## Declare my neighbour ids' queues.
	[channel.queue_declare(queue=str(_)) for _ in neighbour_ids]

	onConsume();
	
	channel.close()

	print("\nExecution ended.\n\n"); sep("=")

