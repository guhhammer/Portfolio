"""Quick scipy.stats calculations used while solving the team problem sets (classes 02, 03 and 07)."""
import scipy.stats as st

# class 02 - discrete random variables
print(1 - st.geom.cdf(3, 0.3))       # geometric, p = 0.3: P[X > 3]
print(1 - st.poisson.cdf(4, 2))      # Poisson, mean 2: P[X > 4]

# class 03 - continuous random variables
print(st.uniform.cdf(500, 200, 600) - st.uniform.cdf(300, 200, 600))   # uniform on [200, 800]: P[300 < X < 500]
print(st.expon.cdf(15, 0, 10) - st.expon.cdf(5, 0, 10))               # exponential, mean 10: P[5 < X < 15]
print(st.norm.cdf(6350, 5000, 1000) - st.norm.cdf(4500, 5000, 1000))   # normal(5000, 1000): P[4500 < X < 6350]

# class 07 - gamma distribution (sum of 3 exponentials with mean 3): P[X > 10]
print(1 - st.gamma.cdf(10, a=3, scale=3))
