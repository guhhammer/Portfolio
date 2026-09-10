############################################
############################################

## NAME: Gustavo Hammerschmidt.

############################################
############################################

## Check whether:
## 
## T(0) = 1, if n <= 0
## 
## T(n) = 3T(n/3) + n²/2, if n >= 1

def A5(A):

	i = 0									# C1 <- 1
	f = len(A)								# C2 <- 1

	if f <= 0: 								# C3 <- 1

		return -1							# C4 <- 1

	else:

		for j in range(f):					# C5 <- n

			print(j)						# C6 <- n

			a = 0							# C7 <- n

			for k in range(f/2):			# C8 <- n * n/2

				a = a + j * k				# C9 <- n * n/2

			print(a)						# C10 <- n

		A5(A[0:(f/3)])						# C11 <- n/3
		A5(A[(f/3):((2*f)/3)])				# C12 <- n/3
		A5(A[((2*f)/3):f])					# C13 <- n/3

## Premise 1:
"""

	When A == 0 -> T(0) == 1

	def A5(A):				# 1 call.

		i = 0				# C1 <- 1
		f = len(A)			# C2 <- 1

		if f <= 0: 			# C3 <- 1

			return -1		# C4 <- 1
		
		...
		
		C1 + .. + C4 = 4 -> O(1)
		

	Therefore,

		T(0) = 1 

		~ the cost is constant and equal to 1.

"""

## Premise 2:
"""
	
	Se T(n) = 3T(n/3) + n²/2, if n >= 1
		
		a: C1 + .. + C10 = 4 + 4n + 2 * (n²/2) ~ O (n²)

		b: C11 + .. + C13 = 3 * T(n/3)
		
	Therefore,

		T(n) = a + b
			 = 3T(n/3) + n²
 
 		T(n) = 3T(n/3) + n²

 		Extended version: T(n) = 3T(n/3) + n² + 4n + 4

"""

"""
	
	Premise 1: holds
	Premise 2: does not hold

"""

############################################
############################################


## Check whether:
## 
## T(0) = 1, if n <= 0
## 
## T(n) = 4T(n/4) + n ^ 4 + 4n, if n >= 1

def A11(A):

	n, i, f, m = len(A), 0, n, (i+f)/5  		# C1 <- 4

	if n <= 0:									# C2 <- 1

		return m								# C3 <- 1
	
	else:

		B = copy(A)							# C4 <- n
		C = copy(A)							# C5 <- n

		BC = concatenate(B, C)					# C6 <- 2n
		x = 0									# C7 <- 1

		for b in B:								# C8 <- n
			for c in C:							# C9 <- n

				x = x + c 						# C10 <- n * n
				y = 0							# C11 <- n * n

				for d in BC:					# C10 <- n * n * 2n 
					y = y + x * d 				# C11 <- n * n * 2n

		print(x)								# C12 <- 1

		A11(A[0:m])								# C13 <- n/5
		A11(A[m:2*m])							# C14 <- n/5
		A11(A[2*m:3*m])							# C15 <- n/5
		A11(A[3*m:n])							# C16 <- 2n/5


## Premise 1:
"""
	When A == 0, T(0) == 1

	def A11(A):													

		n, i, f, m = len(A), 0, n, (i+f)/5  		# C1 <- 4

		if n <= 0:									# C2 <- 1

			return m								# C3 <- 1
		
	4 -> O(1)

	T(0) = 1

"""


## Premise 2:
"""

	Se T(n) = 4T(n/4) + n ^ 4 + 4n, if n >= 1
	
		a: C1 + .. + C12 = 8 + 6n + 2n² + 2n³ -> O(2n³)
		b: C13 + .. + C16 = 3n/5 + 2n/5
	
	Therefore,

		T(n) = a + b

		T(n) = 3T(n/5) + T(2n/5) + 2n³
		
 		Extended version: T(n) = 3T(n/3) + 2n³ + 2n² + 6n + 8


"""


"""
		
	Premise 1 -> holds
	Premise 2 -> does not hold

"""

############################################
############################################
