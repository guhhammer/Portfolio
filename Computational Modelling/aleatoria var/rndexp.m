function out = rndexp(mu, n)
% RANDEXP(a,n)
%   Esta fun��o cria com 'n' valores distribuidos aleat�riamente  
%   segundo distribui��o exponencial com par�metro m�dia mu.  

    a = 1/mu;
    out = -log(rand(1,n)) ./ a;
end