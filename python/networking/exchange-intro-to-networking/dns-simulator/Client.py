#from Project.Client.AbstractClient import AbstractClient as AbstractClient   # PyCharm
#import Project.Structure.AbsTable as abs_t  # PyCharm

from AbstractClient import AbstractClient as AbstractClient # CMD
import AbsTable as abs_t  # CMD


"""

    This class is used to represent a client.

"""
class Client(AbstractClient):
    # Constructor:
    def __init__(self, _serverName_="localhost", _serverPort_=15000, _myname_="Client"):
        self.serverName = _serverName_
        self.serverPort = _serverPort_
        self.myNAME = _myname_
        AbstractClient.__init__(self, self.serverName, self.serverPort, self.myNAME)

    # Starts the client.
    def start(self):
        self.record.insert(abs_t.AbsTable("hedfasfasfay","aaaa", "112.232.122.222",5))
        AbstractClient.start(self)

client = Client()
client.start()
