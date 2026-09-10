# Project-based learning report, part 1: TCP sensors and monitor

**a) Difference between closing a connection with FINISH (FIN) and RESET (RST)?**
With FIN the connection ends because the client application closed it with `close()`; with RST it ends because the client application was aborted before calling `close()` and the operating system reported that the connection was terminated.

**b) Why is it better for the sensors to be clients and the monitor the server? What would be the problem if the monitor were the client and each sensor a server?**
The monitor would need to know the IP address of every sensor.

**c) Meaning of the number N in `socket.listen(N)`?**
N is the application's backlog: the maximum number of pending connections. If more connections than N are waiting, the operating system rejects the extra ones.

**d) Difference between a client socket and a server socket?**
The server socket binds the application to an interface (IP) and a port of the local machine, for example `s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)` followed by `bind`/`listen`; it only accepts connections. The client socket is the `conn` object returned by `s.accept()`: it represents the connection with one client and is used to send and receive data.

```
S -- (server socket) --- | (IP, PORT) | ---- (client socket 1) ---- C1
                                      | ---- (client socket 2) ---- C2
                                      | ---- (client socket 3) ---- C3
```

**e) How does the monitor separate data from different sensors and identify which sensor transmitted?**
Each thread serves a single sensor, identified by its connection `conn`.

**f) What happens to the server thread when a sensor stops?**
`recv` returns empty data (FIN) or raises (RST); the loop ends, the connection is closed and the thread finishes.
