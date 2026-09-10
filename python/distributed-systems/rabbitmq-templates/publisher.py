import pika

connection = pika.BlockingConnection()

channel = connection.channel()

channel.queue_declare(queue='queue')

msg = "hey"

channel.basic_publish(exchange='', routing_key='queue', body=msg)

channel.close()
