import pika


def callback(channel, method, properties, body) -> None:

	print("Recebido: ", body.decode())


connection = pika.BlockingConnection()

channel = connection.channel()

channel.queue_declare(queue='fila')


channel.basic_consume(queue='fila', on_message_callback=callback, auto_ack=True)

try:

	print("Esperando mensagens...")
	channel.start_consuming()

except KeyboardInterrupt:

	channel.stop_consuming()

channel.close()
