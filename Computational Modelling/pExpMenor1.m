function [ probS, probA ] = pExpMenor1(lambda1 , lambda2, nSim)
mu1 = 1/lambda1;
mu2 = 1/lambda2;

x1 = exprnd(mu1, nSim, 1);
x2 = exprnd(mu2, nSim, 1);
hit = sum((x1 < x2));
probS = hit/nSim;

probA = lambda1 / (lambda1 + lambda2);
