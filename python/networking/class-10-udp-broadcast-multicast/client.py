import socket
import sys

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
#s.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

#s.bind(('127.0.0.1', 0))

while True:

    print()
    ip = input('Enter the destination IP: ')

    print('Enter the destination port: ')
    port = int(input())

    print('Enter the message: ')
    msg = input()

    try:
        s.sendto(msg.encode(), (ip, port))
    except OSError:
        print('unsupported address')

print('the client stopped')
s.close()
