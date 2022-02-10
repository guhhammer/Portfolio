import numpy as np
from math import factorial
from math import exp

class FilaMMm:
    def calculaP0(self):
        temp = 1 + ((self.m * self.ro) ** self.m) / (factorial(self.m) * (1 - self.ro))
        for k in range(1, self.m):
            temp = temp + (((self.m * self.ro) ** k) / factorial(k))
        return 1 / temp

    #def calculaEpsilon(self):

    def __init__(self, lb, mu, m):
        if (lb >= m*mu):
            raise ValueError('Lambda deve ser menor do que m*mu')
        self.lb = float(lb)
        self.mu = float(mu)
        self.m = m
        self.ro = lb / (m*mu)
        self.p0 = self.calculaP0()
        self.epsilon = ((self.m*self.ro)**self.m)/((1-self.ro)*factorial(self.m))*self.p0
        # valor esperado de Ns (quantidade media de tarefas servidas)
        self.E_Ns = self.m * self.ro
        # valor esperado de Nq (tamanho medio da fila)
        self.E_Nq = (self.epsilon * self.ro) / (1 - self.ro)
        # valor esperado de N 9quentidade media de tarefas no sistema)
        self.E_N = self.E_Nq + self.E_Ns
        # valor esperado de S (tempo de serviço médio)
        self.E_S = 1 / self.mu
        # valor esperado de W (tempo de espera medio na fila)
        self.E_W = self.epsilon / (self.m * self.mu * (1 - self.ro))
        # valor esperado de R (tempo de resposta medio)
        self.E_R = self.E_S + self.E_W


    def pmf_N(self, x):
        if (x < self.m):
            return (((self.m * self.ro) ** x) / factorial(x)) * self.p0
        else:
            return (((self.ro ** x) * (self.m ** self.m)) / factorial(self.m)) * self.p0

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


fmm = FilaMMm(30, 1/0.05, 3)

print(fmm.cdf_W(0.01))

#print( 'E_Nq: ', fmm.E_Nq )



