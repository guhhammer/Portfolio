import os

def initBoard():
	return [[" "," "," "],[" "," "," "],[" "," "," "]]
	
def printBoard(board):
	if os.name == 'nt':
		os.system("cls")
	print("  1 2 3  ")
	print(" +-----+")
	letter = 65
	for row in board:
		print(chr(letter) + "|",end="")
		for value in row:
			print(value + "|",end="")
		print("\n +-----+")
		letter += 1
		
def getInput(board):
	valid = False
	while not valid:
		col = 0
		while col < 1 or col > 3:
			try:
				col = int(input("Selecione a coluna (1,2,3):"))
			except:
				print("Valor inválido!")
	
		row = "D"
		while ord(row) < 65 or ord(row) > 67:
			try:
				row = input("Selecione a linha (A,B,C):")[0]
			except:
				print("Valor inválido!")
				
		if board[ord(row)-65][col-1] == " ":
			valid = True
		else:
			print("Esta casa já está ocupada!")
	
	return (ord(row)-65, col-1)
	
def checkBoard(board):
	for row in board:
		if row[0] != " " and row[0] == row[1] and row[1] == row[2]:
			return ["X","O"].index(row[0])+1
	
	for i in range(3):
		if board[0][i] != " " and board[0][i] == board[1][i] and board[1][i] == board[2][i]:
			return ["X","O"].index(board[0][i])+1
	
	if (board[0][0] != " " and board[0][0] == board[1][1] and board[1][1] == board[2][2]) or (board[0][2] != " " and board[0][2] == board[1][1] and board[1][1] == board[2][0]):
		return ["X","O"].index(board[1][1])+1
	
	return 0
	
def play(board, turn, row, col):
	board[row][col] = ["X","O"][turn-1]
	return 1-turn

winner = 0
board = initBoard()
printBoard(board)
turn = 1
while winner == 0:
	print("Turno:",["X","O"][turn-1])
	coord = getInput(board)
	turn = play(board, turn, coord[0], coord[1])
	printBoard(board)
	winner = checkBoard(board)
	
print("Vencedor:",["X","O"][winner-1])