% Bar chart of the geometric distribution with success probability 0.1:
% P(X = k) is the chance that the first success happens after k failures.
% Requires the Statistics toolbox/package (geopdf).

x = 0:25;
y = geopdf(x, 0.1);
bar(x, y, 0.05);
xlabel('failures before the first success');
ylabel('probability');
title('Geometric(p = 0.1)');
