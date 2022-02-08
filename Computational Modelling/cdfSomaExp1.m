function cdfSomaExp1(x, lambda, N, nSimul)
    % Gustavo Hammerschmidt
    tic;
    mu = 1/lambda;
    
    A(1:nSimul) = sum(exprnd(mu, N, nSimul));
    
    probS = sum(A < x)/nSimul;
    tempo = toc;
    
    probA = gamcdf(x, N, mu);
    
    fprintf("\n\n tempo: %f \n probS: %f \n probA: %f \n",tempo, probS,probA)

end