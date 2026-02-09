import datetime

"""
    
    This class is used to make a resource record table.
    
"""
class ResourceRecordTable:
    # Constructor:
    def __init__(self):
        self.isEmpty = True
        self.record = []
        self.current_time = []

    # Inserts Abstables into the RRtable.
    def insert(self, absTable):
        if absTable.getValue() != "none":
            self.isEmpty = False
            self.record.append(absTable)
            self.current_time.append(datetime.datetime.now())

    # Returns size of the RRtable.
    def size(self):
        return len(self.record)

    # Cleans the RRtable from expired AbsTables.
    def cleaner(self, index):
        if len(self.record) == 0:
            self.isEmpty = True
        else:
            self.record[index].removed = 1

    # Checks if Abstables in the RRTable have not expired.
    def scheduler(self):
        if not self.isEmpty:
            for i in range(0, len(self.record)):
                if self.record[i].getTTL() > 0 and self.record[i].getRemoved() == 0:
                    if ((datetime.timedelta(seconds=self.record[i].getTTL())+
                        self.current_time[i]) < (datetime.datetime.now())):
                        self.cleaner(i)

    # Displays the RRtable.
    def display(self, my_name, only_row="-1",tab="\t", nl_1="\n", nl_2="\n"):

        true_ = not self.isEmpty

        _line = (" "+("-"*7) +" "+ ("-"*28) +" "+ ("-"*10) +
                 " "+ ("-"*32) +" "+ ("-"*8) +" "+ ("-"*8)+" ")

        print((nl_1+tab+("-"*(98+len(my_name)) if true_ else ("-"*(30+len(my_name)))) +"\n"+
                 tab+my_name+"'s Resource Record Table:\n\n"+("\n" if true_ else "")
               +tab+"\t"+(_line if true_ else "")),end='')

        if true_:

            print("\n"+tab+"\t"+
                    ("| %5s | %26s | %8s | %30s | %6s | %6s |" %
                    ("index", "name", "DNS type", "value", "TTL", "static"))
                    +"\n"+tab+"\t"+_line)

            counter = 0
            for i in range(0, len(self.record)):
                if self.record[i].getRemoved() == 0 and (only_row=="-1" or only_row==str(counter)):
                    aux = self.record[i].getInfo()
                    last_line = "" if (i == len(self.record)-1) else "\n"
                    _ttl_ = (int(aux[3]) - int(str(datetime.datetime.now()-self.current_time[i]).split(":")[2].split(".")[0]))
                    print(tab+"\t"+ ("| %5s | %26s | %8s | %30s | %6s | %6s |"
                         % (str(counter), aux[0], aux[1],
                            aux[2], ( _ttl_ if int(aux[3]) != -1 else str(aux[3])), str(aux[4])))
                            +"\n"+ (tab+"\t"+_line), end=last_line)
                    counter += 1
            print("\n"+tab+"\t"+"Obs.: the TTL -1 value means infinite time.")

        else:
            print("\n"+tab+"\tResource Record Table is Empty!\n")

        print((nl_1+tab+(("-"*(98+len(my_name))) if true_ else ("-"*(30+len(my_name))))+nl_2), end='')

    # Finds a record in the table.
    def find(self, name, type):
        for i in range(0, len(self.record)):
            if (self.record[i].getName() == str(name)
                and self.record[i].getType() == str(type)
                and self.record[i].getRemoved() == 0):
                    return True, i
        return False, -1

    # Gets value in the RRtable at index.
    def get(self, index):
        return self.record[index]

    # Encodes its records and returns itself in an array.
    def self_encode(self):
        aux = []
        for i in range(0, len(self.record)):
            if self.record[i].getRemoved() == 0:
                _ttl_ = ( self.record[i].getTTL() - int(str(datetime.datetime.now()-self.current_time[i]).split(":")[2].split(".")[0]))
                aux.append(self.record[i].self_encode(_ttl_ if self.record[i].getTTL() != -1 else -1))
        return aux

    # Fills itself with AbsTables.
    def fulfill(self, AbsTables):
        for i in AbsTables:
            self.record.append(i)
