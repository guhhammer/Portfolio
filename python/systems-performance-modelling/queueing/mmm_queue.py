"""M/M/m queue: closed-form steady-state metrics (class 09 / team problem 09).

MMmQueue computes, from the arrival rate lb, the service rate mu and the number
of servers m: the utilisation ro, the idle probability P0, Erlang's C (epsilon),
the expected numbers in service, in queue and in the system (E_Ns, E_Nq, E_N),
the expected service, waiting and response times (E_S, E_W, E_R), and the
distributions of N, W and R.

The example at the bottom answers team problem 09: a port receives a ship every
8 hours on average (exponential inter-arrival times) and unloading a ship takes
12 hours on average. With 2 berths, what are the mean queue length and the mean
response time? How many berths are needed for the mean response time to drop
below 15 hours?
"""
import numpy as np
from math import factorial
from math import exp

class MMmQueue:
    def p0(self):
        temp = 1 + ((self.m * self.ro) ** self.m) / (factorial(self.m) * (1 - self.ro))
        for k in range(1, self.m):
            temp = temp + (((self.m * self.ro) ** k) / factorial(k))
        return 1 / temp

    def __init__(self, lb, mu, m):
        if (lb >= m*mu):
            raise ValueError('Lambda must be smaller than m*mu')
        self.lb = float(lb)
        self.mu = float(mu)
        self.m = m
        self.ro = lb / (m*mu)
        self.P0 = self.p0()
        self.epsilon = ((self.m*self.ro)**self.m)/((1-self.ro)*factorial(self.m))*self.P0
        # expected value of Ns (mean number of tasks being served)
        self.E_Ns = self.m * self.ro
        # expected value of Nq (mean queue length)
        self.E_Nq = (self.epsilon * self.ro) / (1 - self.ro)
        # expected value of N (mean number of tasks in the system)
        self.E_N = self.E_Nq + self.E_Ns
        # expected value of S (mean service time)
        self.E_S = 1 / self.mu
        # expected value of W (mean waiting time in the queue)
        self.E_W = self.epsilon / (self.m * self.mu * (1 - self.ro))
        # expected value of R (mean response time)
        self.E_R = self.E_S + self.E_W


    def pmf_N(self, x):
        if (x < self.m):
            return (((self.m * self.ro) ** x) / factorial(x)) * self.P0
        else:
            return (((self.ro ** x) * (self.m ** self.m)) / factorial(self.m)) * self.P0

    def cdf_W(self, x):
        return 1 - (self.epsilon*exp(-self.m*self.mu*(1-self.ro)*x))

    def cdf_R(self, x):
        if (self.ro != (self.m-1)/self.m):
            p = 1 - exp(-self.mu * x) - (
                    (self.epsilon / (1 - self.m + self.m * self.ro)) * exp(-self.m * self.mu * (1 - self.ro) * x))
        else:
            p = 1 - exp(-self.mu * x) - (
                    self.epsilon * self.mu * x * exp(-self.m * self.mu * (1 - self.ro) * x))
        return p


if __name__ == "__main__":
    # team problem 09: ships arrive every 8 h, unloading takes 12 h, 2 berths
    port = MMmQueue(1/8, 1/12, 2)

    print('Mu: ', port.mu)
    print('E_Nq: ', port.E_Nq)
    print('E_R: ', port.E_R)

    # how many berths bring the mean response time below 15 hours?
    servers = 2
    while True:

        port = MMmQueue(1/8, 1/12, servers)

        if port.E_R < 15.0:

            print('E_R: ', port.E_R)

            print('servers: ', servers)

            print('E_Nq: ', port.E_Nq)

            break

        servers += 1

    print("ok")
