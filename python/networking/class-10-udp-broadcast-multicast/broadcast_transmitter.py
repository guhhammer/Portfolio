import socket
import sys

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

s.bind(('127.0.0.1', 0))

while True:
    ip = input('Enter the destination IP: ')
    port = int(input('Enter the destination port: '))
    msg = input('Enter the message: ')

    try:
        s.sendto(msg.encode(), (ip, port))
    except OSError:
        print('address', ip, 'not supported')

print('the client stopped')
s.close()
