# Networking (Python)

Socket programming and operating-system fundamentals from two courses: the "Cyber-Systems Connectivity" course at PUCPR (2020, class scripts translated from Portuguese) and "Introduction to Networking" from my 2020 exchange semester in the United States (taught in English), whose main deliverable is a small DNS system simulated with UDP sockets.

For a non-technical reader: these programs show how two computers talk to each other at the lowest practical level (TCP and UDP sockets), how a server handles many clients at once (threads, locks, semaphores), and how name resolution (DNS) works.

## PUCPR course: class by class

| Folder | Topic | Files |
| --- | --- | --- |
| `class-03-tcp-echo/` | First TCP client and server; ephemeral ports, bind errors, privileged ports | `client.py`, `server.py`, `report.md` |
| `class-04-processes-and-threads/` | Processes vs threads, shared variables, race conditions, locks, a file-backed counter | six scripts, `answers.md` |
| `class-05-sensor-server-tcp/` | A monitor server that spawns one thread per connected sensor; sensors register with an ID | `server.py`, `client.py`, `sensor.py`, `report-part-1.md` |
| `class-06-monitor-and-sensor-classes/` | The same system as classes: `Monitor` (sensor threads + a console port that forwards `SENSOR_ID COMMAND`) and `Sensor` (on/off/query state machine) | `monitor.py`, `sensor.py` |
| `class-07-udp-monitor-sensor/` | The monitor/sensor protocol over UDP: `REGISTER`, `STATE`, on/off/query | `monitor_udp.py`, `sensor_udp.py`, `client.py`, `server.py` |
| `class-08-exercise-lists/` | Theory exercises: transport-layer services, ports, TCP handshake | `answers-list-1.md`, handwritten answers (PDF) |
| `class-09-assessments/` | Two in-class assessments (scanned answers) | JPG |
| `class-10-udp-broadcast-multicast/` | Broadcast discovery of the monitor with an acknowledgement, a multicast receiver, a broadcast transmitter | six scripts, `answers.md` |
| `class-11-files-and-directories/` | `os`, `shutil`, text and binary file modes, `seek` | `main.py` with answers at the end |
| `class-12-synchronization-exercises/` | Lock, bounded semaphore, `Event` and `Condition` on producer/consumer problems | four scripts |
| `class-13-exam-review/` | Answers to the review list: file systems, open modes, multicast, DNS records, DHCP, NAT | `exam-review-answers.md` |
| `assignments/` | Two graded Linux assignments (system inspection; users, groups and permissions) with their reports | specs (PDF) and reports (Markdown) |
| `packet-tracer/` | Cisco Packet Tracer topologies from the first classes | `.pkt` |

Run any class script with `python3 file.py`; servers and monitors first, then clients and sensors, each in its own terminal. All scripts compile with Python 3.13 (`python -m py_compile`).

## Exchange course: DNS simulator

[`exchange-intro-to-networking/dns-simulator/`](exchange-intro-to-networking/dns-simulator/) is a project that simulates a local DNS server forwarding to two authoritative servers ("Qualcomm" and "Viasat"), all over UDP:

- `DNSmessage.py` defines a binary message format (transaction id, query/response flag, type flag, name and value lengths).
- `RRTable.py` and `AbsTable.py` implement a resource-record table with TTL expiry and static (administrator) records.
- `AbstractServer.py` / `AbstractClient.py` hold the shared behaviour; `Localserver.py`, `Qualcommserver.py`, `Viasatserver.py`, `Client.py` and `Admin.py` specialise it (the admin can add and remove records remotely).
- `HOW-TO-RUN.txt`, the written `report.docx` and `screenshots/` document the test scenarios.

Start the three servers, then the client and admin, each in its own terminal (`python3 Qualcommserver.py`, and so on).

The same folder keeps the graded homework (network architectures, delay and throughput calculations written in LaTeX, subnetting, fragmentation and routing) under `homework/`, and Wireshark lab captures under `labs/`.
