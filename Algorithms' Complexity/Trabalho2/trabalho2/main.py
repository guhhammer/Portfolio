#####################################################
#####################################################

# Nome: Gustavo Hammerschmidt.

#####################################################
#####################################################
# Imports.


from itertools import combinations, tee
from functools import reduce

from match_selector import selector_for_

import threading, time, sys, matplotlib.pyplot as plt


#####################################################
#####################################################
# Variáveis utilizadas.


values = list(range(1, 26)) # [1 .. 25]

S15, S14, S13, S12, S11 = [], [], [], [], []

SB15_14, SB15_13, SB15_12, SB15_11 = [], [], [], []

show_last_console = False


#####################################################
#####################################################


# Decorator para calcular tempo de execução.
##
def exec_time(func) -> ():

	def wrapper(*args, **kwargs) -> ():

		track = time.time()

		ret = func(*args, **kwargs)

		track = time.time() - track

		print(f'\nExecution time of {func.__name__}: {round(float(track), 2)} seconds.\n')

		return ret

	return wrapper


# Decorator para cancelar o resto da execução.
##
def last_console_displayed(func) -> ():

	def wrapper(*args, **kwargs) -> ():

		if show_last_console:

			print(f"\nDecorator stopped the execution of {func.__name__} (show_last_console = True).")

			return

		return func(*args, **kwargs)

	return wrapper


# Limpa a memória.
##
@last_console_displayed
def cleanMemory() -> None:

	global SB15_14, SB15_13, SB15_12, SB15_11

	SB15_14, SB15_13, SB15_12, SB15_11 = [], [], [], []


# Retorna a quantidade de cartões.
##
@exec_time
def getNCards(arr) -> int:

	counter = 0
	for _ in arr:

		counter += 1

	return counter


#####################################################
#####################################################


# Gera as combinações de S15, S14, S13, S12 e S11. 
##
@last_console_displayed
@exec_time
def Programa1() -> None:

	global values, S15, S14, S13, S12, S11

	S15 = combinations(values, 15)
	S14 = combinations(values, 14)
	S13 = combinations(values, 13)
	S12 = combinations(values, 12)
	S11 = combinations(values, 11)


# Gera o cenário SB15_14.
##
@last_console_displayed
@exec_time
def Programa2() -> None:

	global SB15_14

	SB15_14 = selector_for_( combinations(values, 15), combinations(values, 14), 14 )


# Gera o cenário SB15_13.
##
@last_console_displayed
@exec_time
def Programa3() -> None:

	global SB15_14, SB15_13

	SB15_14, SB15_14_clone = tee(SB15_14)

	SB15_13 = selector_for_( SB15_14_clone, combinations(values, 13), 13 )

	del SB15_14_clone


# Gera o cenário SB15_12.
##
@last_console_displayed
@exec_time
def Programa4() -> None:

	global SB15_13, SB15_12

	SB15_13, SB15_13_clone = tee(SB15_13)

	SB15_12 = selector_for_( SB15_13_clone, combinations(values, 12), 12 )

	del SB15_13_clone


# Gera o cenário SB15_11.
##
@last_console_displayed
@exec_time
def Programa5() -> None:

	global SB15_12, SB15_11

	SB15_12, SB15_12_clone = tee(SB15_12)

	SB15_11 = selector_for_( SB15_12_clone, combinations(values, 11), 11 )

	del SB15_12_clone


# Analisa o tempo médio dos programas e gera os gráficos.
##
@last_console_displayed
@exec_time
def Programa6() -> None:

	progs = [Programa2, Programa3, Programa4, Programa5]

	print('\n{\n\tPrograma6:\n\n')

	kernel = sys.stdout

	for p in progs:

		print(f'\t\tExecuting Programa{progs.index(p)+2} [0 to 9] => ', end='')

		track_sum = 0

		graph = plt

		time_arr = []

		for _ in range(0, 10):

			sys.stdout = None

			track = time.time()

			p()

			track_sum += time.time() - track

			time_arr.append(time.time()-track)

			sys.stdout = kernel

			print(f'{_} ', end='')

		avg_ = round(float(track_sum/10), 2)

		print(f'=> Average time: {avg_} seconds.')

		graph.plot(time_arr,label=f'Programa{progs.index(p)+2}')
		graph.plot([avg_ for _ in range(len(time_arr))], label='Average='+str(avg_))
		graph.title(f'When p() == Programa{progs.index(p)+2}')
		graph.legend(loc='best')
		graph.savefig(f'graphics/Programa{progs.index(p)+2}_graph.png')

	print('\n}\n')


# Mostra o custo com os cartões.
##
@last_console_displayed
@exec_time
def Programa7() -> None:
		
	kernel = sys.stdout
	sys.stdout = None
	
	Programa2()
	Programa3()
	Programa4()
	Programa5()

	sys.stdout = kernel
	
	sbs = [SB15_14, SB15_13, SB15_12, SB15_11]

	invested_cards = []
	
	print('\n{\n\tPrograma7:\n\n')

	for sb in sbs:

		print(f'\t\tExecuting SB15_{14-sbs.index(sb)} now... ', end='')

		cards = getNCards(sb)

		invested_cards.append(cards)

	print('\n\n')

	for i in invested_cards:

		print(f'SB15_{14-invested_cards.index(i)} => {i} cards * R$ 2,50 => R$ {i * 2.5}\n')

	print('\n}\n')


# Shows Last console.
##
@exec_time
def lastConsole(show=False):

	global show_last_console

	if show:

		[print(row, end='') for row in open("console/PRIMEconsole.txt",'r')]	

		show_last_console = True


#####################################################
#########   ####   ####   #####  ###    ###  ########
#########  # ## #  ###  #  ####  ###  #  ##  ########
#########  ##  ##  ##  ###  ###  ###  ##  #  ########
#########  ######  #  #####  ##  ###  ####   ########
#####################################################


lastConsole(show=True)


Programa1()

Programa2()

Programa3()

Programa4()

Programa5()

cleanMemory()

Programa6()

cleanMemory()

Programa7()


# For every halving of SB15_14 in Programa7, the combinations' size decreases in 2 and
# the execution time increases in 4 roughly, because the number of comparisons increases
# with every lessing of (r) in a combintation.


#####################################################
#####################################################