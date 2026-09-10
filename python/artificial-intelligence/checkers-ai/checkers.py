"""Console checkers (draughts) with coloured board - Artificial Intelligence course, PUCPR 2021.

Board cells hold " " (empty), "1"/"2" (red/blue men) or "1*"/"2*" (kings).
Player 1 moves down the board, player 2 moves up; kings move both ways.
Captures jump two squares diagonally and may be chained.

Run `python checkers.py` for two human players (needs `pip install colorama`),
or `python checkers_ai.py` to play against the rule-based AI.
"""
#####################################################
#####################################################
# Imports, colorama initialisation and board size.


"""
	Team:

		André Wlodkoski.
		Gustavo Hammerschmidt.
		Isa Stohler.
	
"""


#encoding:utf-8
from colorama import Fore, Back, Style, init

init()

boardSize = 8

#####################################################
#####################################################
# Base class for AI players (checkers_ai.py subclasses it).

class AbstractAI():

	def __init__(self):
		pass
		
	def play(self):
		return (0,0,0,0)

#####################################################
#####################################################

# Coloured print helper.
##
def cprint(color, msg, end="\n"):
	print(color + msg + Fore.RESET + Back.RESET, end=end)


# A hand-made mid-game position used while testing the AI.
def generateTestBoard():
	board = []
	
	board.append([" "," "," "," "," ","1"," ","2*"])
	board.append([" "," "," "," "," "," "," "," "])
	board.append([" "," "," "," "," "," "," "," "])
	board.append([" "," ","1"," "," "," "," "," "])
	board.append([" "," "," "," "," ","2"," "," "])
	board.append(["2"," "," "," "," "," "," "," "])
	board.append([" ","2"," ","1"," ","2"," "," "])
	board.append(["2"," "," "," ","1*"," ","2"," "])
	
	return board

def checkWin(board):
	amount1 = 0
	amount2 = 0
	for row in board:
		for cell in row:
			if "1" in cell:
				amount1 += 1
			elif "2" in cell:
				amount2 += 1
				
			if amount1 > 0 and amount2 > 0:
				return 0
	
	if amount1 == 0:
		return 2
	if amount2 == 0:
		return 1
		
	return 0
	
# Builds the initial board as a 2D list: '1' = red men, '2' = blue men, a trailing '*' marks a king.
##
def generateBoard():

	board = []

	for i in range(boardSize):

		row = []

		for j in range(boardSize):

			if i < boardSize/2 -1 and ((i%2==0 and j%2==1) or (i%2==1 and j%2==0)):

				row.append("1")

			elif i > boardSize/2 and ((i%2==0 and j%2==1) or (i%2==1 and j%2==0)):

				row.append("2")

			else:
				row.append(" ")
			
		board.append(row)
	
	return board
			

# Prints the board in colour with the pieces in place (men and kings).
##
def printBoard(board):
	
	cprint(Back.YELLOW, " ",end="")
	
	for i in range(boardSize):
	
		cprint(Back.YELLOW + Fore.BLACK + Style.NORMAL, str(i), end="")
	
	cprint(Back.YELLOW, " ")
	
	for i in range(boardSize):
	
		cprint(Back.YELLOW + Fore.BLACK + Style.NORMAL, str(i), end="")
	
		for j in range(boardSize):
	
			if (i%2==0 and j%2==1) or (i%2==1 and j%2==0):
	
				if board[i][j] == "1":
	
					cprint(Back.BLACK + Style.BRIGHT + Fore.RED, "o", end="")
	
				elif board[i][j] == "2":
	
					cprint(Back.BLACK + Style.BRIGHT + Fore.BLUE, "o", end="")
	
				elif board[i][j] == "1*":
	
					cprint(Back.BLACK + Style.BRIGHT + Fore.RED, "O", end="")
	
				elif board[i][j] == "2*":
	
					cprint(Back.BLACK + Style.BRIGHT + Fore.BLUE, "O", end="")
	
				else:
	
					cprint(Back.BLACK + Style.BRIGHT + Fore.BLUE, " ", end="")
	
			else:
	
				cprint(Back.WHITE + Fore.YELLOW, board[i][j], end="")
	
		cprint(Back.YELLOW, " ")
	
	cprint(Back.YELLOW, " "*(boardSize+2))


