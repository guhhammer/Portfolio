import socket, sys, time

STATE, MONITOR_IP, MONITOR_PORT = 'OFF', '127.0.0.1', 9999

ID = input('Enter the sensor name: ')

def interpret_command(command, addr):
    global STATE
    strcommand = str(command, 'utf-8').lower()
    print('Received the command', strcommand)
    if strcommand == 'on':
        STATE = 'ON'
    elif strcommand == 'off':
        STATE = 'OFF'
    elif strcommand == 'query':
        s.sendto(bytes('STATE ' + STATE, 'utf-8'), addr)
    else:
        print('unknown command')

def register_sensor(s, ip, port):
	s.sendto(bytes('REGISTER ' + ID, 'utf-8'), (ip, port))
	s.setblocking(0)
	time.sleep(5)
	try:
	    data, addr = s.recvfrom(1024)
	    strdata = str(data, 'utf-8')
	    if strdata == 'ACKregister':
	        print('registered on monitor', addr)
	        return True
	    else:
	        return False
	except OSError:
	    print('\nthe monitor is off')
	    return False

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

while True:
    if register_sensor(s, MONITOR_IP, MONITOR_PORT):
        break

s.setblocking(1)

while True:
    data, addr = s.recvfrom(1024)
    interpret_command(data, addr)

print('the sensor stopped')
s.close()
