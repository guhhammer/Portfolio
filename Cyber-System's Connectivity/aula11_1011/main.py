import os, shutil

def exercicio1():
	print("Diretório corrente: ", os.getcwd(), "\n")

	for root, dirs, files in os.walk(".", topdown=False):
		for name in files:
			print(os.path.join(root, name))
		for name in dirs:
			print(os.path.join(root, name))


def exercicio2():
	#a) Imprima o diretório de trabalho corrente
	print("Diretório corrente: ", os.getcwd(), "\n")

	#b) Crie os subdiretórios SUBDIR1 e SUBDIR2
	try:
		os.makedirs('SUBDIR1')
		os.makedirs('.\\SUBDIR2')
	except:
		print("As pastas já existem!")

	#c) Copie o arquivo TEXTO.txt para o SUBDIR1
	shutil.copy2('arquivoTexto.txt', 'SUBDIR1\\arquivoTexto2.txt') # Intepretador pede para escapar a barra.

	#d) Mova o arquivo TEXTO.txt do SUBDIR1 para o SUBDIR2
	shutil.move('SUBDIR1\\arquivoTexto2.txt', "SUBDIR2\\arquivoTexto2.txt")

	#e) Apague o SUBDIR1 e tudo o que estiver dentro
	shutil.rmtree('SUBDIR1')


def exercicio3():

	f = open('teste.txt', 'w')
	f.write('Primeira linha\n')
	f.write('Segunda linha\n')
	try:
		f.read()
	except:
		print("Leitura não permitida.")
	f.close()


	f = open('teste.txt','r')
	print(f.readline())
	print(f.readline())
	try:
		f.write('Terceira linha\n')
	except:
		print("Escrita não permitida.")
	f.close()


	f = open('teste.txt', 'r+')
	print(f.readline())
	f.write('Quarta linha\n')
	print(f.readline())
	f.seek(0,0)
	print(f.readlines())


def exercicio4():
	f = open('teste.txt','a+')
	f.write('Quinta linha\n')
	f.seek(0,0)
	print(f.readlines())
	f.close()

	f = open('teste.txt','w+')
	f.write('Sexta linha\n')
	f.seek(0,0)
	print(f.readlines())
	f.close()

	f = open('teste.txt','r+')
	f.write('Setima linha\n')
	print(f.readlines())
	f.seek(0,0)
	print(f.readlines())
	f.close()


def exercicio5():
	f = open('teste.bin','wb+')
	print(f.write(bytearray([254])))
	print(f.write(bytearray([10,13])))
	print(f.write(bytearray([48,49,50])))
	f.seek(0,0)
	print(f.read())
	try:
		f.write('012')
		f.close()
	except:
		print("O arquivo é binário.")

	f = open('teste.bin','r+')
	print(f.read())
	try:
		f.write(bytearray([48,49,50]))
	except:
		print("O arquivo é binário.")
	
	print(f.write('254'))
	print(f.write('012\n'))
	f.seek(0,0)
	print(f.read())
	f.close()


#exercicio1()
#exercicio2()
#exercicio3()
#exercicio4()
#exercicio5()



# RESPOSTAS:

## Pergunta 1: '.' significa diretório corrente.

## Pergunta 2: O seek é usado para mover o ponteiro do arquivo.
###            O ponteiro é movido para o fim após qualquer operação de escrita.

## Pergunta 3: 'a': cria o arquivo se não existir e altera se não existir.
###            'w': cria um arquivo novo, ele existindo ou não.
###            'r+': não cria o arquivo se não existir e altera se ele existir.


## Pergunta 4: 254 em binário é apenas um byte(0 até 255), 254 em texto são 3 bytes.
###            E o número Pi? Onto flutuante são representados com 64 bits => binário.
