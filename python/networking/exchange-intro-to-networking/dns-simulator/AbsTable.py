"""

    This class is used to make it easier to structure a row
    in the Resource Record Table.

"""
class AbsTable:

    # Constructor:
    def __init__(self):
        self.name = ""
        self.type = ""
        self.value = ""
        self.TTL = -2 # -1 -> infinite; -2 -> undefined.
        self.static = 1 # 0 -> dynamic; 1 -> admin defined.
        self.removed = 0 # 0 -> not removed.

    # Constructor:
    def __init__(self, name, type, value, TTL, static=0):
        self.name = str(name)
        self.type = str(type).upper()
        self.value = str(value)
        self.TTL = int(TTL) # -1 -> infinite.
        self.static = int(static) # 0 -> dynamic; 1 -> admin defined.
        self.removed = 0 # 0 -> not removed.

    # Returns its data as a string.
    def display(self):
        return ("%s\t%s\t%s\t%d\t%d" % (self.name,
                                        self.type, self.value,
                                        self.TTL, self.static))

    def getInfo(self):
        return [self.name, self.type, self.value, self.TTL, self.static]

    # Returns its data encoded in a string.
    def self_encode(self):
        spc = "--SPACE--"
        return (self.name +spc+ self.type +spc+
               self.value +spc+ str(self.TTL) +spc+ str(self.static))

    # Returns its data encoded in a string.
    def self_encode(self, ttl):
        spc = "--SPACE--"
        return (self.name +spc+ self.type +spc+
               self.value +spc+ str(ttl) +spc+ str(self.static))

    # Decodes a string and saves the data.
    def self_decode(self, message):
        spc = "--SPACE--"
        values = message.split(spc)
        self.name = str(values[0])
        self.type = str(values[1]).upper()
        self.value = str(values[2])
        self.TTL = int(values[3])
        self.static = int(values[4])

    # Returns the name.
    def getName(self):
        return self.name

    # Returns the type.
    def getType(self):
        return self.type

    # Returns the value.
    def getValue(self):
        return self.value

    # Returns the Time-to-live.
    def getTTL(self):
        return self.TTL

    # Returns if row was removed.
    def getRemoved(self):
        return self.removed
