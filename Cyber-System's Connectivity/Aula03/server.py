import socket, sys

host, port = '127.0.0.1', 9999

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
	server_socket.bind((host, port))
except:
	print("# erro de bind")
	sys.exit()

server_socket.listen(5)

print("Aguardando conexões em ", port,"\n")

while True:
	conn, addr = server_socket.accept()
	print("Recebi uma conexão de ", addr,"\n")

	while True:
		data = conn.recv(1024)
		print("Recebi", len(data), " bytes.\n")

		if not data:
			break
		print(data)

	print("A conexão foi encerrada.\n")
	conn.close()