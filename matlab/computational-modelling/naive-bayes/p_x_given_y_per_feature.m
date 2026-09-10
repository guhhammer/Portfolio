function likelihood = p_x_given_y_per_feature(X, Y, x, classes)
%P_X_GIVEN_Y_PER_FEATURE Conditional probability of each feature value.
%   Returns a matrix with one row per class and one column per feature:
%   likelihood(i, j) = P(feature j = x(j) | class i).  Multiplying the
%   entries of a row gives the naive likelihood used by p_x_given_y.

    likelihood = zeros(numel(classes), numel(x));
    for i = 1:numel(classes)
        rows = x_given_y(X, Y, classes(i));
        for j = 1:numel(x)
            likelihood(i, j) = sum(rows(:, j) == x(j)) / size(rows, 1);
        end
    end
end
