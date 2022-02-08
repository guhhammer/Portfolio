import socket, sys

def interpretaComando(comando, socket):
    
    global ESTADO
    
    print('\nRecebi o comando: {}\n'.format(comando))

    if comando.lower() == 'ligar':
        ESTADO = "ON"
    elif comando.lower() == 'desligar':
        ESTADO = "OFF"
    elif comando.lower() == 'consulta':
        print('esqueci de fazer o exercicio 3A')
        socket.send(bytes(ESTADO, 'utf-8'))
    else:
        print('comando desconhecido: ', comando)


####################################
####################################

# GLOBAL VARIABLES:
ESTADO = "OFF"


if __name__ == "__main__":

    sensor_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)


    IP = input('\nEntre com o IP do servidor: ')
    PORTA = int(input('\nEntre com a porta do servidor: '))
    ID = input('\nEntre com o ID do sensor: ')

    try:
        sensor_socket.connect((IP, PORTA))
        #Envia o identificador
        sensor_socket.send(bytes(ID,'utf-8'))

    except:
        print('erro de conexao')

    while True:
        try:
            dados = str(sensor_socket.recv(100),'utf-8')
            interpretaComando(dados, sensor_socket)
        except:
            print('Erro na conexão com o monitor')
            sys.exit()