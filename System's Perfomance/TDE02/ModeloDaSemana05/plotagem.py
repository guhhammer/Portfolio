#!/usr/bin/python

# Contém as funções figuraTreinamento e figuraTeste

import warnings

warnings.filterwarnings("ignore")

import matplotlib

# matplotlib.use('agg')

import matplotlib.pyplot as plt
import pylab as pl
import numpy as np


# Funcao figuraTreinamento
# Argumentos
#      Atributos de teste (X_trein)
#      Rotulos de teste (Y_trein)
# Grava figura "treinamento.png"
#      Scatter plot dos pontos do conjuto de treinamento
#
def figuraTreinamento(X_trein, y_trein):
    ### Prepara a plotagem dos pontos de treinamento
    ### os dados de treinamento (X_trein, y_trein) possuem pontos "rapido" e "devagar" misturados
    ### separar para dar cores diferentes no scatterplot
    cpu_normal = [X_trein[ii][0] for ii in range(0, len(X_trein)) if y_trein[ii] == 0]
    memoria_normal = [X_trein[ii][1] for ii in range(0, len(X_trein)) if y_trein[ii] == 0]
    cpu_alta = [X_trein[ii][0] for ii in range(0, len(X_trein)) if y_trein[ii] == 1]
    memoria_alta = [X_trein[ii][1] for ii in range(0, len(X_trein)) if y_trein[ii] == 1]

    # Limites dos eixos
    plt.xlim(0.0, 1.0)
    plt.ylim(0.0, 1.0)

    # Scatter plot
    plt.scatter(cpu_normal, memoria_normal, color="b", label="carga normal")
    plt.scatter(cpu_alta, memoria_alta, color="r", label="carga alta")

    # Legenda
    plt.legend()
    plt.xlabel("utilização de cpu")
    plt.ylabel("utilização de memória")

    # Salva figura
    plt.savefig("treinamento.png")
    plt.close()


def figuraTeste(clf, X_test, y_test):
    x_min = 0.0;
    x_max = 1.0
    y_min = 0.0;
    y_max = 1.0

    #####################################################################
    # Esse trecho do codigo plota figura com o limite de decisao
    ######################################################################

    # Construir uma grade de pontos com os eixos x e y com entre 0.0 e 1.0
    # O passo da grade é h = 0.01
    h = .01  # tamanho do passo na construcao do mesh
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h), np.arange(y_min, y_max, h))

    ## Fazer a previsão para cada ponto e salvar no array Z
    Z = clf.predict(np.c_[xx.ravel(), yy.ravel()])

    ## O valor da previsão em Z determina a cor de cada ponto (argumanto cmap)
    Z = Z.reshape(xx.shape)
    plt.xlim(xx.min(), xx.max())
    plt.ylim(yy.min(), yy.max())
    plt.pcolormesh(xx, yy, Z, cmap=pl.cm.seismic)

    ###############################################################
    # Essa parte do codigo prepara a plotagem dos pontos de teste
    ###############################################################

    ### dados de teste (X_test, y_test) possuem pontos "carga normal" e "carga alta" misturados
    ### separar para dar cores diferentes no scatterplot
    ### assim podem ser identificados
    cpu_normal = [X_test[ii][0] for ii in range(0, len(X_test)) if y_test[ii] == 0]
    memoria_normal = [X_test[ii][1] for ii in range(0, len(X_test)) if y_test[ii] == 0]
    cpu_alta = [X_test[ii][0] for ii in range(0, len(X_test)) if y_test[ii] == 1]
    memoria_alta = [X_test[ii][1] for ii in range(0, len(X_test)) if y_test[ii] == 1]

    ### colore os pontos com label = 1 (carga normal) de azul
    ### colore os pontos com label = 0 (sobrecarregado) de vermelho
    plt.scatter(cpu_normal, memoria_normal, color="b", label="carga normal")
    plt.scatter(cpu_alta, memoria_alta, color="r", label="carga alta")

    #############################################################
    # Fim da preparacao da plotgem dos pontos de teste
    ############################################################

    # Legenda
    plt.legend()
    plt.xlabel("utilização de cpu")
    plt.ylabel("utilização de memória")

    # Salva figura
    plt.savefig("teste.png")
    plt.close()
