
import socket
import sys

print('Entre com a porta do servidor')
porta = int(input())

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

try: 
    s.bind(('', porta))
except:
   print('# erro de bind')
   sys.exit()

while True:
    data, addr = s.recvfrom(1024)
    print('cliente ', addr, ' enviou:', data)

print('o servidor encerrou')
s.close()