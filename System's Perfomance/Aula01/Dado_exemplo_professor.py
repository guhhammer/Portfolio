import numpy as np

def Dado(n):
    deuCerto = 0
    for i in range(n):
        # Substituir os comandos abaixo para realizar o sorteio dos dois dados
        d1 = 0  # sortear o primeiro dado
        d2 = 0  # sortear o segundo dado
        if (False):     # testar se saiu 3 e 6 ou 6 e3
            deuCerto = deuCerto + 1
    return deuCerto/n


probS = Dado(10000)
print('Probabilidade simulada:  {:.4f}'.format(probS))
print('Probabilidade teórica: {:.4f}'.format(2/36))