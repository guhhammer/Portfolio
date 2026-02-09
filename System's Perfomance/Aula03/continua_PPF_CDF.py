import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as st

def plotUnifPDF(a, b):
    fig, ax = plt.subplots(1, 1)
    x = np.arange(st.uniform.ppf(0.01, a, b), st.uniform.ppf(0.99, a, b), (b)/1000)
    # print(x)
    ax.plot(x, st.uniform.pdf(x, a, b), 'bo', ms=1,
            label='uniforme pdf \nlinicio = {0:.2f} desloc = {1:.2f}'.format(a, b))
    ax.vlines([a, a+b], 0, st.uniform.pdf(x, a, b), colors='k', linestyle='dashed', lw=1, alpha=0.5)
    ax.legend(loc='best', frameon=False)
    plt.show()

def plotUnifCDF(a, b):
    fig, ax = plt.subplots(1, 1)
    x = np.arange(st.uniform.ppf(0.01, a, b), st.uniform.ppf(0.99, a, b), (b)/1000)
    # print(x)
    ax.plot(x, st.uniform.cdf(x, a, b), 'bo', ms=1,
            label='uniforme cdf \nlinicio = {0:.2f} desloc = {1:.2f}'.format(a, b))
    ax.legend(loc='best', frameon=False)
    plt.show()

def plotExpPDF(mu):
    fig, ax = plt.subplots(1, 1)
    x = np.arange(0, st.expon.ppf(0.999, 0, mu), 1/100)
    ax.plot(x, st.expon.pdf(x, 0, mu), 'bo', ms=1, label='exponencial pdf mu = {0:.2f}'.format(mu))
    ax.legend(loc='best', frameon=False)
    plt.show()

def plotExpCDF(mu):
    fig, ax = plt.subplots(1, 1)
    x = np.arange(0, st.expon.ppf(0.999, 0, mu), 1/100)
    ax.plot(x, st.expon.cdf(x, 0, mu), 'bo', ms=1,
            label='exponencial cdf mu = {0:.2f}'.format(mu))
    ax.legend(loc='best', frameon=False)
    plt.show()


def plotNormPDF(mu, sigma):
    fig, ax = plt.subplots(1, 1)
    x = np.arange(st.norm.ppf(0.0001, mu, sigma), st.norm.ppf(0.9999, mu, sigma), 1/100)
    ax.plot(x, st.norm.pdf(x, mu, sigma), 'bo', ms=1,
            label='normal pdf \nmu = {0:.2f} sigma = {1:.2f}'.format(mu, sigma))
    ax.legend(loc='best', frameon=False)
    plt.show()

def plotNormCDF(mu, sigma):
    fig, ax = plt.subplots(1, 1)
    x = np.arange(st.norm.ppf(0.0001, mu, sigma), st.norm.ppf(0.9999, mu, sigma), 1/100)
    ax.plot(x, st.norm.cdf(x, mu, sigma), 'bo', ms=1,
            label='normal cdf \nmu = {0:.2f} sigma = {1:.2f}'.format(mu, sigma))
    ax.legend(loc='best', frameon=False)
    plt.show()

plotUnifPDF(2, 10)

plotUnifCDF(2, 10)

plotExpPDF(5)

plotExpCDF(5)

plotNormPDF(0, 1)

plotNormCDF(0, 1)