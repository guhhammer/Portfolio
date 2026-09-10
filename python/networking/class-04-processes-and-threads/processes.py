from multiprocessing import Process
import time, random

def my_process(n):
    t = random.randint(1, 3)
    time.sleep(t)
    print("Process {0} after {1}s".format(n, t))


if __name__ == "__main__":
	start = time.time()
	processes = []
	for i in range(10):
		p = Process(target=my_process, args=(i,))
		processes.append(p)
		p.start()

	[x.join() for x in processes]

	print("Processes launched!")
	print("Elapsed time: {0}s.".format(time.time() - start))
