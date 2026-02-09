import socket

host, port = socket.gethostname(), 5000

client_sock = socket.socket()
client_sock.connect((host, port))

message = input("Type: ")

while True:
	if message == "b":
		break

	client_sock.send(message.encode())

	data = client_sock.recv(1024).decode()

	print('Received from server: '+ data)

	message = input("Type: ")

client_sock.close()


