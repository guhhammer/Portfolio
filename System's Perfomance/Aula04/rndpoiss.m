function [ out ] = rndpoiss(L, n, m)
%   Esta fun��o cria uma matriz n x m com valores aleat�rios 
%   com distribui��o de Poisson e parametro 'lambda'
out = zeros(n, m);
for k=1:m
    for j=1:n
        i = 0;
        cdf = exp(-L);
        pdf = exp(-L);
        u = rand;
        while cdf<=u
            pdf = pdf * (L / (i + 1));
            cdf = cdf + pdf;
            i = i + 1;
        end
        out(j, k) = i;
    end
end