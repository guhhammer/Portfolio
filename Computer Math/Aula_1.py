#================================================================
# PUCPR - Bacharelado em Ciencia da Computacao (BCC)
# Resolucao de Problemas de Natureza Discreta - 2019/1
# Aula 1 (27/02/2019)
#   Conjuntos: Conceitos Fundamentais e Operacoes
# Conceitos fundamentais de Conjuntos
# Author @GregoryWanderley
#================================================================

from matplotlib import *
#from matplotlib-venn import *



# Python: set() e frozenset()

# set(): uma colecao mutavel representando o conceito de conjuntos
# frozenset(): o mesmo que set(), porem imutavel.
#              Motivo: num conjunto em Python, os elementos dele nao podem
#                      ser mutaveis.


# Criacao de conjuntos atraves de set(iterator), sendo
#   iterator: lista, tupla, loops, etc.
print('=============================================')
print('Conjuntos criados atraves do set() do Python:')
print('=============================================')

C1 = set(['a', 'b'])
C2 = set(('a', 'b'))
C3 = set('Ciencia da Computacao')
print('Conjunto por set(): C1 = ', C1, ' C2 = ', C2, ' C3 = ', C3)

# Criacao de conjuntos atraves de chaves {}
print('\n\n=============================================')
print('Conjunto criado atraves de chaves {}')
print('=============================================')
A = {1, 2, 3, 4, 5}
print('Conjuntos por chaves = ', A)

# Uso de Frozenset()
print('\n\n=============================================')
print('Uso de Frozenset()')
print('=============================================')
# Ex.: Quando um conjunto e elemento de outro conjunto.
#      Nesse caso, ele torna o conjunto-elemento imutavel
#      (o que e aceito pelo Python)

# A = {1, 2, 3, 4, {8, 9}} -> nao e aceito pelo Python
A = {1, 2, 3, 4, frozenset({8, 9})}
print('Frozenset examplo -> A = ', A)


# Repeticoes (elementos duplicados num conjunto); ordem dos elementos
print('\n\n=======================================================================')
print('Repeticoes (elementos duplicados num conjunto); ordem dos elementos')
print('=======================================================================')

# Constroi uma lista para ver a diferenca com conjuntos
# Uma lista pode possuir elementos repetidos
lista_exemplo = [8, 9, 3, 3]
print('Exemplo de lista (admite repeticoes): ', lista_exemplo)

# Conjuntos (nao admitem repeticoes de seus elementos)
A = {8, 9, 3, 3, 1, 8, 1, 1}
print('Conjunto (nao admite repeticoes) = ', A)

# diferentes tipos de elementos no mesmo conjunto (NORMALMENTE compartilham
# uma propriedade em comum)
A = {42, 'foo', 3.14159, frozenset({'a', 'e', 'i', 'o', 'u'})}
print('Conjunto com elementos nada parecidos = ', A)


# Notacao por extensao
print('\n\n=============================================')
print('Notacao por extensao')
print('=============================================')

T = {0, 1, 4, 9, 16, 25, 36, 49, 64, 81}
V = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096}
M = {0, 4, 16, 36, 64}
print('\nNotacao por extensao (resultado):\nT = ', T, '\nV = ', V, '\nM = ', M)


# Notacao por propriedades
print('\n\n=============================================')
print('Notacao por propriedades')
print('=============================================')

# T = {xˆ2 : x in {0 ... 9}}
T = set(x**2 for x in range(10))

# V = (1, 2, 4, 8, ..., 2ˆ12)
V = {2**i for i in range(13)}

# M = {x | x pertence a T and x e par}
M = {x for x in T if x % 2 == 0}

print('\nNotacao por propriedade (resultado):\nT = ', T, '\nV = ', V, '\nM = ', M)


# Pertinencia
# Comandos python: in ; not in
print('\n\n=============================================')
print('Pertinencia')
print('=============================================')

A = {'Curitiba', 'Florianopolis', 'Recife', 'Manaus', 'Macapa'}
b = 'Curitiba'
C = {'Recife, Macapa'}
d = 'Cuiaba'
E = {'Manaus', 'Aracaju'}

print('b pertence a A? ', b in A)
print('C pertence a A? ', C in A)
print('d pertence a A? ', d in A)
print('E nao pertence a A? ', E not in A)


