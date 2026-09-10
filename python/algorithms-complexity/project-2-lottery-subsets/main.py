#####################################################
#####################################################

# Name: Gustavo Hammerschmidt.

#####################################################
#####################################################
# Imports.


from itertools import combinations, tee
from functools import reduce

from match_selector import selector_for_

import threading, time, sys, matplotlib.pyplot as plt


#####################################################
#####################################################
# Variables.


values = list(range(1, 26)) # [1 .. 25]

S15, S14, S13, S12, S11 = [], [], [], [], []

SB15_14, SB15_13, SB15_12, SB15_11 = [], [], [], []

show_last_console = False


#####################################################
#####################################################


# Decorator that measures execution time.
##
def exec_time(func) -> ():

	def wrapper(*args, **kwargs) -> ():

		track = time.time()

		ret = func(*args, **kwargs)

		track = time.time() - track

		print(f'\nExecution time of {func.__name__}: {round(float(track), 2)} seconds.\n')

		return ret

	return wrapper


# Decorator that cancels the rest of the execution.
##
def last_console_displayed(func) -> ():

	def wrapper(*args, **kwargs) -> ():

		if show_last_console:

			print(f"\nDecorator stopped the execution of {func.__name__} (show_last_console = True).")

			return

		return func(*args, **kwargs)

	return wrapper


# Frees memory.
##
@last_console_displayed
def cleanMemory() -> None:

	global SB15_14, SB15_13, SB15_12, SB15_11

	SB15_14, SB15_13, SB15_12, SB15_11 = [], [], [], []


# Returns the number of cards.
##
@exec_time
def getNCards(arr) -> int:

	counter = 0
	for _ in arr:

		counter += 1

	return counter


#####################################################
#####################################################


# Generates the combinations S15, S14, S13, S12 and S11.
##
@last_console_displayed
@exec_time
def Program1() -> None:

	global values, S15, S14, S13, S12, S11

	S15 = combinations(values, 15)
	S14 = combinations(values, 14)
	S13 = combinations(values, 13)
	S12 = combinations(values, 12)
	S11 = combinations(values, 11)


# Generates scenario SB15_14.
##
@last_console_displayed
@exec_time
def Program2() -> None:

	global SB15_14

	SB15_14 = selector_for_( combinations(values, 15), combinations(values, 14), 14 )


# Generates scenario SB15_13.
##
@last_console_displayed
@exec_time
def Program3() -> None:

	global SB15_14, SB15_13

	SB15_14, SB15_14_clone = tee(SB15_14)

	SB15_13 = selector_for_( SB15_14_clone, combinations(values, 13), 13 )

	del SB15_14_clone


# Generates scenario SB15_12.
##
@last_console_displayed
@exec_time
def Program4() -> None:

	global SB15_13, SB15_12

	SB15_13, SB15_13_clone = tee(SB15_13)

	SB15_12 = selector_for_( SB15_13_clone, combinations(values, 12), 12 )

	del SB15_13_clone


# Generates scenario SB15_11.
##
@last_console_displayed
@exec_time
def Program5() -> None:

	global SB15_12, SB15_11

	SB15_12, SB15_12_clone = tee(SB15_12)

	SB15_11 = selector_for_( SB15_12_clone, combinations(values, 11), 11 )

	del SB15_12_clone


# Measures the average time of the programs and plots the charts.
##
@last_console_displayed
@exec_time
def Program6() -> None:

	progs = [Program2, Program3, Program4, Program5]

	print('\n{\n\tProgram6:\n\n')

	kernel = sys.stdout

	for p in progs:

		print(f'\t\tExecuting Program{progs.index(p)+2} [0 to 9] => ', end='')

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

		graph.plot(time_arr,label=f'Program{progs.index(p)+2}')
		graph.plot([avg_ for _ in range(len(time_arr))], label='Average='+str(avg_))
		graph.title(f'When p() == Program{progs.index(p)+2}')
		graph.legend(loc='best')
		graph.savefig(f'charts/Program{progs.index(p)+2}_graph.png')

	print('\n}\n')


# Shows the cost of the cards.
##
@last_console_displayed
@exec_time
def Program7() -> None:
		
	kernel = sys.stdout
	sys.stdout = None
	
	Program2()
	Program3()
	Program4()
	Program5()

	sys.stdout = kernel
	
	sbs = [SB15_14, SB15_13, SB15_12, SB15_11]

	invested_cards = []
	
	print('\n{\n\tProgram7:\n\n')

	for sb in sbs:

		print(f'\t\tExecuting SB15_{14-sbs.index(sb)} now... ', end='')

		cards = getNCards(sb)

		invested_cards.append(cards)

	print('\n\n')

	for i in invested_cards:

		print(f'SB15_{14-invested_cards.index(i)} => {i} cards * R$ 2.50 => R$ {i * 2.5}\n')

	print('\n}\n')


# Shows Last console.
##
@exec_time
def lastConsole(show=False):

	global show_last_console

	if show:

		[print(row, end='') for row in open("sample-output.txt",'r')]	

		show_last_console = True


#####################################################
#########   ####   ####   #####  ###    ###  ########
#########  # ## #  ###  #  ####  ###  #  ##  ########
#########  ##  ##  ##  ###  ###  ###  ##  #  ########
#########  ######  #  #####  ##  ###  ####   ########
#####################################################


lastConsole(show=True)


Program1()

Program2()

Program3()

Program4()

Program5()

cleanMemory()

Program6()

cleanMemory()

Program7()


# For every halving of SB15_14 in Program7, the combinations' size decreases in 2 and
# the execution time increases in 4 roughly, because the number of comparisons increases
# with every lessing of (r) in a combintation.


#####################################################
#####################################################