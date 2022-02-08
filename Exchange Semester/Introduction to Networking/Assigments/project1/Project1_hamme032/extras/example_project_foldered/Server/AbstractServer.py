from socket import *
#import Project.Structure.RRTable as rtt   # PyCharm
#import Project.Structure.DNSmessage as dns  # PyCharm
#import Project.Structure.AbsTable as abs_t  # PyCharm
import Structure.RRTable as rtt   # CMD
import Structure.DNSmessage as dns # CMD
import Structure.AbsTable as abs_t  # CMD

"""

    This class defines the structure of a server.

"""
class AbstractServer:
    # Constructor.
    def __init__(self, _serverPort_=9999, _myname_="Abstract", _servers_= []):
        self.serverPort = _serverPort_
        self.myNAME = _myname_
        self.serverSocket = socket(AF_INET, SOCK_DGRAM)
        self.serverSocket.bind(('', self.serverPort))
        self.record = rtt.ResourceRecordTable()
        self.servers = _servers_  # <- [ ['name', 'address', 'port'], [...], ... ]
        print("\n\t%s server is ready.\n" % (self.myNAME))

    # Checks its records.
    def checkRecords(self, host_name, dns_type):
        type_ = ""
        if dns_type == 0 or str(dns_type).upper() == "A":
            type_ = "A"
        elif dns_type == 1 or str(dns_type).upper() == "AAAA":
            type_ = "AAAA"
        elif dns_type == 2 or str(dns_type).upper() == "CNAME":
            type_ = "CNAME"
        else:
            type_ = "NS"

        found, index = self.record.find(host_name, type_)

        if found:
            return self.record.get(index).getValue()
        else:
            return "none"

    # Returns dns type as int.
    def dns_type_to_int(self, dns_type):
        type_ = 0
        if dns_type == 0 or str(dns_type).upper() == "A":
            type_ = 0
        elif dns_type == 1 or str(dns_type).upper() == "AAAA":
            type_ = 1
        elif dns_type == 2 or str(dns_type).upper() == "CNAME":
            type_ = 2
        else:
            type_ = 3
        return type_

    # Returns dns type as string.
    def dns_type_to_str(self, dns_type):
        type_ = ""
        if dns_type == 0 or str(dns_type).upper() == "A":
            type_ = "A"
        elif dns_type == 1 or str(dns_type).upper() == "AAAA":
            type_ = "AAAA"
        elif dns_type == 2 or str(dns_type).upper() == "CNAME":
            type_ = "CNAME"
        else:
            type_ = "NS"
        return type_

    # Finds the next server domain.
    def find_next_server_domain(self, host_name):
        for i in host_name.split("."):
            for j in range(0, len(self.servers)):
                if i == self.servers[j][0].lower():
                    return j
        return -1

    # Transforms message into an answer to the request.
    def message_transform(self, transaction_id, host_name, dns_type):

        self.record.scheduler()

        found = self.checkRecords(host_name, dns_type)

        if found == "none":

            index = self.find_next_server_domain(host_name)

            if index != -1:

                clientSocket = socket(AF_INET, SOCK_DGRAM)
                self.send_to_another_server(clientSocket, transaction_id,
                                            host_name, dns_type,
                                            self.servers[index][1], # server address.
                                            int(self.servers[index][2])) # server port.

                message, serverAddress = self.receive_from_another_server(clientSocket)

                if message != "none":

                    true, i = self.record.find(host_name, dns_type)

                    if not true:

                        self.record.insert(abs_t.AbsTable(host_name,
                                                        self.dns_type_to_str(dns_type),
                                                        message, 60))

                    clientSocket.close()
                    return message

            return "none"
        else:
            return found

    # Receives a message.
    def receiver(self):
        raw_dns_message, clientAddress = self.serverSocket.recvfrom(2048)
        dns_message = dns.DNSmessage()
        dns_message.unpack_data(raw_dns_message.decode())

        return (dns_message.getUID(), dns_message.getQR(), dns_message.getName(),
                dns_message.getTypeFlag(), clientAddress)

    # Receives a message from another server.
    def receive_from_another_server(self, clientSocket):
        raw_dns_message, serverAddress = clientSocket.recvfrom(2050)
        dns_message = dns.DNSmessage()
        dns_message.unpack_data(raw_dns_message.decode())

        self.display_as_client(dns_message.getValue(), "", "", "", serverAddress, "", False)
        print("\n")

        return dns_message.getValue(), serverAddress

    # Makes a dns message.
    def makeDNSmessage(self, transaction_id, name, dns_type, value_found):
        dns_m = dns.DNSmessage()
        dns_m.set_variables_all(transaction_id,
                           1,   #response
                           self.dns_type_to_int(dns_type),   # 0,1,2 or 3
                           len(name)*8,      # size name
                           len(value_found)*8,    # size value
                           name,           # name
                           value_found          # value
        )  # conversion of it to binary is made on constructor.
        return dns_m

    # Makes a dns ending message.
    def makeDNSmessage_ender(self, transaction_id, name, dns_type, value_found):
        dns_m = dns.DNSmessage()
        dns_m.set_variables_all(transaction_id,
                           3,   #end of response
                           self.dns_type_to_int(dns_type),   # 0,1,2 or 3
                           len(name)*8,      # size name
                           len(value_found)*8,    # size value
                           name,           # name
                           value_found          # value
        )  # conversion of it to binary is made on constructor.
        return dns_m

    # Sends message to another server.
    def send_to_another_server(self, clientSocket, transaction_id, host_name, dns_type, serverName, serverPort):
        dns_message = self.makeDNSmessage(transaction_id, host_name, dns_type, "none")
        self.display_as_client(host_name, dns_type, "\n\n", "\n", serverName, serverPort)
        return clientSocket.sendto(dns_message.concatenate_data().encode(), (serverName, serverPort))

    # Sends a message.
    def sender(self, transaction_id, host_name, dns_type, value_found, clientAddress):
        dns_message = self.makeDNSmessage(transaction_id, host_name, dns_type, value_found)

        return self.serverSocket.sendto(dns_message.concatenate_data().encode(), clientAddress)

    # Sends multiple messages.
    def sender_line_by_line(self, transaction_id, host_name, dns_type, messages, clientAddress):

        if len(messages) == 0:
            dns_message = self.makeDNSmessage_ender(transaction_id, host_name,
                                                        dns_type, "empty")

            self.serverSocket.sendto(dns_message.concatenate_data().encode(), clientAddress)

        for i in range(0, len(messages)):
            if i == len(messages)-1:
                dns_message = self.makeDNSmessage_ender(transaction_id, host_name,
                                                        dns_type, messages[i])
            else:
                dns_message = self.makeDNSmessage(transaction_id, host_name,
                                                  dns_type, messages[i])

            self.serverSocket.sendto(dns_message.concatenate_data().encode(), clientAddress)

    # Returns its RRTable encoded.
    def return_rrtable(self):
        return self.record.self_encode()

    # Displays a message.
    def display_as_client(self, str_1, str_2, grid_1, grid_2, serverName, serverPort, sent=True):
        print((grid_1+"\t\t%s "+("sent" if sent else "received")+" a message "+
              ("to" if sent else "from")+" %s"+(" at port %s." if sent else "%s.")+
               "\n\t\t"+
              (("Message's host_name was %s and the DNS type was %s."+grid_2)
                if sent else (grid_1+"Message's value: %s%s"+grid_2))) %
              (self.myNAME, serverName,
               (str(serverPort) if sent else ""), str_1, str_2))

    # Displays a message.
    def display(self, str_1, str_2, str_3, grid_1, grid_2, sent=True):
        print((grid_1+"\t\t%s "+("sent" if sent else "received")+" a message "+
              ("to" if sent else "from")+" %s."+"\n\t\t"+
              (("Message's host_name was %s and the DNS type was %s."+grid_2)
                if (not sent) else (grid_1+"Message's value: %s%s"+grid_2))) %
              (self.myNAME, str_3, str_1, str_2))

    # Run function.
    def run(self):
        while True:

            transaction_id, msg_qr, host_name, dns_type, clientAddress = self.receiver()

            print("\t"+("-"*97)+"\n\t"+("-"*97)+"\n", end='')

            self.display(host_name, dns_type, clientAddress,"\n\n", "\n", False)

            self.record.scheduler()

            value = ""
            if msg_qr == 2:
                messages = self.return_rrtable()
                value = "Request Resource Record Table"
                self.sender_line_by_line(transaction_id, host_name, dns_type,
                                         messages, clientAddress)

            elif msg_qr == 0:
                value_found = self.message_transform(transaction_id, host_name, dns_type)
                value = value_found
                self.sender(transaction_id, host_name, dns_type, value_found, clientAddress)
            else:
                value_found = "none"
                value = value_found
                self.sender(transaction_id, host_name, dns_type, value_found, clientAddress)

            self.display(value, "", clientAddress, "", "")

            self.record.display(self.myNAME)

            print("\n\t"+("-"*97)+"\n\t"+("-"*97)+"\n\n", end='')

    # Starts the Abstract Server.
    def start(self):

        print("\n\t%s server is running.\n" % (self.myNAME))
        self.run()
