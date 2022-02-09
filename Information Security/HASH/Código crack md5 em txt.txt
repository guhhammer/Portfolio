import hashlib
import time
import itertools
import string

#data = [line.strip() for line in open("test_passwords.txt", 'r')]
data = [line.strip() for line in open(raw_input("Enter a file name: "), 'r')]

def crack(target, size=1):
    for xs in itertools.product(string.printable, repeat=size):
        pw = ''.join(xs)
        if hashlib.md5(pw).hexdigest() == target:
            return pw
    return crack(target, size+1)

for line in data:
    t0 = time.clock();
    print crack(line)
    print time.clock() - t0, "seconds elapsed"