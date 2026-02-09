import pika, sys

## Nome: Gustavo Hammerschmidt.

if len(sys.argv) < 1:

	print("\n\tYou should run it like this: \n\n\t\t>> "+
		    __file__.split("\\")[-1]+
		    " [entity id]\n")
	
	sys.exit()


connection = pika.BlockingConnection()

channel = connection.channel()

channel.queue_declare(queue='starter')

msg = f"NULL:_"

print(str(sys.argv[1]))

channel.basic_publish(exchange='', routing_key=str(sys.argv[1]), body=msg)

channel.close()
