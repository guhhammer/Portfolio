function binomial_pdf_vs_histogram(n, p, n_samples)
%BINOMIAL_PDF_VS_HISTOGRAM Compare simulated binomial data with its pmf.
%   BINOMIAL_PDF_VS_HISTOGRAM(n, p, n_samples) draws n_samples values of
%   Binomial(n, p), plots their relative frequencies (blue) and overlays the
%   theoretical probability mass function (red).
%   Requires the Statistics toolbox/package (binornd, binopdf).

    samples = binornd(n, p, 1, n_samples);
    x = 0:n;
    [freq, x] = hist(samples, x);
    freq = freq / n_samples;
    pmf = binopdf(x, n, p);
    plot(x, freq, '-b', x, pmf, '-r');
    legend('simulated', 'theoretical');
    title(sprintf('Binomial(n = %d, p = %.2f)', n, p));
end
