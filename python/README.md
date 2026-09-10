# Python

Coursework and projects in Python, from a Computer Science degree at PUCPR and an exchange semester in the United States. Each folder is one course or theme, with its own README, and covers a different use of the language: web and distributed backends, data science and machine learning, algorithms, security, and scientific computing.

For a non-technical reader: Python is used here for almost everything, from building web servers and talking between machines, to analysing millions of rows of data, to teaching a computer to recognise images.

## Backends, distributed systems and networking

- [`distributed-systems/`](distributed-systems/) — REST APIs (Flask), message passing over RabbitMQ, remote objects with Pyro and CORBA, and a ZooKeeper leader election.
- [`networking/`](networking/) — TCP/UDP sockets, a DNS simulator, HTTP labs and Cisco Packet Tracer exercises.
- [`big-data-spark/`](big-data-spark/) — Apache Spark (RDD and SQL) analyses of multi-gigabyte datasets on an HDFS cluster.

## Data science and machine learning

- [`data-science/`](data-science/) — exploratory analysis, statistics and a credit-default prediction project with XGBoost.
- [`computer-vision/`](computer-vision/) — classical image processing and deep learning: coin detection, segmentation and CycleGAN style transfer.
- [`deep-learning/`](deep-learning/) — Keras/TensorFlow fundamentals and a cheese-image classifier served as a web app.
- [`artificial-intelligence/`](artificial-intelligence/) — game-playing AI (checkers, tic-tac-toe) and an expert system.

## Algorithms and theory

- [`algorithms-complexity/`](algorithms-complexity/) — cost analysis, asymptotic notation, recurrences and two lottery set-cover projects.
- [`interpreters-and-compilers/`](interpreters-and-compilers/) — a lexer and parser for the Portugol teaching language, plus natural-language processing.
- [`discrete-math/`](discrete-math/) — counting, proofs, recursion, Boolean algebra and a hashing/cryptography assignment.

## Modelling, simulation and security

- [`systems-performance-modelling/`](systems-performance-modelling/) — probability simulations, queueing theory and Markov chains.
- [`decision-models/`](decision-models/) — linear programming, AHP multi-criteria decisions and Monte Carlo simulation.
- [`information-security/`](information-security/) — classical ciphers, key-distribution centres, MD5 cracking, steganography and RSA timing.
- [`3d-graphics-notebooks/`](3d-graphics-notebooks/) — the vector and matrix mathematics behind computer graphics, in Jupyter.
- [`iot-raspberry-pi/`](iot-raspberry-pi/) — MQTT messaging and the Smart Split waste-sorter hardware project.

## Miscellaneous

- [`challenges/`](challenges/) — small coding challenges and a Kivy app prototype.

Most scripts run with Python 3.13; every `.py` file passes `python -m py_compile`, and the notebooks keep the outputs of their original runs. Course reports and datasets stay in their folders. The same courses that used other languages are cross-linked from here: the AI search algorithms and sorting analysis are in [`../java/`](../java/), and the polyglot coding challenges in [`../haskell/`](../haskell/), [`../cpp/`](../cpp/) and [`../java/`](../java/).
