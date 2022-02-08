
"""

Nome: Gustavo Hammerschmidt.

Parte 1:

1)

	Linguagem de Programação: Python.

	a)   Σ = {a,b,c,d,e,f,g,h,i,j,k,l,m,
	          n,o,p,q,r,s,t,u,v,w,x,y,z,
	          0,1,2,3,4,5,6,7,8,9}
			  
	b)	 L = {'def','for','var','int','bin','len'}



2) 

	Alfabeto:  Σ = {0,1}    |    Palavra:  W = 011


	Qual o Valor de W ?   |W| = 3
	
	Se W é uma palavra de Σ*, então, W.W também é uma palavra de Σ*?
		
		- Sim,  o alfabeto Σ* contém todas as combinações do alfabeto Σ.
	
	
	Liste todos os prefixos, sufixos e subpalavras de w.
		
		- Prefixos: ε, 0, 01, 011.
		- Sufixos: ε, 1, 11, 011.
		- Subpalavras: ε, 0, 1, 01, 11, 011.
	
	
	
	
	
3) Dadas as linguagens:
	
	L1 = {w pertence {a, b}*|w contém número ímpar de a's}
	L2 = {w pertence {a, b}*|w contém pelo menos dois a's}

	a) Liste todas as palavras de L1 e L2 possuindo tamanho 3.
	
		abb, bab, bba, aaa,  aab, aba, baa.
		
		L1: abb, bab, bba, aaa.
		L2: aab, aba, baa, aaa.
	
	b) Determine a linguagem resultante após efetuar as operações abaixo.
		
		a) L1∪L2:
			
			L1∪L2 = {w pertence {a, b}*|w contém número ímpar de a's v w contém pelo menos dois a's}
			
			 Obs.:  v é ou.
			
		b) L1 – L2 
			
			L1 - L2 = {w pertence {a, b}*|w contém número ímpar de a's ^ w contém, no máximo, um a}
			
"""
print(
"\nNome: Gustavo Hammerschmidt.\n\n1)\n\n Parte 1: \n\n",
"a)      Σ = {a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9}\n",
"b)	 L = {'def','for','var','int','bin','len'}\n\n2)\n",
"Valor: |W| = 3. \n W.W está contida? - Sim,  o alfabeto Σ* contém todas as combinações do alfabeto Σ.\n",
"Liste ...:     - Prefixos: ε, 0, 01, 001.\n		- Sufixos: ε, 1, 11, 011.\n		- Subpalavras: ε, 0, 1, 01, 11, 011.\n\n3)\n",
"a) abb, bab, bba, aaa,  aab, aba, baa.\n b)\n   a) 	L1(união)L2 = {w pertence {a, b}*|w contém número ímpar de a's v w contém pelo menos dois a's}\n",
"  b)   L1 - L2 = {w pertence {a, b}*|w contém número ímpar de a's ^ w contém, no máximo, um a}\n"
)


"""

Parte 2:

Seja Σ = {0, 1}, determine exemplos de palavras geradas pelas seguintes ER's.

	- (0+1)* 10 (0+1)*  
		Exemplos: 10, 010, 0100, 1100, 1101, ...
	
    - (0+1)* (11+00) (0+1)*
		Exemplos: 11, 00, 011, 000, 0110, 0111, 111, 100, ...
		
		
Escreva ERs para a sua linguagem de programação preferida para especificar:
	
	- Palavras reservadas (ex.: if, while, etc.)

		PReservadas = if | while | def | for | else
	
		Palavras_Reservadas = PReservadas
	
	- Números inteiros e reais (ex.: 10, 148, 1.96, etc.)
	
		Naturais = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
		Sinais = + | -
		Float = .
		Números_Inteiros = Sinais Naturais Naturais*
		Números_Floats = Sinais Naturais Naturais* Float Naturais Naturais*
		
		Inteiros_e_Floats = Números_Inteiros | Números_Floats
		
	- Identificadores (ex.: nome de variáveis)
	
		PReservadas = if | while | def | for | else
		Letra = a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z
		Números = 0|1|2|3|4|5|6|7|8|9
		
		Variáveis = ( Letra (Números + Letra)* ) - PReservadas

"""
print("\nParte 2: \n\n Questão 1: \n a) Exemplos: 10, 010, 0100, 1100, 1101, ... \n b) Exemplos: 11, 00, 011, 000, 0110, 0111, 111, 100, ... ",
"\n\n Questão 2: está feita em forma de comentário.")

 


