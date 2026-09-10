# Exam review: answers

## Exercise 1 (file systems)

- I. True (the usual cluster size is 4K)
- II. True (the wasted space is called slack space)
- III. False (a small cluster increases the complexity of read operations, because many pieces have to be located)
- IV. True
- V. False (fragmentation happens because the system cannot know the final size of a file)
- VI. True

## Exercise 2

- I. True. ext uses a model called i-nodes to map files to clusters.
- II. True
- III. True. NTFS and ext4 are robust against this problem.
- IV. False. NTFS and ext4 can restore the file system, using journaling for example; ext2, ext3 and FAT are subject to these problems.
- V. False. The data must be written to disk and the table that maps clusters to the file must be updated.

## Exercise 3

(e) (f) (i) (f) (i)

## Exercise 4 (file open modes)

- `r`: does not create the file.
- `w+`: always creates a new file.
- `a+`: only creates the file if it does not exist.

Answers: (r), (r or r+), (w, w+, a, a+), (w+, a+), (w+), (a+, r+), (a) opens a file in update mode.

## Exercise 5

I. False; II. True; III. True (BASE64 encodes any binary file as text); IV. False; V. True; VI. True

## Exercise 6

(1) (6) `cd ../..` (4) (5) (3)

## Exercise 7

(a) (d) (c) (b) (c)

## Exercise 8 (synchronization)

(1) mutually exclusive (LOCK) (2) (3) (5) (4)

## Exercise 9 (multicast and broadcast)

(3) (4) (2) the computer joins the group when an application starts (1) broadcast is a permanent group (3) (4) (4)

## Exercise 10 (DNS)

(3) (3) (2) (2) (1) FQDN: Fully Qualified Domain Name (1) implemented for security, not always available (1)

## Exercise 11 (DNS record types)

- (4) NS = Name Server: any DNS server that answers for a domain
- (1) SOA = Start of Authority: only one master per domain
- (2) A = Address
- (3) MX = Mail Exchange
- (5) CNAME = Canonical Name
- (6) PTR = Pointer

## Exercise 12

(3) (3) (5) (2) (1)

## Exercise 13

- I. False: returns only the IP.
- II. True
- III. True
- IV. False: the cache time (TTL) is returned with the query.
- V. False: the DNS server always flags that it answered from cache (not an authoritative answer).

## Exercise 14 (DHCP)

1. False. There is no "default web server", but a default domain can be configured, for example `pucpr.br`.
2. False. The client discovers the DHCP server by broadcast.
3. False. The lease can be renewed indefinitely.
4. False. A relay agent service forwards DHCP requests to a server on another network.

## Exercise 15 (NAT and private addresses)

1. False. A private IP can only be duplicated on different networks separated by the Internet.
2. True, but a forwarding rule can expose a server inside the network.
3. True
4. True
5. False. 169.254.x.x is APIPA: a random address chosen by the computer when DHCP fails.
