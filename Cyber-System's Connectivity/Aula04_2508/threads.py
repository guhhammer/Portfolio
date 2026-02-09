import threading, time, random

def minhathread(n):
    t = random.randint(1,3)
    time.sleep(t)
    print("Thread {0} depois de {1}s".format(n, t))


if __name__ == "__main__":

	start = time.time()

	threads = []
	for i in range(10):
	    t = threading.Thread(target=minhathread, args=(i,))
	    threads.append(t)
	    t.start()

	for x in threads:
		x.join()

	print("Threads lançadas!")

	end = time.time()

	print("Tempo decorrido: {0}s.\n".format(end-start))

