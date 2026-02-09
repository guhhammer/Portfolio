import socket, threading

semaphore = threading.BoundedSemaphore(2)

def TrataCliente(conn, addr):
    global semaphore
    while True:
        data = conn.recv(100)
        print('{} enviou {}'.format(addr,data))
        if not data:
            break
    print('{} encerrou'.format(addr))
    semaphore.release()


###
###   MAIN:
###


s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
try:
    s.bind(('', 9999))
except:
   print('# erro de bind')
   sys.exit()

s.listen(5)

print('aguardando conexoes em ', 9999)


while True:
    semaphore.acquire()
    conn, addr = s.accept()
    print('recebi uma conexao do cliente ', addr)
    t = threading.Thread(target=TrataCliente, args=(conn,addr,))
    t.start()



