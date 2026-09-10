import socket, sys

def define_monitor(_ip='127.0.0.1', _port=9999, _ID='room'):
	return _ip, _port, _ID

def interpret_command(command, addr):
	global STATE

	strcommand = str(command, 'utf-8').lower()

	print('Received the command', strcommand)

	STATE = ("ON" if strcommand == "on" else ("OFF" if strcommand == "off" else STATE))
	if strcommand == 'query':
	    s.sendto(bytes('STATE ' + STATE, 'utf-8'), addr)
	else:
	    print('unknown command:', command)


s, STATE = socket.socket(socket.AF_INET, socket.SOCK_DGRAM), "OFF"

ip, port, ID = define_monitor()

print(ip, port, ID)

s.sendto(bytes('REGISTER ' + ID, 'utf-8'), (ip, port))

while True:
    data, addr = s.recvfrom(1024)
    interpret_command(data, (ip, port))

print('the client stopped')
s.close()
