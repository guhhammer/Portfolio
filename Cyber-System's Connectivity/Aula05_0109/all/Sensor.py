import socket, sys


class Sensor:
	def __init__(self, my_id, server_ip, sport):
		self.my_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		self.id = str(my_id)
		self.server_ip = str(server_ip)
		self.server_port = int(sport)

	def getID(self):
		return self.id

	def run(self):
		try:
    		self.my_socket.connect((self.server_ip, self.server_port))
		except:
    		print('Erro de conexão!')

    	self.introduce()

    	# Establish communication here


    def introduce(self):
    	self.my_socket.send(bytes(self.getID(), "utf-8"))


    def transmition(self):
		"""	while True:
			    try:
			        line = input()
			        if not line:
			            print('linha vazia encerra o programa')
			            break
			    except:
			            print('programa abortado com CTRL+C')
			    data = bytes(line, 'utf-8')
			    tam = s.send(data)
			           
			    print('enviei ',tam, 'bytes')
			    print(data)		
		"""
		return ""