from socket import *

import Project.Structure.AbsTable as abs_t
import Project.Structure.DNSmessage as dns
import Project.Structure.RRTable as rtt

"""

    This class defines the structure of a client.

"""
class AbstractClient:
    # Constructor:
    def __init__(self, _serverName_="localhost", _serverPort_=15000, _myname_="AbstractClient"):
        self.serverName = _serverName_
        self.serverPort = _serverPort_
        self.myNAME = _myname_
        self.clientSocket = socket(AF_INET, SOCK_DGRAM)
        self.record = rtt.ResourceRecordTable()
        print("\n\t%s is ready.\n" % self.myNAME)

    # Checks if request is in its RRTable.
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
        return self.record.find(host_name, type_.upper())

    # Returns the dns type as int.
    def dns_type_to_int(self, dns_type):
        type_ = ""
        if dns_type == 0 or dns_type.upper() == "A":
            type_ = 0
        elif dns_type == 1 or dns_type.upper() == "AAAA":
            type_ = 1
        elif dns_type == 2 or dns_type.upper() == "CNAME":
            type_ = 2
        else:
            type_ = 3
        return type_

    # Makes a dns message.
    def makeDNSmessage(self, name, dns_type):
        dns_m = dns.DNSmessage()
        dns_m.set_variables_(0,   #query
                           self.dns_type_to_int(dns_type),   # 0,1,2 or 3
                           len(name)*8,      # size name
                           len("none")*8,    # size value
                           name,           # name
                           "none"          # value
        )  # conversion of it to binary is made on constructor.

        return dns_m

    # Displays a message.
    def display(self, str_1, str_2, grid_1, grid_2, sent=True):
        print((grid_1+"\t\t%s "+("sent" if sent else "received")+" a message "+
              ("to" if sent else "from")+" %s"+(" at port %s." if sent else "%s.")+
               "\n\t\t"+
              (("Message's host_name was %s and the DNS type was %s."+grid_2)
                if sent else (grid_1+"Message's value: %s%s"+grid_2))) %
              (self.myNAME, self.serverName,
               (str(self.serverPort) if sent else ""), str_1, str_2))

    # Receives a message.
    def receiver(self):
        raw_dns_message, serverAddress = self.clientSocket.recvfrom(2048)
        dns_message = dns.DNSmessage()
        dns_message.unpack_data(raw_dns_message.decode())
        return dns_message.getValue(), serverAddress

    # Sends a message.
    def sender(self, name, dns_type, serverName, serverPort):
        dns_message = self.makeDNSmessage(name, dns_type)

        return self.clientSocket.sendto(dns_message.concatenate_data().encode(),
                                        (serverName, serverPort))

    # Run function.
    def run(self):
        on, show_line, first_loop = True, True, True
        while on:

            self.record.scheduler()

            if show_line and not first_loop:
                print("\t"+("-"*40)+"\n")

            show_line, first_loop = True, False

            host_name = str(input("\n\tEnter the host name/domain name:\n\t>>  "))
            self.record.scheduler()
            on = self.terminate(host_name)
            show, show_line = self.display_record(host_name)

            if on and show and host_name != "":
                dns_type = str(input("\n\tEnter the type of DNS "+
                                     "query(0. A, 1. AAAA 2.CNAME, 3. NS):\n\t>>  "))
                self.record.scheduler()
                on = self.terminate(dns_type)
                show, show_line = self.display_record(dns_type)

                if on and show and dns_type != "":

                    self.record.scheduler()
                    found, index = self.checkRecords(host_name, dns_type)

                    if not found:
                        self.display(host_name, dns_type, "\n\n", "\n")

                    if found:
                        self.display_record("-d", index)
                        print("\tObs.: Record found in %s's resource record table.\n" % self.myNAME)
                        show_line = False

                    else:

                        self.sender(host_name, dns_type, self.serverName, self.serverPort)
                        self.record.scheduler()
                        message, serverAddress = self.receiver()

                        self.display(message, "", "", "", False)

                        self.record.insert(abs_t.AbsTable(host_name, dns_type, message, 60))
                        self.record.scheduler()

                        print("\n")
                if dns_type == "":
                    print("\n", end='')
            if host_name == "":
                print("\n",end='')
            self.record.scheduler()

    # Terminates the connection.
    def terminate(self, message):
        if (message.lower().strip() == "-c"
                or message.lower().strip() == "-close"):
            self.clientSocket.close()
            print("\n\n\tConnection terminated.\n")
            return False
        return True

    # Displays its RRTable.
    def display_record(self, flag, only_row="-1"):
        if flag == "-d" or flag == "-display":
            self.record.scheduler()
            self.record.display(self.myNAME)
            return False, False
        return True, True

    # Starts the Abstract Client.
    def start(self):
        print("\t%s is running.\n" % self.myNAME)
        print("\tType (-c or -close) to terminate conection.")
        print("\tType (-d or -display) to display records.\n")
        self.run()
