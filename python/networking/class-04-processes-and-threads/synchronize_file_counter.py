import threading, time, random

# Same as synchronize_lock.py, but the counter lives in counter.txt so the
# critical section also covers file I/O.
def my_thread(n):
	global numthreads, lock
	t = random.randint(1, 3); time.sleep(t)

	lock.acquire()
	f = open('counter.txt', 'r'); numthreads = int(f.read()) + 1; f.close()
	f = open('counter.txt', 'w'); f.write(str(numthreads)); f.close()
	lock.release()

	print("Thread {0} after {1}s".format(n, t))

f = open('counter.txt', 'w'); f.write('0'); f.close()
numthreads, threads, lock = 0, [], threading.Lock()

[threads.append(threading.Thread(target=my_thread, args=(i,))) for i in range(10)]
[t.start() for t in threads]; [x.join() for x in threads]

print("{0} threads launched!".format(numthreads))
