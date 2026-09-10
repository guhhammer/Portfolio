"""n-step transition probability of a discrete-time Markov chain (team problem 11).

Paulo's mood is good (state 1), so-so (state 2) or bad (state 3) with the
transition matrix P below. The probability of being in a bad mood today and
so-so in 3 days is the entry [3, 2] of P cubed.
"""
import numpy as np

P = [[0.5, 0.4, 0.1],
     [0.3, 0.4, 0.3],
     [0.2, 0.3, 0.5] ]

def matrix_power(matrix, times):

    aux = matrix

    for i in range(times-1):

        aux = np.dot(aux, matrix)

    return aux

print("P^3 [2][1] = ", matrix_power(P, 3)[2][1] )
