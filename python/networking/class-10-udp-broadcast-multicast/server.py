
import socket
import sys

print('Enter the server port')
port = int(input())

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

try:
    s.bind(('', port))
except OSError:
   print('# bind error')
   sys.exit()

while True:
    data, addr = s.recvfrom(1024)
    print('client', addr, 'sent:', data)

print('the server stopped')
s.close()
