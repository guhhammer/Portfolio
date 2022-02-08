import socket, sys, time

ESTADO, IP_MONITOR, PORTA_MONITOR = 'OFF', '127.0.0.1', 9999

ID = input('Entre com o nome do sensor: ')

def interpretaComando(comando, addr):
    global ESTADO
    strcomando = str(comando,'utf-8').lower()
    print('Recebi o comando', strcomando)
    if strcomando == 'ligar':
        ESTADO = 'ON'
    elif strcomando == 'desligar':
        ESTADO = 'OFF'
    elif strcomando == 'consulta':
        s.sendto(bytes('ESTADO '+ ESTADO, 'utf-8'), addr)
    else:
        print('comando desconhecido')

def registraSensor(s,ip,porta):
	s.sendto(bytes('REGISTRO '+ ID, 'utf-8'), (ip, porta))
	s.setblocking(0)
	time.sleep(5)
	try:
	    data, addr = s.recvfrom(1024)
	    strdata = str(data,'utf-8')
	    if strdata == 'ACKregistro':
	        print('registrado no monitor ', addr)
	        return True
	    else:
	        return False
	except:
	    print('\no monitor está desligado')
	    return False

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

while True:
    if registraSensor(s, IP_MONITOR, PORTA_MONITOR):
        break

s.setblocking(1)

while True:
    data, addr = s.recvfrom(1024)
    interpretaComando(data, addr)

print('o sensor encerrou')
s.close()