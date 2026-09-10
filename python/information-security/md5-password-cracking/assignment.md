# Hash assignment (translated specification)

**Item 1.** Write a program with two features: register a user and authenticate a user. A user has a name (4-character string) and a password (4-character string). Registrations are stored in a text file. Passwords must be stored as MD5 hashes.

**Item 2.** Find a brute-force algorithm for MD5 and run it against the stored password file. Measure the time needed to break the hashes of four users.

**Item 3.** Implement a solution that reduces the chance of a successful brute-force attack on the program of item 1.

**Deliverables.** Source code of items 1, 2 and 3, and a report describing the procedure, the time needed to break the hashes, and the solution adopted in item 3.

Reference links used: Python file I/O tutorial, `hashlib` documentation, and discussions on brute-forcing MD5, double hashing and salting on Security StackExchange and Crypto StackExchange.
