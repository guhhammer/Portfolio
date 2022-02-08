import socket, sys

class sensor():
    def __init__(self, server_ip, server_porta, sensor_id):
        self.ESTADO, self.switch_table, self.sensor_socket = "OFF", dict(), None
        self.IP, self.porta, self.id = server_ip, server_porta, sensor_id

    def switch_table_set(self):
        self.switch_table["ligar"] = "ON"
        self.switch_table["desligar"] = "OFF"

    def interpretaComando(self, comando, socket):
        
        print('\nCommand received: {}\n'.format(comando))
        
        if comando in self.switch_table.keys():
            self.ESTADO = self.switch_table[comando.lower()]
        elif comando.lower() == 'consulta':
            socket.send(bytes(self.ESTADO, 'utf-8'))
        else:
            print("\nUnknown command: ", comando)
        
    def connecting(self):
        self.sensor_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            self.sensor_socket.connect((self.IP, self.porta))
            self.sensor_socket.send(bytes(self.id,'utf-8'))  # Envia o identificador
        except:
            print("\nConnection Error!\n")

    def run(self):
        self.connecting()
        
        while True:
            try:
                dados = str(self.sensor_socket.recv(100), "utf-8")
                print(dados)
                self.interpretaComando(dados, self.sensor_socket)
            except:
                print("\nMonitor connection error!\n")
                sys.exit()

    def start(self):
        print("\nServer IP: {}.\nServer port: {}.\nSensor ID: {}.".format(self.IP, self.porta, self.id))
        self.switch_table_set()
        self.run()
        
if __name__ == "__main__":
    my_sensor = sensor("127.0.0.1", 9999, "sala")
    my_sensor.start()
