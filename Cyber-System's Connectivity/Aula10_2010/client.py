import socket
import sys

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
#s.setsockopt( socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

#s.bind(('127.0.0.1',0))

while True:

    print()
    ip = input('Entre com o IP de destino: ')

    print('Entre com a porta de destino: ')
    porta = int(input())

    print('Entre com a mensagem: ')
    msg = input()

    try:
        s.sendto(msg.encode(), (ip, porta))
    except:
        print('endereço não suportado')

print('o cliente encerrou')
s.close()