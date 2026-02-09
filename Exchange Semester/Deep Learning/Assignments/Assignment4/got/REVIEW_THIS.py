import socket


s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.connect(("127.0.0.1", 5000))

print("\n IP Address: "+s.getsockname()[0])

s.close()
