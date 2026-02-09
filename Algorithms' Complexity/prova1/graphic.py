
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

exA_f = lambda x : 2/7 * (x ** 7) - 5 * (x ** 6)

exA_g = lambda c1, x : c1 * (x ** 7)

exA_h = lambda c2, x : c2 * (x ** 7)



exA_fn, exA_gn, exA_hn = [exA_f(i) for i in ax], [exA_g(0.1, i) for i in ax], [exA_h(2, i) for i in ax]


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
