function out = rndexp(mu, n)
% RANDEXP(a,n)
%   Esta função cria com 'n' valores distribuidos aleatóriamente  
%   segundo distribuição exponencial com parâmetro média mu.  

    a = 1/mu;
    out = -log(rand(1,n)) ./ a;
end