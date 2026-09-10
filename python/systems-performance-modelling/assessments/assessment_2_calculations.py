"""SciPy calculations for individual assessment 2 (see individual-assessment-2.pdf for the questions).

1) help desk with three request types arriving as independent Poisson processes
   (login 2.5/4 per hour, hardware 0.5 per hour, other 1.5 per hour): probability of
   exactly 3 hardware requests and no other request in 2 hours
2) gamma distribution: sum of 3 exponentials with mean 4 exceeding 6
3) M/M/3 queue with lambda = 30 and mu = 20 (service time 0.05): P[W <= 0.01]
"""
import os
import sys
import scipy.stats as st

sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'queueing'))
from mmm_queue import MMmQueue

#1
val = (st.poisson.pmf(3, 2.5/4 * 2) *
       st.poisson.pmf(0, 0.5 * 2) *
       st.poisson.pmf(0, 1.5 * 2))

print(val)

#2
print(1 - st.gamma.cdf(6, a=3, scale=4))

#3
queue = MMmQueue(30, 1/0.05, 3)

print(queue.cdf_W(0.01))