# Checks whether the piece at (x, y) can capture an opposing piece.
##
def test(x, y, board):

	if "*" in board[y][x]:

		if y >= boardSize-2 or y <= 1:

			return False
			
		enemy = "2" if "1" in board[y][x] else "1"

		if x <= 1:

			return (enemy in board[y+1][x+1] and board[y+2][x+2] == " ") or (enemy in board[y-1][x+1] and board[y-2][x+2] == " ")

		elif x >= boardSize-2:

			return (enemy in board[y+1][x-1] and board[y+2][x-2] == " ") or (enemy in board[y-1][x-1] and board[y-2][x-2] == " ")

		else:

			return ((enemy in board[y+1][x+1] and board[y+2][x+2] == " ") or (enemy in board[y+1][x-1] and board[y+2][x-2] == " ") or 
					(enemy in board[y-1][x+1] and board[y-2][x+2] == " ") or (enemy in board[y-1][x-1] and board[y-2][x-2] == " "))
			
	elif "1" in board[y][x]:

		if y < boardSize-2:

			if x <= 1:

				return "2" in board[y+1][x+1] and board[y+2][x+2] == " "

			elif x >= boardSize-2:

				return "2" in board[y+1][x-1] and board[y+2][x-2] == " "

			else:

				return ("2" in board[y+1][x+1] and board[y+2][x+2] == " ") or ("2" in board[y+1][x-1] and board[y+2][x-2] == " ")

		else:

			return False
			
	elif "2" in board[y][x]:
		
		if y > 1:
		
			if x <= 1:
		
				return "1" in board[y-1][x+1] and board[y-2][x+2] == " "
		
			elif x >= boardSize-2:
		
				return "1" in board[y-1][x-1] and board[y-2][x-2] == " "
		
			else:
		
				return ("1" in board[y-1][x+1] and board[y-2][x+2] == " ") or ("1" in board[y-1][x-1] and board[y-2][x-2] == " ")
		
		else:
		
			return False
		
	return False


# Promotes a man to a king when it reaches the far edge of the board.
##
def checkForLimit(turn, x, y, board):
	
	if "*" not in board[y][x] and ((turn == 1 and y == boardSize-1) or (turn == 2 and y == 0)):
	
		board[y][x] += "*"


# Applies one move for the given player. Returns the next turn, the same turn when a
# capture can be continued, or -1 for an invalid move.
##
def play(turn, x, y, desiredX, desiredY, eating, board):
	
	if str(turn) not in board[y][x] or board[desiredY][desiredX] != " ":
	
		return -1
	
	deltaX = desiredX - x
	deltaY = desiredY - y
	
	if "*" not in board[y][x] and ((turn == 1 and deltaY <= 0) or (turn == 2 and deltaY >= 0)):
	
		return -1
		
	if abs(deltaY) == 1 and abs(deltaX) == 1 and not eating:
	
		board[desiredY][desiredX] = board[y][x]
	
		checkForLimit(turn, desiredX, desiredY, board)
	
		board[y][x] = " "
	
		return 3-turn # Switch turns: 2 -> 1; 1 -> 2.
	
	elif abs(deltaY) == 2 and abs(deltaX) == 2:
	
		if str(3-turn) in board[int(y+deltaY/2)][int(x+deltaX/2)]:
	
			board[int(y+deltaY/2)][int(x+deltaX/2)] = " "
	
			board[desiredY][desiredX] = board[y][x]
	
			checkForLimit(turn, desiredX, desiredY, board)
	
			board[y][x] = " "
	
			if test(desiredX, desiredY, board):
	
				return turn
	
			else:
	
				return 3-turn
	
	return -1


#####################################################
#####################################################			
# Game execution.


# Initial board.
board = generateBoard()


# Main loop: alternates turns between the human player(s) and the AI object.
##
def run(ai, aiObject):

	printBoard(board)

	turn = 1

	winner = 0

	eating = False

	x,y,desiredX,desiredY = 0,0,0,0

	while winner == 0:

		print("Player", turn)

		try:

			if not ai or turn != aiObject.team:

				if not eating:
					x = int(input("X:"))
					y = int(input("Y:"))

				desiredX = int(input("Desired X:"))
				desiredY = int(input("Desired Y:"))

			else:

				x, y, desiredX, desiredY = aiObject.play()
				print(x,y,desiredX,desiredY)

		except ValueError:

			continue
	
		status = play(turn, x, y, desiredX, desiredY, eating, board)

		if status == -1:

			continue

		else:

			eating = status == turn
			x = desiredX
			y = desiredY
			turn = status
		
		printBoard(board)

		winner = checkWin(board)
	
	print("Congratulations, player", str(winner) + "!")
#####################################################
#####################################################	
# MAIN.

if __name__ == "__main__":
	
	run(False, None)

#####################################################
#####################################################	