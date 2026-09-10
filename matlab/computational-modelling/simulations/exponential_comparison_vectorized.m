function [prob_sim, prob_analytic] = exponential_comparison_vectorized(lambda1, lambda2, n_sim)
%EXPONENTIAL_COMPARISON_VECTORIZED Vectorised version of exponential_comparison.
%   Draws the n_sim pairs in two calls instead of a loop.
%   Requires the Statistics toolbox/package (exprnd).

mu1 = 1 / lambda1;
mu2 = 1 / lambda2;

x1 = exprnd(mu1, n_sim, 1);
x2 = exprnd(mu2, n_sim, 1);
prob_sim = sum(x1 < x2) / n_sim;

prob_analytic = lambda1 / (lambda1 + lambda2);

end
