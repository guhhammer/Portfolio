import numpy as np
import time

def DadoV(n):
    d1 = np.random.randint(1,7, n)
    d2 = np.random.randint(1,7, n)
    deuCerto = ((d1 == 3) & (d2 == 6)) | ((d1 == 6) & (d2 == 3))
    return (deuCerto.sum() / n)

t1 = time.perf_counter()
probS = DadoV(10000)
t2 = time.perf_counter()
print('Probabilidade simulada:  {:.4f}'.format(probS))
print('Probabilidade teórica: {:.4f}'.format(2/36))
print('Tempo de simulação: {:.4f}'.format(t2-t1))