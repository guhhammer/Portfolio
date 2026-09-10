function [mu, Sigma] = estimate_gaussian_full(X)
%ESTIMATE_GAUSSIAN_FULL Mean vector and full covariance matrix of X.
%   [mu, Sigma] = ESTIMATE_GAUSSIAN_FULL(X) returns the 1 x n vector of
%   feature means and the n x n sample covariance matrix, so correlations
%   between features are captured by the model.

m = size(X, 1);

mu    = sum(X) / m;
Sigma = cov(X);

end
