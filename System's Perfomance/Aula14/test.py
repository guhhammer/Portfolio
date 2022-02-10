
import numpy as np

P = [[0.5, 0.4, 0.1],
	 [0.3, 0.4, 0.3],
	 [0.2, 0.3, 0.5] ]

def multipleOfP(matrix, times):

	aux = matrix

	for i in range(times-1):

		aux = np.dot(aux, matrix)

	return aux

print("P_21 ^ 3 = ", multipleOfP(P, 3)[2][1] )
