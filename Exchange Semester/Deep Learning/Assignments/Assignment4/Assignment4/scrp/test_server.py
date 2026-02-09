import socket


s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.connect(("127.0.0.1", 5000)

print(s.getsockname()[0])


s.close()