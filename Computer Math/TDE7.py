#encoding:utf-8
import random

x = 0

"""
	Nomes: 
		André Wlodkovski,
		Gustavo Hammerschmidt,
		João Vitor Borges,
		Lucas Marrega,
		Lucca Molina.
		
"""
#Questão 1:

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

# Questão 2:	
		
def generate_random(array,a,c):
	global x
	xNew = (a*array[x][x % len(array[x])] + c)%len(array)
	x = xNew
	return xNew


# Questão 3:

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
	
# Questão 4:

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
    #n = pq
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

def encode_RSA(text, public, private):
	n = public[1]
	e = public[0]
	mapped = map_ascii(text)
	Cs = []
	for i in mapped:
		Cs.append((i**e)%n)
	
	return map_to_ascii(Cs)
	
def decode_RSA(text, public, private):
	n = public[1]
	d = private[0]
	mapped = map_ascii(text)
	Cs = []
	for i in mapped:
		Cs.append((i**d)%n)
		
	return map_to_ascii(Cs)

# Outputs:


print("Integrantes:")
print("André de Macedo Wlodkovski")
print("Gustavo Hammerschmidt")
print("João Vitor Borges")
print("Lucas Eduardo Giovanini Marrega")
print("Lucca Honorio Molina de Araujo","\n")
print("Questão 1: Função Hash")
oi = create_empty_array(100)
for i in range(100):
	store_data(oi,i*33)

print("Array gerado a partir das funções hash:")
print(oi,"\n")

print("Questão 2: Valores aleatórios gerados:")
for i in range(50):
	print(generate_random(oi,2,9),end=" ")
	
print("\n")

print("Questão 3: Cifra de César")

msg = "comi laranja demais"
chave = 5
msg_codificada = codificar(msg,chave)
print("\nCodificar mensagem: {} \t| Chave: {} \n\n\t mensagem codificada: {}".format(msg,chave,msg_codificada))


print("\nDecodificar mensagem: {} \t| Chave: {} \n\n\t mensagem decodificada: {}".format(msg_codificada,chave,decodificar(msg_codificada,chave)),"\n")

print("Questão 4: RSA")

public, private = generate_keypair(17,31)
msg = encode_RSA("Boi da cara preta", public, private)
print("Mensagem codificada:",msg)
print("Mensagem decodificada:",decode_RSA(msg, public, private))



