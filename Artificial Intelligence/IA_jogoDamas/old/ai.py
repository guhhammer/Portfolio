import dama as main

class CheckersAI(main.AbstractAI):
	

	def __init__(self):
	
		pass
		

	def freePosition(self):
		
		movable = []
		
		for i in range(len(main.board)):
		
			for j in range(len(main.board)):
		
				if "2" in main.board[i][j]:
		
					if i > 0 and (j < len(main.board)-1 and main.board[i-1][j+1] == " " or  j > 0 and main.board[i-1][j-1] == " "):
		
						movable.append((j,i))
		
						continue
						
					if "*" in main.board[i][j] and i < len(main.board)-1 and (j < len(main.board)-1 and main.board[i+1][j+1] == " " or j > 0 and main.board[i+1][j-1] == " "):
		
						movable.append((j,i))
		
						continue
					
					if main.test(j, i, main.board):
		
						movable.append((j,i))
		
		return movable
		

	def enemysQueenOnSlaught(self, positions):
		
		pass


	def enemysRockMovement(self, positions):

		pass

	
	def queenMove(self, positions):
		'''
		for x,y in positions:
			if main.board[y][x] == "2*":
				try:
					a = main.board[y-1][x-1]
					b = main.board[y-1][x+1]
					c = main.board[y+1][x-1]
					d = main.board[y+1][x+1]
				except IndexError:
					try:
						if x > 0 and x < len(main.board)-1:
							continue
							
						a = main.board[y-1][x-1]
						b = main.board[y-1][x+1]
		'''	
	

	def checkForQueen(self, positions):
		for x,y in positions:
			if y-1 == 0 and main.board[y-1][x+1] == " ":
				return (x, y, y-1, x+1)
				
			if y-1 == 0 and main.board[y-1][x-1] == " ":
				return (x, y, y-1, x-1)
		
		return (-1,-1,-1,-1)


	def rockMovement(self, positions):

		pass


	def killNSafe(self, positions):

		pass


	def killNInThreat(self, positions):

		pass


	def play(self):
		

		"""
		    - ver se rainha do inimigo está ameaçada e matá-la.				| def enemysQueenOnSlaught
		    - ver se inimigo consegue dar pedra e impossibilitá-lo.			| def enemysRockMovement
			- tirar dama de ameaça.											! def queenMove

		    - prezar por tornar dama.										| def checkForQueen
		    - matar 2 ou mais.												| def rockMovement
		    - matar uma e estar 'safe'.										! def killNSafe
		    - matar uma e ficar em ameaça.									| def killNInThreat

		    (Em ordem de prevalência.)

		"""

		positions = self.freePosition()
		

		slaught = self.enemysQueenOnSlaught(positions)

		if :
			pass


		stopRock = self.enemysRockMovement(positions)

		if :
			pass

		godSavesTheQueen = self.queenMove(positions)

		if :
			pass
		
		becomeAQueen = self.checkForQueen(positions)
		
		if -1 not in becomeAQueen:
			return becomeAQueen

		rocking = self.rockMovement(positions)

		if :
			pass

		conservative = self.killNSafe(positions)

		if :
			pass
		
		risky = self.killNInThreat(positions)
		
		if :
			pass
		
		# select random pos.
		
		

main.run(True, CheckersAI())