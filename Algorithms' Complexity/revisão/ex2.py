
import numpy as np
import matplotlib.pyplot as plt

"""
	Lembrete:
		

			O: f(n) < c. g(n)

			Ômega : c.g(n) < f(n)

			Theta : c1.g(n) < f(n) < c2.g(n)

"""

##########################################################
##########################################################

ax = [i for i in range(0,100)] # Valores de x.


render = lambda x, y1, y2, nome, label1, label2: [ plt.plot(x, y1, color='green', label=label1),
				 								   plt.plot(x, y2, color='red', label=label2), 
				 								   plt.legend(loc='best'),
				 								   plt.savefig(nome), plt.show()
				 								   ]


render_theta = lambda x, y1, y2, y3, nome, label1, label2, label3: [ plt.plot(x, y1, color='green', label=label1),
								 								     plt.plot(x, y2, color='red', label=label2),
								 								     plt.plot(x, y3, color='orange', label=label3), 
								 								     plt.legend(loc='best'),
								 								     plt.savefig(nome), plt.show()
								 								   ]

##########################################################
##########################################################


"""

	A) (2/5) * n^5 - 3 * n^4 = Theta( n^5 )

		=	((2/5) * n^5 - 3 * n^4) / ( n^5 ) = M * ( n^5 ) / ( n^5 )
		= 	(2/5) - 3 / n = M 

		=   |- c1.g(X) < f(X) -> c1 < -2.6
		
		=	|- c2.h(X) > f(X) -> c2 > -2.6

		Obs.: adotando-se apenas valores positivos, quando X tende ao oo, então c1 < 0.4 e c2 > 0.4.

"""

exA_f = lambda x : 2/5 * (x ** 5) - 3 * (x ** 4)

exA_g = lambda c1, x : c1 * (x ** 5)

exA_h = lambda c2, x : c2 * (x ** 5)



exA_fn, exA_gn, exA_hn = [exA_f(i) for i in ax], [exA_g(0.1, i) for i in ax], [exA_h(3, i) for i in ax]


render_theta(ax, exA_fn, exA_gn, exA_hn, "Ex-A.png", "f(n)", "c1 * g(n)", "c2 * h(n)")


##########################################################
##########################################################


"""

	B) (2/5) * n^5 - 3 * n^4 = ômega( n^5 )

		=	((2/5) * n^5 - 3 * n^4) / ( n^5 ) = c1 * ( n^5 ) / ( n^5 )
		= 	(2/5) - 3 / n = c1 

		=   |- c1.g(X) < f(X) -> c1 < -2.6
	

		Obs.: adotando-se apenas valores positivos, quando X tende ao oo, então c1 < 0.4 e c2 > 0.4.

"""


exB_f = lambda x : 2/5 * (x ** 5) - 3 * (x ** 4)

exB_g = lambda c1, x : c1 * (x ** 5)



exB_fn, exB_gn = [exB_f(i) for i in ax], [exB_g(0.1, i) for i in ax]


render(ax, exB_fn, exB_gn, "Ex-B.png", "f(n)", "c * g(n)")

##########################################################
##########################################################



"""

	C) (2/5) * n^5 - 3 * n^4 = O( n^5 )

		=	((2/5) * n^5 - 3 * n^4) / ( n^5 ) = c2 * ( n^5 ) / ( n^5 )
		= 	(2/5) - 3 / n = c2 

		=   |- c2.g(X) > f(X) -> c2 < -2.6
	

		Obs.: adotando-se apenas valores positivos, quando X tende ao oo, então c1 < 0.4 e c2 > 0.4.

"""


exC_f = lambda x : 2/5 * (x ** 5) - 3 * (x ** 4)

exC_g = lambda c2, x : c2 * (x ** 5)

exC_fn, exC_gn = [exC_f(i) for i in ax], [exC_g(3, i) for i in ax]


render(ax, exC_fn, exC_gn, "Ex-C.png", "f(n)", "c * g(n)")

##########################################################
##########################################################


# Todas as igualdades conferem.




"""

	D) Dado o polinômio p(n) = A_g * n^g + A_(g-1) * n ^ (g-1) + ... + A0
	
	
		Prove:

			i.   se k >= g, então p(n) = O(n^k).

			ii.  se k =< g, então p(n) = Ômega (n^k)
			
			iii. se k = g, então p(n) = Theta (n^k)

		
		'''
				
				k >= g 		->		 k = g

		i.		p(n) = A_k * n^k + A_(k-1) * n ^ (k-1) + ... + A0 == O (n^K)		
				
				p(n) = A_k * n^k / (n^K) + A_(k-1) * n ^ (k-1)/ (n^K) + ... + A0/(n^K) == C1 * (n^K) / (n^K)
							
					 = A_k * 1 + ... + A0 / n^k < C1
					 
					 =	|- c1 -> não varia em n, então é passível de Pior Caso (O) contanto que c1 > A_k.
				

				|- p(n) = O(n^k) |- Tautologia.

		'''

		'''
				
				k =< g 		->		 k = g

		ii.		p(n) = A_k * n^k + A_(k-1) * n ^ (k-1) + ... + A0 == Ômega(n^K)		
				
				p(n) = A_k * n^k / (n^K) + A_(k-1) * n ^ (k-1)/ (n^K) + ... + A0/(n^K) == C2 * (n^K) / (n^K)
							
					 = A_k * 1 + ... + A0 / n^k > C2
					 
					 =	|- c2 -> não varia em n, então é passível de Melhor Caso (Ômega) contanto que c2 < A_k.
				

				|- p(n) = Ômega(n^k) |- Tautologia.

		'''

		'''
				
				k = g 		->		 k1 < g  && k2 > g

		iii.	p(n) = A_k * n^k + A_(k-1) * n ^ (k-1) + ... + A0 == Theta(n^K)		
				
				|- k -> não varia em n, c.q.d. item i. e ii., contanto que k1 < A_k. e k2 > A_k
				
				|- p(n) = Theta(n^k) |- Tautologia.

		'''
	
	

"""

