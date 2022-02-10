import numpy as np


P = [ [0.0, 0.2, 0.0, 0.0, 0.4, 0.4],
    [0.0, 0.0, 0.2, 0.0, 0.6, 0.2],
    [0.0, 0.0, 0.0, 0.2, 0.8, 0.0],
    [0.0, 0.0, 0.0, 0.0, 1.0, 0.0],
    [1.0, 0.0, 0.0, 0.0, 0.0, 0.0],
    [1.0, 0.0, 0.0, 0.0, 0.0, 0.0] ]

P = np.array(P)


def cmtdP(P):

    [r,c] = P.shape
    if ((r != c) | np.all(np.sum(P, 1) != 1)):
        raise Exception('Matriz P invalida!')

    A = np.vstack(( (np.transpose(P) - np.identity(r)), [1 for _ in range(r)] ))
    
    B = np.hstack((np.zeros(r), [1]))

    print("A:\n", A, "\n\nB:", B, "\n")

    PI = np.dot( np.linalg.pinv(A), B)

    return PI

print("PI: ",cmtdP(P),"\n")