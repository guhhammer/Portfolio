function [prob_sim, prob_analytic] = sum_of_exponentials_cdf(x, lambda, n, n_sim)
%SUM_OF_EXPONENTIALS_CDF P(S < x) where S is the sum of n exponentials.
%   [prob_sim, prob_analytic] = SUM_OF_EXPONENTIALS_CDF(x, lambda, n, n_sim)
%   estimates the probability by simulation (n_sim experiments, one loop
%   iteration each) and compares it with the exact value: the sum of n
%   independent Exponential(rate lambda) variables is Gamma(n, 1/lambda).
%   Requires the Statistics toolbox/package (exprnd, gamcdf).

mu  = 1 / lambda;
hits = 0;

for k = 1:n_sim
    s = sum(exprnd(mu, 1, n));
    if s < x
        hits = hits + 1;
    end
end
prob_sim = hits / n_sim;

prob_analytic = gamcdf(x, n, mu);

end
