%% Anomaly detection on network-server measurements
%
%  Fits a Gaussian model to "normal" server behaviour and flags the points
%  whose probability density falls below a threshold epsilon.  The threshold
%  is chosen on a labelled cross-validation set by maximising the F1 score.
%
%  Part 1  two features (latency, throughput) so the fit can be drawn
%  Part 2  eleven features, where only a few of them reveal the anomalies
%
%  Helper functions in this folder:
%     estimate_gaussian.m       per-feature mean and variance (diagonal model)
%     estimate_gaussian_full.m  mean and full covariance matrix
%     multivariate_gaussian.m   density of the multivariate normal
%     select_threshold.m        best epsilon on the cross-validation set
%     visualize_fit.m           scatter plot with density contours
%
%  Works in MATLAB and in GNU Octave without any extra toolbox/package.

clear; close all; clc

% Set to true to model the features jointly (full covariance matrix)
% instead of assuming independent features (diagonal covariance).
use_full_covariance = false;

%% ================== Part 1: two-dimensional example ===================
fprintf('Part 1: latency/throughput dataset\n\n');

% Loads X (training set), Xval and yval (labelled cross-validation set)
load('network_data.mat');

figure('Name', 'Training data');
plot(X(:, 1), X(:, 2), 'bx');
axis([0 30 0 30]);
xlabel('Latency (ms)');
ylabel('Throughput (Mb/s)');
title('Server measurements');

% Estimate the distribution parameters from the training set
if use_full_covariance
    [mu, sigma2] = estimate_gaussian_full(X);
else
    [mu, sigma2] = estimate_gaussian(X);
end

% Density of every training point under the fitted model
p = multivariate_gaussian(X, mu, sigma2);

figure('Name', 'Gaussian fit');
visualize_fit(X, mu, sigma2);
xlabel('Latency (ms)');
ylabel('Throughput (Mb/s)');
title('Fitted Gaussian density');

% Choose epsilon on the cross-validation set
pval = multivariate_gaussian(Xval, mu, sigma2);
[epsilon, F1] = select_threshold(yval, pval);

fprintf('Best epsilon found by cross-validation: %e\n', epsilon);
fprintf('Best F1 on the cross-validation set:    %f\n', F1);
fprintf('   (expected epsilon is about 8.99e-05 with the diagonal model)\n\n');

% Flag the training points below the threshold and circle them in red
anomalies = find(p < epsilon);
fprintf('Anomalies found in the training set: %d\n\n', numel(anomalies));

hold on
plot(X(anomalies, 1), X(anomalies, 2), 'ro', 'LineWidth', 2, 'MarkerSize', 10);
hold off

%% ================== Part 2: eleven-dimensional dataset ===================
fprintf('Part 2: eleven-feature dataset\n\n');

load('network_data2.mat');

if use_full_covariance
    [mu, sigma2] = estimate_gaussian_full(X);
else
    [mu, sigma2] = estimate_gaussian(X);
end

p    = multivariate_gaussian(X, mu, sigma2);
pval = multivariate_gaussian(Xval, mu, sigma2);

[epsilon, F1] = select_threshold(yval, pval);

fprintf('Best epsilon found by cross-validation: %e\n', epsilon);
fprintf('Best F1 on the cross-validation set:    %f\n', F1);
fprintf('Anomalies found in the training set:    %d\n', sum(p < epsilon));
fprintf('   (expected epsilon is about 1.38e-18 with the diagonal model)\n');
