import socket, sys, threading

semaphore = threading.BoundedSemaphore(2)

def handle_client(conn, addr):
    global semaphore
    while True:
        data = conn.recv(100)
        print('{} sent {}'.format(addr, data))
        if not data:
            break
    print('{} disconnected'.format(addr))
    semaphore.release()


###
###   MAIN:
###


s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
try:
    s.bind(('', 9999))
except OSError:
   print('# bind error')
   sys.exit()

s.listen(5)

print('waiting for connections on', 9999)


while True:
    semaphore.acquire()
    conn, addr = s.accept()
    print('received a connection from client', addr)
    t = threading.Thread(target=handle_client, args=(conn, addr,))
    t.start()
