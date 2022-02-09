import hashlib
import time
import itertools
import string
from Funções_IT01 import pegar_hash

## NOME: GUSTAVO HAMMERSCHMIDT

data2 = [line.strip() for line in open("hash_Banco_Dados2BF.txt", 'r')]

##################
## Função Uncrack:


def uncrack(parameter):
    return hashlib.md5(hashlib.md5(hashlib.md5(parameter.encode()).hexdigest().encode()).hexdigest().encode()).hexdigest()


## Função Uncrack:
##################


##################
## Função Crack:


def crack2(target, size=1):
    for xs in itertools.product(string.printable, repeat=size):
        pw = ''.join(xs)
        if uncrack(pw) == target:
            return pw
    return crack2(target, size+1)


## Função Crack:
##################


##################
## Função Execute:


def executeCrack2():
    temptotal = []
    for line in data2:
        t0 = time.clock();
        print("Senha(Quebra do Hash):  ", crack2(line))
        print("Tempo de execução: ", time.clock() - t0)
        tempinho = time.clock() - t0
        temptotal.append(tempinho)

    print("Tempo para executar a quebra de todas as senhas "
          "em segundos(s): ", sum(temptotal))


## Função Execute:
##################
