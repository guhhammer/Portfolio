"""

    This class is used to generate unique Ids to dns messages.

"""
class UniqueID:

    # Instance class:
    class _instance_:

        # Constructor:
        def __init__(self):
            self.uniqueID = str(bin(0))[2:].zfill(32)

        # Returns a new generated ID.
        def generateMyID(self):
            self.uniqueID = str( bin(int(self.uniqueID, 2)+1)[2:].zfill(32))
            return self.uniqueID

    # Instance of the generator.
    instance = None

    # Constructor:
    def __init__(self):
        if not UniqueID.instance:
            UniqueID.instance = UniqueID._instance_()

    # Returns an Id.
    def generateMyID(self):
        return UniqueID.instance.generateMyID()


# Executing single-object.
uid_generator = UniqueID()
