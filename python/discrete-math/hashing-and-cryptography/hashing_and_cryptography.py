"""Hash table, pseudo-random numbers, Caesar cipher and RSA - assignment 07
(Discrete Problem Solving, PUCPR 2019).

Question 1: a hash table with chaining (key = value mod table size).
Question 2: a linear-congruential pseudo-random generator seeded from the table.
Question 3: Caesar cipher over a 27-symbol alphabet (a-z and space).
Question 4: textbook RSA (key generation adapted from
            https://gist.github.com/JonCooperWorks/5314103) encrypting one
            character at a time.

Team: André Wlodkovski, Gustavo Hammerschmidt, João Vitor Borges,
      Lucas Marrega, Lucca Molina.
"""
import random

x = 0

# Question 1: hash table with chaining

def create_empty_array(m):
    array = []
    for i in range(m):
        array.append(None)
    return array

def store_data(array, data):
    key = data % len(array)
    if array[key] == None:
        array[key] = [data]
    else:
        array[key].append(data)

# Question 2: linear congruential generator over the table contents

def generate_random(array, a, c):
    global x
    x_new = (a * array[x][x % len(array[x])] + c) % len(array)
    x = x_new
    return x_new


# Question 3: Caesar cipher

alphabet = ['a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t',
            'u','v','w','x','y','z',' ']


def index(a):
    for i in range(0, len(alphabet)):
        if a == alphabet[i]:
            return i


def wrap(i): return i % len(alphabet)


def encode(msg, key):
    aux = []
    for i in msg:
        aux.append(alphabet[wrap(index(i) + key)])
    return ''.join(aux)


def decode(msg, key):
    aux = []
    for i in msg:
        aux.append(alphabet[wrap(index(i) - key)])
    return ''.join(aux)

# Question 4: RSA

def map_ascii(text):
    new = []
    for i in text:
        new.append(ord(i))
    return new

def map_to_ascii(_map):
    text = ""
    for i in _map:
        text += chr(i)
    return text

def gcd(a, b):
    while b != 0:
        a, b = b, a % b
    return a

def multiplicative_inverse(e, phi):
    d = 0
    x1 = 0
    x2 = 1
    y1 = 1
    temp_phi = phi

    while e > 0:
        temp1 = temp_phi//e
        temp2 = temp_phi - temp1 * e
        temp_phi = e
        e = temp2

        x = x2 - temp1 * x1
        y = d - temp1 * y1

        x2 = x1
        x1 = x
        d = y1
        y1 = y

    if temp_phi == 1:
        return d + phi

def is_prime(num):
    if num == 2:
        return True
    if num < 2 or num % 2 == 0:
        return False
    for n in range(3, int(num**0.5)+2, 2):
        if num % n == 0:
            return False
    return True

def generate_keypair(p, q):
    if not (is_prime(p) and is_prime(q)):
        raise ValueError('Both numbers must be prime.')
    elif p == q:
        raise ValueError('p and q cannot be equal')
    # n = pq
    n = p * q

    # phi is the totient of n
    phi = (p-1) * (q-1)

    # choose an integer e such that e and phi(n) are coprime
    e = random.randrange(1, phi)

    # use Euclid's algorithm to verify that e and phi(n) are coprime
    g = gcd(e, phi)
    while g != 1:
        e = random.randrange(1, phi)
        g = gcd(e, phi)

    # use the extended Euclidean algorithm to generate the private key
    d = multiplicative_inverse(e, phi)

    # public key is (e, n) and private key is (d, n)
    return ((e, n), (d, n))

def encode_RSA(text, public, private):
    n = public[1]
    e = public[0]
    mapped = map_ascii(text)
    Cs = []
    for i in mapped:
        Cs.append((i**e) % n)

    return map_to_ascii(Cs)

def decode_RSA(text, public, private):
    n = public[1]
    d = private[0]
    mapped = map_ascii(text)
    Cs = []
    for i in mapped:
        Cs.append((i**d) % n)

    return map_to_ascii(Cs)

# Outputs:


print("Team members:")
print("André de Macedo Wlodkovski")
print("Gustavo Hammerschmidt")
print("João Vitor Borges")
print("Lucas Eduardo Giovanini Marrega")
print("Lucca Honorio Molina de Araujo", "\n")
print("Question 1: hash function")
table = create_empty_array(100)
for i in range(100):
    store_data(table, i * 33)

print("Table built with the hash function:")
print(table, "\n")

print("Question 2: generated pseudo-random values:")
for i in range(50):
    print(generate_random(table, 2, 9), end=" ")

print("\n")

print("Question 3: Caesar cipher")

msg = "i ate too many oranges"
key = 5
encoded_msg = encode(msg, key)
print("\nEncode message: {} \t| Key: {} \n\n\t encoded message: {}".format(msg, key, encoded_msg))


print("\nDecode message: {} \t| Key: {} \n\n\t decoded message: {}".format(encoded_msg, key, decode(encoded_msg, key)), "\n")

print("Question 4: RSA")

public, private = generate_keypair(17, 31)
msg = encode_RSA("Hello, world", public, private)
print("Encoded message:", msg)
print("Decoded message:", decode_RSA(msg, public, private))
