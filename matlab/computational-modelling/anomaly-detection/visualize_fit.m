function visualize_fit(X, mu, sigma2)
%VISUALIZE_FIT Scatter plot of the data with contours of the fitted density.
%   VISUALIZE_FIT(X, mu, sigma2) draws the two-dimensional examples in X
%   and overlays the level curves of the Gaussian with parameters mu and
%   sigma2, so the reader can see how well the model follows the data.

[X1, X2] = meshgrid(0:.5:35);

Z = multivariate_gaussian([X1(:) X2(:)], mu, sigma2);
Z = reshape(Z, size(X1));

plot(X(:, 1), X(:, 2), 'bx');
hold on;
% Skip the contours if the density overflowed anywhere
if (sum(isinf(Z(:))) == 0)
    contour(X1, X2, Z, 10 .^ (-20:3:0)');
end
hold off;

end
