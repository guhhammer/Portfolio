import socket, sys

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

while True:
    ip, porta, msg = '127.0.0.1', 9999, input('Entre com a mensagem: ')

    s.sendto(bytes(msg, 'utf-8'), (ip, porta))

    print(f'Sent message: \'{msg}\' to <{ip}, {porta}>')

print('o cliente encerrou')
s.close()