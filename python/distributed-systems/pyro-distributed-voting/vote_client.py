#!/usr/bin/env python3

# Voting client (Pyro5), built on the course's echo-client template.
# Author: Gustavo Hammerschmidt.

import Pyro5.api, Pyro5.errors, sys

def main():

    if len(sys.argv) < 3:
        print(f"USAGE: {sys.argv[0]} <name> <vote>")
        sys.exit(1)

    name, my_vote = sys.argv[1], sys.argv[2]
    eco = Pyro5.api.Proxy("PYRONAME:" + name)  # gets a reference to the distributed object
    print("ns ok")

    try:  # calls the operations
        
        print("Calling operations...", flush=True)    
        
        print(f"Submitting my vote: {my_vote}.")
        eco.vote( int(my_vote) )
            
    except Pyro5.errors.CommunicationError as e:
        print("crash failure detected")
        print(e)
        
    except Pyro5.errors.NamingError as ne:
        print("name not found in the name server")
        print(ne)


if __name__ == '__main__':
    main()