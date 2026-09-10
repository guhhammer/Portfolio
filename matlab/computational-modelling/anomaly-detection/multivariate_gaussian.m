function p = multivariate_gaussian(X, mu, Sigma2)
%MULTIVARIATE_GAUSSIAN Density of the multivariate normal distribution.
%   p = MULTIVARIATE_GAUSSIAN(X, mu, Sigma2) evaluates the probability
%   density of every row of X under the multivariate normal with mean mu
%   and covariance Sigma2.  If Sigma2 is a vector it is taken as the
%   variances of each dimension (a diagonal covariance matrix); if it is a
%   matrix it is used as the covariance matrix itself.

k = length(mu);

if (size(Sigma2, 2) == 1) || (size(Sigma2, 1) == 1)
    Sigma2 = diag(Sigma2);
end

X = bsxfun(@minus, X, mu(:)');
p = (2 * pi) ^ (- k / 2) * det(Sigma2) ^ (-0.5) * ...
    exp(-0.5 * sum(bsxfun(@times, X * pinv(Sigma2), X), 2));

end
