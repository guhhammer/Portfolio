import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as st

def plotGamPDF(alfa, mu):
    fig, ax = plt.subplots(1, 1)
    x = np.arange(0, st.gamma.ppf(0.999, a=alfa, scale=mu), 1/100)
    ax.plot(x, st.gamma.pdf(x, a=alfa, scale=mu), 'bo', ms=1,
            label='gama pdf \nalfa = {0:.2f} mu = {1: .2f}'.format(alfa, mu))
    ax.legend(loc='best', frameon=False)
    plt.show()

def plotGamCDF(alfa, mu):
    fig, ax = plt.subplots(1, 1)
    x = np.arange(st.gamma.ppf(0.001, a=alfa, scale=mu), st.gamma.ppf(0.999, a=alfa, scale=mu), 1/100)
    ax.plot(x, st.gamma.cdf(x, a=alfa, scale=mu), 'bo', ms=1,
            label='gama cdf \nalfa = {0:.2f} mu = {1: .2f}'.format(alfa, mu))
    ax.legend(loc='best', frameon=False)
    plt.show()


plotGamPDF(0.8, 2)

plotGamPDF(1.5, 2)

plotGamCDF(5, 0.5)

plotGamPDF(50, 1)