import socket, sys


class Sensor:
	def __init__(self, my_id, server_ip, sport):
		self.my_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		self.id = str(my_id)
		self.server_ip = str(server_ip)
		self.server_port = int(sport)

	def get_id(self):
		return self.id

	def run(self):
		try:
			self.my_socket.connect((self.server_ip, self.server_port))
		except OSError:
			print('Connection error!')

		self.introduce()

		# Establish communication here

	def introduce(self):
		self.my_socket.send(bytes(self.get_id(), "utf-8"))

	def transmission(self):
		"""Draft of the send loop (not finished in class):

		while True:
		    try:
		        line = input()
		        if not line:
		            print('an empty line ends the program')
		            break
		    except KeyboardInterrupt:
		        print('program aborted with CTRL+C')
		    data = bytes(line, 'utf-8')
		    size = self.my_socket.send(data)
		    print('sent', size, 'bytes')
		"""
		return ""
