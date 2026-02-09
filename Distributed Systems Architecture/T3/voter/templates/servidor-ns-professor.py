#!/usr/bin/env python3

# Servidor de Eco Simples
# ASDPC
# Prof. Luiz A. de P. Lima Jr. (luiz.lima@pucpr.br)

import Pyro5.api
from sys import argv


@Pyro5.api.expose
class Eco(object):
    def __init__(self):
        self.cont = 0

    def diga(self, oque):
        self.cont += 1
        print(f'diga ("{oque}")')
        return oque + "_ECO"

    def contador(self):
        return self.cont


def main():

    if len(argv) < 2:
        print(f"USO: {argv[0]} <nome>")
        exit(1)

    nome = argv[1]

    daemon = Pyro5.api.Daemon()     # cria daemon Pyro
    eco = Eco()                     # instancia servant
    uri = daemon.register(eco)      # registra objeto Pyro

    print("uri:", uri)              # publica URI

    ns = Pyro5.api.locate_ns()      # NS
    ns.register(nome, uri)          # publica referência no NS (servidor de nomes)

    print(f'"{nome}" aguardando requisições.')
    daemon.requestLoop()        # inicia loop de espera


if __name__ == '__main__':
    
    main()
