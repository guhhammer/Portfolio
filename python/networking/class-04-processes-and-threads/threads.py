import threading, time, random

def my_thread(n):
    t = random.randint(1, 3)
    time.sleep(t)
    print("Thread {0} after {1}s".format(n, t))


if __name__ == "__main__":

	start = time.time()

	threads = []
	for i in range(10):
	    t = threading.Thread(target=my_thread, args=(i,))
	    threads.append(t)
	    t.start()

	for x in threads:
		x.join()

	print("Threads launched!")

	end = time.time()

	print("Elapsed time: {0}s.\n".format(end - start))
