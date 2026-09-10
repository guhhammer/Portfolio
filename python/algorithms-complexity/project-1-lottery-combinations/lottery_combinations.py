
try:

	from itertools import combinations
	from functools import reduce, wraps
	import matplotlib.pyplot as plt, numpy as np, pickle, csv, sys, time, threading

except:

	import os, sys

	os.system("echo You can read the output of the last full run in console/sample-output.txt")

	sys.exit()


##############################
##############################
# NAME: GUSTAVO HAMMERSCHMIDT.
##############################
##############################


"""
	| Given the 50 integers from 1 to 50 and the following combinations.
	

	a) [ n = 50 | p = 5 ] = 2.118.760

	b) [ n = 50 | p = 4 ] = 230.300

	c) [ n = 50 | p = 3 ] = 19.600

	d) [ n = 50 | p = 2] = 1.225
"""
class Programs():


	# C Total -> 17 -> O(1)
	def __init__(self) -> None:

		self.values = list(range(1,51)) # Numbers from 1 to 50.																						# C1 <- 1

		self.combinations_a, self.combinations_b = list(combinations(self.values, 5)), list(combinations(self.values, 4))							# C2 <- 2
		self.combinations_c, self.combinations_d = list(combinations(self.values, 3)), list(combinations(self.values, 2))							# C3 <- 2

		self.sceneryC1, self.sceneryC2, self.sceneryC3, self.over = None, None, None, False															# C4 <- 4

		self.database, self.database_header, self.db_backtest, self.db_backtest_header = [], [], [], []												# C5 <- 4

		self.all_gains, self.bets = {}, {}																										# C6 <- 2


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

				-- m is, in the worst case, 20% of n -> m == 0.2n.

				--  n * o ~ constant * n
				
				-- p happens 20% of the time -> p == 0.2 n * o
				
				-- k < 5

		C Total -> 11 + 5.4n + 4.4 * (k * n) -> O(k * n)	
	"""
	# Algorithm that finds the smallest number of sequences.
	def program_handler(self, program_number=0, savename="", combination_p=[], combination_number=0, set_index=(), takes=[], load=False) -> None:

		if load:																																	# C1 <- 1

			return self.loader(f'arrs/{savename}.pkl')																								# C2 <- 1

		chosen, coverage = list(), {}																										# C3 <- 2

		for k in combination_p:																														# C4 <- m (m < C(50,5)==n)

			coverage[set_index(k)] = False																								# C5 <- m

		control, next_ = takes[0], 1	# Assumed take_order_based = [5, 4, 3, 2, 1] || Take 5 elements array with all combinations...				# C6 <- 2
									
		while True:																																	# C7 <- n := len(takes)

			for _ in self.combinations_a:																											# C8 <- n * (o := 2.118.760)

				indexes = list(map(set_index, list(combinations( _ , combination_number))))													# C9 <- n * o 

				taken = list(map((lambda i: coverage[i]), indexes ))																			# C10 <- n * o

				if control(taken):																													# C11 <- n * o

					[ coverage.__setitem__(i, True) for i in indexes ]																		# C12 <- p (p * n < 0)

					chosen.append(_)																												# C13 <- p

			print(f"\n\t Program {program_number}:  Phase take{6-next_} finished -> len(chosen) == {len(chosen)} sequences.", end='')			# C14 <- n

			if list(coverage.values()).count(False) == 0:  # comb(50, program number) = len(program1 -> Scenario x)							# C15 <- n

				break 																																# C16 <- 1

			else:																																	# C17  -----
			
				control = takes[next_]																												# C18 <- n-1

				next_ += 1																															# C19 <- n-1

		self.storage(chosen, f'arrs/{savename}.pkl')																								# C20 <- 1

		print(f"\n\n\t Scenario {program_number-1}: ", len(chosen), "sequences.")																	# C21 <- 1
		
		del coverage, control, next_																											# C22 <- 3

		return chosen[:]																															# C24 <- 1


	# C Total -> 4 -> O(1)
	"""
		1.	generate the combinations so that (a) gives 2,118,760 sequences of
		 	5 distinct numbers, (b) 230,300 sequences of 4 distinct numbers,
		 	(c) 19,600 sequences of 3 distinct numbers and (d) 1,225 sequences
		 	of 2 distinct numbers. PROGRAM 1.
	"""
	@decorator_over
	def program1(self) -> None:

		print("\nProgram 1:\n")																													# C1 <- 1
		print("\tSequences:")																														# C2 <- 1
		print("\n\t\ta: {} combinations.\n\t\tb: {} combinations.".format(len(self.combinations_a), len(self.combinations_b)))						# C3 <- 1
		print("\t\tc: {} combinations.\n\t\td: {} combinations.\n".format(len(self.combinations_c), len(self.combinations_d)))						# C4 <- 1


	# C Total -> 5 + k*n -> O(k * n)
	"""
		2.	find the smallest set of 5-number sequences that contains every
		 	2-number sequence, scenario C1. PROGRAM 2.

			Note: I read "a 5-number sequence that contains the largest number of
			2-number sequences" as:

					Ex.:   (  1 -> 5 -> 16 -> 27 -> 50 )

					containing the sequences:

						- 1 -> 5 | 1 -> 16 | 1 -> 27 | 1 -> 50
						- 5 -> 16 | 5 -> 27 | 5 -> 50
						- 16 -> 27 -> 50 | 16 -> 50
						- 27 -> 50

					Because if the sequence 1->50 had to appear side by side,
					we would need numbers above 52.
					- Smallest possible combination in that reading: (1->50->51->52->53).

					And the exercise asks for ALL 2-number sequences!


		About the take functions in programs 2, 3 and 4:

				Combinations of 5 taken 2 at a time; 10 possible results.
				>>> print(list(combinations(['1','2','3','4','5'], 2)))
				... [('1', '2'), ('1', '3'), ('1', '4'), ('1', '5'), ('2', '3'), ('2', '4'), ('2', '5'), ('3', '4'), ('3', '5'), ('4', '5')]
			
				Combinations of 5 taken 2 at a time with one repeated element; 6 possible results.
				>>> print(list(combinations(['1','2','3','4','A'], 2)))
				... [('1', '2'), ('1', '3'), ('1', '4'), ('1', 'A'), ('2', '3'), ('2', '4'), ('2', 'A'), ('3', '4'), ('3', 'A'), ('4', 'A')]


				!!! 
					Some take functions use a threshold below the possible results to save running time
					and code size; the comparison counts in the lambdas came from earlier runs
					and other tests.
				!!!
	"""
	@decorator_over
	def program2(self, load=False) -> None:

		take5 = lambda arr: not reduce( (lambda a, b: a or b), arr) # All true.																		# C1 <- 1
		take4 = lambda arr: arr.count(False) == 6																									# C2 <- 1
		take3 = lambda arr: arr.count(False) == 2																									# C3 <- 1
		take2 = lambda arr: arr.count(False) == 1	# last chance.																					# C4 <- 1

		index_markup = (lambda x: str(x[0])+":"+str(x[1]))																							# C5 <- 1

		self.sceneryC1 = self.program_handler(program_number=2, savename='sceneryC1', combination_p=self.combinations_d,	
								 combination_number=2, set_index=index_markup, takes=[take5, take4, take3, take2], load=load)					# C6 <- k*n


	# C Total -> 4 + k*n -> O(k * n)
	"""
		3.	find the smallest set of 5-number sequences that contains every
		    3-number sequence, scenario C2. PROGRAM 3.		
	"""
	@decorator_over
	def program3(self, load=False) -> None:

		take5 = lambda arr: not reduce( (lambda a, b: a or b), arr) # All true.																		# C1 <- 1
		take4 = lambda arr: arr.count(False) == 4																									# C2 <- 1
		take3 = lambda arr: arr.count(False) == 2 # last chance.																					# C3 <- 1

		index_markup = (lambda x: str(x[0])+":"+str(x[1])+":"+str(x[2]))																			# C4 <- 1

		self.sceneryC2 = self.program_handler(program_number=3, savename='sceneryC2', combination_p=self.combinations_c,
			 							combination_number=3, set_index=index_markup, takes=[take5, take4, take3], load=load)					# C5 <- k*n


	# C Total -> 4 + k*n -> O(k * n)
	"""
		4.	find the smallest set of 5-number sequences that contains every
			4-number sequence, scenario C3. PROGRAM 4.
	"""
	@decorator_over
	def program4(self, load=False) -> None:

		take5 = lambda arr: not reduce( (lambda a, b: a or b), arr) # All true.																		# C1 <- 1
		take4 = lambda arr: arr.count(False) == 3																									# C2 <- 1
		take3 = lambda arr: arr.count(False) == 1	# last chance.																					# C3 <- 1

		index_markup = (lambda x: str(x[0])+":"+str(x[1])+":"+str(x[2])+":"+str(x[3]))																# C4 <- 1

		self.sceneryC3 = self.program_handler(program_number=4, savename='sceneryC3', combination_p=self.combinations_b,
										 combination_number=4, set_index=index_markup, takes=[take5, take4, take3], load=load)					# C5 <- k*n


	# C Total -> 1 -> O(1)
	# Number of matching numbers; arr2 is a database row.
	def point_hits(self, arr1, arr2) -> int:

		return len( ( set(arr1) & set(arr2[0:5]) ))																									# C1 <- 1


	# C Total -> 33 + n + k * n -> O(k * n)
	"""
		5.	assuming these combinations represent the betting system of some European country,
		 	compute the return of a bettor in each scenario: C1, C2 and C3.
		 	Use the following rules and values:

		 		a)	2 matching numbers on a card pay 4.16 euros. PROGRAM 5.

				C1, C2, C3 -> prefer cards with 4 hits, then the ones with 2.
	"""
	@decorator_over
	def program5(self) -> None:
		
		if self.database == []:																														# C1 <- 1
			
			self.load_database()																													# C2 <- n 

		if not self.load_sceneries():																												# C3 <- 1

			print("\n\t\tScenarios not loaded.\n")																								# C4 <- 1

			return;																																	# C5 <- 0

		result = {}																																	# C6 <- 1

		def all_cards(call, scenery):																												# C7 <- 1

			points = []																																# C8 <- 1

			[points.append(max([2 if m > 1 else m for m in [self.point_hits(i, db_i) for db_i in self.database]] )) for i in scenery] 				# C9 <- k * n

			result[call] = int( ( points.count(2) ) * 4.19 )																						# C10 <- 1

			print('\t Program 5 --> '+call+' finished.')																							# C11 <- 1

		data_ = [self.sceneryC1, self.sceneryC2, self.sceneryC3[:15000],self.sceneryC3[15000:30000],
												self.sceneryC3[30000:45000], self.sceneryC3[45000:]]												# C12 <- 1

		threads = []																																# C13 <- 1

		[threads.append(threading.Thread(target=all_cards, args=('thread '+str(i), data_[i],))) for i in range(0, len(data_))]						# C14 <- 6

		[t.start() for t in threads]																												# C15 <- 6

		[t.join() for t in threads]																													# C16 <- 6

		res = [result['thread 0'], result['thread 1'], sum(result[i] for i in list(result.keys())[2:])]												# C17 <- 3
		
		self.all_gains['Program 5'] = res																											# C18 <- 1

		self.bets['5'] = (f"\nProgram 5: \n\n\t\tScenario 1: {res[0]} euros.\n\t\tScenario 2: {res[1]} euros.\n\t\tScenario 3: {res[2]} euros.\n")	# C19 <- 1

	
	# C Total -> 33 + n + k * n -> O(k * n)
	"""
		b)	3 matching numbers on a card pay 11.89 euros. Note that the maximum payout
			in this case includes 1 card with 3 numbers and 3 cards with 2 numbers.
			PROGRAM 6.
	"""
	@decorator_over
	def program6(self) -> None:
		
		if self.database == []:																														# C1 <- 1
			
			self.load_database()																													# C2 <- n

		if not self.load_sceneries():																												# C3 <- 1

			print("\n\t\tScenarios not loaded.\n")																								# C4 <- 1

			return;																																	# C5 <- 0
		
		result = {}																																	# C6 <- 1
		
		def all_cards(call, scenery):																												# C7 <- 1

			points = []																																# C8 <- 1

			[points.append(max([3 if m > 2 else m for m in [self.point_hits(i, db_i) for db_i in self.database]] )) for i in scenery] 				# C9 <- k * n

			result[call] = int( ( points.count(3) + int(points.count(2)/3) ) * 11.89 )																# C10 <- 1

			print('\t Program 6 --> '+call+' finished.')																							# C11 <- 1

		data_ = [self.sceneryC1, self.sceneryC2, self.sceneryC3[:15000], self.sceneryC3[15000:30000], 
												 self.sceneryC3[30000:45000], self.sceneryC3[45000:]]												# C12 <- 1

		threads = []																																# C13 <- 1

		[threads.append(threading.Thread(target=all_cards, args=('thread '+str(i), data_[i],))) for i in range(0, len(data_))]						# C14 <- 6

		[t.start() for t in threads]																												# C15 <- 6

		[t.join() for t in threads]																													# C16 <- 6

		res = [result['thread 0'], result['thread 1'], sum(result[i] for i in list(result.keys())[2:])]												# C17 <- 3

		self.all_gains['Program 6'] = res 																											# C18 <- 1

		self.bets['6'] = (f"\nProgram 6: \n\n\t\tScenario 1: {res[0]} euros.\n\t\tScenario 2: {res[1]} euros.\n\t\tScenario 3: {res[2]} euros.\n")	# C19 <- 1


	# C Total -> 33 + n + k * n -> O(k * n)
	"""
		c)	4 matching numbers on a card pay 82.31 euros. Note that the maximum payout in this
			case includes 1 card with 4 numbers, 4 cards with 3 numbers and 6 cards with
			2 numbers. PROGRAM 7.
	"""
	@decorator_over
	def program7(self) -> None:
	
		if self.database == []:																														# C1 <- 1
			
			self.load_database()																													# C2 <- n

		if not self.load_sceneries():																												# C3 <- 1

			print("\n\t\tScenarios not loaded.\n")																								# C4 <- 1

			return;																																	# C5 <- 0

		result = {}																																	# C6 <- 1
		
		def all_cards(call, scenery):																												# C7 <- 1

			cards = []																																# C8 <- 1

			[cards.append(max([4 if m > 3 else m for m in [self.point_hits(i, db_i) for db_i in self.database]] )) for i in scenery]				# C9 <- k * n

			result[call] = int( ( cards.count(4) + int(cards.count(3)/4) + int(cards.count(2)/6) ) *  82.31 )										# C10 <- 1

			print('\t Program 7 --> '+call+' finished.')																							# C11 <- 1
		
		data_ = [self.sceneryC1, self.sceneryC2, self.sceneryC3[:15000], self.sceneryC3[15000:30000], 
												 self.sceneryC3[30000:45000], self.sceneryC3[45000:]]												# C12 <- 1

		threads = []																																# C13 <- 1

		[threads.append(threading.Thread(target=all_cards, args=('thread '+str(i), data_[i],))) for i in range(0, len(data_))]						# C14 <- 6

		[t.start() for t in threads]																												# C15 <- 6

		[t.join() for t in threads]																													# C16 <- 6

		res = [result['thread 0'], result['thread 1'], sum(result[i] for i in list(result.keys())[2:])]												# C17 <- 3

		self.all_gains['Program 7'] = res																											# C18 <- 1

		self.bets['7'] = (f"\nProgram 7: \n\n\t\tScenario 1: {res[0]} euros.\n\t\tScenario 2: {res[1]} euros.\n\t\tScenario 3: {res[2]} euros.\n")	# C19 <- 1


	# C Total -> 33 + n + k * n -> O(k * n)
	"""
		d)	5 matching numbers on a card pay 70,584.44 euros. Note that the maximum payout
		 	in this case includes 1 card with 5 numbers, 5 cards with 4 numbers,
		 	10 cards with 3 numbers and 10 cards with 2 numbers. PROGRAM 8.
	"""
	@decorator_over
	def program8(self) -> None:
		
		if self.database == []:																														# C1 <- 1
			
			self.load_database()																													# C2 <- n

		if not self.load_sceneries():																												# C3 <- 1

			print("\n\t\tScenarios not loaded.\n")																								# C4 <- 1

			return;																																	# C5 <- 0

		result = {}																																	# C6 <- 1
		
		def all_cards(call, scenery):																												# C7 <- 1

			cards = []																																# C8 <- 1

			[cards.append(max([ m for m in [self.point_hits(i, db_i) for db_i in self.database]] )) for i in scenery] 								# C9 <- k * n

			result[call] = int( ( cards.count(5) + int(cards.count(4)/5) + int(cards.count(3)/10) + int(cards.count(2)/10) ) *  70584.44 )			# C10 <- 1

			print('\t Program 8 --> '+call+' finished.')																							# C11 <- 1
		
		data_ = [self.sceneryC1, self.sceneryC2, self.sceneryC3[:15000], self.sceneryC3[15000:30000], 
												 self.sceneryC3[30000:45000], self.sceneryC3[45000:]]												# C12 <- 1

		threads = []																																# C13 <- 1

		[threads.append(threading.Thread(target=all_cards, args=('thread '+str(i), data_[i],))) for i in range(0, len(data_))]						# C14 <- 6

		[t.start() for t in threads]																												# C15 <- 6

		[t.join() for t in threads]																													# C16 <- 6

		res = [result['thread 0'], result['thread 1'], sum(result[i] for i in list(result.keys())[2:])]												# C17 <- 3

		self.all_gains['Program 8'] = res																											# C18 <- 1

		self.bets['8'] = (f"\nProgram 8: \n\n\t\tScenario 1: {res[0]} euros.\n\t\tScenario 2: {res[1]} euros.\n\t\tScenario 3: {res[2]} euros.\n")	# C19 <- 1


	# C Total -> 3 -> O(1)
	# Sorts the betting results for display after programs 5, 6, 7 and 8 ran.
	def betting_results(self):

		k = list(self.bets.keys())																												# C1 <- 1
		k.sort()																																	# C2 <- 1
		[print(self.bets[n]) for n in k if len(k) == 4]																							# C3 <- 1


	# C Total -> 2 -> O(1)
	# Store the last gains.
	@decorator_over
	def store_gains(self) -> None:

		if len(self.all_gains) == 4:																												# C1 <- 1
			
			self.storage(self.all_gains, 'last_gains/all_gains.pkl')																				# C2 <- 1


	# C Total -> 22 + 9n + k * n -> O(k * n)
	"""
		6.	Using the prize history of recent years available in "euro_concursos.csv",
			write a backtest program and produce a report showing the amounts invested and
			the returns obtained. PROGRAM 9.
	"""
	@decorator_over
	def program9(self) -> None:
		
		compare = lambda arr_i:  sorted([[ len(set(i[1:6]) & set(arr_i)), i] for i in self.db_backtest], key=(lambda elem: elem[0]))[-1]			# C1 <- n

		readjust = lambda arr_i, match: [ (i if i in match else chr(248)) for i in arr_i]  															# C2 <- n

		winners = lambda scenery, limit: [ [ readjust(i, compare(i)[1][1:6]), compare(i)[1] ] if compare(i)[0] > limit else 
										[readjust([-1,-1,-1,-1,-1], compare(i)[1][1:6]), compare(i)[1]] for i in scenery if compare(i)[0] > 0]		# C3 <- n
		
		content = lambda scenery, res: [ res.append("\t\t"+(str(i+1)+":  ").ljust(8)+str(scenery[i][0]).ljust(25)+
																			   "   -->  "+str(scenery[i][1])) for i in range(0, len(scenery))]  	# C4 <- n

		display = lambda scenery, title, res: [res.append("\n\t\t"+title+"   ('ø': number not hit).\n\n\t\t"+str(self.database_header[0:5])+
								         						 "      ---  "+str(self.db_backtest_header)+"\n\n"), content(scenery, res)]			# C5 <- n

		compare_db_i = lambda scenery, min_points: [ [i[0], len( [k for k in [ int(len(set(i[1:6]) & set(j))/min_points) 
													for j in scenery] if k > 0])] for i in self.db_backtest] # [Card number, number of hits].		# C6 <- k * n

		db_display = lambda scenery, res: [ [res.append("CON\t\t\t"), 
											[res.append('CON[No:'+(str(i[0])+',').ljust(5)+' hits='+(str(i[1])+']').ljust(13)) for i in scenery[i:i+5]], 
									   		 res.append("")] for i in range(0, len(scenery), 10)]													# C7 <- n

		if len(self.all_gains) < 4:																													# C8 <- 1

			self.all_gains = self.loader('last_gains/all_gains.pkl')																				# C9 <- 1

		invested = lambda scenery: [str(k)+" -> "+str(len(scenery))+" cards." for k in self.all_gains.keys()]										# C10 <- 1

		profit = lambda _0_to_2: [str(k)+" -> "+str(self.all_gains[k][_0_to_2])+" euros." for k in self.all_gains.keys()]							# C11 <- 1
		
		op = lambda scenery: 0 if scenery == self.sceneryC1 else ( 1 if scenery == self.sceneryC2 else 2)											# C12 <- 1
		
		inv_profit = lambda scenery: [ ["Invested:  "+invested(scenery)[i], "Payout:    "+profit(op(scenery))[i]]
																			for i in range(0, len(self.all_gains))]									# C13 <- 1

		scenery_report = lambda scenery, res: [res.append("\n\t\tReport (Scenario "+str(op(scenery)+1)+"):\n\n\t\t\t\t"),
									(res.append("CON\t\t\t\t"+"\n\n\t\t\t\t".join(["\n\t\t\t\t".join(i) for i in inv_profit(scenery)])+"\n\n"))]     # C14 <- 1

		control = lambda scenery, res: [display(winners(scenery, op(scenery)+1), ("Scenario "+str(op(scenery)+1)+":"), res),
						   res.append(f"\n\n\t\tEvery hit of scenario {str(op(scenery)+1)} received by the entries (No) in db_backtest:\n"),
																	db_display(compare_db_i(scenery, 2), res), scenery_report(scenery, res)	]	# C15 <- 1

		resultC1, resultC2, resultC3 = [], [], []																									# C16 <- 3

		print("\n\nBacktest report:")																											# C17 <- 1

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
		7.	Since the course focuses on complexity analysis, the last item of the assignment is the
			time-complexity analysis of each of the following items: PROGRAM 1, PROGRAM 2,
			PROGRAM 3, PROGRAM 4, PROGRAM 5, PROGRAM 6, PROGRAM 7, PROGRAM 8, PROGRAM 9.
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
			
			[print(row, end='') for row in open("console/sample-output.txt",'r')]																	# C6 <- 1

		if store_console:																															# C7 <- 1

			sys.stdout = open('console/console.txt', 'w')																							# C8 <- 1


	# C Total -> 7 + 3 * k * n -> O(k * n)
	# Quick-test helper.
	@decorator_over
	def quick_test(self, test=False, programs_2_to_4=None, programs_5_to_8=None):

		if not test:																																# C1 <- 1

			return																																	# C2 <- 0

		print("\nPrograms 2 to 4: ")																													# C3 <- 1

		programs_2_to_4()																															# C4 <- k * n

		self.sceneryC1 = self.loader('arrs/sceneryC1.pkl')																							# C5 <- 1
		self.sceneryC2 = self.loader('arrs/sceneryC2.pkl')																							# C6 <- 1
		self.sceneryC3 = self.loader('arrs/sceneryC3.pkl')																							# C7 <- 1

		print("\nPrograms 5 to 8: ")																													# C8 <- 1

		programs_5_to_8()																															# C9 <- k * n

		self.time_analysis(funcs=[programs_2_to_4, programs_5_to_8], repeat=5, call_name='quick_test', show_fig=False)								# C10 <- k * n

		self.over = True 																															# C11 <- 1



#####################################################
#########   ####   ####   #####  ###    ###  ########
#########  # ## #  ###  #  ####  ###  #  ##  ########
#########  ##  ##  ##  ###  ###  ###  ##  #  ########
#########  ######  #  #####  ##  ###  ####   ########
#####################################################


prog = Programs()


#####################################################
#####################################################

prog.console(last_console_show=True, store_console=False)  # Show the last console output OR save this run's results to console/console.txt.


prog.quick_test(test=False, programs_2_to_4=prog.program2, programs_5_to_8=prog.program5) # Tests two functions and times them.

# last_console_show=True stops quick_test from running. Set it to False and test=True for quick tests.

#####################################################
#####################################################


prog.load_database() # Loads the CSV database.


prog.program1() # Loads the combinations and prints the sequence counts.


#####################################################
#####################################################

if not prog.over:

	print("\nGenerating scenarios:\n")


thread_sceneries = []

#                                                  load flag | (if True) ~ loads the scenarios saved as .pkl files.
#												            V
[thread_sceneries.append(threading.Thread(target=p, args=(False,))) for p in [prog.program2, prog.program3, prog.program4]]


[thread.start() for thread in thread_sceneries]


[thread.join() for thread in thread_sceneries]

#####################################################
#####################################################

if not prog.over:

	print("\n\nGenerating the bets:\n")


bet_threads = []


[bet_threads.append(threading.Thread(target=p)) for p in [prog.program5, prog.program6, prog.program7, prog.program8]] 


[thread.start() for thread in bet_threads]


[thread.join() for thread in bet_threads]


prog.betting_results() # Cents were dropped to normalize the output.

#####################################################
#####################################################


prog.store_gains() # Saves the betting returns.


#####################################################
#####################################################


prog.program9() # Backtest.


#####################################################
#####################################################

funcs = [prog.program1, prog.program2, prog.program3, prog.program4, prog.program5, prog.program6, prog.program7, prog.program8, prog.program9]


prog.time_analysis(funcs=funcs, repeat=5, show_fig=False) # Times every function.

#####################################################
#####################################################


# You can skip a specific function with these instructions.
#
# prog.over = True
#  
#   func()
#
# prog.over = False
