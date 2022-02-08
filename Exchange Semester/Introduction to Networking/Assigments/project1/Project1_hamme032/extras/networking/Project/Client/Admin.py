import Project.Structure.AbsTable as abs_t
import Project.Structure.DNSmessage as dns
import Project.Structure.RRTable as rtt
from Project.Client.AbstractClient import AbstractClient as AbstractClient

"""
    
    This class is used to represent an Administrator.

"""
class Admin(AbstractClient):
    # Constructor:
    def __init__(self, _serverName_='localhost', _serverPort_=15000, _myname_="Administrator"):
        self.serverName = _serverName_
        self.serverPort = _serverPort_
        self.myNAME = _myname_
        AbstractClient.__init__(self, self.serverName, self.serverPort, self.myNAME)

    # Returns if selection is query.
    def selection_is_query(self, selection):
        if selection == "-q" or selection == "-query":
            return True
        return False

    # Returns if selection is request RRTable
    def selection_is_req(self, selection):
        if (selection == "-r"
            or selection == "-req"
            or selection == "-request"):
            return True
        return False

    # Makes a request RRTable dns message.
    def makeDNSmessage_request(self):
        dns_m = dns.DNSmessage()
        dns_m.set_variables_(2,   #request RRTable
                           3,   # 0,1,2 or 3;  3 -> NS or other.
                           len("none")*8,      # size name
                           len("none")*8,    # size value
                           "none",           # name
                           "none"          # value
        )  # conversion of it to binary is made on constructor.

        return dns_m

    # Sends a message to the server.
    def sender(self, serverName, serverPort, requestRRTable=0):

        if requestRRTable == 1:
            dns_message = self.makeDNSmessage_request()
        else:
            dns_message = self.makeDNSmessage()

        return self.clientSocket.sendto(dns_message.concatenate_data().encode(),
                                        (serverName, serverPort))

    # Decodes a message into a AbsTable.
    def makeAbsTable(self, message):
        abs = abs_t.AbsTable("", "", "", 0)
        abs.self_decode(message)
        return abs

    # Receives a RRTable request message.
    def getRRTable_local(self):
        self.sender(self.serverName, self.serverPort, 1)
        my_rtt = rtt.ResourceRecordTable()
        while True:
            message, serverAddress = self.clientSocket.recvfrom(2048)
            dns_message = dns.DNSmessage()
            dns_message.unpack_data(message.decode())

            if dns_message.getValue() != "empty":
                my_rtt.insert(self.makeAbsTable(dns_message.getValue()))

            if dns_message.getQR() == 3:
                del dns_message
                break

        my_rtt.display(self.serverName)
        del my_rtt

    # Run function.
    def run(self):
        on, show_line, first_loop, request_ = True, True, True, False
        while on:

            self.record.scheduler()

            if show_line and not first_loop and not request_:
                print("\t"+("-"*40)+"\n")

            show_line, first_loop, request_ = True, False, False

            selection = str(input("\tFormulate a query(-q or -query) or request RRTable(-r, -req or -request):\n\t>>  "))
            on = self.terminate(selection)
            show, show_line = self.display_record(selection)

            if on and show:

                if self.selection_is_query(selection):

                    host_name = str(input("\n\tEnter the host name/domain name:\n\t>>  "))
                    on = self.terminate(host_name)
                    show, show_line = self.display_record(host_name)

                    if on and show and host_name != "":

                        dns_type = str(input("\n\tEnter the type of DNS query(0. A, 1. AAAA 2.CNAME, 3. NS):\n\t>>  "))
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

                                AbstractClient.sender(self, host_name, dns_type, self.serverName, self.serverPort)

                                message, serverAddress = self.receiver()

                                self.display(message, "\n", "", "", False)

                                self.record.insert(abs_t.AbsTable(host_name, dns_type, message, 60))

                        if dns_type == "":
                            print("\n", end='')
                    if host_name == "":
                        print("\n",end='')

                if self.selection_is_req(selection):

                    self.getRRTable_local()
                    request_ = True

                print("\n", end='')

    # Starts the Admin.
    def start(self):
        print("\t%s is running.\n" % self.myNAME)
        print("\tType (-c or -close) to terminate conection.")
        print("\tType (-d or -display) to display records.\n")
        self.run()

admin = Admin()

admin.start()
