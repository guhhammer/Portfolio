############################################################
############################################################

# NAME: Gustavo Hammerschmidt.

import numpy as np
import threading

# Generate random array.
gen_arr = lambda length:  [int(_ * 10000000 ) for _ in np.random.rand(length)]

# a) implement the algorithm shown on page 18 of the handout.
def insertionSort(array) -> None:
	
	# The two mistakes fixed below stopped the algorithm from sorting
	# the first element of the array.

	# BC = best case & WC = worst case.

	for j in range(1, len(array)): # Fix: the handout started at 2.						# C1 <- n-1

		key = array[j]																	# C2 <- 1

		i = j - 1																			# C3 <- 1

		while i >= 0 and array[i] > key: # Fix: the handout was missing the '=' in '>='.			# C4 <- (BC <- 1) | (WC <- i)  

			array[i + 1] = array[i]															# C5 <- (BC <- 0) | (WC <- i)

			i = i - 1																		# C6 <- (BC <- 0) | (WC <- i)

			array[i + 1] = key															# C7 <- (BC <- 0) | (WC <- i)

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

# b) use that implementation to build a parallel sorting approach.
def parallel_insertionsort(arrays) -> None:

	ts = [threading.Thread(target=insertionSort, args=(a,)) for a in arrays]

	[t.start() for t in ts]; [t.join() for t in ts]

"""
# c) estimate the best-case and worst-case complexity of the solution in (b).
	
	# BC = best case & WC = worst case.
	for j in range(1, len(array)): # Fix: the handout started at 2.						# C1 <- n-1
		key = array[j]																	# C2 <- 1
		i = j - 1																			# C3 <- 1
		while i >= 0 and array[i] > key: # Fix: the handout was missing the '=' in '>='.			# C4 <- (BC <- 1) | (WC <- i)  
			array[i + 1] = array[i]															# C5 <- (BC <- 0) | (WC <- i)
			i = i - 1																		# C6 <- (BC <- 0) | (WC <- i)
			array[i + 1] = key															# C7 <- (BC <- 0) | (WC <- i)
	

	Best case:
		
		Ω(InsertionSort) ~> C1 + ... + C7 ~> (n-1) * (1 + 1 + 1) ~> 3n - 3 ~> Ω(n)

	Worst case:

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
	
	# From the attached handout:

	elems = 1000
	A = gen_arr( elems * 3 )

	p1, p2, p3 = partitionedArray(A, 3)
	print('Length p1: %d; length p2: %d; length p3: %d\n' % (len(p1),len(p2),len(p3)))

	
	# 10: a) implement the algorithm shown on page 18.
	parallel_insertionsort([p1,p2,p3])
	# 56: b) use it to build a parallel sort.

	# 64: c) estimate best- and worst-case complexity of (b).

	sorted_list = merge([p1,p2,p3])

	print('sorted_list: %d elements.' % len(sorted_list))

	print()
	print('=' * 40,'\n\nUsing the suggested problem:\n')

	# Suggested problem: let A = [3, 1, 9, 5, 7, 2, 9, 10, 2, 0, -1] and 
	A = [3, 1, 9, 5, 7, 2, 9, 10, 2, 0, -1]; print("A =", A)

	# P1, P2 and P3 three partitions of A.
	P1, P2, P3, rest = partitionedArray(A, 3)
	print('\nP1 =',P1,'| P2 =',P2,'| P3 =',P3,'| Remainder =', rest)

	P1.append(rest[0]); P2.append(rest[1])
	print('\nP1 =',P1,'| P2 =',P2,'| P3 =',P3)

	# Each part P1, P2 and P3 is sorted in parallel.
	parallel_insertionsort([P1,P2,P3])
	print('\nP1 =',P1,'| P2 =',P2,'| P3 =',P3)

	# The sorted parts are merged into a single sorted vector.
	data_vector = merge([P1,P2,P3])

	print("\ndata_vector =", data_vector)

	print("\n")


