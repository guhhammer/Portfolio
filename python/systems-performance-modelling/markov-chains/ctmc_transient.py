"""Transient and steady-state analysis of continuous-time Markov chains (class 12).

ctmc_eQt(Q, t, n_max) approximates the matrix exponential e^{Qt} with the
limit (I + Qt/n)^n, increasing n until every entry is below 1. Example 2 applies
it to a four-state chain with generator matrix Q and initial distribution PI0
at t = 0.0035; example 3 solves the balance equations of an eight-state chain
with the pseudo-inverse.
"""
import numpy as np


def ctmc_eQt(Q, t, n_max):
    I = np.identity(Q.shape[1])
    for n in range(100, n_max, 100):
        X = np.linalg.matrix_power(I + (t * Q / n), n)
        if np.all(X < 1):
            break
    return X


# example 2: transient regime
Q = np.array([[-30, 10, 20, 0],[100, -120, 0, 20], [100, 0, -110, 10],[0,100, 100, -200]],
             dtype=np.float64)
PI0 = np.array([1, 0, 0, 0], dtype=np.float64)
t = 0.0035
n_max = 2000
eQt = ctmc_eQt(Q, t, n_max)

print(eQt)
print(np.dot(PI0, eQt))

# example 3: steady state from the balance equations (pi * Q = 0, sum(pi) = 1)
A = np.array([[-1/12, 1/2, 0, 0, 0, 3, 0, 3/2], [1/12, -5, 0, 0, 0, 0, 0, 0],
              [0, 3, -3/2, 0, 0, 0, 0, 0], [0, 3/2, 0, -3, 0, 0, 0, 0],
              [0, 0, 3/2, 3, -1/6, 0, 0, 0], [0, 0, 0, 0, 1/15, -3, 0, 0],
              [0, 0, 0, 0, 1/10, 0, -4, 0], [0, 0, 0, 0, 0, 0, 4, -3/2],
              [1, 1, 1, 1, 1, 1, 1, 1]],
             dtype=np.float64)

B = np.array([0, 0, 0, 0, 0, 0, 0, 0, 1] , dtype=np.float64)

A_pinv = np.linalg.pinv(A)
PI = np.dot(A_pinv,B)
print(PI)
