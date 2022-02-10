#!/usr/bin/python

# Arquivo contém a função dadosServidor utilizada
# para gerar aleatoriamente um dataset com medidas de
# proporção de utilização de CUP e de memória para um servidor.
# Também gera os rótulos correspondentes a cada par de medidas.

import random


def dadosServidor(n_pontos=1000):
    ###############################################################################
    ### constroi o dataset
    ### quantidade de pontos = n_pontos

    ### sorteia as proporcoes de utilizacao de cpu e de memoria
    random.seed(42)
    prop_util_cpu = [random.random() for ii in range(0, n_pontos)]
    prop_util_memoria = [random.random() for ii in range(0, n_pontos)]

    ### sorteia uma ocilacao (erro) para gerar os valores de y (rotulos)
    ### depois desse passo, y tera valores iguais a 0 ou 1
    ### dependendo das proporções de utilizacao da cpu, da memoria, e da oscilacao
    erro = [random.random() for ii in range(0, n_pontos)]
    y = [round(prop_util_cpu[ii] * prop_util_memoria[ii] + 0.3 + 0.1 * erro[ii]) for ii in range(0, n_pontos)]

    ### para casos mais criticos forca y = 1
    for ii in range(0, len(y)):
        if prop_util_cpu[ii] > 0.9 or prop_util_memoria[ii] > 0.9:
            y[ii] = 1.0

    ### divide nos conjuntos train e test
    ### 75% vai para treinamento e 25% vai para teste
    X = [[gg, ss] for gg, ss in zip(prop_util_cpu, prop_util_memoria)]
    split = int(0.75 * n_pontos)
    X_trein = X[0:split]
    X_test = X[split:]
    Y_trein = y[0:split]
    Y_test = y[split:]

    return X_trein, Y_trein, X_test, Y_test