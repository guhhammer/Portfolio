import numpy as np
import time

# Simulacao iterativa
def Dado(n):
    deuCerto = 0
    for i in range(n):
        d1 = np.random.randint(1, 7, 1)
        d2 = np.random.randint(1, 7, 1)
        if ((d1 == 3) & (d2 == 6)) | ((d1 == 6) & (d2 == 3)):
            deuCerto = deuCerto + 1
    return deuCerto/n

t1 = time.perf_counter()
probS = Dado(10000)
t2 = time.perf_counter()
print('Probabilidade simulada:  {:.4f}'.format(probS))
print('Probabilidade teórica: {:.4f}'.format(2/36))
print('Tempo de simulação: {:.4f}'.format(t2-t1))