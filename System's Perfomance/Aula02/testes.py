from scipy.stats import geom


print(1-geom.cdf(3, 0.3))



from scipy.stats import poisson

print(1-poisson.cdf(4, 2))