function [ probS, probA ] = cdfSomaExp(x, lambda , N, nSim)
%SomaExp.m
mu = 1 / lambda;
hit = 0;

for k = 1:nSim
	s = sum(exprnd(mu, 1, N));
	if(s < x)
		hit = hit + 1;
	end
end
probS = hit/nSim;

probA = gamcdf(x, N, mu);