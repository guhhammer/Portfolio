function [mu, sigma2] = estimate_gaussian(X)
%ESTIMATE_GAUSSIAN Mean and variance of every feature in X.
%   [mu, sigma2] = ESTIMATE_GAUSSIAN(X) takes a dataset with one
%   n-dimensional example per row and returns the 1 x n vector of feature
%   means (mu) and the 1 x n vector of feature variances (sigma2).
%
%   The features are treated as independent, so sigma2 describes a
%   diagonal covariance matrix.

[m, n] = size(X);

mu     = sum(X) / m;
sigma2 = sum((X - mu) .^ 2) / m;   % population variance (divides by m)

end
