#from Project.Server.AbstractServer import AbstractServer  # PyCharm
#import Project.Structure.DNSmessage as dns   # PyCharm
#import Project.Structure.AbsTable as abs_t   # PyCharm

from AbstractServer import AbstractServer # CMD
import Structure.DNSmessage as dns  # CMD
import Structure.AbsTable as abs_t # CMD


"""

    This class represents a Qualcomm server.

"""
class Qualcommserver(AbstractServer):
    # Constructor:
    def __init__(self, _serverPort_=21000, _myname_="Qualcomm", _servers_=[]):
        self.serverPort = _serverPort_
        self.myNAME = _myname_
        self.servers = _servers_
        AbstractServer.__init__(self, self.serverPort, self.myNAME, self.servers)

    # Receives a message.
    def receiver(self):
        raw_dns_message, clientAddress = self.serverSocket.recvfrom(2050)
        dns_message = dns.DNSmessage()
        dns_message.unpack_data(raw_dns_message.decode())

        return dns_message.getUID(), dns_message.getName(), dns_message.getTypeFlag(), clientAddress

    # Transforms a message.
    def message_transform(self, host_name, dns_type):
        return self.checkRecords(host_name, dns_type)

    # Sends a message.
    def sender(self, transaction_id, host_name, dns_type, value_found, clientAddress):
        dns_message = self.makeDNSmessage(transaction_id, host_name, dns_type, value_found)

        return self.serverSocket.sendto(dns_message.concatenate_data().encode(), clientAddress)

    # Run function.
    def run(self):
        while True:

            transaction_id, host_name, dns_type, clientAddress = self.receiver()

            print("\t"+("-"*97)+"\n\t"+("-"*97)+"\n", end='')

            self.display(host_name, dns_type, clientAddress,"\n\n", "\n", False)

            value_found = self.message_transform(host_name, dns_type)

            self.sender(transaction_id, host_name, dns_type, value_found, clientAddress)

            self.display(value_found, "", clientAddress, "", "")

            self.record.display(self.myNAME)

            print("\n\t"+("-"*97)+"\n\t"+("-"*97)+"\n\n", end='')

    # Starts the Qualcomm Server.
    def start(self):
        self.record.insert(abs_t.AbsTable("www.qualcomm.com","A", "104.86.224.205",-1, 1))
        self.record.insert(abs_t.AbsTable("qtiack12.qti.qualcomm.com","A", "129.46.100.21",-1, 1))

        #self.record.display(self.myNAME)

        print("\n\t%s server is running.\n" % (self.myNAME))
        self.run()

qualcomm = Qualcommserver()

qualcomm.start()
