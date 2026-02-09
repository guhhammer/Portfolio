
# NOME: Gustavo Hammerschmidt.

print("\n\n NOME: Gustavo Hammerschmidt. ")
print("\n\n Todos os Exercícios do slide pré Exercícios-contagem: \n\n")


#--------------------------------------------------------------------------------------------------
"""
1a) Numa cidade as placas dos veículos consistem de duas
letras seguidas por três números. Quantas placas são
possíveis de existirem nessa cidade?
"""


#1A)

# Total de possibilidades:     _ _  _ _ _   (Letra Letra Número Número Número)   .

#  (Letra Letra Número Número Número) ->  (26 26 10 10 10) -> 676.000 possibilidades.

# Letras -> 26; Números -> 10.

def possibilidade_A(Letra, Numero):
	return Letra * Letra * Numero * Numero * Numero


print("\n1A total de possibilidades -> ",possibilidade_A(26,10),"\n")
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------

"""
1b) Um pesquisador necessita visitar as capitais do Brasil para
uma análise populacional. De quantas maneiras ele pode
fazer isso?
"""

#1B)  Fatorial de 27 !     ( primeira  e segunda e terceira ...) ->  (1 * 2 * 3 * ... * 27) -> (27!)  .

#    27!   ->     1.088886945 * (10 ^ 28)    ->   10888869450418352160768000000 possibilidades.

def possibilidade_B(N):
	aux = 1
	for i in range(1,N+1):
		aux *= i
	return aux


print("1B total de possibilidades -> ",possibilidade_B(27),"\n")
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
1c) Uma senha é criada através dos elementos de um conjunto
de 8 caracteres especiais e de um conjunto de dígitos. O
fomato permitido de uma senha é: quatro dígitos seguidos de
2 caracteres especiais. Quantas senhas são possíveis de
serem definidas? 
"""

#1C)  Conjunto de dígitos (CD),  8 caracteres especiais (8)      ->    (CD CD CD CD 8 8)

#    (10 10 10 10 8 8)  ->  640.000 possibilidades.

# Conjunto de dígitos -> CD; caracteres especiais -> CE

def possibilidade_C(CD, CE):
	return CD * CD * CD * CD * CE * CE


print("1C total de possibilidades -> ",possibilidade_C(10,8),"\n")
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
2a) Um estudante pode escolher um projeto de computação a
partir de quatro listas. As quatro listas contêm 17, 28, 7, 20
projetos possíveis respectivamente. Nenhum projeto está em
mais de uma lista. Quantos projetos existem para serem
escolhidos?
"""

# soma ->  2A e 2B	
def soma(lis):
	aux = 0 
	for i in lis:
		aux += i
	return aux	
	
print("Possibilidades 2A -> ",soma([17,28,7,20])," possibilidades. \n")	
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
2b) Num restaurante existem 18 refeições de carne, 10 de
peixe e 5 vegetarianas. Quantas refeições podem ser
escolhidas? 
"""

print("Possibilidades 2B -> ",soma([18,10,5])," possibilidades. \n")
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
3a) Lana quer ir para Manaus. Ela pode escolher entre 3
serviços de ônibus ou 2 serviços de avião para ir de casa até
Cuiabá. De lá, ela pode escolher entre 2 serviços de ônibus
ou 3 serviços de avião para ir até Manaus. Quantas maneiras
existem para Lana chegar a Manaus?
"""

# 3A

def prod(A,B):
	return A*B
	
print("Possibilidades 3A -> ",soma([prod(3,2), prod(3,3), prod(2,3), prod(2,2)])," possibilidades. \n")
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
3b) As senhas num sistema consistem em cadeias de 6 a 8
caracteres.
l Cada caractere é uma letra ou um dígito.
l Cada senha deve conter pelo menos um dígito.
l Quantas senhas são possíveis? 
"""

# 3B	
def produto(lis):
	aux = 1
	for i in lis:
		aux *= i
	return aux
	
	
print("Possibilidades 3B -> ",
soma( [(produto([36,36,36,36,36,36])-produto([26,26,26,26,26,26])),
	   (produto([36,36,36,36,36,36,36])-produto([26,26,26,26,26,26,26])),
	   (produto([36,36,36,36,36,36,36,36])-produto([26,26,26,26,26,26,26,26]))])," possibilidades. \n")
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
4a) Quantos inteiros entre 1 e 1500 são divisíveis por 3 ou 5?
"""


print("Possibilidades 4A (divisíveis ou por 3 ou por 5)-> ",(1500/3 + 1500/5 - 1500/15)," possibilidades. \n")
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""     

Inclusão-Exclusão: Definição
      - Número de maneiras de escolher um elemento de A1 ou de A2:
        |A1 (união) A2| = |A1| + |A2| - |A1 (disjunção) A2| 


a) Carlos estájogando "cara-ou-coroa". Cada lançamento
resulta em cara (C) ou coroa (K). De quantas formas ele pode
lançar a moeda cinco vezes sem obter duas caras
consecutivas? 

