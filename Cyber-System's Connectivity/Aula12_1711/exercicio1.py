import threading, time, random

balance = 100

lock = threading.Lock()

def withdraw():
    global balance
    for _ in range(10):
        lock.acquire()
        x = balance
        time.sleep(random.random()/100)
        balance = x - 1
        lock.release()

def deposit():
    global balance
    for _ in range(9):
        lock.acquire()
        x = balance
        time.sleep(random.random()/100)
        balance = x + 1
        lock.release()
 
def perform_transactions():   
 
    p1 = threading.Thread(target=withdraw)
    p2 = threading.Thread(target=deposit)
 
    p1.start()
    p2.start()
 
    p1.join()
    p2.join()
 
    print("Final balance = {}".format(balance))

for _ in range(10):
    perform_transactions()
    balance = 100
