from Project.Structure.UniqueID import uid_generator as uid_generator

"""

    This class is defines a DNS message structure and
    is used to communicate between sockets.

"""
class DNSmessage:
    # Constructor:
    def __init__(self):
        self.transactionID = "" #uid_generator.generateMyID()  # 32
        self.QR = "" #str(bin(query_response)[2:].zfill(4))
        self.typeFlag = "" #str(bin(type_flag)[2:].zfill(4))
        self.nameLength = "" #str(bin(name_length)[2:].zfill(32))
        self.valueLength = "" #str(bin(value_length)[2:].zfill(32))
        self.name = "" #''.join(format(ord(i), 'b') for i in str(_name_))
        self.value = "" #''.join(format(ord(i), 'b') for i in str(_value_))

    # Sets variables except the transaction ID.
    def set_variables_(self, query_response, type_flag, name_length, value_length, _name_, _value_):
        self.transactionID = uid_generator.generateMyID()  # 32
        self.QR = str(bin(query_response)[2:].zfill(4)) # 0->query, 1->response, 2->rrt, 3->ender.
        self.typeFlag = str(bin(type_flag)[2:].zfill(4))
        self.nameLength = str(bin(name_length)[2:].zfill(32))
        self.valueLength = str(bin(value_length)[2:].zfill(32))
        self.name = ''.join([ (bin(ord(i))[2:].zfill(8)) for i in str(_name_)])
        self.value = ''.join([ (bin(ord(i))[2:].zfill(8)) for i in str(_value_)])

    # Sets variables.
    def set_variables_all(self, uid, query_response, type_flag, name_length, value_length, _name_, _value_):
        self.transactionID = uid  # 32
        self.QR = str(bin(query_response)[2:].zfill(4))
        self.typeFlag = str(bin(type_flag)[2:].zfill(4))
        self.nameLength = str(bin(name_length)[2:].zfill(32))
        self.valueLength = str(bin(value_length)[2:].zfill(32))
        self.name = ''.join([ (bin(ord(i))[2:].zfill(8)) for i in str(_name_)])
        self.value = ''.join([ (bin(ord(i))[2:].zfill(8)) for i in str(_value_)])

    # Concatenates its data.
    def concatenate_data(self):
        return (self.transactionID + self.QR +
                self.typeFlag + self.nameLength +
                self.valueLength + self.name + self.value)

    # Unpacks the data from a message.
    def unpack_data(self, message):
        self.transactionID = message[0:32]
        self.QR = message[32:36]
        self.typeFlag = message[36:40]
        self.nameLength = message[40:72]
        self.valueLength = message[72:104]

        size_name = int(self.nameLength, 2)
        size_value = int(self.valueLength, 2)

        self.name = message[104:(104+size_name)]
        self.value = message[(104+size_name):(104+size_name+size_value)]

    # Transforms bytes into a string.
    def bytes_to_string(self, message):
        aux, char_  = "",  []
        for i in range(0, len(message)):
            if i % 8 == 7:
                char_.append(chr(int((aux+message[i]), 2)))
                aux = ""
            else:
                aux += message[i]
        return ''.join(char_)

    # Returns the UID.
    def getUID(self):
        return self.transactionID

    # Returns the Name.
    def getName(self):
        return self.bytes_to_string(self.name)

    # Returns the type flag.
    def getTypeFlag(self):
        return int(self.typeFlag, 2)

    # Returns the value.
    def getValue(self):
        return self.bytes_to_string(self.value)

    # Returns the qr.
    def getQR(self):
        return int(self.QR, 2)

    # Displays itself.
    def display(self):
        print(self.transactionID, self.QR,
              self.typeFlag, self.nameLength,
              self.valueLength, self.name, self.value)

    # Returns its data.
    def getInfo(self):
        return (self.transactionID, self.QR,
              self.typeFlag, self.nameLength,
              self.valueLength, self.name, self.value)
