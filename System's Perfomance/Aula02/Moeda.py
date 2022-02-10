import numpy as np
import time

def Moeda(n):
    deuCerto = 0
    for i in range(n):
        k = 0
        moeda = 0
        while (moeda != 1):
            k = k+1
            moeda = np.random.randint(0, 2, 1)
        if (k % 2 == 0):
            deuCerto = deuCerto + 1
    return deuCerto/n


t1 = time.perf_counter()
probS = Moeda(10000)
t2 = time.perf_counter()
print('Probabilidade simulada:  {:.4f}'.format(probS))
print('Probabilidade teórica: {:.4f}'.format(1/3))
print('Tempo de simulação: {:.4f}'.format(t2-t1))
