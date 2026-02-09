import numpy as np
import time

def aniverT(tGrupo):
    x = np.arange(365, 365 - tGrupo, -1, dtype = float)
    return(1 - np.prod(x)/(365**tGrupo))


def aniverS(tGrupo, nSim):
    deuCerto = 0
    for i in range(nSim):
        # sorteia grupo com tGrupo pessoas
        grupo = np.random.randint(1, 366, tGrupo)
        # se duas ou mais pessoa fazem aniver na mesma data
        if grupo.size > np.unique(grupo).size:
            deuCerto = deuCerto + 1

    return(deuCerto/nSim)

probT = aniverT(40)
t1 = time.perf_counter()
probS = aniverS(40, 10000)
t2 = time.perf_counter()
print('Probabilidade simulada:  {:.4f}'.format(probS))
print('Probabilidade teórica: {:.4f}'.format(probT))
print('Tempo de simulação iterativa: {:.4f}'.format(t2-t1))


def aniverV(tGrupo, nSim):
    # sorteia nSim grupos de tamanho tGrupo
    grupos =
    # ordena
    ordena = 
    # calcula a diferebca entre as datas adjacentes
    diferenca =
    # se diferenca for zero tem aniver na mesma data
    # mesmaData e um array com valor verdadeiro paa cada grupo que tive aniver no mesmo dia
    mesmaData = np.any(diferenca == 0, 1)
    return


probT = aniverT(40)
t1 = time.perf_counter()
probS = aniverV(40, 10000)
t2 = time.perf_counter()
print('\n-------------------------------------------')
print('Probabilidade simulada:  {:.4f}'.format(probS))
print('Probabilidade teórica: {:.4f}'.format(probT))
print('Tempo de simulação vetorial: {:.4f}'.format(t2-t1))