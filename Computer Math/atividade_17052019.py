
# NOME: GUSTAVO HAMMERSCHMIDT


# função auxiliar (fatorial):
def fakt(n):
	if(n == 0):
		return 1
	else:
		a = 1
		for i in range(1, n+1):
			a *= i
		return a

"""
Exercícios:
	a) Quantas pessoas precisam estar em um grupo para se
	   garantir que duas pessoas tenham o mesmo aniversário
	  (ignore o ano)?
    b) Prove que se 4 números são escolhidos do conjunto {1, 2,3, 4, 5, 6},
       pelo menos um par precisa somar 7.
    c) Se 12 cartas são tiradas de um baralho convencional,
       pode-se afirmar que duas têm valores iguais,
       independentemente do naipe?

"""
def pombo_a():
	return ("Precisa-se de 366 pessoas, pois, se um ano tem 365 dias e,\n"+
		  "se cada pessoa do nosso grupo nascesse em um dia diferente, \n"+
		  "a pessoa 366 nasceria em um mesmo dia que outra pessoa do grupo. \n")

def pombo_b():
	return ("{(número, seu_match)} -> { (1,a), (2,b), (3,c), (4,c), (5,b), (6,a) }\n"+
		   " Precisa de ao menos 4 números para ter match, pois, probalisticamente,\n"+
		   " teriamos um a, um b, e um c e mais um número com o mesmo \n"+
		   "match de um dos outros 3 números selecionados.\n")

def pombo_c():
	return ("Não, porque cada naipe tem 13 cartas. Portanto se você quiser ter certeza\n"+
	      "que em todo caso você terá duas cartas iguais, idependente do naipe, é necessário \n"+
		  "ter 14 cartas (pelo princípio da casa do pombo).\n\n")

"""

Exercícios:
	a) Quantas palavras de três letras (não necessariamente com
	   sentido) podem ser formadas com as letras da palavra
       "compilar", se não pudermos repetir letras?
	b) Quantas permutações distintas da palavra TESTE existem?
	c) De quantas maneiras os primeiro, segundo e terceiro
	   prêmios em um concurso de tortas podem ser atribuídos a 15
       concorrentes?
    d) De quantos modos seis pessoas podem sentar-se em uma
       sala com seis cadeiras?

"""
def permutation_a(word, size):
	return (fakt(word)/(fakt(word-size)*1.0))
	
# teste tem duas letras iguais t e e (se repetem 2 vezes cada)
def permutation_b():
	return fakt(len("TESTE"))/(fakt(2) * fakt(2)*1.0)

# primeiro, segundo e terceiro ->   15 * 14 * 13 
def permutation_c(concorrente):
	return fakt(concorrente)/ (fakt(concorrente-3)*1.0)
	
def permutation_d(n):
	return fakt(n)/ (fakt(n - n)*1.0)
	
"""
	Exercícios:
		a) De quantas maneiras podemos escolher um comitê de três
		   pessoas dentre um grupo de 12?
		b) O controle de qualidade deseja testar 25 chips de
		   microprocessadores dentre os 300 que são produzidos
		   diariamente. De quantas maneiras isto pode ser feito?
		c) De quantas maneiras pode ser selecionado um júri de
		   cinco homens e sete mulheres dentre um elenco de 17
           homens e 23 mulheres? (dica: utilize em conjunto a regra do produto)   
"""
def combination_a(n_pess, n_grupo):
	return fakt(n_pess)/(fakt(n_grupo) * fakt(n_pess - n_grupo))*1.0
	
def combination_b(total, quant):
	a = 1
	b = 1
	for i in range(total-quant,total+1):
		a *= i
	
	for i in range(1,quant+1):
		b *= i
	
	return a/b
	
	
def combination_c(totalF, grupoF, totalM, grupoM):
	return combination_b(totalF, grupoF) * combination_b(totalM, grupoM)
	
"""
	Exercícios Lista:
		1) Uma família tem 12 filhos.
			a) Prove que pelo menos dois filhos nasceram no mesmo dia da
			   semana.
			b) Prove que pelo menos dois membros da família (incluindo mãe
			   e pai) nasceram no mesmo mês.
			c) Supondo que há 4 quartos para os filhos na casa, mostre que
			   há pelo menos 3 filhos dormindo em pelo menos um dos quartos.
		2) Uma produtora de jogos tem 500 funcionários. Mostre que
		   pelo menos dois deles nasceram no mesmo dia do ano.
		3) Existem 800.000 árvores numa floresta. Cada árvore não
		   tem mais de 600.000 folhas. Mostre que pelo menos duas
		   árvores têm o mesmo número de folhas. 
		
		4) Ana, Paula, Carlos, João e Alessandro querem tirar uma
   		   foto em que três dos cinco amigos estão alinhados. Quantas
           fotos diferentes são possíveis?
        5) De quantas maneiras você pode escolher um presidente,
		   secretário e tesoureiro para um clube de 12 candidatos, se
		   cada candidato é elegível para cada cargo, mas nenhum
		   candidato pode ocupar 2 posições? (dica: a hierarquia dos papéis é
		   relevante).
		6) De quantas maneiras você pode arrumar 5 livros de
		   matemática em uma prateleira? 
		7) Um comitê de oito estudantes deve ser selecionado de
		   uma turma de 19 calouros e 34 veteranos.
			a) De quantas maneiras podem ser selecionados três calouros e
			   cinco veteranos?
			b) De quantas maneiras podem ser selecionados comitês com
			   exatamente um calouro?
			c) De quantas maneiras podem ser selecionados comitês com no
			   máximo um calouro?
			d) De quantas maneiras podem ser selecionados comitês com
			   pelo menos um calouro? 
		8) Do pessoal de uma companhia, sete trabalham no projeto, 14 na produção, 
		   quatro nos testes, cinco em vendas, dois na contabilidade e três em
  		   marketing. Um comitê de seis pessoas deve ser formado para uma 
		   reunião com o supervisor.
			a) De quantas maneiras podemos formar este comitê, se tiver que
			   haver um membro de cada departamento?
		9) Uma rede de computadores com 60 nós. De quantas
		   maneiras podem falhar um ou dois nós?
"""
def exerc_1a():
	return "Em uma família de 12 filhos, pelo menos 2 filhos nasceram em um mesmo dia uma\nvez que uma semana tem 7 dias.\n\n"

