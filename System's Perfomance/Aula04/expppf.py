from scipy.stats import expon
import numpy as np
import matplotlib.pyplot as plt
from gerador_va import rndexp

mu = 4
sigma = mu

a = expon.rvs(0, mu, size=10000)
############################################################
# para testar a sua função, comentar o comando anterior e  #
# retirar o comentario do proximo comando                  #
############################################################
# a = rndexp(mu, 100000)

fig, ax = plt.subplots(1, 1)
x = np.arange(0, expon.ppf(0.9999, 0, mu), 1 / 100)
ax.plot(x, expon.pdf(x, 0, mu), 'b', ms=1,
        label='exponecial pdf \nmu = {0:.2f}'.format(mu))

ax.hist(a, bins=12*sigma, density=True, histtype='stepfilled', label='dados')
ax.legend(loc='best', frameon=False)

plt.show()