import random, threading, time

queue, results = [], []
event = threading.Event()

def consumer():
    global queue, results

    while True:
        try:
            x = queue.pop(0)
            print('\nCONSUMER: processing task {}'.format(x))
            time.sleep(2)
            results.append(x)
        except IndexError:
            event.set()
            time.sleep(2)

def producer():
    global queue, results

    for i in range(10):
        if len(queue) >= 2:
            event.clear()
            event.wait()
        queue.append(i)
        time.sleep(random.random())
        print('PRODUCER: pending tasks {}'.format(queue))

    while True:
        print('PRODUCER: finished tasks {}'.format(results))
        if len(queue) == 0:
           break
        time.sleep(1)


t1 = threading.Thread(target=consumer)
t2 = threading.Thread(target=producer)

t1.start()
t2.start()
