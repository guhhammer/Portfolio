from multiprocessing import Process
import time, random

def meuprocesso(n):
    t = random.randint(1,3)
    time.sleep(t)
    print("Thread {0} depois de {1}s".format(n, t))


if __name__ == "__main__":
	start = time.time()
	processes = []
	for i in range(10):
		p = Process(target=meuprocesso, args=(i,))
		processes.append(p)
		p.start()

	[x.join() for x in processes]
		
	print("Processos Lançados!")
	print("Tempo decorrido: {0}s.".format(time.time() - start))
