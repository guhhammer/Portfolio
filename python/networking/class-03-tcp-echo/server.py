import socket, sys

host, port = '127.0.0.1', 9999

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
	server_socket.bind((host, port))
except OSError:
	print("# bind error")
	sys.exit()

server_socket.listen(5)

print("Waiting for connections on port", port, "\n")

while True:
	conn, addr = server_socket.accept()
	print("Received a connection from", addr, "\n")

	while True:
		data = conn.recv(1024)
		print("Received", len(data), "bytes.\n")

		if not data:
			break
		print(data)

	print("The connection was closed.\n")
	conn.close()
