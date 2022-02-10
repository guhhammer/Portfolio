from scipy.stats import poisson
import numpy as np
import matplotlib.pyplot as plt
from gerador_va_resposta import rndpoiss

L = 4
a = poisson.rvs(L, size=10000)
############################################################
# para testar a sua função, comentar o comando anterior e  #
# retirar o comentario do proximo comando                  #
############################################################
# a = rndpoiss(L, 10000)

x = np.unique(a)

fig, ax = plt.subplots(1, 1)
ax.plot(x, poisson.pmf(x, L), 'bo', ms=5, label='Poisson pmf')
ax.vlines(x, 0, poisson.pmf(x, L), colors='k', linestyles='-', lw=1, alpha=0.5)
ax.hist(a, bins=x.max(), density=True, histtype='stepfilled',
        label='dados', align='left', alpha=0.2)
ax.legend(loc='best', frameon=False)