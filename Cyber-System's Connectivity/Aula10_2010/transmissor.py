import socket
import sys

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt( socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

s.bind(('127.0.0.1',0))

while True:
    ip = input('Entre com o IP de destino: ')
    porta = int(input('Entre com a porta de destino: '))
    msg = input('Entre com a mensagem: ')

    try:
        s.sendto(msg.encode(), (ip, porta))
    except:
        print('endereço ', ip ,' não suportado')

print('o cliente encerrou')
s.close()
