import threading, time, random

def my_thread(n):
	global numthreads, lock
	t = random.randint(1, 3); time.sleep(t)
	lock.acquire(); numthreads += 1; lock.release()
	print("Thread {0} after {1}s".format(n, t))

numthreads, threads, lock = 0, [], threading.Lock()

[threads.append(threading.Thread(target=my_thread, args=(i,))) for i in range(10)]
[t.start() for t in threads]; [x.join() for x in threads]

print("{0} threads launched!".format(numthreads))
