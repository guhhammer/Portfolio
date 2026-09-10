import Pyro5.api
import Pyro5.errors

name = "test"

echo = Pyro5.api.Proxy("PYRONAME:" + name)

try:

	e = echo.say("Hello!")

except Pyro5.errors.CommunicationError:

	print("unavailable.")
