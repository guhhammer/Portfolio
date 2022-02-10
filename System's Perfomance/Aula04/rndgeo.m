function [ out ] = rndgeo(p, n, m)
%   Esta função cria uma matriz n x m com valores aleatórios 
%   com distribuição geometrica e parametro 'p'
out = zeros(n, m);
for k=1:m
    for j=1:n
        i = 0;
        cdf = p;
        pdf = p;
        u = rand;
        while cdf<=u
            pdf = pdf * (1-p);
            cdf = cdf + pdf;
            i = i + 1;
        end
        out(j, k) = i;
    end
end