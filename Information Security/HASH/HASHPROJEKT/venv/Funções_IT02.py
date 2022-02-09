import hashlib
import time
import itertools
import string
from Funções_IT01 import pegar_hash



## NOME: GUSTAVO HAMMERSCHMIDT

data = [line.strip() for line in open("hash_Banco_Dados.txt", 'r')]

#################
## Função Crack:


def crack(target, size=1):
    for xs in itertools.product(string.printable, repeat=size):
        pw = ''.join(xs)
        if hashlib.md5(pw.encode()).hexdigest() == target:
            return pw
    return crack(target, size+1)


## Função Crack:
#################


#################
## Função Execute:


def executeCrack():
    temptotal = []
    for line in data:
        t0 = time.clock();
        print("Senha(Quebra do Hash):  ", crack(line))
        print("Tempo de execução: ", time.clock() - t0)
        tempinho = time.clock() - t0
        temptotal.append(tempinho)

    print("Tempo para executar a quebra de todas as senhas "
          "em segundos(s): ", sum(temptotal))


## Função Execute:
#################