import Pyro5.api
import Pyro5.errors

nome = "test"

eco = Pyro5.api.Proxy("PYRONAME:"+nome)

try:

	e = eco.diga("Olá!")

except Pyro5.errors.CommunicationError:

	print("indisponível.")

