% Ordinary least squares by hand.
%
% Fits y = b0 + b1*x1 + b2*x2 to six observations.  The first column of X
% is all ones so that b0 is the intercept.  The normal equations give
% beta = (X'X)^-1 X'y; the backslash operator solves the same system more
% accurately and is the idiomatic MATLAB/Octave way.

y = [1.5; 6.5; 10; 11; 11.5; 16.5];

X = [1 0 0;
     1 1 2;
     1 1 4;
     1 2 2;
     1 2 4;
     1 3 6];

beta_normal_equations = inv(X' * X) * X' * y
beta_backslash        = X \ y

fitted    = X * beta_backslash;
residuals = y - fitted;
r_squared = 1 - sum(residuals .^ 2) / sum((y - mean(y)) .^ 2)
