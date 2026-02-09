
import os, time, random


table_start = [ [' ', ' ', ' '], [' ', ' ', ' '], [' ', ' ', ' ']]

cleaner = lambda: os.system('cls')

table_show = lambda table: [print(i) for i in ['\nJogo da velha:\n', f'\n\t{table[0][0]} | {table[0][1]} | {table[0][2]}', 
												'\t{}'.format('-'*10), f'\t{table[1][0]} | {table[1][1]} | {table[1][2]}', 
												'\t{}'.format('-'*10), f'\t{table[2][0]} | {table[2][1]} | {table[2][2]}','\n']]


def update_from_input(table, row, col, chr_):
	
	if row > 2 or col > 2 or col < 0 or row < 0:
	
		return False
	
	if table[row][col] == " ":
	
		table[row][col] = chr_; cleaner(); return True
	
	return False


def strike(table, chr_): #chr_ == "X" or "O"
	
	for i in range(0,3):
	
		k, l = True, True
		for j in range(0, 3):
	
			k, l = k and table[i][j]==chr_, l and table[j][i]==chr_
	
		if k or l:
	
			return True
	
	if table[0][0]==chr_ and table[1][1]==chr_ and table[2][2]==chr_:
	
		return True
	
	if table[2][0]==chr_ and table[1][1]==chr_ and table[0][2]==chr_:
	
		return True
	
	return False


def isfull(table):

	for i in range(0,3):
		for j in range(0,3):

			if table[i][j]==' ':

				return False

	return True


def execution_ai():

	table_start = [ [' ', ' ', ' '], [' ', ' ', ' '], [' ', ' ', ' ']]

	while True:
		
		r, c = input('\nDigite a linha (0 a 2): '), input('\nDigite a coluna (0 a 2): ')

		try: 

			r, c = int(r),int(c)

		except:
			
			print('Digite apenas números válidos!')

			continue
		
		confirm = update_from_input(table_start, int(r), int(c), 'X')
		
		if not confirm:

			print(f'\nTable[{r}][{c}] já estava marcado! Digite outro espaço.')

			continue

		else:

			table_show(table_start)

			if isfull(table_start):

				break

			if strike(table_start, 'X'):

				break

			print(f'\nI.A. está jogando...')

			time.sleep(2) 

			hold = start_state(table_start, 'O')

			for a in range(3):
			
				for b in range(3):

					if hold[a][b]=='O':
					
						table_start[a][b] = 'O'

			cleaner()

			table_show(table_start)

			if strike(hold, 'O'):

				break

	if strike(table_start, 'X'):

		print('\n\tVocê venceu!\n')

	elif strike(table_start, 'O'):

		print('\n\tI.A. venceu!\n')

	elif isfull(table_start):

		print("\n\tEmpate!\n")
		

def execution():

	cleaner()

	table_show(table_start)

	two_players = input('Dois jogadores(Y/n)? ').lower()=='y'

	curr_chr = "X"

	if not two_players:

		execution_ai()

		return

	while True:
		
		r, c = input('\nDigite a linha (0 a 2): '), input('\nDigite a coluna (0 a 2): ')

		try: 

			r, c = int(r), int(c)

		except:

			print('Digite apenas números válidos!')

			continue
		
		confirm = update_from_input(table_start, int(r), int(c), curr_chr)
		
		if not confirm:

			print(f'\nTable[{r}][{c}] já estava marcado! Digite outro espaço.')

			continue

		else:

			table_show(table_start)

		if two_players:

			curr_chr = "O" if curr_chr=="X" else "X"

		if strike(table_start, 'X'):

			print('\n\tX venceu!\n')

			break

		elif strike(table_start, 'O'):

			print('\n\tO venceu!\n')

			break

		if isfull(table_start):

			print("\n\tEmpate!\n")

			break


def generate(table_, chr_):
	
	tables = []
	
	for i in range(0, 3):
	
		for j in range(0, 3):
	
			if table_[i][j] == ' ':
	
				cop = [[table_[a][b] for b in range(0,3)] for a in range(0,3)]
				cop[i][j] = chr_
				tables.append(cop)
	
	return tables


points = lambda table_: 1 if strike(table_, 'O') else (-1 if strike(table_, 'X') else 0)

maximum = lambda a, b: b if a==0 else (a if b==0 else ( a if a > b else b ))

minimum = lambda a, b: b if a==0 else (a if b==0 else ( a if a < b else b ))


def start_state(table_, start_chr_):

	track, track_chr, track_index = [], [], []

	track.append(table_)
	track_chr.append(start_chr_)
	track_index.append([0,0, 'max'])

	opposite_chr = lambda x: 'O' if x=='X' else 'X'
	opposite_func = lambda x: 'min' if x=='max' else 'max'
	applier = lambda x: maximum if x=='max' else minimum

	indicator = 0
	
	while True:

		try:
	
			curr, curr_chr = track[indicator], track_chr[indicator]
	
		except:
	
			break

		checks = isfull(curr) or strike(curr, curr_chr) or strike(curr, opposite_chr(curr_chr))
		
		if not checks:

			next_chr, curr_len = opposite_chr(curr_chr), len(track)

			for i in generate(curr, curr_chr):

				track.append(i); track_chr.append(next_chr)
				track_index.append([indicator, 0, opposite_func(track_index[indicator][2]) ])
									# pai     res           função.

		indicator += 1

	
	reverse_track = track_index[::-1][:]
	
	for i in range(0, len(track_index)):
	
		track_index[i][1] =  points( track[i] )

	for i in range(0, len(track_index)-1):
	
		index = track_index[(len(track_index)-1)-i]	
	
		track_index[index[0]][1] = applier(opposite_func(index[2])) (index[1], track_index[index[0]][1] )
		
	
	find = track_index[0][1]				 # grab max.
	for i in range(1, len(track_index)):
	
		if track_index[i][1]==find:		
	
			find = track_index[i][1]
	
			break

	# all states equal to find.
	states = ([i for i in track_index if i[0]==0 and i[1]==find][1:])


	# if win is close.
	for s in range(len(states)):

		if strike(track[1+s], 'O'):

			return track[1+s]

	# if lose is close.
	for i in generate(track[0], 'X'):

		if strike(i, 'X'):

			i_track = track[0]

			for _a in range(3):
				for _b in range(3):

					if i_track[_a][_b] != i[_a][_b]:

						i_track[_a][_b] = 'O'

						return i_track

	# random-selcet  find-value.
	good_moves = len(states)

	select = random.randint(1, good_moves)-1

	return (track[1+select]) # return track to see all possibilities.


if __name__ == "__main__":

	execution()

