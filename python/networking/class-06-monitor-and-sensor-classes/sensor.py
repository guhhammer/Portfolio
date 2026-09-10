import socket, sys

class Sensor():
    def __init__(self, server_ip, server_port, sensor_id):
        self.STATE, self.switch_table, self.sensor_socket = "OFF", dict(), None
        self.IP, self.port, self.id = server_ip, server_port, sensor_id

    def switch_table_set(self):
        self.switch_table["on"] = "ON"
        self.switch_table["off"] = "OFF"

    def interpret_command(self, command, sock):

        print('\nCommand received: {}\n'.format(command))

        if command in self.switch_table.keys():
            self.STATE = self.switch_table[command.lower()]
        elif command.lower() == 'query':
            sock.send(bytes(self.STATE, 'utf-8'))
        else:
            print("\nUnknown command: ", command)

    def connecting(self):
        self.sensor_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            self.sensor_socket.connect((self.IP, self.port))
            self.sensor_socket.send(bytes(self.id, 'utf-8'))  # Sends the identifier
        except OSError:
            print("\nConnection Error!\n")

    def run(self):
        self.connecting()

        while True:
            try:
                data = str(self.sensor_socket.recv(100), "utf-8")
                print(data)
                self.interpret_command(data, self.sensor_socket)
            except OSError:
                print("\nMonitor connection error!\n")
                sys.exit()

    def start(self):
        print("\nServer IP: {}.\nServer port: {}.\nSensor ID: {}.".format(self.IP, self.port, self.id))
        self.switch_table_set()
        self.run()

if __name__ == "__main__":
    my_sensor = Sensor("127.0.0.1", 9999, "room")
    my_sensor.start()
