import threading, time, random

# Race condition on purpose: every thread reads numthreads before sleeping,
# so the final count is wrong (see answers.md, C.1).
def my_thread(n):
    global numthreads
    t = random.randint(1, 3)
    time.sleep(t)
    numthreads += 1
    print("Thread {0} after {1}s\n".format(n, t))

numthreads, threads = 0, []
for i in range(10):
    t = threading.Thread(target=my_thread, args=(i,))
    threads.append(t)
    t.start()

[x.join() for x in threads]

print("{0} threads launched!".format(numthreads))
