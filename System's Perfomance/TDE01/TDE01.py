import numpy as np
from math import factorial

def combinacao(n, x):
    return factorial(n)/(factorial(x)*(factorial(n - x)))

def mesma_paridade(n, k):
	return (n % 2 == 0 and k % 2 == 0) or (n % 2 != 0 and k % 2 != 0)

def Passeio(npassos, pos, nsim):

    # simulacao vetorial
    # Passo 1
    # sortear uma matriz (array) com nsim linhas e npassos colunas
    # cada linha da matriz é um passeio com npassos
    # cada passo tem valor 1 (passo à direita) ou -1 (passo à esquerda) com a mesma probabilidade
    # dica: utilizar np.random.choice

    # Passo 2
    # somar os valores das linhas da matriz calculada anteriormente
    # o resultado é um vetor com as posicoes finais de cada passeio

    # Passo 3
    # calcular a probabilidade simulada
    # pSim deve ser igual a quantidade de passeios que terminaram na posicao pos divida pelo numero de simulacoes
    # o comando abaixo faz pSim = 0 para o programa não dar erro se for executado
    # você deve substituir o comando pelo calculo apropriado
    # Dica: se o o nome do vetor calculado no Passo 2 for "posicoes", o comando "posicoes == pos" produz um novo vetor
    # Os valores do novo vetor são iguais a True se o passeio terminou na posicao pos e False caso contrario
    # Usar np.sum para contar a quantidade de True

    pSim = 0

    # calculo teorico
    if mesma_paridade(npassos, pos):
        pTeorica = combinacao(npassos, (npassos + pos) / 2) * (2 ** (-npassos))
    else:
        pTeorica = 0

    return pSim, pTeorica


passos = int(input("Defina o numero de passos: "))
pos = int(input("Defina a posicao final da trajetoria: "))
nsim = int(input("Def1ina o numero de simulacoes: "))

pSim, pTeorica = Passeio(passos, pos, nsim)

print('Probabilidade simulada:  {:.4f}'.format(pSim))
print('Probabilidade teorica: {:.4f}'.format(pTeorica))