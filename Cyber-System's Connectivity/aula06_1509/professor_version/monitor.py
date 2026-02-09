import socket, sys, threading

def TrataSensor(conn, addr):

	global CONSOLE
	     
	print('A thread', addr, 'iniciou')

	# O sensor deve enviar seu ID apos a conexao
	sensor = str(conn.recv(10),'utf-8')
	
	print(len(sensor))
	SENSORES[sensor] = conn

	print('sensor ', sensor, ' registrado no socket', conn)

	while True:
		
		try:
			data = conn.recv(100)
		except:
			break

		if not data:
			break
		print('sensor ', sensor, addr, 'enviou ', data)

		if CONSOLE is not None:
			print('esqueci de fazer o exercicio 2D')

	conn.close()
	print('O sensor', addr, 'encerrou')


####################################
####################################


def Console():

	global CONSOLE

	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	try:
		s.bind(('', 8888))
	except:
		print('# erro de bind')
		sys.exit()

	s.listen(1)

	while True:
		conn, addr = s.accept()
		print('console ativado ')
		CONSOLE = conn

		CONSOLE.send(bytes('Digite SENSOR_ID:COMANDO\r\n','utf-8')) 
		while True:

			try: 
				data = str(CONSOLE.recv(200),'utf-8')
			except:
				CONSOLE = None
				break

			if not data:
				print('Console encerrou')
				break
			if(len(data) < 4):
				continue
			try:
				(sensor, comando) = data.split(' ', 1)
				if sensor in SENSORES:
					SENSORES[sensor].send(bytes(comando,'utf-8'))
				else:
					CONSOLE.send(bytes('Esse sensor nao existe\r\n','utf-8'))

			except:
				#print('esqueci de fazer o exercicio 2C')
				CONSOLE.send(bytes('Digite SENSOR_ID:COMANDO\r\n','utf-8')) 
	
		


#####################################
#####################################

tconsole = threading.Thread(target=Console)
tconsole.start()

# GLOBAL VARIABLES:
CONSOLE, SENSORES, porta = None, {}, 9999

#print('esqueci de fazer o exercicio 2A')
#print('esqueci de fazer o exercicio 2B')


if __name__ == "__main__":

	server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	
	try:
	    server_socket.bind(('', porta))
	except:
	   print('# erro de bind')
	   sys.exit()

	
	server_socket.listen(5)
	 
	print("Server is listening on port {}.\n".format(porta))

	while True:
	    conn, addr = server_socket.accept()
	    print('recebi uma conexao de ', addr)

	    t = threading.Thread( target=TrataSensor, args=(conn,addr,))
	    t.start()

	print('o servidor encerrou')
	server_socket.close()
