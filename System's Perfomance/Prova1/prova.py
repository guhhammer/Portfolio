

import scipy.stats as stats

#1#print( 1 - (stats.norm.cdf(15, 13, 3) - stats.norm.cdf(0, 13, 3)) )

print(1- stats.geom.cdf(4, 0.75) )

#3#print(stats.poisson.cdf(5, 10))

#4#print(1 - stats.expon.cdf(6, 0, 5))
