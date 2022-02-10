import numpy as np
X = np.array([[2, 6300, 0, 0],
              [2, 5800, 0, 0],
              [0, 6100, 0, 0],
              [1, 5200, 0, 0],
              [1, 2300, 1, 0],
              [1, 2800, 1, 1],
              [0, 3100, 1, 1],
              [2, 4500, 0, 0],
              [2, 3300, 1, 0],
              [1, 4800, 1, 0],
              [2, 4400, 1, 1],
              [0, 4900, 0, 1],
              [0, 6000, 1, 0],
              [1, 4600, 0, 1]])

Y = np.array([0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0])

from sklearn.naive_bayes import GaussianNB
clf = GaussianNB()
clf.fit(X, Y)

previsao = clf.predict([[2, 4650, 1, 0]])

print('Classe prevista = ', previsao)