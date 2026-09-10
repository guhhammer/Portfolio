import pika


def callback(channel, method, properties, body) -> None:

	print("Received: ", body.decode())


connection = pika.BlockingConnection()

channel = connection.channel()

channel.queue_declare(queue='queue')


channel.basic_consume(queue='queue', on_message_callback=callback, auto_ack=True)

try:

	print("Waiting for messages...")
	channel.start_consuming()

except KeyboardInterrupt:

	channel.stop_consuming()

channel.close()
