import random, threading, time

fila, resultado = [], []
evento = threading.Event()

def Consumidor():
    global fila, resultado

    while True:
        try:
            x = fila.pop(0)
            print('\nCONSUMIDOR: processando tarefa {}'.format(x))
            time.sleep(2)
            resultado.append(x)
        except:
            evento.set()
            time.sleep(2)
  
def Produtor():
    global fila, resultado
       
    for i in range(10):
        if len(fila) >= 2:
            evento.clear()
            evento.wait()
        fila.append(i)
        time.sleep(random.random())
        print('PRODUTOR: tarefas pendentes {}'.format(fila))

    while True:
        print('PRODUTOR: tarefas terminadas {}'.format(resultado))
        if len(fila) == 0:
           break
        time.sleep(1)
   
       
t1 = threading.Thread(target=Consumidor)
t2 = threading.Thread(target=Produtor)

t1.start()
t2.start()