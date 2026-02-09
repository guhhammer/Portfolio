#!/usr/bin/env python3

# Servidor de Eco Simples
# ASDPC
# Prof. Luiz A. de P. Lima Jr. (luiz.lima@pucpr.br)

# Nome: Gustavo Hammerschmidt.

import Pyro5.api, signal, numpy as np, sys

# Global variables
VE, VR, to, votes, timeout_set, ignore_handler, push_ok = 3, 0, 10, [], False, False, True


def reset_global_vars_next_exec():

	global VE, VR, to, votes, timeout_set, ignore_handler, push_ok
	
	VE, VR, to, votes, timeout_set, ignore_handler, push_ok = 3, 0, 10, [], False, False, True

def alarm_handler(signum, frame):
	
	global push_ok, VE, VR, ignore_handler, votes
	
	res = None
	push_ok = False
	if not ignore_handler:
		if VR >= VE/2:
			
			mostvoted = np.argmax(np.bincount(np.array(votes)))
			if sum([1 for i in votes if mostvoted == i]) >= VE/2:
				
				reset_global_vars_next_exec()
				print(f"O resultado eleito foi {mostvoted}."); res = mostvoted
				
		if res == None: print("Não obteve a maioria dos votos antes do timeout!");

	reset_global_vars_next_exec()
	return res

@Pyro5.api.expose
class Eco(object):
  
    def vote(self, _vote):
    	
    	global votes, push_ok, VR, VE, timeout_set, to, ignore_handler
    	
    	if push_ok: votes.append(_vote); VR += 1
    	
    	if not timeout_set: timeout_set = True; signal.alarm(to)
    	
    	if VR == VE: ignore_handler = True; self.produce_result()
    
    def produce_result(self):
    	
    	global votes, VR, VE
    	
    	if VR >= VE/2:
    		
    		mostvoted = np.argmax(np.bincount(np.array(votes)))
    		
    		if sum([1 for i in votes if mostvoted == i]) >= VE/2:
    			
    			print(f"O resultado eleito foi {mostvoted}."); return mostvoted
    	
    	print("Não obteve a maioria dos votos antes do timeout!"); return
    
def main():

    if len(sys.argv) < 2:
        print(f"USO: {sys.argv[0]} <nome>")
        sys.exit(1)

    nome = sys.argv[1]

    daemon = Pyro5.api.Daemon()     # cria daemon Pyro
    eco = Eco()                     # instancia servant
    uri = daemon.register(eco)      # registra objeto Pyro

    print("uri:", uri)              # publica URI

    ns = Pyro5.api.locate_ns()      # NS
    ns.register(nome, uri)          # publica referência no NS (servidor de nomes)

    print(f'"{nome}" aguardando requisições.')
    daemon.requestLoop()        # inicia loop de espera


if __name__ == '__main__':
    
    signal.signal(signal.SIGALRM, alarm_handler)
    
    main()