def exerc_1b():
	return ("Em uma família de 12 filhos, incluindo o pai e mãe teriamos 14\n "+
	        "pessoas. Nesse grupo de pessoas pelo menos duas delas nasceram no mesmo mês,\n"+
		    " pois, em um ano temos 12 meses\n\n")
			
def exerc_1c():
	return "Em uma família com 12 filhos e 4 quaartos, temos que (12/4 -> 3) pelo menos 3 filhos dormem por quarto.\n\n"
	
def exerc_2():
	return ("Em uma produtora com 500 funcionários, ((500/365) > 1 -> Verdade) temos que, ao menos,\n"+
			" 2 funcionários fazem aniversário no mesmo dia.\n\n")
	
	
def exerc_3():
	return ("Em uma florestas com 800.000 árvores - que não tem mais de 600.000 folhas -,\n"+
			"\texistem árvores de 0 a 600.00 folhas. Como temos um número de árvores maior do que\n"+
			"\tfolhas, podemos dizer que há pelo menos duas árvores com o mesmo número de folhas pelo\n"+
			"\tprincípio da casa dos pombos.\n\n")



def exerc_4(n_amigos, n_foto):
	return fakt(n_amigos)/(fakt(n_foto) * fakt(n_amigos - n_foto))*1.0
	
def exerc_5(membros,eleitos):
	return fakt(membros)/ (fakt(membros - eleitos) * 1.0)
	
def exerc_6(n_livros):
	return fakt(n_livros)/ (fakt(n_livros - n_livros) * 1.0)
	
def comb(n, r):
	return fakt(n)/(fakt(r)*fakt(n-r)*1.0)
	
# 3 calouros e 5 veteranos.
def exerc_7a(cal, vet, ncal, nvet):
	return (comb(cal,ncal)*comb(vet,nvet))
	
def exerc_7b(cal,vet,ncal,nvet):
	return int(comb(cal,ncal)*comb(vet,nvet))

def exerc_7c(cal,vet,ncal,nvet):
	return exerc_7b(cal,vet,ncal,nvet)+comb(vet,ncal+nvet)

def exerc_7d(cal,vet, comite):
	return comb(cal+vet,comite)-comb(vet,comite)

# 7 -> projeto, 14 -> produção, 4 -> testes, 5 -> vendas,
# 2 -> contabilidade e 3 ->  marketing. 6 -> comite
def exerc_8a(proj, prod,t, v, c, m):
	return proj*prod*t*v*c*m

# 60!/1!*59! -> 60; 60!/2!*58! -> 60*59/2	
def exerc_9():
	return 60 * (60*59/2)

# ------------------------------------------------------------------------
print("\n\nExercício pombo a:\n\t",pombo_a())
print("\nExercício pombo b:\n\t",pombo_b())
print("\nExercício pombo c:\n\t",pombo_c())
print("\n\nExercício permutação a:\n\t",permutation_a(8,3)," maneiras.\n")
print("\nExercício permutação b:\n\t",permutation_b()," maneiras.\n")
print("\n\nExercício permutação c:\n\t",permutation_c(15)," maneiras.\n")
print("\nExercício permutação d:\n\t",permutation_d(6)," maneiras.\n\n")
print("\n\nExercício combinação a:\n\t",combination_a(12,3)," maneiras.\n")
print("\nExercício combinação b:\n\t",combination_b(300,25)," maneiras.\n")
print("\nExercício combinação c:\n\t",combination_c(23,7,17,5)," maneiras.\n\n")

# Lista de exercício:
print("\n\nQuestão 1a: \n",exerc_1a())
print("\n\nQuestão 1b: \n",exerc_1b())
print("\n\nQuestão 1c: \n",exerc_1c())
print("\n\nQuestão 2: \n",exerc_2())
print("\n\nQuestão 3:\n\t",exerc_3())
print("\n\nQuestão 4:\n\t",exerc_4(5,3)," fotos diferentes são possíveis.\n\n")
print("\n\nQuestão 5:\n\t",exerc_5(12,3)," maneiras.\n\n")
print("\n\nQuestão 6:\n\t",exerc_6(5)," maneiras.\n\n")
print("\n\nQuestão 7a:\n\t",exerc_7a(19,34,3,5)," maneiras.\n\n")
print("\n\nQuestão 7b:\n\t",exerc_7b(19,34,1,7)," maneiras.\n\n")
print("\n\nQuestão 7c:\n\t",exerc_7c(19,34,1,7)," maneiras.\n\n")
print("\n\nQuestão 7d:\n\t",exerc_7d(19,34,8)," maneiras.\n\n")
print("\n\nQuestão 8:\n\t",exerc_8a(7,14,4,5,2,3)," maneiras.\n\n")
print("\n\nQuestão 9:\n\t",exerc_9()," maneiras.\n\n")




