
try:

	from itertools import combinations
	from functools import reduce, wraps
	import matplotlib.pyplot as plt, numpy as np, pickle, csv, sys, time, threading

except:

	import os, sys

	os.system("echo Você pode ver o resultado da última execução em trabalho1/console/PRIME_console.txt")

	sys.exit()


##############################
##############################
# NOME: GUSTAVO HAMMERSCHMIDT.
##############################
##############################


"""
	| Dados 50 números inteiros de 1 a 50 e as seguintes comninações.
	

	a) [ n = 50 | p = 5 ] = 2.118.760

	b) [ n = 50 | p = 4 ] = 230.300

	c) [ n = 50 | p = 3 ] = 19.600

	d) [ n = 50 | p = 2] = 1.225
"""
class Programas():


	# C Total -> 17 -> O(1)
	def __init__(self) -> None:

		self.values = list(range(1,51)) # Números de 1 a 50.																						# C1 <- 1

		self.combinations_a, self.combinations_b = list(combinations(self.values, 5)), list(combinations(self.values, 4))							# C2 <- 2
		self.combinations_c, self.combinations_d = list(combinations(self.values, 3)), list(combinations(self.values, 2))							# C3 <- 2

		self.sceneryC1, self.sceneryC2, self.sceneryC3, self.over = None, None, None, False															# C4 <- 4

		self.database, self.database_header, self.db_backtest, self.db_backtest_header = [], [], [], []												# C5 <- 4

		self.all_gains, self.apostas = {}, {}																										# C6 <- 2


	# C Total -> 1 -> O(1)
	# Dump scenery results.
	def storage(self, var, name) -> None:

		pickle.dump(var, open(name, "wb"))																											# C1 <- 1


	# C Total -> 1 -> O(1)
	# Load scenery results.
	def loader(self, name) -> []:

		return pickle.load(open(name, "rb"))																										# C1 <- 1


	# C Total -> 6 -> O(1)
	# Check stop condition.
	def decorator_over(func):
		
		@wraps(func)																																# C1 <- 1
		def inner(inst, *args, **kwargs):																											# C2 <- 1
		
			if inst.over:																															# C3 <- 1
		
				print(f"\n\t-- Decorator stopped the execution of {func.__name__} (self.over = True).")												# C4 <- 1
		
				return																																# C5 <- 0
		
			return func(inst, *args, **kwargs)																										# C6 <- 1
		
		return inner																																# C7 <- 1


	# C Total -> 4n + 2 -> O(n)
	# Load database.
	def load_database(self) -> None:

		[self.database.append([*row[1:6],*row[8:10]]) for row in csv.reader(open('database/euro_concursos.csv'), delimiter=',')]					# C1 <- len(database) * 1
		
		self.database_header = self.database.pop(0)																									# C2 <- 1

		self.database = [ [ int(elem) for elem in row ] for row in self.database ]																	# C3 <- len(database) * 1

		[self.db_backtest.append(row) for row in csv.reader(open('database/euro_concursos.csv'), delimiter=',')]									# C4 <- len(database) * 1

		self.db_backtest_header = self.db_backtest.pop(0)																							# C5 <- 1

		self.db_backtest = [ [int(elem) for elem in row] for row in self.db_backtest ]																# C6 <- len(database) * 1


	# C Total -> 1 -> O(1)
	# Check if sceneries are loaded.
	def load_sceneries(self) -> bool:

		return False if None in [self.sceneryC1, self.sceneryC2, self.sceneryC3] else True															# C1 <- 1


	"""
		C Total -> 11 + 2m + 5n + 4(n * o) + 2p

				-- m é, no pior caso, 20% de n -> m == 0.2n.

				--  n * o ~ constante * n
				
				-- p ocorre 20% das vezes -> p == 0.2 n * o
				
				-- k < 5

		C Total -> 11 + 5.4n + 4.4 * (k * n) -> O(k * n)	
	"""
	# Algoritmo para encontrar menor número de sequências.
	def program_handler(self, program_number=0, savename="", combination_p=[], combination_number=0, conjunto_index=(), takes=[], load=False) -> None:

		if load:																																	# C1 <- 1

			return self.loader(f'arrs/{savename}.pkl')																								# C2 <- 1

		conjunto, conjunto_matrix = list(), {}																										# C3 <- 2

		for k in combination_p:																														# C4 <- m (m < C(50,5)==n)

			conjunto_matrix[conjunto_index(k)] = False																								# C5 <- m

		control, next_ = takes[0], 1	# Assumed take_order_based = [5, 4, 3, 2, 1] || Take 5 elements array with all combinations...				# C6 <- 2
									
		while True:																																	# C7 <- n := len(takes)

			for _ in self.combinations_a:																											# C8 <- n * (o := 2.118.760)

				indexes = list(map(conjunto_index, list(combinations( _ , combination_number))))													# C9 <- n * o 

				taken = list(map((lambda i: conjunto_matrix[i]), indexes ))																			# C10 <- n * o

				if control(taken):																													# C11 <- n * o

					[ conjunto_matrix.__setitem__(i, True) for i in indexes ]																		# C12 <- p (p * n < 0)

					conjunto.append(_)																												# C13 <- p

			print(f"\n\t Programa {program_number}:  Phase take{6-next_} finished -> len(conjunto) == {len(conjunto)} sequências.", end='')			# C14 <- n

			if list(conjunto_matrix.values()).count(False) == 0:  # comb(50, program number) = len(programa1 -> Cenário x)							# C15 <- n

				break 																																# C16 <- 1

			else:																																	# C17  -----
			
				control = takes[next_]																												# C18 <- n-1

				next_ += 1																															# C19 <- n-1

		self.storage(conjunto, f'arrs/{savename}.pkl')																								# C20 <- 1

		print(f"\n\n\t Cenário {program_number-1}: ", len(conjunto), "sequências.")																	# C21 <- 1
		
		del conjunto_matrix, control, next_																											# C22 <- 3

		return conjunto[:]																															# C24 <- 1


	# C Total -> 4 -> O(1)
	"""
		1.	gerar as combinações tal que: em (a) se obtenha 2.118.760 sequencias de
		 	5 números diferentes, em (b) se obtenha 230.300 sequencias de 4 números
		 	diferentes, em (c) se obtenha 19.600 sequencias de 3 números diferentes, 
		 	em (d) se obtenha 1.225 sequencias de 2 números diferentes. PROGRAMA 1.
	"""
	@decorator_over
	def programa1(self) -> None:

		print("\nPrograma 1:\n")																													# C1 <- 1
		print("\tSequências:")																														# C2 <- 1
		print("\n\t\ta: {} combinações.\n\t\tb: {} combinações.".format(len(self.combinations_a), len(self.combinations_b)))						# C3 <- 1
		print("\t\tc: {} combinações.\n\t\td: {} combinações.\n".format(len(self.combinations_c), len(self.combinations_d)))						# C4 <- 1


	# C Total -> 5 + k*n -> O(k * n)
	"""
		2.	encontrar o menor conjunto de sequencias de 5 números que contem todas
		 	as sequencias de 2 números, cenário C1. PROGRAMA 2.

			Obs.: entendo uma sequência de 5 números que contém o maior número de 
			sequências de 2 como sendo:

					Ex.:   (  1 -> 5 -> 16 -> 27 -> 50 )

					contendo as sequências:

						- 1 -> 5 | 1 -> 16 | 1 -> 27 | 1 -> 50
						- 5 -> 16 | 5 -> 27 | 5 -> 50
						- 16 -> 27 -> 50 | 16 -> 50
						- 27 -> 50

					Pois, se for esperado que a sequência 1->50 seja encontrada
					lado a lado, precisariamos de uma sequência com limite acima de 52.
					- Menor combinação possível nesse cenário: (1->50->51->52->53).

					E o exercício pede TODAS as sequências de 2 números!


		Explicação sobre as funções takes nos programas 2, 3 e 4:

				Combinação de 5, 2 a 2; 10 resultados possíveis.
				>>> print(list(combinations(['1','2','3','4','5'], 2)))
				... [('1', '2'), ('1', '3'), ('1', '4'), ('1', '5'), ('2', '3'), ('2', '4'), ('2', '5'), ('3', '4'), ('3', '5'), ('4', '5')]
			
				Combinações de 5, 2 a 2, com um elemento repetido; 6 resultados possíveis.
				>>> print(list(combinations(['1','2','3','4','A'], 2)))
				... [('1', '2'), ('1', '3'), ('1', '4'), ('1', 'A'), ('2', '3'), ('2', '4'), ('2', 'A'), ('3', '4'), ('3', 'A'), ('4', 'A')]


				!!! 
					Algumas funções takes tem o limite abaixo dos resultados possíveis para otimizar tempo de execução 
					e tamanho da função; obtive o número de comparação nas funções lambdas através de execução prévia 
					e outros testes.
				!!!
	"""
	@decorator_over
	def programa2(self, load=False) -> None:

		take5 = lambda arr: not reduce( (lambda a, b: a or b), arr) # All true.																		# C1 <- 1
		take4 = lambda arr: arr.count(False) == 6																									# C2 <- 1
		take3 = lambda arr: arr.count(False) == 2																									# C3 <- 1
		take2 = lambda arr: arr.count(False) == 1	# last chance.																					# C4 <- 1

		index_markup = (lambda x: str(x[0])+":"+str(x[1]))																							# C5 <- 1

		self.sceneryC1 = self.program_handler(program_number=2, savename='sceneryC1', combination_p=self.combinations_d,	
								 combination_number=2, conjunto_index=index_markup, takes=[take5, take4, take3, take2], load=load)					# C6 <- k*n


	# C Total -> 4 + k*n -> O(k * n)
	"""
		3.	encontrar o menor conjunto de sequencias de 5 números que contem todas
		    as sequencias de 3 números, cenário C2. PROGRAMA 3.		
	"""
	@decorator_over
	def programa3(self, load=False) -> None:

		take5 = lambda arr: not reduce( (lambda a, b: a or b), arr) # All true.																		# C1 <- 1
		take4 = lambda arr: arr.count(False) == 4																									# C2 <- 1
		take3 = lambda arr: arr.count(False) == 2 # last chance.																					# C3 <- 1

		index_markup = (lambda x: str(x[0])+":"+str(x[1])+":"+str(x[2]))																			# C4 <- 1

		self.sceneryC2 = self.program_handler(program_number=3, savename='sceneryC2', combination_p=self.combinations_c,
			 							combination_number=3, conjunto_index=index_markup, takes=[take5, take4, take3], load=load)					# C5 <- k*n


	# C Total -> 4 + k*n -> O(k * n)
	"""
		4.	encontrar o menor conjunto de sequencias de 5 números que contem todas 
			as sequencias de 4 números, cenário C3. PROGRAMA 4.
	"""
	@decorator_over
	def programa4(self, load=False) -> None:

		take5 = lambda arr: not reduce( (lambda a, b: a or b), arr) # All true.																		# C1 <- 1
		take4 = lambda arr: arr.count(False) == 3																									# C2 <- 1
		take3 = lambda arr: arr.count(False) == 1	# last chance.																					# C3 <- 1

		index_markup = (lambda x: str(x[0])+":"+str(x[1])+":"+str(x[2])+":"+str(x[3]))																# C4 <- 1

		self.sceneryC3 = self.program_handler(program_number=4, savename='sceneryC3', combination_p=self.combinations_b,
										 combination_number=4, conjunto_index=index_markup, takes=[take5, take4, take3], load=load)					# C5 <- k*n


	# C Total -> 1 -> O(1)
	# Match de pontos, arr2 é a database.
	def point_hits(self, arr1, arr2) -> int:

		return len( ( set(arr1) & set(arr2[0:5]) ))																									# C1 <- 1


	# C Total -> 33 + n + k * n -> O(k * n)
	"""
		5.	supondo que tais combinações representem um sistema de aposta de algum pais europeu,
		 	calcular o retorno de um(a) apostador(a) em cada um dos cenários: C1, C2 e C3. 
		 	Para o cálculo do retorno considere as seguintes regras e valores: 

		 		a)	se acertou 2 números em um cartão, então a renumeração é 4,16 Euros. PROGRAMA 5.

				C1, C2, C3 -> Dar preferência a cartões com 4 acertos e, aí, os de 2.
	"""
	@decorator_over
	def programa5(self) -> None:
		
		if self.database == []:																														# C1 <- 1
			
			self.load_database()																													# C2 <- n 

		if not self.load_sceneries():																												# C3 <- 1

			print("\n\t\tCenários não carregados.\n")																								# C4 <- 1

			return;																																	# C5 <- 0

		result = {}																																	# C6 <- 1

		def all_cards(call, scenery):																												# C7 <- 1

			points = []																																# C8 <- 1

			[points.append(max([2 if m > 1 else m for m in [self.point_hits(i, db_i) for db_i in self.database]] )) for i in scenery] 				# C9 <- k * n

			result[call] = int( ( points.count(2) ) * 4.19 )																						# C10 <- 1

			print('\t Programa 5 --> '+call+' terminou.')																							# C11 <- 1

		data_ = [self.sceneryC1, self.sceneryC2, self.sceneryC3[:15000],self.sceneryC3[15000:30000],
												self.sceneryC3[30000:45000], self.sceneryC3[45000:]]												# C12 <- 1

		threads = []																																# C13 <- 1

		[threads.append(threading.Thread(target=all_cards, args=('thread '+str(i), data_[i],))) for i in range(0, len(data_))]						# C14 <- 6

		[t.start() for t in threads]																												# C15 <- 6

		[t.join() for t in threads]																													# C16 <- 6

		res = [result['thread 0'], result['thread 1'], sum(result[i] for i in list(result.keys())[2:])]												# C17 <- 3
		
		self.all_gains['Programa 5'] = res																											# C18 <- 1

		self.apostas['5'] = (f"\nPrograma 5: \n\n\t\tCenário 1: {res[0]} euros.\n\t\tCenário 2: {res[1]} euros.\n\t\tCenário 3: {res[2]} euros.\n")	# C19 <- 1

	
	# C Total -> 33 + n + k * n -> O(k * n)
	"""
		b)	se acertou 3 números em um cartão, então a renumeração é 11,89 Euros. Deve-se observar
			que neste caso a remuneração máxima inclui 1 cartão com 3 números e 3 cartões com 2 números.
			PROGRAMA 6.
	"""
	@decorator_over
	def programa6(self) -> None:
		
		if self.database == []:																														# C1 <- 1
			
			self.load_database()																													# C2 <- n

		if not self.load_sceneries():																												# C3 <- 1

			print("\n\t\tCenários não carregados.\n")																								# C4 <- 1

			return;																																	# C5 <- 0
		
		result = {}																																	# C6 <- 1
		
		def all_cards(call, scenery):																												# C7 <- 1

			points = []																																# C8 <- 1

			[points.append(max([3 if m > 2 else m for m in [self.point_hits(i, db_i) for db_i in self.database]] )) for i in scenery] 				# C9 <- k * n

			result[call] = int( ( points.count(3) + int(points.count(2)/3) ) * 11.89 )																# C10 <- 1

			print('\t Programa 6 --> '+call+' terminou.')																							# C11 <- 1

		data_ = [self.sceneryC1, self.sceneryC2, self.sceneryC3[:15000], self.sceneryC3[15000:30000], 
												 self.sceneryC3[30000:45000], self.sceneryC3[45000:]]												# C12 <- 1

		threads = []																																# C13 <- 1

		[threads.append(threading.Thread(target=all_cards, args=('thread '+str(i), data_[i],))) for i in range(0, len(data_))]						# C14 <- 6

		[t.start() for t in threads]																												# C15 <- 6

		[t.join() for t in threads]																													# C16 <- 6

		res = [result['thread 0'], result['thread 1'], sum(result[i] for i in list(result.keys())[2:])]												# C17 <- 3

		self.all_gains['Programa 6'] = res 																											# C18 <- 1

		self.apostas['6'] = (f"\nPrograma 6: \n\n\t\tCenário 1: {res[0]} euros.\n\t\tCenário 2: {res[1]} euros.\n\t\tCenário 3: {res[2]} euros.\n")	# C19 <- 1


	# C Total -> 33 + n + k * n -> O(k * n)
	"""
		c)	se acertou 4 números em um cartão, então a renumeração é 82,31 Euros. Deve-se observar que neste
			caso a remuneração máxima inclui 1 cartão com 4 números, 4 cartões com 3 números e 6 cartões com
			2 números. PROGRAMA 7.
	"""
	@decorator_over
	def programa7(self) -> None:
	
		if self.database == []:																														# C1 <- 1
			
			self.load_database()																													# C2 <- n

		if not self.load_sceneries():																												# C3 <- 1

			print("\n\t\tCenários não carregados.\n")																								# C4 <- 1

			return;																																	# C5 <- 0

		result = {}																																	# C6 <- 1
		
		def all_cards(call, scenery):																												# C7 <- 1

			cards = []																																# C8 <- 1

			[cards.append(max([4 if m > 3 else m for m in [self.point_hits(i, db_i) for db_i in self.database]] )) for i in scenery]				# C9 <- k * n

			result[call] = int( ( cards.count(4) + int(cards.count(3)/4) + int(cards.count(2)/6) ) *  82.31 )										# C10 <- 1

			print('\t Programa 7 --> '+call+' terminou.')																							# C11 <- 1
		
		data_ = [self.sceneryC1, self.sceneryC2, self.sceneryC3[:15000], self.sceneryC3[15000:30000], 
												 self.sceneryC3[30000:45000], self.sceneryC3[45000:]]												# C12 <- 1

		threads = []																																# C13 <- 1

		[threads.append(threading.Thread(target=all_cards, args=('thread '+str(i), data_[i],))) for i in range(0, len(data_))]						# C14 <- 6

		[t.start() for t in threads]																												# C15 <- 6

		[t.join() for t in threads]																													# C16 <- 6

		res = [result['thread 0'], result['thread 1'], sum(result[i] for i in list(result.keys())[2:])]												# C17 <- 3

		self.all_gains['Programa 7'] = res																											# C18 <- 1

		self.apostas['7'] = (f"\nPrograma 7: \n\n\t\tCenário 1: {res[0]} euros.\n\t\tCenário 2: {res[1]} euros.\n\t\tCenário 3: {res[2]} euros.\n")	# C19 <- 1


	# C Total -> 33 + n + k * n -> O(k * n)
	"""
		d)	se acertou 5 números em um cartão, então a renumeração é 70.584,44 Euros. Deve-se observar
		 	que neste caso a remuneração máxima inclui 1 cartão com 5 números, 5 cartões com 4 números,
		 	10 cartões com 3 números, 10 cartões com 2 números. PROGRAMA 8.
	"""
	@decorator_over
	def programa8(self) -> None:
		
		if self.database == []:																														# C1 <- 1
			
			self.load_database()																													# C2 <- n

		if not self.load_sceneries():																												# C3 <- 1

			print("\n\t\tCenários não carregados.\n")																								# C4 <- 1

			return;																																	# C5 <- 0

		result = {}																																	# C6 <- 1
		
		def all_cards(call, scenery):																												# C7 <- 1

			cards = []																																# C8 <- 1

			[cards.append(max([ m for m in [self.point_hits(i, db_i) for db_i in self.database]] )) for i in scenery] 								# C9 <- k * n

			result[call] = int( ( cards.count(5) + int(cards.count(4)/5) + int(cards.count(3)/10) + int(cards.count(2)/10) ) *  70584.44 )			# C10 <- 1

			print('\t Programa 8 --> '+call+' terminou.')																							# C11 <- 1
		
		data_ = [self.sceneryC1, self.sceneryC2, self.sceneryC3[:15000], self.sceneryC3[15000:30000], 
												 self.sceneryC3[30000:45000], self.sceneryC3[45000:]]												# C12 <- 1

		threads = []																																# C13 <- 1

		[threads.append(threading.Thread(target=all_cards, args=('thread '+str(i), data_[i],))) for i in range(0, len(data_))]						# C14 <- 6

		[t.start() for t in threads]																												# C15 <- 6

		[t.join() for t in threads]																													# C16 <- 6

		res = [result['thread 0'], result['thread 1'], sum(result[i] for i in list(result.keys())[2:])]												# C17 <- 3

		self.all_gains['Programa 8'] = res																											# C18 <- 1

		self.apostas['8'] = (f"\nPrograma 8: \n\n\t\tCenário 1: {res[0]} euros.\n\t\tCenário 2: {res[1]} euros.\n\t\tCenário 3: {res[2]} euros.\n")	# C19 <- 1


	# C Total -> 3 -> O(1)
	# Ordena o resultado das apostas para display depois de executar os programas 5, 6, 7 e 8.
	def resultado_apostas(self):

		k = list(self.apostas.keys())																												# C1 <- 1
		k.sort()																																	# C2 <- 1
		[print(self.apostas[n]) for n in k if len(k) == 4]																							# C3 <- 1


	# C Total -> 2 -> O(1)
	# Store the last gains.
	@decorator_over
	def store_gains(self) -> None:

		if len(self.all_gains) == 4:																												# C1 <- 1
			
			self.storage(self.all_gains, 'last_gains/all_gains.pkl')																				# C2 <- 1


	# C Total -> 22 + 9n + k * n -> O(k * n)
	"""
		6.	Tomando como exemplo o histórico de prêmios dos últimos anos disponíveis no “euro_concursos.csv”,
			escreva um programa de backtest e gere um relatório que mostre os valores investidos e os 
			retornos obtidos.  PROGRAMA 9.
	"""
	@decorator_over
	def programa9(self) -> None:
		
		compare = lambda arr_i:  sorted([[ len(set(i[1:6]) & set(arr_i)), i] for i in self.db_backtest], key=(lambda elem: elem[0]))[-1]			# C1 <- n

		readjust = lambda arr_i, match: [ (i if i in match else chr(248)) for i in arr_i]  															# C2 <- n

		winners = lambda scenery, limit: [ [ readjust(i, compare(i)[1][1:6]), compare(i)[1] ] if compare(i)[0] > limit else 
										[readjust([-1,-1,-1,-1,-1], compare(i)[1][1:6]), compare(i)[1]] for i in scenery if compare(i)[0] > 0]		# C3 <- n
		
		content = lambda scenery, res: [ res.append("\t\t"+(str(i+1)+":  ").ljust(8)+str(scenery[i][0]).ljust(25)+
																			   "   -->  "+str(scenery[i][1])) for i in range(0, len(scenery))]  	# C4 <- n

		display = lambda scenery, title, res: [res.append("\n\t\t"+title+"   ('ø': número não hit).\n\n\t\t"+str(self.database_header[0:5])+
								         						 "      ---  "+str(self.db_backtest_header)+"\n\n"), content(scenery, res)]			# C5 <- n

		compare_db_i = lambda scenery, min_points: [ [i[0], len( [k for k in [ int(len(set(i[1:6]) & set(j))/min_points) 
													for j in scenery] if k > 0])] for i in self.db_backtest] # [Card number, number of hits].		# C6 <- k * n

		db_display = lambda scenery, res: [ [res.append("CON\t\t\t"), 
											[res.append('CON[No:'+(str(i[0])+',').ljust(5)+' hits='+(str(i[1])+']').ljust(13)) for i in scenery[i:i+5]], 
									   		 res.append("")] for i in range(0, len(scenery), 10)]													# C7 <- n

		if len(self.all_gains) < 4:																													# C8 <- 1

			self.all_gains = self.loader('last_gains/all_gains.pkl')																				# C9 <- 1

		invested = lambda scenery: [str(k)+" -> "+str(len(scenery))+" cartões." for k in self.all_gains.keys()]										# C10 <- 1

		lucro = lambda _0_to_2: [str(k)+" -> "+str(self.all_gains[k][_0_to_2])+" euros." for k in self.all_gains.keys()]							# C11 <- 1
		
		op = lambda scenery: 0 if scenery == self.sceneryC1 else ( 1 if scenery == self.sceneryC2 else 2)											# C12 <- 1
		
		inv_lucro = lambda scenery: [ ["Investidos:  "+invested(scenery)[i], "Remuneração: "+lucro(op(scenery))[i]]
																			for i in range(0, len(self.all_gains))]									# C13 <- 1

		scenery_relatory = lambda scenery, res: [res.append("\n\t\tRelatório (Cenário "+str(op(scenery)+1)+"):\n\n\t\t\t\t"),
									(res.append("CON\t\t\t\t"+"\n\n\t\t\t\t".join(["\n\t\t\t\t".join(i) for i in inv_lucro(scenery)])+"\n\n"))]     # C14 <- 1

		control = lambda scenery, res: [display(winners(scenery, op(scenery)+1), ("Cenário "+str(op(scenery)+1)+":"), res),
						   res.append(f"\n\n\t\tTodos os hits do cenário {str(op(scenery)+1)} que as entradas(No) em db_backtest receberam:\n"),
																	db_display(compare_db_i(scenery, 2), res), scenery_relatory(scenery, res)	]	# C15 <- 1

		resultC1, resultC2, resultC3 = [], [], []																									# C16 <- 3

		print("\n\nRelatório backtest:")																											# C17 <- 1

		threads = []																																# C18 <- 1

		[threads.append(threading.Thread(target=control, args=( i[0], i[1], ) )) for i in [
															[self.sceneryC1, resultC1], [self.sceneryC2, resultC2], [self.sceneryC3, resultC3]] ]   # C19 <- 3

		[t.start() for t in threads]																												# C20 <- 3

		threads[0].join()																															# C21 <- 1

		[print(line) if line[0:3]!="CON" else print(line[3:], end='') for line in resultC1]															# C22 <- n

		threads[1].join()																															# C23 <- 1

		[print(line) if line[0:3]!="CON" else print(line[3:], end='') for line in resultC2]															# C24 <- n

		threads[2].join()																															# C25 <- 1
	
		[print(line) if line[0:3]!="CON" else print(line[3:], end='') for line in resultC3]															# C26 <- n


	# C Total -> 5 + 12n + 5*n*r -> (r < n) -> O(k * n)
	"""
		7.	Como foco na disciplina é análise de complexidade de algoritmos, pede-se como último item do trabalho 
			a realização da análise de complexidade de tempo para cada um dos seguintes itens: PROGRAMA 1, PROGRAMA 2,
			PROGRAMA 3, PROGRAMA 4, PROGRAMA 5, PROGRAMA 6, PROGRAMA 7, PROGRAMA 8, PROGRAMA 9.
	"""
	@decorator_over
	def time_analysis(self, funcs=[], repeat=0, call_name='', show_fig=False) -> None:

		def output(x):																																# C1 <- 3
			sys.stdout = last 																														# C1.1 <- 1
			print(x, end='')																														# C1.2 <- 1
			sys.stdout = open('console/hidden.txt', 'w')																							# C1.3 <- 1

		print("\nTime Analysis:\n\n")																												# C2 <- 1

		last = sys.stdout 																															# C3 <- 1

		sys.stdout = open('console/hidden.txt', 'w')																								# C4 <- 1

		for f in funcs:																																# C5 <- n

			time_arr = []																															# C6 <- n

			output("\t"+str(f.__name__)+"(): [repeat==5 => ")																						# C7 <- n*3

			for r in range(0, repeat):																												# C8 <- n*r

				output(f"{r} ")																														# C9 <- n*r*3

				track = time.time()																													# C10 <- n*r

				f()																																	# C11 <- n*r

				time_arr.append(time.time() - track)																								# C12 <- n*r

			output(f"]. Average Time: {sum(time_arr)/repeat} seconds.\n")																			# C13 <- n*3

			sys.stdout = last 																														# C14 <- n

			plt.plot(time_arr, label=''+f.__name__)																									# C15 <- n
			plt.title("When f() == "+f.__name__)																									# C16 <- n
			plt.legend(loc='best')																													# C17 <- n
			plt.savefig("time_analysis/"+f.__name__+"_fig_"+call_name+".png")																		# C18 <- n

			if show_fig:																															# C19 <- n
				plt.show()																															# C20 <- n

			sys.stdout = open('console/hidden.txt', 'w')																							# C21 <- n

		sys.stdout = last 																															# C22 <- 1

		print("\n")																																	# C23 <- 1


	# C Total -> 7 -> O(1)
	# Console controller.
	@decorator_over
	def console(self, last_console_show=False, store_console=False):

		if last_console_show and store_console:																										# C1 <- 1

			self.over = True 																														# C2 <- 1

			return																																	# C3 <- 0

		if last_console_show:																														# C4 <- 1

			self.over = True 																														# C5 <- 1
			
			[print(row, end='') for row in open("console/PRIME_console.txt",'r')]																	# C6 <- 1

		if store_console:																															# C7 <- 1

			sys.stdout = open('console/console.txt', 'w')																							# C8 <- 1


	# C Total -> 7 + 3 * k * n -> O(k * n)
	# Função para teste rápido.
	@decorator_over
	def quick_test(self, test=False, programa_2_a_4=None, programa_5_a_8=None):

		if not test:																																# C1 <- 1

			return																																	# C2 <- 0

		print("\nPrograma 2 a 4: ")																													# C3 <- 1

		programa_2_a_4()																															# C4 <- k * n

		self.sceneryC1 = self.loader('arrs/sceneryC1.pkl')																							# C5 <- 1
		self.sceneryC2 = self.loader('arrs/sceneryC2.pkl')																							# C6 <- 1
		self.sceneryC3 = self.loader('arrs/sceneryC3.pkl')																							# C7 <- 1

		print("\nPrograma 5 a 8: ")																													# C8 <- 1

		programa_5_a_8()																															# C9 <- k * n

		self.time_analysis(funcs=[programa_2_a_4, programa_5_a_8], repeat=5, call_name='quick_test', show_fig=False)								# C10 <- k * n

		self.over = True 																															# C11 <- 1



