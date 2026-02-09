
import time
import numpy as np
import matplotlib.pyplot as plt

# ordenação por inserção
def ex7(V, n):
    for j in range(1, n, 1):            #C1 (n-1) 
        chave = V[ j ]                  #C2 (n-1)
        i = j - 1                       #C3 (n-1)
        while i >= 0 and V[i] > chave:  #C4 (n-1) * (n-1)
            V[i + 1] = V[ i ]           #C5 (n-1) * (n-1)
            i = i - 1                   #C6 (n-1) * (n-1)
        V[i + 1] = chave                #C7 (n-1)
    return V  
    
# 4 * (n-1) + 3*(n-1)*(n-1) => 3n^2 - 6n + 3 + 4n - 4 => 3n^2 -2n -1

#X = [6,5,4,3,2,1]
#X = [1,2,3,4,5,6]
#X = [5,2,4,6,1,3]
#print(ex7(X, len(X)))





# Ordenação por Bolha 
def ex2(V, n):
    nt = 0 # número de trocas       #C1
    while True:                     #C2
        nt= 0                       #C3
        for k in range(0, n-1, 1):  #C4
            if (V[k] > V[k+1]):     #C5
                aux = V[k]          #C6
                V[k] = V[k+1]       #C7
                V[k+1] = aux        #C8
                nt = nt + 1         #C9
        if nt == 0:                 #C10
            break                   #C11

#X = [9,8,7,6,5,4,3]
#ex2(X, len(X))
#print(X)

# Ordenação por Bolha Modificada 
def ex3(V, n):
    fim = n - 1                     #C1
    while True:                     #C2
        kk = 0;                     #C3
        for k in range(0, fim, 1):  #C4
            if V[k] > V[k + 1]:     #C5
                aux = V[k]          #C6
                V[k] = V[k + 1]     #C7
                V[k + 1] = aux      #C8
                kk = k              #C9
        fim = kk                    #C10
        if kk == 0:                 #C11
            break                   #C12



# Ex.2

arr = np.random.randint(0, 99999, 1000)

print("Running Ex2...")

start = time.time()
ex2(arr, len(arr))
end = time.time()

print("Ex2 finished.")

ex2_time = end - start



# Ex.3

arr = np.random.randint(0, 99999, 1000)

print("Running Ex3...")

start = time.time()
ex3(arr, len(arr))
end = time.time()

print("Ex3 finished.")

ex3_time = end - start

print("\nQuestion 1:\n\t4 * (n-1) + 3*(n-1)*(n-1) => 3n^2 - 6n + 3 + 4n - 4 => 3n^2 -2n -1")

print("\nQuestion 2:\n\t3 * (n^2) + n - 8")

print("\nQuestion 3:\n\tTime elapsed:\n\t\tEx2 time: {}\n\t\tEx3 time: {}".format(ex2_time, ex3_time))


ax = [1,2,3,4,5,6,7,8,9,10]

ay1, ay2 = [], [] 

for i in range(0, 10):

    arr = np.random.randint(0, 99999, int(1000/(10-i)))

    start = time.time()
    ex2(arr, len(arr))
    end = time.time()

    ex2_time = end - start

    arr = np.random.randint(0, 99999, int(1000/(10-i)))

    start = time.time()
    ex3(arr, len(arr))
    end = time.time()

    ex3_time = end - start

    ay1.append(ex2_time)
    ay2.append(ex3_time)

plt.plot(ax, ay1, color='green', label='ex2=b')
plt.plot(ax, ay2, color='red', label='ex3=bm')

plt.show()
plt.savefig('b-bm-desempenho.jpg')
