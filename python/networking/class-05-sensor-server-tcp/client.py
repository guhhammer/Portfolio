import socket
import sys

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

print('Enter the server IP')
IP = input()

print('Enter the server port')
PORT = int(input())

print('Enter the sensor ID')
ID = input()

try:
    s.connect((IP, PORT))
except OSError:
    print('connection error')

# Sends the ID automatically right after connecting
s.send(bytes(ID, 'utf-8'))

while True:
    try:
        line = input()
        if not line:
            print('an empty line ends the program')
            break
    except KeyboardInterrupt:
            print('program aborted with CTRL+C')
    data = bytes(line, 'utf-8')
    size = s.send(data)

    print('sent', size, 'bytes')
    print(data)
