"""SciPy calculations for individual assessment 1 (see individual-assessment-1.pdf for the questions).

1) normal distribution: probability that the bank manager arrives late (commute of 13 +/- 3 min, 15 min available)
2) geometric distribution: more than 4 attempts until the first success with p = 0.75
3) Poisson distribution: at most 5 events when the mean is 10
4) exponential distribution: more than 6 time units when the mean is 5
"""
import scipy.stats as stats

#1
print(1 - (stats.norm.cdf(15, 13, 3) - stats.norm.cdf(0, 13, 3)))

#2
print(1 - stats.geom.cdf(4, 0.75))

#3
print(stats.poisson.cdf(5, 10))

#4
print(1 - stats.expon.cdf(6, 0, 5))
