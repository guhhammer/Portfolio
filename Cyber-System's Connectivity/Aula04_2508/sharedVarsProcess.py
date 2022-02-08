from multiprocessing import Process
import time, random

def meuprocesso(n):
    #global numprocs
    t = random.randint(1,3)
    time.sleep(t)
    numprocs+=1
    print("Thread {0} depois de {1}s\n".format(n, t))

if __name__ == '__main__':
    processes, numprocs = [], 0
    
    for i in range(10):
        t = Process(target=meuprocesso, args=(i,))
        processes.append(t)
        t.start()

    [x.join() for x in processes]

    print("{0} Processos lançados!".format(numprocs))