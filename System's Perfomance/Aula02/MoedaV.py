import numpy as np
import time
import scipy.stats as st

def MoedaV(n):
    # calcula por simulação a probabilidade de precisar uma quantidade par de lancamentos
    # para sair a primeira cara
    #
    # usar geom.ppf (inversa da cdf) para encontrar m igual ao valor
    # cuja probabilidade de sair cara apos m-esima jogada seja muito baixa (0.999)
    # significa que a probabilidade de lancar a moeda m vezes e
    # nao sair nenhuma cara (1) no sorteio eh muito baixa (0,0001)
    m = int(st.geom.ppf(0.9999, 0.5))

    # sortear n simulacoes do lancamento de uma moeda m vezes
    # 0 - coroa      1 - cara


    # np.argmax retorna o indice do primeiro valor maximo ao longo de uma dimensao
    # usar para encontrar o indice do primeiro valor 1 em cada linha de sorteio (primeira cara)
    # somar 1 no indice (em numpy os indices iniciam em zero)
    # x = np.argmax(.., 1) armazena em x um array com o o numero do lancamento da moeda
    # onde ocorreu a primeira cara


    # retorna a quantidade de vezes que o numero de lancamentos ate sair cara foi par
    # dividida pela quantidade de simulacoes (n)
    return np.sum(x % 2 == 0)/n


t1 = time.perf_counter()
probS = MoedaV(10000)
t2 = time.perf_counter()
print('Probabilidade simulada:  {:.4f}'.format(probS))
print('Probabilidade teórica: {:.4f}'.format(1/3))
print('Tempo de simulação: {:.4f}'.format(t2-t1))