# Conjunto vazio
print('\n\n=============================================')
print('Conjunto vazio')
print('=============================================')

Vazio = set()
print('Conjunto vazio = ', Vazio)


# Conjunto unitario
print('\n\n=============================================')
print('Conjunto unitario')
print('=============================================')

paises = {'Brasil', 'Italia', 'Japao', 'Paraguai', 'Australia'}
A = {x for x in paises if x == 'Brasil'}
print('Conjunto unitario = ', A)


# Conjuntos numericos padroes
print('\n\n=============================================')
print('Conjuntos numericos padroes')
print('=============================================')

# N
numeros_naturais = {x for x in range(30)}
print('Conjunto N = ', numeros_naturais)

# Z
numeros_inteiros = {x for x in range(-10, 10)}
print('Conjunto Z = ', numeros_inteiros)

# R
# -1.0 ate 1.0 (intervalos de 0.1)
numeros_reais = {x*0.1 for x in range(-10, 11)}
print('Conjunto R = ', numeros_reais)


# Conjuntos finitos e infinitos
print('\n\n=============================================')
print('Conjuntos finitos e infinitos')
print('=============================================')

# Finito
alfabeto = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
                     'h', 'i', 'j', 'k', 'l', 'm', 'n'
                     'o', 'p', 'q', 'r', 's', 't', 'u',
                     'v', 'x', 'w', 'y', 'z'}
print('Conjunto finito = ', alfabeto)

# Infinito
#Um meio para definir infinito em python
INFINITO = float('inf')
print('Infinito = ', INFINITO)

# Definir um infinito, pois os recursos computacionais sao finitos...
infinito_simulado = 2**8
print('Infinito simulado  = ', infinito_simulado)
numeros_impares = {x*2+1 for x in range(infinito_simulado)}
print('Conjunto infinito simulado = ', numeros_impares)


# Cardinalidade de conjuntos
print('\n\n=============================================')
print('Cardinalidade de conjuntos')
print('=============================================')
print('Tamanho do conjunto infinito simulado: ', len(numeros_impares))




# Subconjuntos
print('\n\n=============================================')
print('Subconjuntos')
print('=============================================')
A = {0, 2, 4, 6, 8, 10}
B = {2, 8}
C = {0, 2, 10, 11}

print('B e subconjunto de A? ', B.issubset(A))
print('C e subconjunto de A? ', C.issubset(A))
print('Vazio e subconjunto de A?', Vazio.issubset(A))
print('Vazio e subconjunto de B?', Vazio.issubset(B))
print('Vazio e subconjunto de C?', Vazio.issubset(C))

print('A contem B? ', A.issuperset(B))
print('A contem C? ', A.issuperset(C))
print('C contem Vazio?', C.issuperset(Vazio))


# Subsonjuntos proprios
print('\n\n=============================================')
print('Subsonjuntos proprios')
print('=============================================')
A = {2, 5}
B = {2, 5, 7}

print('A e subconjunto proprio de B' if A.issubset(B) and not B.issubset(A)
      else 'A nao e subconjunto proprio de B')



# Conjunto Potencia
print('\n\n=============================================')
print('Conjunto Potencia')
print('=============================================')

def conjunto_potencia(p):
  conjunto_potencia=[[]]
  for elem in p:
    # iterate over the sub sets so far
    for sub_set in conjunto_potencia:
      # add a new subset consisting of the subset at hand added elem
      conjunto_potencia = conjunto_potencia+[list(sub_set)+[elem]]
  return conjunto_potencia


meu_conjunto = {1, 2}
print('Conjunto potencia (lista): ', conjunto_potencia(meu_conjunto))
print('Conjunto potencia (set): ',
      frozenset(map(frozenset, conjunto_potencia(meu_conjunto))))


# Conjunto Universo (U)
print('\n\n=============================================')
print('Conjunto Universo (U)')
print('=============================================')

U_Alunos_PUCPR = {'Aluno1', 'Aluno2', 'Aluno3', 'Aluno4', 'Aluno5',
                  'Aluno6', 'AlunoN'}
# Conjunto derivado do Universo
Alunos_BCC = {'Aluno3', 'Aluno6'}
print('U: ', U_Alunos_PUCPR, '\nUm conjunto derivado de U: ', Alunos_BCC)


