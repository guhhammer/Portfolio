import numpy as np
import scipy.stats as st


def pExpMenor(MU1, MU2, nSim):
    exp1 = st.expon.rvs(scale=MU1, size=nSim)
    #print(exp1)
    exp2 = st.expon.rvs(scale=MU2, size=nSim)
    #print(exp2)
    menor = exp1 < exp2
    #print(menor)
    return np.count_nonzero(menor) / nSim


def somaExpCDFI(x, N, MU, nSim):
    deuCerto = 0
    for i in range(nSim):
        EXP = st.expon.rvs(scale=MU, size=N)
        soma = sum(EXP)
        if soma <= x:
            deuCerto = deuCerto + 1
    return deuCerto/nSim


def somaExpCDFV(x, N, MU, nSim):
    # sorteia matrz EXPEXP = np.random.exponential(MU, [nSim, N])
    # soma as linhas
    # quais linhas tem soma menor do que x
    return 0


