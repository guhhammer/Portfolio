import numpy as np


Q = np.array([[0.0, 1.0, 0.0, 0.0, 0.0, 0.0],
              [0.0, 0.0, 0.6667, 0.3333, 0.0, 0.0],
              [0.75, 0.0, 0.0, 0.0, 0.25, 0.0],
              [0.0, 0.0, 0.0, 0.0, 1.0, 0.0],
              [0.0, 0.6, 0.0, 0.0, 0.0, 0.4],
              [0.0, 0.0, 1.0, 0.0, 0.0, 0.0]],  dtype=np.float64)

def cmtcP(Q):
    [r,c] = Q.shape
    if ((r != c) | np.any(np.sum(Q, 1) != 1)):
        raise Exception('Matriz P invalida!')

    A = np.transpose(Q)
    A = np.vstack((A, np.ones(r)))
    B = np.zeros(r)
    B = np.hstack((B,[1]))
    A_pinv = np.linalg.pinv(A)
    PI = np.dot(A_pinv, B)
    return PI

print("PI: ", cmtcP(Q))


