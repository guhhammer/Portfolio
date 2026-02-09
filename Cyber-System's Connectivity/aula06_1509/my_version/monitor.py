import socket, sys, threading, time 

class monitor():
	def __init__(self, server_ip, server_port):
		self.CONSOLE, self.console_port, self.SENSORES = None, 8888, {}
		self.ip, self.port = server_ip, server_port
		self.server_socket, self.console_thread = None, None

	def self_set(self):
		self.server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		try:
		    self.server_socket.bind((self.ip, self.port))
		except:
			print("\n Bind error!\n")
			sys.exit()
		self.server_socket.listen(5)

	def info(self):
		print("Server({}) is listening on port {}.\n".format(self.ip, self.port))

	def set_console_thread(self):
		self.console_thread = threading.Thread(target=self.Console)
		self.console_thread.start()

	def run(self):
		while True:
		    conn, addr = self.server_socket.accept()

		    print("\nReceived a connection on: ", addr)

		    t = threading.Thread( target=self.TrataSensor, args=(conn,addr,))
		    t.start()

	def end(self):
		print("\nServer ended connection!\n")
		self.server_socket.close()

	def start(self):
		self.self_set()
		self.info()
		self.set_console_thread()
		self.run()
		#self.end()
	

	###############################
	##############################
	## Auxiliary methods:
	
	def TrataSensor(self, conn, addr):
		     
		print("\nThread {} started.\n".format(addr))

		sensor_id = str(conn.recv(10),'utf-8') # Sensor id.
		
		print(len(sensor_id))
		self.SENSORES[sensor_id] = conn

		print("\nSensor {} registered on socket {}.\n".format(sensor_id, conn))

		while True:
			
			try:
				data = conn.recv(100)
			except:
				break

			if not data:
				break

			print("\nSensor {} on {} sent: \n\t{}.\n".format(sensor_id, addr, data))

			if self.CONSOLE is not None:
				print("CONSOLE IS NOT NONE!")

		conn.close()
		print("\nSensor {} ended connection.\n".format(addr))


	##################################

	def console_set(self, c_port):
		self.console_port = c_port

	def Console(self):
		self.console_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		
		try:
			self.console_socket.bind((self.ip, self.console_port))
		except:
			print("\nBind error!\n")
			sys.exit()

		self.console_socket.listen(1)

		while True:
			conn, addr = self.console_socket.accept()
			print("\nConsole Activated!\n")
			self.CONSOLE = conn

			self.CONSOLE.send(bytes("Digite SENSOR_ID:COMANDO\r\n",'utf-8')) 

			while True:
				try: 
					data = str(self.CONSOLE.recv(200),'utf-8')
				except:
					self.CONSOLE = None
					break

				if not data:
					print("\nConsole ended connection!\n")
					break

				if(len(data) < 4):
					continue
				try:
					(sensor, comando) = data.split(' ', 1)
					if sensor in self.SENSORES:
						self.SENSORES[sensor].send(bytes(comando,'utf-8'))
					else:
						self.CONSOLE.send(bytes("This sensor doesn't exist!\r\n",'utf-8'))

				except:
					self.CONSOLE.send(bytes("Digite SENSOR_ID:COMANDO\r\n",'utf-8'))

if __name__ == "__main__":
	my_monitor = monitor("127.0.0.1", 9999)
	my_monitor.start()