"""Steady-state probabilities of a discrete-time Markov chain (class 11).

dtmc_steady_state(P) checks that P is a square stochastic matrix, builds the
linear system pi * P = pi with the normalisation sum(pi) = 1, and solves it
with the Moore-Penrose pseudo-inverse. The example is the six-state chain of
team problem 11.
"""
import numpy as np


P = [ [0.0, 0.2, 0.0, 0.0, 0.4, 0.4],
    [0.0, 0.0, 0.2, 0.0, 0.6, 0.2],
    [0.0, 0.0, 0.0, 0.2, 0.8, 0.0],
    [0.0, 0.0, 0.0, 0.0, 1.0, 0.0],
    [1.0, 0.0, 0.0, 0.0, 0.0, 0.0],
    [1.0, 0.0, 0.0, 0.0, 0.0, 0.0] ]

P = np.array(P)


def dtmc_steady_state(P):

    [r,c] = P.shape
    if ((r != c) | np.all(np.sum(P, 1) != 1)):
        raise Exception('Invalid matrix P!')

    A = np.vstack(( (np.transpose(P) - np.identity(r)), [1 for _ in range(r)] ))

    B = np.hstack((np.zeros(r), [1]))

    print("A:\n", A, "\n\nB:", B, "\n")

    PI = np.dot( np.linalg.pinv(A), B)

    return PI

print("PI: ", dtmc_steady_state(P), "\n")
