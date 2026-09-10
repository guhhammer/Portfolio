import socket, sys

s, port = socket.socket(socket.AF_INET, socket.SOCK_DGRAM), 9999

try:
    s.bind(('', port))
except OSError:
   print('# bind error')
   sys.exit()

print(f"\n<127.0.0.1, {port}>\n")

while True:
	input('press <ENTER> to continue.')
	try:
	    data, addr = s.recvfrom(1024)
	    print('sensor', addr, 'sent:', data)
	except OSError:
		print('Something strange happened.')
print('the server stopped')
s.close()
