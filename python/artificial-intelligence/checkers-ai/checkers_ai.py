"""Rule-based AI opponent for the checkers game in checkers.py.

The AI always reasons as player 2 (moving up the board). When it plays as
player 1 the board is flipped before choosing a move and the move is mapped
back afterwards. Moves come from a fixed list of priorities (see
CheckersAI.play) rather than from tree search.

Run: `python checkers_ai.py` (needs `pip install colorama`).
The last line decides which side the AI plays: CheckersAI(1) = red (moves
first), CheckersAI(2) = blue.

Team: André Wlodkovski, Gustavo Hammerschmidt, Isa Stohler.
"""
import checkers as game
import random
import time


def flipBoard(board):
	ones = []
	twos = []
	for i in range(len(board)):
		for j in range(len(board)):
			star = "*" if "*" in board[i][j] else ""
			if "1" in board[i][j]:
				ones.append((j,i,star))
			elif "2" in board[i][j]:
				twos.append((j,i,star))
				
			board[i][j] = " "
				
	
	for x,y,star in ones:
		board[len(board)-1-y][x] = "2" + star
		
	for x,y,star in twos:
		board[len(board)-1-y][x] = "1" + star
	
	return board


def findEnemies(x,y,board):
	enemy = ""
	if "1" in board[y][x]:
		enemy = board[y][x].replace("1", "2")
	else:
		enemy = board[y][x].replace("2", "1")
	
	enemies = []
	if y > 0 and ("*" in enemy or "2" in enemy):
		if x > 0 and enemy in board[y-1][x-1]:
			enemies.append((x-1,y-1))
		if x < len(board)-1 and enemy in board[y-1][x+1]:
			enemies.append((x+1,y-1))
	if y < len(board)-1 and ("*" in enemy or "1" in enemy):
		if x > 0 and enemy in board[y+1][x-1]:
			enemies.append((x-1,y+1))
		if x < len(board)-1 and enemy in board[y+1][x+1]:
			enemies.append((x+1,y+1))
	
	return enemies


def isOutOfBounds(x,y,board):
	return x<0 or x>=len(board)-1 or y<0 or y>=len(board)-1

def findProtectors(ally, x,y,enemyX,enemyY,board):
	axisMultiplier = 1
	if "2" in ally:
		axisMultiplier = -1
		
	dangerX, dangerY = (x-(enemyX - x),y-(enemyY - y))
	
	protectors = []
	protectY = dangerY - axisMultiplier
	protectYQueen = dangerY + axisMultiplier
	if (dangerX + 1 != x or protectY != y) and not isOutOfBounds(dangerX + 1, protectY, board) and ally in game.board[protectY][dangerX + 1]:
		protectors.append((dangerX + 1, protectY))
	if (dangerX - 1 != x or protectY != y) and not isOutOfBounds(dangerX - 1, protectY, board) and ally in game.board[protectY][dangerX - 1]:
		protectors.append((dangerX - 1, protectY))
	if dangerX+1 != x and not isOutOfBounds(dangerX + 1, protectYQueen, board) and ally in game.board[protectYQueen][dangerX + 1]:
		protectors.append((dangerX + 1, protectYQueen))
	if dangerX-1 != x and not isOutOfBounds(dangerX - 1, protectYQueen, board) and ally in game.board[protectYQueen][dangerX - 1]:
		protectors.append((dangerX - 1, protectYQueen))
		
	
	return protectors

