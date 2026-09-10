from multiprocessing import Process
import time, random

# Deliberately broken: each process gets its own copy of the variables, so
# numprocs is never shared with the parent (see answers.md, B.1 and B.2).
def my_process(n):
    #global numprocs
    t = random.randint(1, 3)
    time.sleep(t)
    numprocs += 1
    print("Process {0} after {1}s\n".format(n, t))

if __name__ == '__main__':
    processes, numprocs = [], 0

    for i in range(10):
        t = Process(target=my_process, args=(i,))
        processes.append(t)
        t.start()

    [x.join() for x in processes]

    print("{0} processes launched!".format(numprocs))
