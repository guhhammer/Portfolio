


import scipy.stats as st

val = (st.poisson.pmf(3, 2.5/4 * 2)*
	   st.poisson.pmf(0, 0.5 * 2)*
	   st.poisson.pmf(0, 1.5 * 2))

print(val)


print( 1 - st.gamma.cdf(6, a=3, scale=4) )


