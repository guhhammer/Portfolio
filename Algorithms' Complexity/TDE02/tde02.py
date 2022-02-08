############################################################
############################################################

# NOME: Gustavo Hammerschmidt.

import numpy as np
import threading

# Generate random array.
gen_arr = lambda length:  [int(_ * 10000000 ) for _ in np.random.rand(length)]

# a) implementar o algoritmo exibido na página 18.
def insertionSort(array) -> None:
	
	# Os dois erros corrigidos a seguir impedem o algoritmo de ordenar
	# o primeiro elemento do array.

	# MC = Melhor Caso & PC = Pior Caso.

	for j in range(1, len(array)): # Erro: no arquivo, começa-se em 2.						# C1 <- n-1

		chave = array[j]																	# C2 <- 1

		i = j - 1																			# C3 <- 1

		while i >= 0 and array[i] > chave: # Erro: no arquivo, falta o '=' em '>='.			# C4 <- (MC <- 1) | (PC <- i)  

			array[i + 1] = array[i]															# C5 <- (MC <- 0) | (PC <- i)

			i = i - 1																		# C6 <- (MC <- 0) | (PC <- i)

			array[i + 1] = chave															# C7 <- (MC <- 0) | (PC <- i)

def partitionedArray(array, partitions) -> [[]]:

	size_partition, splits = int(len(array) / partitions), []

	for i in range(partitions):

		splits.append( array[ (i * size_partition) : ( (i + 1) * size_partition ) ] )

	if len(array) % partitions != 0:

		splits.append(array[ ((partitions) * size_partition) : ])

	return splits

def example() -> None:

	__example_arr__ = [6, 3, 2, 1, 8, 5, 4]

	print('\nBefore InsertionSort: ',__example_arr__, end=' | ')

	insertionSort(__example_arr__)

	print('After InsertionSort: ',__example_arr__, end='\n\n')

# b) use essa implementação para compor uma abordagem paralela de ordenação.
def parallel_insertionsort(arrays) -> None:

	ts = [threading.Thread(target=insertionSort, args=(a,)) for a in arrays]

	[t.start() for t in ts]; [t.join() for t in ts]

"""
# c) estime a complexidade da solução dada em (b) para o pior caso e melhor caso.
	
	# MC = Melhor Caso & PC = Pior Caso.
	for j in range(1, len(array)): # Erro: no arquivo, começa-se em 2.						# C1 <- n-1
		chave = array[j]																	# C2 <- 1
		i = j - 1																			# C3 <- 1
		while i >= 0 and array[i] > chave: # Erro: no arquivo, falta o '=' em '>='.			# C4 <- (MC <- 1) | (PC <- i)  
			array[i + 1] = array[i]															# C5 <- (MC <- 0) | (PC <- i)
			i = i - 1																		# C6 <- (MC <- 0) | (PC <- i)
			array[i + 1] = chave															# C7 <- (MC <- 0) | (PC <- i)
	

	Melhor caso:
		
		Ω(InsertionSort) ~> C1 + ... + C7 ~> (n-1) * (1 + 1 + 1) ~> 3n - 3 ~> Ω(n)

	Pior caso:

		O(InsertionSort) ~> C1 + ... + C7 ~> (n-1) * (2) + 4 * (n * (n-1)) / 2
						 ~> 2n + 2 + 2n² - 4 ~> 2n² + 2n - 2 
						 ~> O(n²)

"""

def merge(arrays) -> []:

	indexes, assemble = [0 for _ in range(len(arrays))], []

	while True:
		k, val = 0, float('inf')
		for i in range(len(indexes)):
			try:
				if arrays[i][indexes[i]] < val: 
					val, k = arrays[i][indexes[i]], i
			except:
				pass

		if float('inf') == val:
			break

		assemble.append(val)
		indexes[k] += 1

	return assemble

############################################################
############################################################

if __name__ == "__main__":

	
	example()
	
	# Dado do arquivo em anexo, pede-se:

	elems = 1000
	A = gen_arr( elems * 3 )

	p1, p2, p3 = partitionedArray(A, 3)
	print('Length p1: %d; length p2: %d; length p3: %d\n' % (len(p1),len(p2),len(p3)))

	
	# 10: a) implementar o algoritmo exibido na página 18.
	parallel_insertionsort([p1,p2,p3])
	# 56: b) use essa implementação para compor uma abordagem paralela de ordenação.

	# 64: c) estime a complexidade da solução dada em (b) para o pior caso e melhor caso.

	listaOrdenada = merge([p1,p2,p3])

	print('ListaOrdenada: %d elementos.' % len(listaOrdenada))

	print()
	print('=' * 40,'\n\nUsando a sugestão de problema:\n')

	# Sugestão de definição do problema: seja A = [3, 1, 9, 5, 7, 2, 9, 10, 2, 0, -1] e 
	A = [3, 1, 9, 5, 7, 2, 9, 10, 2, 0, -1]; print("A =", A)

	# P1, P2 e P3 três partições de A. 
	P1, P2, P3, rest = partitionedArray(A, 3)
	print('\nP1 =',P1,'| P2 =',P2,'| P3 =',P3,'| Resto =', rest)

	P1.append(rest[0]); P2.append(rest[1])
	print('\nP1 =',P1,'| P2 =',P2,'| P3 =',P3)

	# Cada parte P1, P2 e P3 é ordenada em paralelo.
	parallel_insertionsort([P1,P2,P3])
	print('\nP1 =',P1,'| P2 =',P2,'| P3 =',P3)

	# As partes ordenadas são unidas para formar apenas um vetor de dados ordenados.
	vetorDeDados = merge([P1,P2,P3])

	print("\nvetorDeDados =",vetorDeDados)

	print("\n")


