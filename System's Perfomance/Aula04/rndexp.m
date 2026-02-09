function [ out ] = rndexp(mu, n, m)
%   Cria matriz n x m com valores distribuidos  
%   segundo distribuição exponencial com média mu.  
    u = rand(n, m);
    out = -log(1 - u) .* mu;
end