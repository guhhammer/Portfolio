
import numpy as np
import matplotlib.pyplot as plt

"""
	Reminder:

			Big-O:  f(n) < c.g(n)

			Omega:  c.g(n) < f(n)

			Theta:  c1.g(n) < f(n) < c2.g(n)

"""

##########################################################
##########################################################

ax = [i for i in range(0,100)] # x values.


render = lambda x, y1, y2, name, label1, label2: [ plt.plot(x, y1, color='green', label=label1),
				 								   plt.plot(x, y2, color='red', label=label2), 
				 								   plt.legend(loc='best'),
				 								   plt.savefig(name), plt.show()
				 								   ]


render_theta = lambda x, y1, y2, y3, name, label1, label2, label3: [ plt.plot(x, y1, color='green', label=label1),
								 								     plt.plot(x, y2, color='red', label=label2),
								 								     plt.plot(x, y3, color='orange', label=label3), 
								 								     plt.legend(loc='best'),
								 								     plt.savefig(name), plt.show()
								 								   ]

##########################################################
##########################################################


"""

	A) (2/5) * n^5 - 3 * n^4 = Theta( n^5 )

		=	((2/5) * n^5 - 3 * n^4) / ( n^5 ) = M * ( n^5 ) / ( n^5 )
		= 	(2/5) - 3 / n = M 

		=   |- c1.g(X) < f(X) -> c1 < -2.6
		
		=	|- c2.h(X) > f(X) -> c2 > -2.6

		Note: taking only positive values, as X tends to infinity, c1 < 0.4 and c2 > 0.4.

"""

exA_f = lambda x : 2/5 * (x ** 5) - 3 * (x ** 4)

exA_g = lambda c1, x : c1 * (x ** 5)

exA_h = lambda c2, x : c2 * (x ** 5)



exA_fn, exA_gn, exA_hn = [exA_f(i) for i in ax], [exA_g(0.1, i) for i in ax], [exA_h(3, i) for i in ax]


render_theta(ax, exA_fn, exA_gn, exA_hn, "Ex-A.png", "f(n)", "c1 * g(n)", "c2 * h(n)")


##########################################################
##########################################################


"""

	B) (2/5) * n^5 - 3 * n^4 = Omega( n^5 )

		=	((2/5) * n^5 - 3 * n^4) / ( n^5 ) = c1 * ( n^5 ) / ( n^5 )
		= 	(2/5) - 3 / n = c1 

		=   |- c1.g(X) < f(X) -> c1 < -2.6
	

		Note: taking only positive values, as X tends to infinity, c1 < 0.4 and c2 > 0.4.

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
	

		Note: taking only positive values, as X tends to infinity, c1 < 0.4 and c2 > 0.4.

"""


exC_f = lambda x : 2/5 * (x ** 5) - 3 * (x ** 4)

exC_g = lambda c2, x : c2 * (x ** 5)

exC_fn, exC_gn = [exC_f(i) for i in ax], [exC_g(3, i) for i in ax]


render(ax, exC_fn, exC_gn, "Ex-C.png", "f(n)", "c * g(n)")

##########################################################
##########################################################


# Every equality checks out.




"""

	D) Given the polynomial p(n) = A_g * n^g + A_(g-1) * n ^ (g-1) + ... + A0
	
	
		Prove:

			i.   if k >= g, then p(n) = O(n^k).

			ii.  if k <= g, then p(n) = Omega(n^k)
			
			iii. if k = g, then p(n) = Theta(n^k)

		
		'''
				
				k >= g 		->		 k = g

		i.		p(n) = A_k * n^k + A_(k-1) * n ^ (k-1) + ... + A0 == O (n^K)		
				
				p(n) = A_k * n^k / (n^K) + A_(k-1) * n ^ (k-1)/ (n^K) + ... + A0/(n^K) == C1 * (n^K) / (n^K)
							
					 = A_k * 1 + ... + A0 / n^k < C1
					 
					 =	|- c1 does not depend on n, so an upper bound (O) holds as long as c1 > A_k.
				

				|- p(n) = O(n^k) |- Tautology.

		'''

		'''
				
				k =< g 		->		 k = g

		ii.		p(n) = A_k * n^k + A_(k-1) * n ^ (k-1) + ... + A0 == Omega(n^K)		
				
				p(n) = A_k * n^k / (n^K) + A_(k-1) * n ^ (k-1)/ (n^K) + ... + A0/(n^K) == C2 * (n^K) / (n^K)
							
					 = A_k * 1 + ... + A0 / n^k > C2
					 
					 =	|- c2 does not depend on n, so a lower bound (Omega) holds as long as c2 < A_k.
				

				|- p(n) = Omega(n^k) |- Tautology.

		'''

		'''
				
				k = g 		->		 k1 < g  && k2 > g

		iii.	p(n) = A_k * n^k + A_(k-1) * n ^ (k-1) + ... + A0 == Theta(n^K)		
				
				|- k does not depend on n; by items i and ii, as long as k1 < A_k and k2 > A_k
				
				|- p(n) = Theta(n^k) |- Tautology.

		'''
	
	

"""

