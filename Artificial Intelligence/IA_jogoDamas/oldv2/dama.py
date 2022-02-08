#####################################################
#####################################################
# Imports, iniciando a lib colorama e definindo o 
# tamanho do board do jogo.


"""
	Equipe:

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
# Classe para implementação da IA.

class AbstractAI():

	def __init__(self):
		pass
		
	def play(self):
		return (0,0,0,0)

#####################################################
#####################################################

# Função para print colorido.
##
def cprint(color, msg, end="\n"):
	print(color + msg + Fore.RESET + Back.RESET, end=end)


def generateBoard2():
	board = []
	
	board.append([" ","1"," ","1"," ","1"," ","2*"])
	board.append([" "," "," "," ","1"," "," "," "])
	board.append([" "," "," "," "," "," "," "," "])
	board.append(["1"," "," "," "," "," "," "," "])
	board.append([" ","2"," "," "," "," "," ","1*"])
	board.append([" "," "," "," "," "," "," "," "])
	board.append([" "," "," "," "," ","1"," ","1"])
	board.append([" "," "," "," "," "," "," "," "])
	
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
	
# Função que gera o board inicial em array.
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
			

# Imprime o board de forma colorida e com as peças nos seus lugares(damas e rainhas).
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


# Testa se o movimento é válido ou se é possível matar peça adversária.
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


# Converte dama em rainha quando atinge o limite do board.
##
def checkForLimit(turn, x, y, board):
	
	if "*" not in board[y][x] and ((turn == 1 and y == boardSize-1) or (turn == 2 and y == 0)):
	
		board[y][x] += "*"


# Executa a rotina de rodadas e movimentos das peças.
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
	
		return 3-turn # Troca o turno: 2 -> 1; 1 -> 2.
	
	elif abs(deltaY) == 2 and abs(deltaX) == 2:
	
		if board[int(y+deltaY/2)][int(x+deltaX/2)] == str(3-turn):
	
			board[int(y+deltaY/2)][int(x+deltaX/2)] = " "
	
			board[desiredY][desiredX] = board[y][x]
	
			checkForLimit(turn, desiredX, desiredY, board)
	
			board[y][x] = " "
	
			if test(desiredX, desiredY, board):
	
				return turn
	
			else:
	
				return 3-turn
	else:
	
		return -1


#####################################################
#####################################################			
# Iniciando execução do jogo.


# Criando o board de início.
board = generateBoard()


# Função troca a vez de jogo e executa o programa.
##
def run(ai, aiObject):

	printBoard(board)

	turn = 1

	winner = 0

	eating = False

	x,y,desiredX,desiredY = 0,0,0,0

	while winner == 0:

		print("Jogador",turn)

		try:

			if turn == 1 or not ai:

				if not eating:
					x = int(input("X:"))
					y = int(input("Y:"))

				desiredX = int(input("Desired X:"))
				desiredY = int(input("Desired Y:"))

			else:

				x, y, desiredX, desiredY = aiObject.play()

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
		print(winner)
	
	print("Congratulations, player", str(winner) + "!")
#####################################################
#####################################################	
# MAIN.

if __name__ == "__main__":
	
	run(False, None)

#####################################################
#####################################################	