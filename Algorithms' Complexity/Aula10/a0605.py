
def A9(A):

	n = len(A)							# C1 <- n
	i = 0								# C2 <- 1
	f = n								# C3 <- 1
	m = (i + f)/4						# C4 <- 1

	if n <= 0:							# C5 <- n

		return m						# C6 <- n

	else:

		t = 0							# C7 <- 1
		Z = []							# C8 <- 1

		for x in A:						# C9 <- n

			Z.append(x)					# C10 <- n

		for x in A:						# C11 <- n

			A.append(x * 2)				# C12 <- n

		u = 0							# C13 <- 1

		for y in Z:						# C14 <- n

			u = u + y					# C15 <- n

		print(u)						# C16 <- 1

		print(len(A), len(Z))			# C17 <- 1

		A9(A[0:m])						# C18 <- n/4
		A9(A[m:2*m])					# C19 <- n/4
		A9(A[2*m:3*m])					# C20 <- n/4
		A9(A[3*m:n])					# C21 <- n/4


# arr -> n / 4 | n <- n * 2
"""
		
		A <= 16

		A <= 2 * A <= 32
					
			4 x 8

		A9(A[0:m])			<-	n=8
		A9(A[m:2*m])		<-	n=8
		A9(A[2*m:3*m])		<-	n=8
		A9(A[3*m:n])		<-	n=8

		--------------------------

			16 x 4

		A9(A[0:m])			<- (A==8); (A <= 2*A ==16) ::: 4 x 4
		A9(A[m:2*m])		<-	n=8
		A9(A[2*m:3*m])		<-	n=8
		A9(A[3*m:n])		<-	n=8
		
		--------------------------

			32 x 2
		
		--------------------------

			64 x 1		

		--------------------------

			End

		--------------------------
		--------------------------
	
 	chamada 1 -> A=16 :: 4 chamadas -> A=8
	chamada 2 -> A=32 :: 32 chamadas -> A=4
	chamada 3 -> A=64 :: 128 chamadas -> A=2
		
	
  *						16
	*	*	*	*		x8
	4*	4* 	4* 	4*		x4
	16*	16*	16*	16*		x2
		
		len A = 16	+	32	+	64 + 128 = 240
		
		T(n) = 2 * T(n/4) + 3n

"""


arr = [0,1,2,3, 4,5,6,7, 8,9,10,11, 12,13,14,15]

#A9(arr) ### T(n) = 2 * T(n/4) + 3n





def A11(A):

	n = len(A)							# C1 <- n
	i = 0								# C2 <- 1
	f = n								# C3 <- 1
	m = (i + f)/5						# C4 <- 1
	
	if (n <= 0):						# C5 <- n

		return m						# C6 <- n

	else:		

		B = copia(A)					# C7 <- n
		C = copia(A)					# C8 <- n
		BC = concatena(B, C)			# C9 <- 2*n
		x = 0							# C10 <- 1

		for b in B:						# C11 <- n
			for c in C:					# C12 <- n*n

				x = x + c 				# C13 <- n*n
				y = 0					# C14 <- n*n

				for d in BC:			# C15 <- 2*n * n*n

					y = y + x * d 		# C16 <= 2*n * n*n

		print(x)
		A11(A[0:m])						# C17 <- n/5
		A11(A[m:2*m])					# C18 <- n/5
		A11(A[2*m:3*m])					# C19 <- n/5
		A11(A[3*m:n])					# C20 <- 2 * n/5


# arr -> n / 5 | n <- n * 2
"""
		
		
	*
		* 		*		*		*		**
		4* **	4* **	4* **	4* **	4*	**
 		
		
		T(n) = 4 * T(n/5) + 2n³

"""










