import numpy as np

def cmtc_eQt(Q, t, n_max):
    I = np.identity(Q.shape[1])
    for n in range(100, n_max, 100):
        X = np.linalg.matrix_power(I + (t * Q / n), n)
        if np.all(X < 1):
            break
    return X






