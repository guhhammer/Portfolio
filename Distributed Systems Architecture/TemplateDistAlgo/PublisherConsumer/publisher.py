import pika

connection = pika.BlockingConnection()

channel = connection.channel()

channel.queue_declare(queue='fila')

msg = "hey"

channel.basic_publish(exchange='', routing_key='fila', body=msg)

channel.close()