# Igualdade de conjuntos
print('\n\n=============================================')
print('Igualdade de conjuntos')
print('=============================================')

A = {1, 2, 4, 7}
B = {4, 7, 2, 1}
C = {1, 2, 7}
print('Modo1: A = B' if A.issubset(B) and B.issubset(A)
      else 'A nao e igual a B')
print('Modo2: A = B' if A == B else 'A nao e igual a B')


print('Modo1: A = C' if A.issubset(C) and C.issubset(A)
      else 'A nao e igual a C')


# Teste de subconjunto vs. pertinencia
print('\n\n=============================================')
print('Teste de subconjunto vs. pertinencia')
print('=============================================')

A = {3, 4, 5, frozenset(), frozenset({'a'}), frozenset({'b', 'c'}), 'd'}

print("{'a'} e subconjunto de A? ", {'a'}.issubset(A))
print("{'a'} pertence a A? ", {'a'} in A)
print("{} pertence a A? ", Vazio in A)
print("{} e subconjunto de A? ", Vazio.issubset(A))

# Operacao de Uniao
print('\n\n=============================================')
print('Operacao de Uniao')
print('=============================================')

A = {1, 2, 3}
B = {4, 5, 6}

print('A uniao B = ', A.union(B))


# Operacao de Intersecao
print('\n\n=============================================')
print('Operacao de Intersecao')
print('=============================================')

A = {1, 2, 3}
B = {4, 5, 6}
C = {5, 8}

print('A intersecao B = ', A.intersection(B))
print('B intersecao B = ', B.intersection(C))


# Operacao de Complemento
print('\n\n=============================================')
print('Operacao de Complemento')
print('=============================================')

U = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
A = {4, 5, 6}

print('U \ A = ', U.difference(A))


# Operacao de Complemento
print('\n\n=============================================')
print('Operacao de Diferenca')
print('=============================================')

A = {1, 2, 3}
B = {1, 3, 5}

print('A - B = ', A.difference(B))


# Produto Cartesiano
print('\n\n=============================================')
print('Produto Cartesiano')
print('=============================================')

def produto_cartesiano(A, B):
    return [[a, b] for a in A for b in B]


A = {1, 2, 3}
B = {1, 3, 5}

print('A X B = ', set(map(tuple, produto_cartesiano(A, B))))



# Diagrama de Venn
print('\n\n=============================================')
print('Diagrama de Venn')
print('=============================================')

# == Venn para 2 conjuntos ==

import matplotlib as mpl
#Descomente a linha abaixo para uso no repl.it
#mpl.use('Agg')
import matplotlib.pyplot as plt
from matplotlib_venn import venn2, venn3

A = set({'a', 'b'})
B = set({'b', 'd'})
C = set({'b', 'f', 'h'})

D = A.union(B)
print('C = ', C)
print('D = A U B = ', D)
print('D intersec C = ', D.intersection(C))


v = venn2([D, C], ('Conjunto D', 'Conjunto C'))

#Descomente a linha abaixo para uso no repl.it
#plt.savefig('Venn.png')

#Comente a linha abaixo para uso no repl.it
plt.show()

print('Terminou!')


# == Venn para 3 conjuntos ==

import matplotlib as mpl
#Descomente a linha abaixo para uso no repl.it
#mpl.use('Agg')
import matplotlib.pyplot as plt
from matplotlib_venn import venn2, venn3


# Venn para 3 conjuntos
v = venn3([set({'a', 'b'}), set({'b', 'd'}), set({'b', 'f', 'h'})],
          ('Conjunto A', 'Conjunto B', 'Conjunto C'))

labels = ['100', '101', '110', '010', '001', '011', '111']
[v.get_label_by_id(label).set_text(label) for label in labels]

v.get_label_by_id('100').set_text('a')
v.get_label_by_id('111').set_text('b')
v.get_label_by_id('001').set_text('f, h')
v.get_label_by_id('010').set_text('d')

v.get_label_by_id('110').set_text('')
v.get_label_by_id('101').set_text('')
v.get_label_by_id('011').set_text('')

#Comente a linha abaixo para uso no repl.it
plt.show()

#Descomente a linha abaixo para uso no repl.it
#plt.savefig('Venn.png')

print('Terminou!')

