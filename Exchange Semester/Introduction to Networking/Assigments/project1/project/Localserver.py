from AbstractServer import AbstractServer
import AbsTable as abs_t

"""

    This class represents the local server.

"""
class Localserver(AbstractServer):
    # Constructor:
    def __init__(self, _serverPort_=15000, _myname_="Local",
                 _servers_ = [["qualcomm", "localhost", "21000"],
                              ["viasat", "localhost", "22000"]]):
        self.serverPort = _serverPort_
        self.myNAME = _myname_
        self.servers = _servers_
        AbstractServer.__init__(self, self.serverPort, self.myNAME, self.servers)

    # Starts the local server.
    def start(self):
        self.record.insert(abs_t.AbsTable("www.csusm.edu","A", "144.37.5.45",-1, 1))
        self.record.insert(abs_t.AbsTable("cc.csusm.edu","A", "144.37.5.117",-1, 1))
        self.record.insert(abs_t.AbsTable("cc1.csusm.edu","CNAME", "cc.csusm.edu",-1, 1))
        self.record.insert(abs_t.AbsTable("cc1.csusm.edu","A", "144.37.5.118",-1, 1))
        self.record.insert(abs_t.AbsTable("my.csusm.edu","A", "144.37.5.150",-1, 1))
        self.record.insert(abs_t.AbsTable("qualcomm.com","NS", "dns.qualcomm.com",-1, 1))
        self.record.insert(abs_t.AbsTable("viasat.com","NS", "dns.viasat.com",-1, 1))

        #self.record.display(self.myNAME)

        AbstractServer.start(self)

ls = Localserver()

ls.start()
