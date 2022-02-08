'''
The code below was extracted from: https://gist.github.com/JonCooperWorks/5314103
'''

import random



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
        
        x = x2- temp1* x1
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
    
    n = p * q

    #Phi is the totient of n
    phi = (p-1) * (q-1)

    #Choose an integer e such that e and phi(n) are coprime
    e = random.randrange(1, phi)

    #Use Euclid's Algorithm to verify that e and phi(n) are comprime
    g = gcd(e, phi)
    while g != 1:
        e = random.randrange(1, phi)
        g = gcd(e, phi)

    #Use Extended Euclid's Algorithm to generate the private key
    d = multiplicative_inverse(e, phi)
    
    #Return public and private keypair
    #Public key is (e, n) and private key is (d, n)
    return ((e, n), (d, n))

   
#=====================
# Execucao do programa
#=====================

print ("RSA Encrypter/ Decrypter")
p = int(input("Digite um numero primo (17, 19, 23, etc): "))
q = int(input("Digite um numero primo diferente do anterior: "))
print ("Gerar as chaves publico e privada a partir de p e q . . .")
public, private = generate_keypair(p, q)
print ("Chave publica = ", public ," Chave privada = ", private)







alfa = ['a','b','c','d','e','f','g','h','i','j',
        'k','l','m','n','o','p','q','r','s','t',
		'u','v','w','x','y','z',' ']

def index(a):
	for i in range(0,len(alfa)):
		if a == alfa[i]:
			return i


def hash(i): return i % len(alfa)


def codificar(msg,chave):
	aux = []
	for i in msg:
		aux.append(alfa[hash(index(i)+chave)])
	return ''.join(aux)



def decodificar(msg,chave):
	aux = []
	for i in msg:
		aux.append(alfa[hash(index(i)-chave)])
	return ''.join(aux)
	
	

# Digite uma mensagem em formato texto

#TODO

# Criptografar a mensagem

#TODO

# Descriptografar a mensagem

#TODO


