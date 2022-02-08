import paho.mqtt.client as mqtt
import time


username = "3d6e2b50-e34c-11e8-810f-075d38a26cc9"
password = "59b873e1bd704f84023d7edaebec98f23ce24610"
client_id = "75128670-e34f-11e8-9c33-75e6b356cec4"
server = "mqtt.mydevices.com"
port = 1883

                                                #  "v1/username/things/clientID/data/channel"
channel = 0          #  valor
channel1 = 1          #  temp
channel2 = 2
channel3 = 3          #  but
channel4 = 4          # tap 2
channel5 = 5          # slde
path = "v1/{}/things/{}/data/{}".format(username, client_id, channel)
path1 = "v1/{}/things/{}/data/{}".format(username, client_id, channel1)
# path2 = "v1/{}/things/{}/data/{}".format(username, client_id, channel2)
path3 = "v1/{}/things/{}/cmd/{}".format(username, client_id, channel3)
path4 = "v1/{}/things/{}/cmd/{}".format(username, client_id, channel4)
path5 = "v1/{}/things/{}/cmd/{}".format(username, client_id, channel5)





def msg_recebida(client, userdata, msg, channel=3):
    if channel == 3:
        print(msg.topic.split('/'))
        print(msg.payload.decode().split(','))
        client.publish(path3, 3)
    else:
        print(msg.topic.split('/'))
        print(msg.payload.decode().split(','))
        client.publish(path4, 4)


def read_slide(client,val):
    client.publish(path5, val)


client  = mqtt.Client(client_id)
client.username_pw_set(username, password)
client.connect(server, port)



client.on_message = msg_recebida
client.subscribe(path3)
client.subscribe(path4)
val = client.subscribe(path5)

client.on_publish = read_slide
client.publish(path5)
client.loop_start()


for i in range(0,10):
    client.publish(path, i)
    client.publish(path1,10-i)
    time.sleep(1)


client.disconnect()

