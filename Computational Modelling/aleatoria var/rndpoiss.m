function out = rndpoiss(lambda, n)
%   Esta função cria um vetor com 'n' valores aleatórios 
%   com distribuição de Poisson e parametro 'lambda'
    for k=1:n
        i = 0;
        f = exp(-lambda);
        p = exp(-lambda);
        u = rand;
        while f<=u
            p = p * (lambda / (i + 1));
            f = f + p;
            i = i + 1;
        end
        out(k) = i;
    end
end