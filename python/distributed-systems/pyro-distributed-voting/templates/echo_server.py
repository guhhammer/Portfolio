import Pyro5.api

@Pyro5.api.expose
class Echo(object):

	def __init__(self):
		self.count = 0

	def say(self, what):
		self.count += 1
		print(f'say ("{what}")')
		return what + "_ECHO"


name = "test"

daemon = Pyro5.api.Daemon()
echo = Echo()
uri = daemon.register(echo)
ns = Pyro5.api.locate_ns()
ns.register(name, uri)
daemon.requestLoop()
