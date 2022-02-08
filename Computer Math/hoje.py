
# questão 3:
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
	
	

msg = "comi laranja demais"
chave = 5
msg_codificada = codificar(msg,chave)
print("\nCodificar mensagem: {} \t| Chave: {} \n\n\t mensagem codificada: {}".format(msg,chave,msg_codificada))


print("\nDecodificar mensagem: {} \t| Chave: {} \n\n\t mensagem decodificada: {}".format(msg_codificada,chave,decodificar(msg_codificada,chave)))







# questão 2:

a = 7
x = 0
c = 3
m = 9

def numx(a,x,c,m):
	for i in range(0,m):
		print(a*(i+c) % m)










# questão 4:

















