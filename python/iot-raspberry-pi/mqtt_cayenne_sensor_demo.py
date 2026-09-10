"""MQTT demo against the Cayenne (myDevices) IoT dashboard - Cyber-Physical Systems Fundamentals (PUCPR, 2018).

Publishes two fake sensor channels (a counter and a "temperature") to the
Cayenne broker once per second and subscribes to three actuator channels
(two buttons and a slider) so that commands sent from the dashboard are
printed and acknowledged. Written for a Raspberry Pi during the Smart Split
project; runs anywhere with `pip install paho-mqtt`.

Credentials come from environment variables (create a device on
https://cayenne.mydevices.com to get them):
    CAYENNE_USERNAME, CAYENNE_PASSWORD, CAYENNE_CLIENT_ID
"""
import os
import paho.mqtt.client as mqtt
import time


username = os.environ.get("CAYENNE_USERNAME", "<your-cayenne-mqtt-username>")
password = os.environ.get("CAYENNE_PASSWORD", "<your-cayenne-mqtt-password>")
client_id = os.environ.get("CAYENNE_CLIENT_ID", "<your-cayenne-client-id>")
server = "mqtt.mydevices.com"
port = 1883

                                                #  "v1/username/things/clientID/data/channel"
channel = 0           #  value
channel1 = 1          #  temperature
channel2 = 2
channel3 = 3          #  button
channel4 = 4          #  button 2
channel5 = 5          #  slider
path = "v1/{}/things/{}/data/{}".format(username, client_id, channel)
path1 = "v1/{}/things/{}/data/{}".format(username, client_id, channel1)
# path2 = "v1/{}/things/{}/data/{}".format(username, client_id, channel2)
path3 = "v1/{}/things/{}/cmd/{}".format(username, client_id, channel3)
path4 = "v1/{}/things/{}/cmd/{}".format(username, client_id, channel4)
path5 = "v1/{}/things/{}/cmd/{}".format(username, client_id, channel5)





def on_message_received(client, userdata, msg, channel=3):
    if channel == 3:
        print(msg.topic.split('/'))
        print(msg.payload.decode().split(','))
        client.publish(path3, 3)
    else:
        print(msg.topic.split('/'))
        print(msg.payload.decode().split(','))
        client.publish(path4, 4)


def read_slider(client, val):
    client.publish(path5, val)


client = mqtt.Client(client_id)
client.username_pw_set(username, password)
client.connect(server, port)



client.on_message = on_message_received
client.subscribe(path3)
client.subscribe(path4)
val = client.subscribe(path5)

client.on_publish = read_slider
client.publish(path5)
client.loop_start()


for i in range(0, 10):
    client.publish(path, i)
    client.publish(path1, 10 - i)
    time.sleep(1)


client.disconnect()
