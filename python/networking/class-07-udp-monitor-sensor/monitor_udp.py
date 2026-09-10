import socket, sys, threading, time

SENSORS = {}

def receive_command(s):

    global SENSORS

    while True:
        data, addr = s.recvfrom(100)
        strdata = str(data, 'utf-8')

        try:
            (command, payload) = strdata.split(' ', 1)
        except ValueError:
            print('\r\nInvalid command from sensor', addr)
            continue
        if command == 'REGISTER':
            SENSORS[payload] = addr
            print('Sensor ' + payload + ' registered')
        elif command == 'STATE':
            if addr not in SENSORS.values():
                ID = 'UNKNOWN'
            for ID, a in SENSORS.items():
                if a == addr:
                    break
            print('\r\nSensor ' + ID + ' sent ' + payload + '\r\n')

def send_command(s):
    line = input()
    print('...\r\n')
    try:
        (sensor, command) = line.split(' ', 1)
    except ValueError:
        return

    if sensor in SENSORS:
        s.sendto(bytes(command, 'utf-8'), SENSORS[sensor])
    else:
        print('\r\nThis sensor does not exist')



s, port = socket.socket(socket.AF_INET, socket.SOCK_DGRAM), 10000

try:
    s.bind(('', port))
except OSError:
    print('# bind error')
    sys.exit()

t = threading.Thread(target=receive_command, args=(s,))
t.start()

print('\r\nType SENSOR_ID COMMAND')

while True:
    send_command(s)
    time.sleep(1)

print('\r\nthe server stopped')
s.close()
