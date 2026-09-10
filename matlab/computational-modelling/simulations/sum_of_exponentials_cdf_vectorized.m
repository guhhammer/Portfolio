function [prob_sim, prob_analytic] = sum_of_exponentials_cdf_vectorized(x, lambda, n, n_sim)
%SUM_OF_EXPONENTIALS_CDF_VECTORIZED Vectorised version of sum_of_exponentials_cdf.
%   Draws all n x n_sim exponentials at once, sums each column and counts
%   how many sums fall below x.  Prints the elapsed time so it can be
%   compared with the loop-based version.
%   Requires the Statistics toolbox/package (exprnd, gamcdf).

    tic;
    mu = 1 / lambda;

    sums = sum(exprnd(mu, n, n_sim));       % one column per experiment
    prob_sim = sum(sums < x) / n_sim;

    elapsed = toc;

    prob_analytic = gamcdf(x, n, mu);

    fprintf('\n elapsed: %f s\n simulated: %f\n analytic:  %f\n', ...
            elapsed, prob_sim, prob_analytic);
end
