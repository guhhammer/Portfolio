function [prob_sim, prob_analytic] = exponential_comparison(lambda1, lambda2, n_sim)
%EXPONENTIAL_COMPARISON P(X1 < X2) for two independent exponentials.
%   [prob_sim, prob_analytic] = EXPONENTIAL_COMPARISON(lambda1, lambda2, n_sim)
%   simulates n_sim pairs (X1 ~ Exp(lambda1), X2 ~ Exp(lambda2)) in a loop
%   and compares the observed frequency with the closed form
%   lambda1 / (lambda1 + lambda2).
%   Requires the Statistics toolbox/package (exprnd).

mu1 = 1 / lambda1;
mu2 = 1 / lambda2;
hits = 0;

for k = 1:n_sim
    x1 = exprnd(mu1);
    x2 = exprnd(mu2);
    if x1 < x2
        hits = hits + 1;
    end
end
prob_sim = hits / n_sim;
prob_analytic = lambda1 / (lambda1 + lambda2);

end
