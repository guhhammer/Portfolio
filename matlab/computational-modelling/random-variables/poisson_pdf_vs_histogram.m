function poisson_pdf_vs_histogram(lambda, n_samples)
%POISSON_PDF_VS_HISTOGRAM Compare simulated Poisson data with its pmf.
%   Draws n_samples values of Poisson(lambda), plots their relative
%   frequencies (blue) and the theoretical pmf (red).
%   Requires the Statistics toolbox/package (poissrnd, poisspdf).

    samples = poissrnd(lambda, 1, n_samples);
    x = 0:max(samples);
    [freq, x] = hist(samples, x);
    freq = freq / n_samples;
    pmf = poisspdf(x, lambda);
    plot(x, freq, '-b', x, pmf, '-r');
    legend('simulated', 'theoretical');
    title(sprintf('Poisson(lambda = %.2f)', lambda));
end