def test(x, y, board):
	boardSize = len(board)
	if "*" in board[y][x]:
		if y >= boardSize-2 or y <= 1:
			return (-1,-1,-1,-1)
			
		enemy = "2" if "1" in board[y][x] else "1"
		if x <= 1:
			if enemy in board[y+1][x+1] and board[y+2][x+2] == " ":
				return (x,y,x+2, y+2)
			elif enemy in board[y-1][x+1] and board[y-2][x+2] == " ":
				return (x,y,x+2,y-2)
			
			return (-1,-1,-1,-1)
		elif x >= boardSize-2:
			if enemy in board[y+1][x-1] and board[y+2][x-2] == " ":
				return (x,y,x-2, y+2)
			elif enemy in board[y-1][x-1] and board[y-2][x-2] == " ":
				return (x,y,x-2,y-2)
				
			return (-1,-1,-1,-1)
		else:
			if enemy in board[y+1][x+1] and board[y+2][x+2] == " ":
				return (x,y,x+2,y+2)
			elif enemy in board[y+1][x-1] and board[y+2][x-2] == " ":
				return (x,y,x-2,y+2)
			elif enemy in board[y-1][x+1] and board[y-2][x+2] == " ":
				return (x,y,x+2,y-2)
			elif enemy in board[y-1][x-1] and board[y-2][x-2] == " ":
				return (x,y,x-2,y-2)
				
			return (-1,-1,-1,-1)
			
	elif "1" in board[y][x]:
		if y < boardSize-2:
			if x <= 1:
				if "2" in board[y+1][x+1] and board[y+2][x+2] == " ":
					return (x,y,x+2,y+2)
				
				return (-1,-1,-1,-1)
			elif x >= boardSize-2:
				if "2" in board[y+1][x-1] and board[y+2][x-2] == " ":
					return (x,y,x-2,y+2)
				
				return (-1,-1,-1,-1)
			else:
				if "2" in board[y+1][x+1] and board[y+2][x+2] == " ":
					return (x,y,x+2,y+2)
				elif "2" in board[y+1][x-1] and board[y+2][x-2] == " ":
					return (x,y,x-2,y+2)
				
				return (-1,-1,-1,-1)
		else:
			return (-1,-1,-1,-1)
			
	elif "2" in board[y][x]:
		if y > 1:
			if x <= 1:
				if "1" in board[y-1][x+1] and board[y-2][x+2] == " ":
					return (x,y,x+2,y-2)
				
				return (-1,-1,-1,-1)
			elif x >= boardSize-2:
				if "1" in board[y-1][x-1] and board[y-2][x-2] == " ":
					return (x,y,x-2,y-2)
				
				return (-1,-1,-1,-1)
			else:
				if "1" in board[y-1][x+1] and board[y-2][x+2] == " ":
					return (x,y,x+2,y-2)
				elif "1" in board[y-1][x-1] and board[y-2][x-2] == " ":
					return (x,y,x-2,y-2)
				return (-1,-1,-1,-1)
		else:
			return (-1,-1,-1,-1)
		
	return (-1,-1,-1,-1)


