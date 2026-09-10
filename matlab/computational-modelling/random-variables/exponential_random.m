function out = exponential_random(mu, n)
%EXPONENTIAL_RANDOM n exponential random numbers with mean mu.
%   Uses the inverse-transform method: if U is uniform on (0, 1) then
%   -mu * log(U) follows the exponential distribution with mean mu.
%   Needs no toolbox; it is the hand-made counterpart of exprnd.

    lambda = 1 / mu;
    out = -log(rand(1, n)) ./ lambda;
end
