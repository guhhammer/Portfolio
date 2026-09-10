"""Brute-force MD5 cracker used for item 2 of the hash assignment (ported from Python 2)."""
import hashlib
import time
import itertools
import string

# One MD5 hash per line, e.g. the hashes stored by the registration program of item 1.
data = [line.strip() for line in open(input("Enter a file name: "), 'r')]


def crack(target, size=1):
    for xs in itertools.product(string.printable, repeat=size):
        pw = ''.join(xs)
        if hashlib.md5(pw.encode()).hexdigest() == target:
            return pw
    return crack(target, size + 1)


for line in data:
    t0 = time.perf_counter()
    print(crack(line))
    print(time.perf_counter() - t0, "seconds elapsed")
