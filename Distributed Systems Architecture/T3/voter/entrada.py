#!/usr/bin/env python3

# Servidor de Eco Simples
# ASDPC
# Prof. Luiz A. de P. Lima Jr. (luiz.lima@pucpr.br)

# Nome: Gustavo Hammerschmidt.

import Pyro5.api, Pyro5.errors, sys

def main():

    if len(sys.argv) < 3:
        print(f"USO: {sys.argv[0]} <nome> <vote>")
        sys.exit(1)

    nome, my_vote = sys.argv[1], sys.argv[2]
    eco = Pyro5.api.Proxy("PYRONAME:" + nome)  # obtém referência para obj. distr.
    print("ns ok")

    try:  # chama operações
        
        print("Chamando operações...", flush=True)    
        
        print(f"Estou submetendo meu voto: {my_vote}.")
        eco.vote( int(my_vote) )
            
    except Pyro5.errors.CommunicationError as e:
        print("crash failure detected")
        print(e)
        
    except Pyro5.errors.NamingError as ne:
        print("nome não encontrado no NS")
        print(ne)


if __name__ == '__main__':
    main()