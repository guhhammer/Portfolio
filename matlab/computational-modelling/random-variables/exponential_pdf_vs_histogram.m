function exponential_pdf_vs_histogram(mu, n_samples)
%EXPONENTIAL_PDF_VS_HISTOGRAM Compare simulated exponential data with its pdf.
%   Draws n_samples values of Exponential(mean mu), builds a density
%   histogram (blue) and overlays the theoretical pdf (red).
%   Requires the Statistics toolbox/package (exprnd, exppdf).

    samples = exprnd(mu, 1, n_samples);
    step = (max(samples) - min(samples)) / 100;
    centres = min(samples):step:max(samples);
    [freq, x] = hist(samples, centres);
    density = freq / n_samples / step;   % counts -> probability density
    pdf = exppdf(x, mu);
    plot(x, density, '-b', x, pdf, '-r');
    legend('simulated', 'theoretical');
    title(sprintf('Exponential(mean = %.2f)', mu));
end
