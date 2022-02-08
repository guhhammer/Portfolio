import socket
import struct

GROUP = '224.1.1.1'
HOST='127.0.0.1'

HOST = input('IP do host: ')
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

s.bind((HOST,9999))
s.setsockopt( socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, socket.inet_aton(GROUP)+socket.inet_aton(HOST))

print('aguardando mensagens')
while True:
    data, addr = s.recvfrom(1024)
    print(addr, ' enviou ', data)
