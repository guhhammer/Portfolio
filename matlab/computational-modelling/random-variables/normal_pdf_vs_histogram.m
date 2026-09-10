function normal_pdf_vs_histogram(mu, sigma, n_samples)
%NORMAL_PDF_VS_HISTOGRAM Compare simulated normal data with its pdf.
%   Draws n_samples values of Normal(mu, sigma), builds a density
%   histogram (blue) and overlays the theoretical pdf (red).
%   Requires the Statistics toolbox/package (normrnd, normpdf).

    samples = normrnd(mu, sigma, 1, n_samples);
    step = (max(samples) - min(samples)) / 100;
    centres = min(samples):step:max(samples);
    [freq, x] = hist(samples, centres);
    density = freq / n_samples / step;   % counts -> probability density
    pdf = normpdf(x, mu, sigma);
    plot(x, density, '-b', x, pdf, '-r');
    legend('simulated', 'theoretical');
    title(sprintf('Normal(mu = %.2f, sigma = %.2f)', mu, sigma));
end
