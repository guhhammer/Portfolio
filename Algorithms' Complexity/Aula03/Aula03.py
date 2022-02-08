
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

Ex.1 =>   4n³ + 3n² + 2n + 1 = O(n³)

		 = (4n³ + 3n² + 2n + 1 ) / (n³)= c * (n³) / (n³)
		 = 4 + 3/n + 2/n² + 1/n³ = c
 
 		c > 0

"""

ex1_f = lambda x: 4*(x**3) + 3*(x**2) + 2*x + 1
ex1_g = lambda c, x: c * (x**3)

ex1_fn, ex2_gn = [ex1_f(i) for i in ax], [ex1_g(5,i) for i in ax]

render(ax, ex1_fn, ex2_gn, "Ex-1.png", "f(n)", "c * g(n)")

# 4n³ + 3n² + 2n + 1 = O(n³) |- Válido.

##########################################################
##########################################################

"""

Ex.2 => 	12n² = O(n)
			
			= 12 n² / n = c * n / n
			= 12 n = c

			... Absurdo: n precisa ser, no mínimo, k * n, onde k > 12 

"""


ex2_f = lambda x: 12*(x**2)
ex2_g = lambda c, x: c * x

ex2_fn, ex2_gn = [ex2_f(i) for i in ax], [ex2_g(5,i) for i in ax]

render(ax, ex2_fn, ex2_gn, "Ex-2.png", "f(n)", "c * g(n)")

# 12n² = O(n) |- Absurdo.

##########################################################
##########################################################

"""

Ex.3 => 	4 ** (n+3) = O( 4 ** n)
			= 4 ** (n+3) /(4 ** n)  =  c*(4 ** n)/(4 ** n)
			= 4 ** 3 = c
			
			c >= 64

"""

ex3_f = lambda x: 4 ** (x+3)
ex3_g = lambda c, x: c * (4 ** x)

ex3_fn, ex3_gn = [ex3_f(i) for i in ax], [ex3_g(128,i) for i in ax]

render(ax, ex3_fn, ex3_gn, "Ex-3.png", "f(n)", "c * g(n)")

# 4 ** (n+3) = O( 4 ** n) |- Válido.
# Obs.: Coloquei 128 como c para aparecer a diferença no gráfico. C acima de 64 já é válido.

##########################################################
##########################################################

"""

Ex.4 => 	3 ** (2n) = O(3 ** n)
			= 3 ** (2n) / (3 ** n) = c * (3 ** n) / (3 ** n)
			= 3 ** n = c

			
			c >= 3 ** n

"""

ex4_f = lambda x: 3 ** (2 * x)
ex4_g = lambda c, x: c * (3 ** x)

ex4_fn, ex4_gn = [ex4_f(i) for i in ax], [ex4_g(3 ** i, i) for i in ax]

render(ax, ex4_fn, ex4_gn, "Ex-4.png", "f(n)", "c * g(n)")

# 4 ** (n+3) = O( 4 ** n) |- Absurdo. N persiste na simplificação.

##########################################################
##########################################################

"""

Ex.5 =>	 	12n³ + 9n = Ômega(n)
		 	= (12n³ + 9n) / n = c *(n)/n
		 	= 12n² + 9 = c

		 	c =< 12n² + 9   |- n = 1 -> 21

"""

ex5_f = lambda x : 12 * (x ** 3) + 9 * x
ex5_g = lambda c, x : c * x

ex5_fn, ex5_gn = [ex5_f(i) for i in ax], [ex5_g(1, i) for i in ax]

render(ax, ex5_fn, ex5_gn, "Ex-5.png", "f(n)", "c * g(n)")

# 12n³ + 9n = Ômega(n) |- Válido.

##########################################################
##########################################################

"""

Ex.6 => 	6n² + 8n = Θ(n²) 
	 		= (6n² + 8n)/(n²) = M * Θ(n²)/(n²) 	

	 		= 6 + 8/n = M

	 		M > c1 > 0  |- 6 > c1
	 		c2 > M      |- c2 > 6

