function [ probS, probA ] = pExpMenor(lambda1 , lambda2, nSim)
mu1 = 1/lambda1;
mu2 = 1/lambda2;
hit = 0;

for k = 1:nSim
	x1 = exprnd(mu1);
	x2 = exprnd(mu2);
	if x1 < x2
		hit = hit + 1;
	end
end
probS = hit/nSim; 
probA = lambda1 / (lambda1 + lambda2);
