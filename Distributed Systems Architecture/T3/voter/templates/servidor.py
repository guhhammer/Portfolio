import Pyro5.api

@Pyro5.api.expose
class Eco(object):

	def __init__(self):
		self.cont = 0

	def diga(self, oque):
		self.cont += 1
		print(f'diga ("{oque}")')
		return oque + "_ECO"


nome = "test"

daemon = Pyro5.api.Daemon()
eco = Eco()
uri = daemon.register(eco)
ns = Pyro5.api.locate_ns()
ns.register(nome, uri)
daemon.requestLoop()
