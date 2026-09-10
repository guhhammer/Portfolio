# IoT and cyber-physical systems (Python, Raspberry Pi)

Coursework from the "Cyber-Physical Systems Fundamentals" course at PUCPR (2018): number-base conversion warm-ups, MQTT messaging to the Cayenne IoT dashboard, and the team hardware project **Smart Split**, an automatic waste sorter.

For a non-technical reader: a bin that looks at what you throw into it and opens the right compartment (organic or not), built from a Raspberry Pi, a camera and servo motors.

| Item | What it is | Run |
| --- | --- | --- |
| `number_base_conversion.py` | Class 01: decimal to hexadecimal by repeated division, and 3-digit numbers in any base (2 to 16) back to decimal | `python3 number_base_conversion.py` |
| `mqtt_cayenne_sensor_demo.py` | Publishes fake sensor readings to the Cayenne (myDevices) MQTT broker and reacts to button and slider commands from the dashboard; credentials come from `CAYENNE_*` environment variables | `pip install paho-mqtt && python3 mqtt_cayenne_sensor_demo.py` |
| `smart-split/` | Smart Split, with André Wlodkovski and Eleonora Ceola Reis: work plan (goals, methodology, responsibilities, risks), project timeline, sample food images used to test the organic-waste recogniser, and photos of the build (servo motors, painted wooden enclosure, camera and board wiring, finished device) | read `work-plan.docx`; browse `build-photos/` |

The idea came from a "smart cities" video shown in class: a camera on top of the bin captures the object, OpenCV-based image recognition on a Raspberry Pi 3 decides whether it is organic, and a servo opens the door of the matching compartment. The documents are in Portuguese; the 60 original build photos were reduced to seven representative ones.
