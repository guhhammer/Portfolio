import socket, sys

s, porta = socket.socket(socket.AF_INET, socket.SOCK_DGRAM), 9999

try:
    s.bind(('', porta))
except:
   print('# erro de bind')
   sys.exit()

print(f"\n<127.0.0.1, {porta}>\n")

while True:
	input('digite <ENTER> para continuar.')
	try:
	    data, addr = s.recvfrom(1024)
	    print('sensor ', addr, ' enviou:', data)
	except:
		print('Algo estranho aconteceu.')
print('o servidor encerrou')
s.close()