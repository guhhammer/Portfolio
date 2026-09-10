# Exercise list 1: answers

**Exercise 1:** (1) (1) (2) (2) (1) (2)

**Exercise 2:** (1) (1) (2) (2) (1) (1). NetBEUI: the address is the computer name.

**Exercise 3:** (3) (2) (3) (1) (1) (4) (3)

**Exercise 4:** (1) (4)\* (4)\*\* (5) (3) (6)\*\*\* (4)\*\*\*\*

- \* TCP holds back messages that arrive out of order, which hurts real-time display.
- \*\* TCP interprets packet loss as congestion and lowers the transmission rate.
- \*\*\* Broadcast and multicast only work on the LAN.
- \*\*\*\* Multicast saves resources on the server and its network interface.

**Exercise 5:** I. True; II. True; III. False\*; IV. False\*\*; V. False\*\*\*

- \* Ports are 16-bit integers.
- \*\* A few applications send messages encapsulated directly in IP (PING uses ICMP).
- \*\*\* There is no port broadcast: a broadcast reaches every computer on the LAN but is only received on the right port.

**Exercise 6:** (2) (1) (4) (2) (5) (5) (3)

- Flow control: pause transmission if the receiver's buffer is full.
- Congestion control: lower the transmission rate if the network is congested.
- Stream transmission: the OS creates the packets and guarantees bytes are read in order.
- Reliable transmission: retransmit packets that are not acknowledged.

**Exercise 7:** connection start: 1 -> 6 -> 3; transmission: 3 -> 7; connection end: 4 -> 7 -> 8 -> 3.
