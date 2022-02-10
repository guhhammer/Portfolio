import numpy as np
import random

# Gerador de numeros aleatorios com distribuicao geometrica
# Cria uma matriz n x m com valores aleatorios
# com distribuição geometrica e parametro 'p'
def rndgeo(p, n):
    out = [0 for x in range(n)]
    for i in range(n):
        j = 1
        cdf = p
        pdf = p
        u = random.random()
        while (cdf<=u):
            pdf = pdf * (1-p)
            cdf = cdf + pdf
            j = j + 1
        out[i] = j
    return out


# Gerador de numeros aleatorios com distribuicao de Poisson
# Cria array com n valores aleatorios (distribuição de Poisson com parametro 'L')
def rndpoiss(L, n):
    out = [0 for x in range(n)]
    for i in range(n):
        # substituir o comando abaixo pelo codigo que gera os valores
        out[i] = 0
    return out



# Gerador de numeros aleatorios com distribuicao normal
# Cria uma matriz n x m com valores aleatorios
# com distribuição noemal com parametros 'mu' e 'sigma'
# Uses Kinderman and Monahan method. Reference: Kinderman,
# A.J. and Monahan, J.F., "Computer generation of random
# variables using the ratio of uniform deviates", ACM Trans
# Math Software, 3, (1977), pp257-260.
def rndnorm(mu, sigma, n):
    NV_MAGICCONST = 4 * np.exp(-0.5)/ np.sqrt(2.0)
    out = [0.0 for x in range(n)]
    for i in range(n):
        while (True):
            u1 = random.random()
            u2 = 1.0 - random.random()
            z = NV_MAGICCONST*(u1-0.5)/u2
            zz = z*z/4.0
            if zz <= -np.log(u2):
                break
        # print(zz)
        out[i] = mu + z*sigma
    return out


# Gerador de numeros aleatorios com distribuicao exponencial
# Cria array com n valores aleatorios
# com distribuição exponencial e parametro 'mu'
def rndexp(mu, n):
    out = [0 for x in range(n)]
    for i in range(n):
        # substituir o comando abaixo pelo codigo que gera os valores
        out[i] = 0
    return out
