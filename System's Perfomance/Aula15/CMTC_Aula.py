import numpy as np
from CMTC_transitorio import cmtc_eQt

Q = np.array([[-30, 10, 20, 0],[100, -120, 0, 20], [100, 0, -110, 10],[0,100, 100, -200]],
             dtype=np.float64)
PI0 = np.array([1, 0, 0, 0], dtype=np.float64)
t = 0.0035
n_max = 2000
eQt = cmtc_eQt(Q, t, n_max)

print(eQt)
print(np.dot(PI0, eQt))

A = np.array([[-1/12, 1/2, 0, 0, 0, 3, 0, 3/2], [1/12, -5, 0, 0, 0, 0, 0, 0],
              [0, 3, -3/2, 0, 0, 0, 0, 0], [0, 3/2, 0, -3, 0, 0, 0, 0],
              [0, 0, 3/2, 3, -1/6, 0, 0, 0], [0, 0, 0, 0, 1/15, -3, 0, 0],
              [0, 0, 0, 0, 1/10, 0, -4, 0], [0, 0, 0, 0, 0, 0, 4, -3/2],
              [1, 1, 1, 1, 1, 1, 1, 1]],
             dtype=np.float64)

B= np.array([0, 0, 0, 0, 0, 0, 0, 0, 1] , dtype=np.float64)

A_pinv = np.linalg.pinv(A)
PI = np.dot(A_pinv,B)
print(PI)