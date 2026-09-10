import random, threading, time

queue, results = [], []
condition = threading.Condition()

def consumer():
    global queue, results

    while True:
        try:
            x = queue.pop(0)
            print('\nCONSUMER: processing task {}'.format(x))
            time.sleep(2)
            results.append(x)
        except IndexError:
            condition.acquire()
            condition.notify()
            condition.release()
            time.sleep(2)

def producer():
    global queue, results

    for i in range(10):
        if len(queue) >= 2:
            condition.acquire()
            condition.wait()
            condition.release()
        queue.append(i)
        time.sleep(random.random())
        print('PRODUCER: pending tasks {}'.format(queue))

    while True:
        print('PRODUCER: finished tasks {}'.format(results))
        if len(queue) == 0:
           break
        time.sleep(1)

def producer2():
    global queue, results

    for i in range(11, 20):
        if len(queue) >= 2:
            condition.acquire()
            condition.wait()
            condition.release()
        queue.append(i)
        time.sleep(random.random())
        print('PRODUCER2: pending tasks {}'.format(queue))

    while True:
        print('PRODUCER2: finished tasks {}'.format(results))
        if len(queue) == 0:
           break
        time.sleep(1)


t1 = threading.Thread(target=consumer)
t2 = threading.Thread(target=producer)
t3 = threading.Thread(target=producer2)


t1.start()
t2.start()
t3.start()
