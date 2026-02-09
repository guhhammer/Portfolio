

import scipy.stats as st


print(st.uniform.cdf(500, 200, 600) - st.uniform.cdf(300, 200, 600))


print(st.expon.cdf(15,0,10) - st.expon.cdf(5,0,10))

print(st.norm.cdf(6350,5000,1000) - st.norm.cdf(4500,5000,1000))