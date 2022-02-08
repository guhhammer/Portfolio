import threading, time, random

def minhathread(n):
    global numthreads
    t = random.randint(1,3)
    time.sleep(t)
    numthreads += 1
    print("Thread {0} depois de {1}s\n".format(n, t))

numthreads, threads = 0, []
for i in range(10):
    t = threading.Thread(target=minhathread, args=(i,))
    threads.append(t)
    t.start()

[x.join() for x in threads]

print("{0} threads lançadas!".format(numthreads))