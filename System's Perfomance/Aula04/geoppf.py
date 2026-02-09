from scipy.stats import geom
import numpy as np
import matplotlib.pyplot as plt
from gerador_va import rndgeo

p = 0.5
# a = geom.rvs(p, size=10000)
a = rndgeo(p, 10000)
x = np.unique(a)

fig, ax = plt.subplots(1, 1)
ax.plot(x, geom.pmf(x, p), 'bo', ms=5, label='geom pmf')
ax.vlines(x, 0, geom.pmf(x, p), colors='k', linestyles='-', lw=1, alpha=0.5)
ax.hist(a, bins=x.max(), density=True, histtype='stepfilled',
        label='dados', align='left', alpha=0.2)
ax.legend(loc='best', frameon=False)