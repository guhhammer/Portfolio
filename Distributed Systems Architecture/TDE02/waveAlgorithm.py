import pika, sys, json, time, datetime, enum

## Nome: Gustavo Hammerschmidt.

#############################################
#############################################
## INIT

#   States = { INICIATOR, IDLE, ID_COLLECTING, OK }
#	Sstart = { INICIATOR, IDLE }
#	Sfinish = { OK }

# Restrictions: { CN, TR, BL, UI }

### Obs.1: Quando você disse: o iniciador deve imprimir a lista de todas as entidades do sistema, 
### eu optei por inserir o id do componente iniciador também. Acho que era isso, mas ficou meio ambíguo.
###
### Obs.2: Body of the messages sent is composed of:  "ORIGIN-ID:COMMAND".
###
### Obs.3: If message is I, then: "ORIGIN-ID:COMMAND:INFORMATION".
###
### Obs.4: Starter sends "NULL:_".
###

class States(enum.Enum):

	IDLE = 1
	INICIATOR = 2 
	ID_COLLECTING = 3
	OK = 4


if len(sys.argv) < 2:

	print("\n\tYou should run it like this: \n\n\t\t>> " + __file__.split("\\")[-1]
							+ " [id of your program] [neighbours' ids one-by-one]\n")
	
	sys.exit()


my_exchange, my_id, neighbour_ids = '', sys.argv[1], sys.argv[2:]


connection = pika.BlockingConnection()

channel = connection.channel()


MY_STATE, not_visited, _iniciator, entry_point, info_to_be_returned = States.IDLE, [], False, -1, []


#############################################
#############################################
## FUNCTIONS

sep = lambda pattern: print(pattern * 60)


def resetGlobalVars() -> None:

	global MY_STATE, not_visited, _iniciator, entry_point, info_to_be_returned

	MY_STATE, not_visited, _iniciator, entry_point, info_to_be_returned = States.IDLE, [], False, -1, []


def prelog() -> str: return "\n|" + str(datetime.datetime.now()).split('.')[0] + " " + my_id


def onCallback(channel, method, properties, body) -> None:

	global neighbour_ids, my_exchange, MY_STATE, not_visited, _iniciator, entry_point, info_to_be_returned

	msg = body.decode().split(":")

	if msg[0].upper() == "NULL": MY_STATE = States.INICIATOR

	if MY_STATE == States.INICIATOR:

		MY_STATE, not_visited, _iniciator = States.ID_COLLECTING, neighbour_ids[:], True

		print(f"{prelog()} is INICIATOR.")

		onWave()

	if MY_STATE == States.IDLE:

		if msg[1].upper() == "R" and entry_point == -1:

			MY_STATE, _iniciator, entry_point, not_visited = States.ID_COLLECTING, False, msg[0], neighbour_ids[:]

			if entry_point in not_visited: not_visited.remove(entry_point)

			print(f"{prelog()} is IDLE, received R message from {entry_point}.")

			onWave()

	if MY_STATE == States.ID_COLLECTING:

		print(f"{prelog()} is ID_COLLECTING.")

		if msg[1].upper() == "R" and msg[0] in not_visited: 

			not_visited.remove(msg[0]); print(f"{prelog()} received R message from {msg[0]}.")

		if msg[1].upper() == "I" and msg[0] in not_visited: 

		   	[info_to_be_returned.append(_) for _ in msg[2].split(",")]

		   	not_visited.remove(msg[0]); print(f"{prelog()} received I message from {msg[0]}.")

		if len(not_visited) == 0: 

			info_to_be_returned.append(my_id)

			onWave()


def onConsume() -> None:

	global channel, my_id, onCallback
	
	channel.basic_consume(queue=str(my_id), on_message_callback=onCallback, auto_ack=True)

	try:

		sep("-"); print("\nWaiting for messages... [Press ctrl+c to quit consuming.]")
		
		channel.start_consuming()

	except KeyboardInterrupt: channel.stop_consuming()

	print("\n"); sep("-")


def onUnicast(my_exchange, route, msg) -> None:

	global channel

	channel.basic_publish(exchange=my_exchange, routing_key=route, body=msg)


def onWave() -> None:

	global MY_STATE, not_visited

	time.sleep(2)

	if len(not_visited) > 0:
		
		for _ in not_visited:

			onUnicast(my_exchange, _, f"{my_id}:R"); print(f"{prelog()} is sending R to {_}.")

	else:

		MY_STATE = States.OK; print(f"{prelog()} is OK.")
		
		if not _iniciator: 

			print(f"{prelog()} is sending I to {entry_point}.")

			_info = my_id if len(info_to_be_returned) == 0 else ",".join(info_to_be_returned)

			onUnicast(my_exchange, entry_point, f"{my_id}:I:{_info}")
	
		else: print(f"\n{prelog()} received this information:\n|\n|>\t{info_to_be_returned}\n")

	if MY_STATE == States.OK:

		time.sleep(5)

		resetGlobalVars(); print(f"{prelog()} is IDLE again.")


#############################################
#############################################
## MAIN

if __name__ == "__main__":


	## Declare my id queue. 
	channel.queue_declare(queue=str(my_id))

	## Declare my neighbour ids' queues.
	[channel.queue_declare(queue=str(_)) for _ in neighbour_ids]

	print("\n"+__file__.split("\\")[-1]+" ID:"+my_id)

	onConsume()
	
	channel.close()

	print("\nExecution ended.\n\n"); sep("=")

