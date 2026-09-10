# Class 3 report: TCP sockets

## Question 1

**a) Ports obtained in the test**

```
Received a connection from ('127.0.0.1', 59757)
Received a connection from ('127.0.0.1', 59758)
Received a connection from ('127.0.0.1', 59759)
Received a connection from ('127.0.0.1', 59760)
```

**b) What are these ports?** Client-side application ports, known as dynamic or ephemeral ports.

## Question 2

**a) Two servers on the same port at the same time:** bind error. Two processes cannot use the same port.

**b) Using a port below 1024 (for example 700):** those ports require administrator permission.

## Question 3

**a) Connecting two PuTTY clients at the same time:** the second PuTTY waits in the backlog until the first one is served.

b) What happens when the first PuTTY is closed; c) how the content arrives with a small server buffer (10 bytes); d) with a large buffer (10000), make the server wait for keyboard input before reading, send three messages from PuTTY, and observe: the three messages are delivered together in one read (TCP is a byte stream, not a message stream).
