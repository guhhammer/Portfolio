import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as st

def plotGeoPMF(p):
    # from scipy.stats import geom
    fig, ax = plt.subplots(1, 1)
    x = np.arange(st.geom.ppf(0.01, p), st.geom.ppf(0.999, p))
    ax.plot(x, st.geom.pmf(x, p), 'bo', ms=3, label='geom pdf p = {0:.2f}'.format(p))
    ax.vlines(x, 0, st.geom.pmf(x, p), colors='k', linestyle='dashed', lw=1, alpha=0.5)
    ax.legend(loc='best', frameon=False)
    plt.show()

def plotGeoCDF(p):
    fig, ax = plt.subplots(1, 1)
    x = np.arange(st.geom.ppf(0.01, p), st.geom.ppf(0.999, p))
    ax.plot(x, st.geom.cdf(x, p), 'bo', ms=3, label='geom cdf p = {0:.2f}'.format(p))
    ax.vlines(x, 0, st.geom.cdf(x, p), colors='k', linestyle='dashed', lw=1, alpha=0.5)
    ax.legend(loc='best', frameon=False)
    plt.show()

def plotPoissPMF(mu):
    from scipy.stats import poisson
    fig, ax = plt.subplots(1, 1)
    x = np.arange(st.poisson.ppf(0.01, mu), st.poisson.ppf(0.999, mu))
    ax.plot(x, st.poisson.pmf(x, mu), 'bo', ms=3, label='poisson pdf mu = {0:.2f}'.format(mu))
    ax.vlines(x, 0, st.poisson.pmf(x, mu), colors='k', linestyle='dashed', lw=1, alpha=0.5)
    ax.legend(loc='best', frameon=False)
    plt.show()

def plotPoissCDF(mu):
    fig, ax = plt.subplots(1, 1)
    x = np.arange(st.poisson.ppf(0.01, mu), st.poisson.ppf(0.999, mu))
    ax.plot(x, st.poisson.cdf(x, mu), 'bo', ms=3, label='poisson cdf mu = {0:.2f}'.format(mu))
    ax.vlines(x, 0, st.poisson.cdf(x, mu), colors='k', linestyle='dashed', lw=1, alpha=0.5)
    ax.legend(loc='best', frameon=False)
    plt.show()


plotGeoPMF(0.25)

plotGeoCDF(0.25)

plotPoissPMF(5)

plotPoissCDF(5)