#####################################################
#########   ####   ####   #####  ###    ###  ########
#########  # ## #  ###  #  ####  ###  #  ##  ########
#########  ##  ##  ##  ###  ###  ###  ##  #  ########
#########  ######  #  #####  ##  ###  ####   ########
#####################################################


prog = Programas()


#####################################################
#####################################################

prog.console(last_console_show=True, store_console=False)  # Mostrar o último console OU salvar os resultados dessa execução no arquivo console.txt.


prog.quick_test(test=False, programa_2_a_4=prog.programa2, programa_5_a_8=prog.programa5) # Testa duas funções e faz a análise temporal delas.

# last_console_show=True bloqueia a função quick_test de executar. Defina l.c.s. como False e test como True em quick test para fazer testes rápidos.

#####################################################
#####################################################


prog.load_database() # Carrega a base de dados csv.


prog.programa1() # Carrega as combinações e mostra as sequências.


#####################################################
#####################################################

if not prog.over:

	print("\nGerando cenários:\n")


thread_sceneries = []

#                                                  var load | (if True) ~ carrega os cenários salvos em arquivos .pkl.
#												            V
[thread_sceneries.append(threading.Thread(target=p, args=(False,))) for p in [prog.programa2, prog.programa3, prog.programa4]]


[thread.start() for thread in thread_sceneries]


[thread.join() for thread in thread_sceneries]

#####################################################
#####################################################

if not prog.over:

	print("\n\nGerando as apostas:\n")


threads_apostas = []


[threads_apostas.append(threading.Thread(target=p)) for p in [prog.programa5, prog.programa6, prog.programa7, prog.programa8]] 


[thread.start() for thread in threads_apostas]


[thread.join() for thread in threads_apostas]


prog.resultado_apostas() # Centavos foram desconsiderados para normalizar o output.

#####################################################
#####################################################


prog.store_gains() # Salva os retornos das apostas.


#####################################################
#####################################################


prog.programa9() # Backtest. 


#####################################################
#####################################################

funcs = [prog.programa1, prog.programa2, prog.programa3, prog.programa4, prog.programa5, prog.programa6, prog.programa7, prog.programa8, prog.programa9]


prog.time_analysis(funcs=funcs, repeat=5, show_fig=False) # Testando a complexidade temporal de cada função.

#####################################################
#####################################################


# Você pode deixar de executar uma função em específico usando essas instruções.
#
# prog.over = True
#  
#   func()
#
# prog.over = False