Questão A:

                  
Árvore com todas as possibilidades:                         
													*
                           /                                                \
			              C                                                  K
		      /                      \                           /                      \
		     C                        K                         C                        K
	   /          \             /          \      	      /          \             /          \ 
	  C            K    	   C            K            C            K    	      C            K
    /   \	     /   \       /   \	      /   \        /   \	    /   \       /   \	     /   \
   C     K      C     K     C     K      C     K      C     K      C     K     C     K      C     K
  / \   / \    / \   / \   / \   / \    / \   / \    / \   / \    / \   / \   / \   / \    / \   / \
 C   K C   K  C   K C   K C   K C   K  C   K C   K  C   K C   K  C   K C   K C   K C   K  C   K C   K


Árvore com todas as possibilidades respeitando a ordem da questão:                         
													*
                           /                                                \
			              C                                                  K
		                             \                           /                      \
		                              K                         C                        K
	                            /          \      	                 \             /          \ 
	                   	       C            K                         K    	      C            K
       	                         \	      /   \              	    /   \           \	     /   \
                                  K      C     K                   C     K           K      C     K
                                 / \      \   / \                   \   / \         / \      \   / \
                                C   K      K C   K                   K C   K       C   K      K C   K
								
            Possibilidades ->  CKCKC, CKCKK, CKKCK, CKKKC, CKKKK, KCKCK, KCKKC, KCKKK, KKCKC, KKCKK, KKKCK,KKKKC, KKKKK
			Possibilidades ->   13 Possibilidades.

"""
#--------------------------------------------------------------------------------------------------

print("\n\nExercícios-Contagem (1 a 9): \n\n")

#--------------------------------------------------------------------------------------------------
"""
1) Suponha que os quatro últimos dígitos de um número de
telefone precisam incluir, pelo menos, um dígito repetido.
Quantos números deste tipo existem?

"""

# 1 -> Número de telefone ->  exemplo: _ _ _ _ ->  total de tel. -> 10 ^4 | tel. sem repetição -> 10*9*8*7
def ex1():
	print("\nNúmero de telefones (exercício 1): ",(10**4 - 10*9*8*7), " telefones. \n")

ex1()
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
2) Quantos inteiros de quatro dígitos (números entre 1000 e
9999) são pares?
"""

# 2 -> entre 1000 e 9999 que são pares ->   _ _ _ *  (* -> so valores como 0, 2, 4, 6 e 8 (5 números no total))
#      9999 - 1000 ->  8999 / 2 -> 4449 números.
def ex2():
	print("\nNúmero de números pares entre 1000 e 9999 (exercício 2): ",((9999-1)-1000)/2," números. \n")

ex2()
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
3) Quantos números de CPF são possíveis?

"""

# 3 -> Quantos números de CPF são possíveis?   _ _ _ . _ _ _ . _ _ _ - _ _  -> 10 10 10 10 10 10 10 10 10 10 10 -> 10^11 
def ex3():
	print("\nNúmeros de CPF possíveis (exercício 3): ",10**11," possibilidades. \n")

ex3()
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
4) Um jogo de computador é iniciado fazendo-se seleções em
cada um dos três menus. O primeiro menu (número de
jogadores) tem quatro opções, o segundo menu (nível de
dificuldade do jogo) tem oito, e o terceiro menu (velocidade)
tem seis. Com quantas configurações o jogo pode ser
jogado?

"""

# 4 ->  Primeiro : 4 , Segundo : 8, terceiro : 6
def ex4():
	print("\nNúmero de configurações (exercício 4): ",4*8*6," configurações. \n")
	
ex4()
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
5) O nome de uma variável em BASIC precisa ser ou uma
letra simples ou uma letra seguida de outra letra ou dígito.
Quantas variáveis são possíveis de serem definidas?
"""

#5 -> ou letra ou letra seguida de letra ou dígito  ->  26 + 26 * 36
def ex5():
	print("\nNúmero de variáveis (exercício 5): ", 26+26*36," variáveis.\n")

ex5()
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
6) Qual o valor de Contador após a execução do seguinte
trecho de programa? 
"""

def ex6():
	print("Valor do contador depois dos loops (exercício 6):  contador = ",3*5,". \n")

ex6()
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
7) Uma eleição é feita através de pedaços de papel verde,
branco e azul que devem ser colocados em uma cartola.
Essas tiras de papel são retiradas uma de cada vez, e a
primeira cor que receber dois votos ganha. Desenhe uma
árvore de decisão para encontrar o número de maneiras que
o resultado da votação pode ocorrer. 
"""


"""
A -> Azul, B -> Branco, V -> Verde.

                                                 *
                  /                              |                               \
                 A                               B                                V
		 /       |         \            /        |         \            /         |          \
		A	 	 B          V          A	 	 B          V          A          B           V
			   / | \      / | \      / | \                / | \      / | \      / | \
			  A	 B  V    A  B  V    A  B  V              A  B  V    A  B  V    A  B  V
				   /|\     /|\           /|\            /|\           /|\     /|\
				  A B V   A B V         A B V          A B V         A B V   A B V
				 
