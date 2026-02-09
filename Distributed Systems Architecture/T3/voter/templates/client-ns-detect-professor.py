#!/usr/bin/env python3

# Servidor de Eco Simples
# ASDPC
# Prof. Luiz A. de P. Lima Jr. (luiz.lima@pucpr.br)

import Pyro5.api
import Pyro5.errors
from sys import argv


def main():

    if len(argv) < 2:
        print(f"USO: {argv[0]} <nome>")
        exit(1)

    nome = argv[1]
    eco = Pyro5.api.Proxy("PYRONAME:" + nome)  # obtém referência para obj. distr.
    print("ns ok")

    try:
        # chama operações

        print("Chamando operações...", flush=True)
        print("Resposta =", eco.diga("OLÁ"))
        print("Contador =", eco.contador())
    except Pyro5.errors.CommunicationError as e:
        print("crash failure detected")
        print(e)
    except Pyro5.errors.NamingError as ne:
        print("nome não encontrado no NS")
        print(ne)


if __name__ == '__main__':
   
    main()