import copy

class Board:
	
	def __init__(self, board):
		self.board = board
		
	def get_blanks_map(self):
		blanks = []
		row_i = 0
		for row in self.board:
			col_i = 0
			for value in row:
				if value == " ":
					blanks.append((row_i,col_i))
				col_i += 1
			
			row_i += 1
					
		return blanks
		
	def check(self):
		for row in self.board:
			if row[0] != " " and row[0] == row[1] and row[1] == row[2]:
				return ["O","X"].index(row[0])*2-1
	
		for i in range(3):
			if self.board[0][i] != " " and self.board[0][i] == self.board[1][i] and self.board[1][i] == self.board[2][i]:
				return ["O","X"].index(self.board[0][i])*2-1
	
		if (self.board[0][0] != " " and self.board[0][0] == self.board[1][1] and self.board[1][1] == self.board[2][2]) or (self.board[0][2] != " " and self.board[0][2] == self.board[1][1] and self.board[1][1] == self.board[2][0]):
			return ["O","X"].index(self.board[1][1])*2-1
	
		return 0
		
	def full(self):
		for row in self.board:
			for value in row:
				if value == " ":
					return False
					
		return True

class Node:
	
	def __init__(self, turn, board, substates=[]):
		self.turn = turn
		self.board = board
		self.substates = substates
	
	def create_substates(self):
		if self.board.check() != 0 or self.board.full():
			return
		
		for row, col in self.board.get_blanks_map():
			board = copy.deepcopy(self.board.board)
			board[row][col] = ["X","O"][self.turn-1]
			node = Node(1-self.turn, Board(board),[])
			node.create_substates()
			self.substates.append(node)
		
	def sum_states(self):
		result = self.board.check()
		if result != 0:
			print(self.board.board, result)
			return result
		
		total = 0
		for substate in self.substates:
			total += substate.sum_states()
		
		return total
		
	def find_min(self):
		min = 0
		minState = None
		for substate in self.substates:
			stateSum = substate.sum_states()
			if stateSum < min:
				min = stateSum
				minState = substate
		
		return minState
		
	def find_max(self):
		max = 0
		maxState = None
		for substate in self.substates:
			stateSum = substate.sum_states()
			if stateSum > max:
				max = stateSum
				maxState = substate
		
		return maxState
		
	def print(self):
		print(self.board.board)
		
		print(len(self.substates))
		
			

class Tree:
	
	def __init__(self, turn, board):
		self.root = Node(turn, Board(board), [])
		
	def evaluate(self):
		self.root.create_substates()
		
	def find_min(self):
		return self.root.find_min()
		
	def find_max(self):
		return self.root.find_max()
		
	def print(self):
		self.root.print()
		
		
board1 = [	["X","O"," "],
			["X","X","O"],
			["O"," "," "]]
			
tree = Tree(1,board1)
tree.evaluate()
print(tree.find_max().board.board)

## WRONG