"""

# Número de maneiras em que o resultado da votação pode ocorrer: 33 maneiras.

def ex7():
	print("\nNúmero de maneiras em que o resultado da votação pode ocorrer (exercício 7): 33 maneiras.")


ex7()
#--------------------------------------------------------------------------------------------------

#--------------------------------------------------------------------------------------------------
"""
8) Desenhe uma árvore de decisão (use os times A e B) para
encontrar o número de maneiras que as partidas de basquete
podem ocorrer, onde o vencedor é o primeiro time a vencer
quatro partidas de sete. 


"""

"""                                                      
                     
Lado Esquerdo da árvore:
					 
                     													 * 
                                                         /
								                        A					
				    /                                                                  \     
				  A                                                                      B        
		 /                 \                                          /                                    \
	   A                    B                                        A                                       B 
	 /   \          /                  \                     /                \                     /                 \     
	A     B        A                    B          		    A                  B                   A                    B
		 / \      / \              /         \   		   / \	          /         \         /         \            /    \
		A   B    A   B            A           B  	      A   B          A           B       A           B          A       B
		   / \      / \          / \         / \ 			 / \        / \         / \     / \         / \       /   \
		  A	  B    A   B        A   B       A   B			A   B      A   B       A   B   A   B       A   B     A     B
			 / \      / \          / \     / \   			   / \        / \     / \         / \     / \       / \
			A   B    A   B        A   B   A   B               A   B  	 A   B   A   B     	 A   B   A   B	   A   B    
			

Lado Direito da árvore (sem espelhamento) :
					 
                     													 * 
                                                         /
								                        B					
				    /                                                                  \     
				  B                                                                      A        
		 /                 \                                          /                                    \
	   B                    A                                        B                                       A 
	 /   \          /                  \                     /                \                     /                 \     
	B     A        B                    A          		    B                  A                   B                    A
		 / \      / \              /         \   		   / \	          /         \         /         \            /    \
		B   A    B   A            B           A  	      B   A          B           A       B           A          B       A
		   / \      / \          / \         / \ 			 / \        / \         / \     / \         / \       /   \
		  B	  A    B   A        B   A       B   A			B   A      B   A       B   A   B   A       B   A     B     A
			 / \      / \          / \     / \   			   / \        / \     / \         / \     / \       / \
			B   A    B   A        B   A   B   A               B   A  	 B   A   B   A     	 B   A   B   A	   B   A    
			


"""


# Número de maneiras em que o resultado de vitória pode ocorrer: 70 maneiras.

def ex8():
	print("\nNúmero de maneiras em que o resultado de vitória pode ocorrer (exercício 8): 70 maneiras.")

ex8()

#--------------------------------------------------------------------------------------------------

#-------------------------------------------------------------------------------------------------- 
"""
9) Os principais endereços de computadores são de um dos três tipos:
   - Classe A: endereço contendo un netid de 7 bits (netid ≠ 17), e um hostid de 24 bits.
   - Classe B: endereço com um netid de 14 bits, e um hostid de 16 bits.
   - Classe C: endereço com um netid de 21 bits, e um hostid de 8 bits.
  
   - Hostids que são todos 0s ou 1s não são permitidos.
   - Quantos endereços de IP são válidos?

    Classe A total de IPS ->  (2 ** 7) * (2 ** 24) 
    Classe B total de IPS ->  (2 ** 14) * (2 ** 16)
    Classe C total de IPS ->  (2 ** 21) * (2 ** 8)	
	
	
	Hostids que são todos 0s ou 1s não são permitidos:
		Classe A -> (000.000.000), (111.111.111)
	    Classe B -> (000.000), (111.111)
		Classe C -> (000), (111)
		
	Classe A total ->  (2 ** 7) * (2 ** 24)  - (2 ** 7) * 2  
    Classe B total ->  (2 ** 14) * (2 ** 16) - (2 ** 14) * 2
    Classe C total ->  (2 ** 21) * (2 ** 8)  - (2 ** 21) * 2 
	
	
"""
def ex9():
	print("\n(exercício 9)\n Classe A total de IPs: ", (2**7)*(2**24)," IPs. \n",
	"Classe B total de IPs: ", (2**14)*(2**16)," IPs. \n",
	"Classe C total de IPs: ", (2**21)*(2**8)," IPs. \n\n")
	print("(exercício 9)\n Classe A total (IPs sem 0s ou 1s): ", (2**7)*(2**24)-((2**7)*2)," IPs.\n",
	"Classe B total (IPs sem 0s ou 1s): ", (2**14)*(2**16)-((2**14)*2)," IPs. \n",
	"Classe C total (IPs sem 0s ou 1s): ", (2**21)*(2**8)-((2**21)*2)," IPs. \n\n")
	
ex9()
#--------------------------------------------------------------------------------------------------

#  Dúvidas :
#  			Por quê, quando você divide 1500 / 3 por exemplo, o resultado é a quantidade 
#           de números que são divisíveis por 3 em 1500 (qual a lógica por trás disso)? 





