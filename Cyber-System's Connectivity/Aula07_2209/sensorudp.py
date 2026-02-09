import socket, sys

def defineMonitor(_ip='127.0.0.1',_port=9999, _ID='sala'):
	return _ip, _port, _ID

def interpretaComando(comando, addr):
	global ESTADO
	
	strcomando = str(comando,'utf-8').lower()
	
	print('Recebi o comando', strcomando)
	
	ESTADO = ("ON" if strcomando == "ligar" else  ("OFF" if strcomando=="desligar" else ESTADO))
	if strcomando == 'consulta':
	    s.sendto(bytes('ESTADO '+ ESTADO, 'utf-8'), addr)
	else:
	    print('comando desconhecido: ', comando)


s, ESTADO = socket.socket(socket.AF_INET, socket.SOCK_DGRAM), "OFF"
  
ip, porta, ID = defineMonitor()

print(ip, porta, ID)

s.sendto(bytes('REGISTRO '+ ID, 'utf-8'), (ip, porta))
 
while True:
    data, addr = s.recvfrom(1024)
    interpretaComando(data, (ip,porta))

print('o cliente encerrou')
s.close()