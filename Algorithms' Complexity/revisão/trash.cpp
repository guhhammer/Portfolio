

// g() -> 3 -> O(1)
// SWAP FUNCTION - PARAMETERS
void g(int * x, int * y) {

	int q = *x;				//	C1 <- 1
	*x = *y;				//	C2 <- 1
	*y = q;					//	C3 <- 1

}	// Instruções de atribuição, optimizadas em hardware, custo constante.




// P1() -> 3 + 4n + 2n² -> O(n²)
//------------------------------
void P1(int V[ ], int n) { 
	
	int k, z, aux; 									// C1 <- 1
	
	do{ 											// C2 <- (C10 := n)  
		
		z = 0;										// C3 <- 1
		k = 0;										// C4 <- 1
		
		while (k < n-1) {							// C5 <- n
		
			if (V[ k ] > V[ k + 1 ]) { 				// C6 <- n
		
				// g() == O(1)
				g(&V[ k ], &V[ k + 1 ]);			// C7 <- 1 * n * n (1 loop <- n-1 comparações ~ bubblesort).
		
				++z; 								// C8 <- n * n
		
			}
		
			++k;									// C9 <- n
		
		}

	}	while (z != 0); 							// C10 <- C2 : condição de parada.

}



// P2() -> 5 + 7 * log2(n) -> O( log2(n) )
//----------------------------------------
int P2(int ch, int V[ ],  int n) { 

	int a = 0;										// C1 <- 1							
	int b = n – 1;									// C2 <- 1
	int x;											// C3 <- 1
	int k = -1; 									// C4 <- 1
	
	while ( (a <= b) && (k == -1) ) {				// C5 <- log2(n)

		x = (a + b) / 2; 							// C6 <- 1 * C5
		
		if (ch == V[x]) 							// C7 <- 1 * C5
		
			k = x; 									// C8 <- 1 * C5
		
		else {										//	-----------
			
			if (ch < V[x]) 							// C9 <- 1 * C5
		
				b = x - 1; 							// C10 <- 1 * C5
		
			else									// ------------
		
				a = x + 1; 							// C11 <- 1 * C5
		}
	} 

	return (k); 									// C12 <- 1

}
