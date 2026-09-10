# Distributed systems (Python)

Coursework from the "Distributed Systems Architecture" course at PUCPR (2020): REST services with Flask, message-passing algorithms over RabbitMQ, remote objects with Pyro5, a CORBA interface, and a written design of leader election with ZooKeeper.

For a non-technical reader: these programs make several independent processes cooperate without sharing memory, which is how cloud services, message queues and microservices work.

| Folder | What it is | Stack |
| --- | --- | --- |
| `rest-bank-accounts-api/` | A bank-accounts REST API (team project): create, list, deposit, withdraw and delete accounts, with a decorator (`ExecutionHandler`) that validates ids and amounts before every handler runs. Example `curl` calls are in the source comments. | Flask, Flask-RESTful |
| `rest-echo-service/` | The smallest REST service: `GET /eco` returns a counter, `PATCH /eco/<msg>` echoes the message and increments it | Flask-RESTful |
| `rabbitmq-traversal-algorithm/` | Distributed depth-first traversal: each process is a node with a queue; a `starter` injects the first token, nodes forward `T` (traverse), answer `B` (back edge) and `R` (return) until the initiator reaches state OK | pika / RabbitMQ |
| `rabbitmq-wave-algorithm/` | Wave algorithm (assignment 2): the initiator floods `R` messages, every node collects the ids of its subtree and returns them in `I` messages, so the initiator ends up with the list of every entity in the system. `state-model.txt` gives the state machine | pika / RabbitMQ |
| `rabbitmq-templates/` | Reusable skeletons: a publisher/consumer pair and a homogeneous algorithm template with unicast and flooding broadcast (tracks who already received a message) | pika / RabbitMQ |
| `pyro-distributed-voting/` | Assignment 3: a voting server exposed as a remote object; clients submit votes by name through the name server, the server counts them and elects the majority, with a timeout enforced by `SIGALRM`. `templates/` has the echo client/server the assignment builds on | Pyro5 |
| `corba-account/` | IDL for a bank account remote object (deposit, withdraw, transfer, shutdown) used in the CORBA class | CORBA IDL |
| `postal-code-lookup/` | A one-file client of a public web service that resolves a Brazilian postal code to an address | requests |
| `zookeeper-leader-election.md` | Assignment 1: design of distributed leader election with ephemeral znodes, atomic `set data` and watches | ZooKeeper |

## Run

RabbitMQ examples need a local broker (`docker run -p 5672:5672 rabbitmq:3`) and `pip install pika`; Pyro5 examples need `pip install Pyro5` and a name server (`python -m Pyro5.nameserver`); Flask examples need `pip install flask flask-restful`.

```bash
# traversal: one terminal per node, then the starter
python3 traversal_algorithm.py A B C     # node A with neighbours B and C
python3 traversal_algorithm.py B A
python3 traversal_algorithm.py C A
python3 starter.py A                     # A becomes the initiator
```

All scripts compile with Python 3.13 (`python -m py_compile`).