"""

ex6_f = lambda x : 6 * (x ** 2) + 8 * x
ex6_g = lambda c1, x : c1 * (x ** 2)
ex6_h = lambda c2, x : c2 * (x ** 2)

ex6_fn, ex6_gn, ex6_hn = [ex5_f(i) for i in ax], [ex5_g(1, i) for i in ax], [ex6_h(14, i) for i in ax]

render_theta(ax, ex6_fn, ex6_gn, ex6_hn, "Ex-6.png", "f(n)", "c1 * g(n)", "c2 * g(n)")

# 6n² + 8n = Θ(n²) |- Absurdo. C2 não supera f(n).

##########################################################
##########################################################

"""

Ex.7 =>		12n³ + 10n² ≠ Θ(n²)
			= (12n³ + 10n²) / (n²) ≠ M * (n²)/(n²)  
			= 12n + 10 = M

			M > c1 > 0 		|- M=1 -> 22 > c1 
			c2 > M

"""

ex7_f = lambda x : 12 * (x ** 3) + 10 * (x ** 2)
ex7_g = lambda c1, x : c1 * (x ** 2)
ex7_h = lambda c2, x : c2 * (x ** 2)

ex7_fn, ex7_gn, ex7_hn = [ex7_f(i) for i in ax], [ex7_g(1, i) for i in ax], [ex7_h(26, i) for i in ax]

render_theta(ax, ex7_fn, ex7_gn, ex7_hn, "Ex-7.png", "f(n)", "c1 * g(n)", "c2 * g(n)")

# 12n³ + 10n² ≠ Θ(n²) |- Válido. C2 varia em n.

##########################################################
##########################################################

"""

Ex.8 =>		4n² + 16n + 2 = Θ(n²)
			=	(4n² + 16n + 2)/(n²) = M*(n²)/(n²)
			= 4 + 16/n + 2/n² = M
			
			|- M=1 -> (4 + 16/n + 2/n²) = 22

			M > c1 > 0  
			c2 > 22

"""

ex8_f = lambda x : 4 * (x ** 2) + 16 * x + 2
ex8_g = lambda c1, x : c1 * (x ** 2)
ex8_h = lambda c2, x : c2 * (x ** 2)

ex8_fn, ex8_gn, ex8_hn = [ex8_f(i) for i in ax], [ex8_g(2, i) for i in ax], [ex8_h(25, i) for i in ax]

render_theta(ax, ex8_fn, ex8_gn, ex8_hn, "Ex-8.png", "f(n)", "c1 * g(n)", "c2 * g(n)")

# 	4n² + 16n + 2 = Θ(n²) |- Válido.

##########################################################
##########################################################

"""

Ex.9 =>		4n² + 16n + 2 = Θ(n³) 	   
			= (4n² + 16n + 2)/(n³) = M*(n³)/(n³)
			= 4/n + 16/n² + 2/n³ = M
			

			|- M=1 -> 22

			M > c1 > 0

			C2 > M

"""

ex9_f = lambda x : 4 * (x ** 2) + 16 * x + 2
ex9_g = lambda c, x : c * (x ** 3)
ex9_h = lambda c, x : c * (x ** 3)

ex9_fn, ex9_gn, ex9_hn = [ex9_f(i) for i in ax], [ex9_g(2, i) for i in ax], [ex9_h(5, i) for i in ax]

render_theta(ax, ex9_fn, ex9_gn, ex9_hn, "Ex-9.png", "f(n)", "c1 * g(n)", "c2 * g(n)")

# 4n² + 16n + 2 = Θ(n³) |- Absurdo. Ômega varia em x, c1*g(n) > f(n).

##########################################################
##########################################################

"""

Ex.10 =>	6n³ ≠ Θ(n²)
			= 6n³ / (n²) ≠ M * (n²)/(n²)
			=  6n ≠ M
			
			|- M = 1 -> c1 < 6

			M > c1 > 0
			c2 > M

"""

ex10_f = lambda x : 6 * (x ** 3)
ex10_g = lambda c, x : c * (x ** 2)
ex10_h = lambda c, x : c * (x ** 2)

ex10_fn, ex10_gn, ex10_hn = [ex10_f(i) for i in ax], [ex10_g(2, i) for i in ax], [ex10_h(10, i) for i in ax]

render_theta(ax, ex10_fn, ex10_gn, ex10_hn, "Ex-10.png", "f(n)", "c1 * g(n)", "c2 * g(n)")

#  6n³ ≠ Θ(n²) |- Válido. C2 varia em n.

##########################################################
##########################################################