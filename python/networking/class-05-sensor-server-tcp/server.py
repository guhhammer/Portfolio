import socket, sys, threading

#--------------------------------------------------------------
# FUNCTIONS

def handle_sensor(conn, addr):

    global SENSORS

    print('A thread was created for:', addr)

    # The sensor must send its ID right after connecting
    sensor = conn.recv(10)
    SENSORS[sensor] = conn

    print('sensor', sensor, 'registered on socket', conn)

    # QUERY is a message of the application protocol
    # ... a string must be turned into bytes before it is transmitted

    conn.send(bytes('QUERY', 'utf-8'))
    while True:
        try:
            data = conn.recv(100)
        except OSError:
            print("The client closed with RESET")
            return

        print('sensor', sensor, 'sent', data)

        if not data:
            break

    conn.close()
    print('Sensor', sensor, 'disconnected')

#--------------------------------------------------------------
# MAIN PROGRAM

HOST = ''               # ANY_IP = every IP of the host
SENSORS = {}            # connected sensors
CONSOLE = None          # connection with the remote console

print('Enter the server port')
PORT = int(input())

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
try:
    s.bind((HOST, PORT))
except OSError:
   print('# bind error')
   sys.exit()

s.listen(5)

print('waiting for connections on', PORT)

#--------------------------------------------------------------
# LOOP that serves the clients

while True:
    conn, addr = s.accept()
    print('received a connection from sensor', addr)

    t = threading.Thread(target=handle_sensor, args=(conn, addr,))
    t.start()

#--------------------------------------------------------------

print('the server stopped')
s.close()
