function out = poisson_random(lambda, n)
%POISSON_RANDOM n Poisson random numbers with rate lambda.
%   Inverse-transform sampling: draw a uniform u and walk up the
%   cumulative distribution F(i) = P(X <= i) until it exceeds u.
%   Hand-made counterpart of poissrnd, no toolbox required.

    out = zeros(1, n);
    for k = 1:n
        i = 0;
        p = exp(-lambda);   % P(X = 0)
        f = p;              % cumulative probability so far
        u = rand;
        while f <= u
            p = p * (lambda / (i + 1));   % P(X = i+1) from P(X = i)
            f = f + p;
            i = i + 1;
        end
        out(k) = i;
    end
end
