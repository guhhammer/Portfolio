import socket, sys

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

while True:
    ip, port, msg = '127.0.0.1', 9999, input('Enter the message: ')

    s.sendto(bytes(msg, 'utf-8'), (ip, port))

    print(f'Sent message: \'{msg}\' to <{ip}, {port}>')

print('the client stopped')
s.close()
