import socket, sys, threading, time

SENSORES = {}

def recebeComando(s):

    global SENSORES

    while True:
        data, addr = s.recvfrom(100)        
        strdata = str(data,'utf-8')

        try:
            (comando, dado) = strdata.split(' ',1)
        except:
            print('\r\nComando invalido do sensor', addr)
            continue
        if comando == 'REGISTRO':
            SENSORES[dado] = addr
            print('O sensor ' + dado + ' registrou')
        elif comando == 'ESTADO':
            if addr not in SENSORES.values():
                ID = 'DESCONHECIDO'
            for ID,a in SENSORES.items():
                if a == addr:
                    break
            print('\r\nSensor ' + ID + ' enviou ' + dado + '\r\n')

def enviaComando(s):
    dados = input()
    print('...\r\n')
    try:
        (sensor, comando) = dados.split(' ',1)
    except:
        return

    if sensor in SENSORES:
        s.sendto(bytes(comando, 'utf-8'), SENSORES[sensor])
    else:
        print('\r\nEste sensor não existe') 

              

s, porta = socket.socket(socket.AF_INET, socket.SOCK_DGRAM), 10000
         
try:
    s.bind(('', porta))
except:
    print('# erro de bind')
    sys.exit()

t = threading.Thread( target=recebeComando, args=(s,))
t.start()

print('\r\nDigite SENSOR_ID COMANDO')
   
while True:
    enviaComando(s)
    time.sleep(1)

print('\r\no servidor encerrou')
s.close()