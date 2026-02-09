from scipy.stats import norm
import numpy as np
import matplotlib.pyplot as plt
from gerador_va import rndnorm

mu = 0
sigma = 1

# a = norm.rvs(mu, sigma, size=10000)
a = rndnorm(mu, sigma, 10000)

fig, ax = plt.subplots(1, 1)
x = np.arange(norm.ppf(0.0001, mu, sigma), norm.ppf(0.9999, mu, sigma), 1 / 100)
ax.plot(x, norm.pdf(x, mu, sigma), 'b', ms=1,
        label='normal pdf \nmu = {0:.2f} \nsigma = {1:.2f}'.format(mu, sigma))

ax.hist(a, bins=24*sigma, density=True, histtype='stepfilled', label='dados')
ax.legend(loc='best', frameon=False)