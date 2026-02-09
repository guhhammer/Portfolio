import threading, time, random

def minhathread(n):
	global numthreads, lock
	t = random.randint(1,3); time.sleep(t)
	
	lock.acquire()
	f = open('contador.txt', 'r'); numthreads = int(f.read()) + 1; f.close()
	f = open('contador.txt', 'w'); f.write(str(numthreads)); f.close()
	lock.release()
	
	print("Thread {0} depois de {1}s".format(n, t))

f = open('contador.txt','w'); f.write('0'); f.close()
numthreads, threads, lock = 0, [], threading.Lock()

[threads.append(threading.Thread(target=minhathread, args=(i,))) for i in range(10)]
[t.start() for t in threads]; [x.join() for x in threads]
	
print("{0} threads lançadas!".format(numthreads))