# Class 10 answers: broadcast and multicast

## Exercise 1

**A) Can broadcast messages be sent without setting SO_BROADCAST?** Yes, for every broadcast address except 255.255.255.255.

**B) After fixing the broadcast option, the address the server saw for the client:**

- Loopback broadcast 127.255.255.255: loopback interface 127.0.0.1
- Local network broadcast (found with `route print -4` on Windows or `route -n` on Linux/macOS): 192.168.1.102
- Generic broadcast 255.255.255.255: all interfaces

**C) Why did the source address shown by the server change?** Each kind of broadcast leaves through a specific interface: 127.255.255.255 uses loopback, 192.168.1.255 uses the physical interface, 255.255.255.255 uses all of them. Note: if no application listens on port 9999, the operating system, not the network card, discards the packet.

## Exercise 2

1. Can the multicast receiver receive messages sent to ports other than the one it bound? No.
2. Can it also receive unicast messages? Yes.
3. Best strategy to split the receivers of a streaming application into groups: different ports or different multicast groups? Always multicast groups: (1) the network interface discards the message on hosts without a program for it; (2) on a wired network the switch does not even forward the packet to computers outside the group.

## Exercise 3

**A) Advantage of sensors locating the monitor by broadcast:** they no longer need manual configuration.

**B) Is there a risk of a sensor connecting to the wrong monitor?** Yes, any host answering on the port could be taken for the monitor; the fix is an identifier or shared key in the registration handshake so the sensor only accepts the expected monitor.