class CheckersAI(game.AbstractAI):
	

	def __init__(self, team):
		self.team = team
		

	def freePosition(self):
		
		movable = []
		
		for i in range(len(game.board)):
		
			for j in range(len(game.board)):
		
				if "2" in game.board[i][j]:
		
					if i > 0 and (j < len(game.board)-1 and game.board[i-1][j+1] == " " or  j > 0 and game.board[i-1][j-1] == " "):
		
						movable.append((j,i))
		
						continue
						
					if "*" in game.board[i][j] and i < len(game.board)-1 and (j < len(game.board)-1 and game.board[i+1][j+1] == " " or j > 0 and game.board[i+1][j-1] == " "):
		
						movable.append((j,i))
		
						continue
					
					if game.test(j, i, game.board):
		
						movable.append((j,i))
		
		return movable
		
		
	def eval_risks(self, positions):
		
		hold = positions[:]

		goodMoves = []

		for x,y in positions:
				
			if y > 1 and x < len(game.board)-2 and game.board[y-1][x+1] == " " and ("1" in game.board[y-2][x+2] or "1" in game.board[y-2][x]):

				continue
			
			if y > 1 and x > 1 and game.board[y-1][x-1] == " " and ("1" in game.board[y-2][x-2] or "1" in game.board[y-2][x]):

				continue

			if "*" in game.board[y][x]:

				if y < len(game.board)-2 and x < len(game.board)-2 and game.board[y+1][x+1] == " " and ("1" in game.board[y+2][x+2] or "1" in game.board[y+2][x]):

					continue
				
				if y < len(game.board)-2 and x > 1 and game.board[y+1][x-1] == " " and ("1" in game.board[y+2][x-2] or "1" in game.board[y+2][x]):

					continue

			goodMoves.append((x, y))

		return hold if len(goodMoves)==0 else goodMoves
	

	def enemysQueenOnSlaught(self, positions):
		
		for x,y in positions:
			if "*" in game.board[y][x]:
				continue
				
			if y > 1 and x < len(game.board)-2 and game.board[y-1][x+1] == "1*" and game.board[y-2][x+2] == " ":
				return (x,y, x+2, y-2)
			if y > 1 and x > 1 and game.board[y-1][x-1] == "1*" and game.board[y-2][x-2] == " ":
				return (x,y, x-2, y-2)
		
		for x,y in positions:
			if "*" not in game.board[y][x]:
				continue
				
			if y < len(game.board)-2 and x < len(game.board)-2 and game.board[y+1][x+1] == "1*" and game.board[y+2][x+2] == " ":
				return (x,y, x+2, y+2)
			if y < len(game.board)-2 and x > 1 and game.board[y+1][x-1] == "1*" and game.board[y+2][x-2] == " ":
				return (x,y, x-2, y+2)
		
			if y > 1 and x < len(game.board)-2 and game.board[y-1][x+1] == "1*" and game.board[y-2][x+2] == " ":
				return (x,y, x+2, y-2)
			if y > 1 and x > 1 and game.board[y-1][x-1] == "1*" and game.board[y-2][x-2] == " ":
				return (x,y, x-2, y-2)
		
		return (-1,-1,-1,-1)
				
			


	def enemysRockMovement(self, positions):

		for x,y in positions:
		
			enemies = findEnemies(x,y,game.board)
			for enemyX,enemyY in enemies:
				enemyKill = test(enemyX,enemyY,game.board)
				if -1 not in enemyKill:
					protectors = findProtectors("2",x,y,enemyX,enemyY,game.board)
					if len(protectors) > 0:
						return (protectors[0][0], protectors[0][1], enemyKill[2], enemyKill[3])
					
					if not isOutOfBounds(x+1,y-1,game.board) and game.board[y-1][x+1] == " ":
						return (x,y,x+1,y-1)
					if not isOutOfBounds(x-1,y-1,game.board) and game.board[y-1][x-1] == " ":
						return (x,y,x-1,y-1)
		
		return (-1,-1,-1,-1)

	
	def queenMove(self, positions):
		for x,y in positions:
			if game.board[y][x] == "2*":
				enemies = findEnemies(x,y,game.board)
				for enemyX, enemyY in enemies:
					enemyKill = test(enemyX,enemyY,game.board)
					if -1 not in enemyKill:
						protectors = findProtectors("2",x,y,enemyX,enemyY,game.board)
						if len(protectors) > 0:
							return (protectors[0][0], protectors[0][1], enemyKill[2], enemyKill[3])
					
						if not isOutOfBounds(x+1,y-1,game.board) and game.board[y-1][x+1] == " ":
							return (x,y,x+1,y-1)
						if not isOutOfBounds(x-1,y-1,game.board) and game.board[y-1][x-1] == " ":
							return (x,y,x-1,y-1)
							
		return (-1,-1,-1,-1)
					
	

	def checkForQueen(self, positions):
		for x,y in positions:
			if y-1 == 0 and x < len(game.board)-1 and game.board[y-1][x+1] == " ":
				return (x, y, x+1, y-1)
				
			if y-1 == 0 and x > 0 and game.board[y-1][x-1] == " ":
				return (x, y, x-1, y-1)
		
		return (-1,-1,-1,-1)


	def rockMovement(self, positions):

		for x,y in positions:
			rockCheck = test(x,y,game.board)
			if -1 not in rockCheck:
				rockCheck2 = test(rockCheck[2], rockCheck[3], game.board)
				if -1 not in rockCheck2:
					return rockCheck
		
		return (-1,-1,-1,-1)


	def killNSafe(self, positions):

		for x,y in positions:
			killCheck = test(x,y,game.board)
			if -1 not in killCheck:
				_, _, kx, ky = killCheck
				if ky > 0 and kx > 0 and "1" in  game.board[ky-1][kx-1] and -1 not in test(kx-1, ky-1, game.board):
					continue
				if ky > 0 and kx < len(game.board)-1 and "1" in  game.board[ky-1][kx+1] and -1 not in test(kx+1, ky-1, game.board):
					continue
				if ky < len(game.board)-1 and kx > 0 and "1" in  game.board[ky+1][kx-1] and -1 not in test(kx-1, ky+1, game.board):
					continue
				if ky < len(game.board)-1 and kx < len(game.board)-1 and "1" in  game.board[ky+1][kx+1] and -1 not in test(kx+1, ky+1, game.board):
					continue
				
				return (x,y,kx,ky)
				
		return (-1,-1,-1,-1)


	def killNInThreat(self, positions):

		for x,y in positions:
			killCheck = test(x,y,game.board)
			if -1 not in killCheck:
				return killCheck
				
		return (-1,-1,-1,-1)


	def checkFlip(self,movePos):
		x,y,dx,dy = movePos
		if self.team == 1:
			game.board = flipBoard(game.board)
			movePos = (x,len(game.board)-1-y,dx,len(game.board)-1-dy)
		
		return movePos

	def play(self):
		

		"""
		Move selection by fixed priorities (the order actually applied is the order
		of the calls below):

		    - capture the enemy king if it is exposed                     | enemysQueenOnSlaught
		    - stop an enemy piece that is about to capture                 | enemysRockMovement
		    - move our own king out of danger                              | queenMove
		    - capture a piece even if it leaves us exposed                 | killNInThreat
		    - capture a piece and stay safe                                | killNSafe
		    - promote a man to king                                        | checkForQueen
		    - start a double capture                                       | rockMovement
		    - otherwise, a random legal move
		"""
		
		if self.team == 1:

			game.board = flipBoard(game.board)

		
		#positions = self.freePosition()
		
		
		positions = self.eval_risks(self.freePosition())

		slaught = self.enemysQueenOnSlaught(positions)
		
		if -1 not in slaught:
			return self.checkFlip(slaught)

		stopRock = self.enemysRockMovement(positions)

		if -1 not in stopRock:
			return self.checkFlip(stopRock)

		godSavesTheQueen = self.queenMove(positions)

		if -1 not in godSavesTheQueen:
			return self.checkFlip(godSavesTheQueen)
		
		risky = self.killNInThreat(positions)
		
		if -1 not in risky:
			return self.checkFlip(risky)
			
		conservative = self.killNSafe(positions)

		if -1 not in conservative:
			return self.checkFlip(conservative)
			
		becomeAQueen = self.checkForQueen(positions)
		
		if -1 not in becomeAQueen:
			return self.checkFlip(becomeAQueen)

		rocking = self.rockMovement(positions)

		if -1 not in rocking:
			return self.checkFlip(rocking)
		
		
		# no priority applies: pick a random movable piece and a random destination.
		
		position = positions[random.randint(0,len(positions)-1)]
		x,y = position
		
		poses = []
		if "*" in game.board[y][x]:
			if y < len(game.board)-1 and x < len(game.board)-1 and game.board[position[1]+1][position[0]+1] == " ":
				poses.append((position[0]+1, position[1]+1))
			if y < len(game.board)-1 and x > 0 and game.board[position[1]+1][position[0]-1] == " ":
				poses.append((position[0]-1, position[1]+1))
		
		
		if y > 0 and x < len(game.board)-1 and game.board[position[1]-1][position[0]+1] == " ":
			poses.append((position[0]+1, position[1]-1))
		if y > 0 and x > 0 and game.board[position[1]-1][position[0]-1] == " ":
			poses.append((position[0]-1, position[1]-1))
		
		pose = poses[random.randint(0,len(poses)-1)]
		movePos = (position[0],position[1],pose[0],pose[1])
		if self.team == 1:
			game.board = flipBoard(game.board)
			movePos = (position[0],len(game.board)-1-position[1],pose[0],len(game.board)-1-pose[1])
			
		return movePos
				
		
game.run(True, CheckersAI(1))
