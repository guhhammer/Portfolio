function likelihood = p_x_given_y(X, Y, x, classes)
%P_X_GIVEN_Y Naive likelihood P(x | class) for every class.
%   Under the naive independence assumption the likelihood of x given a
%   class is the product over features j of P(x(j) | class), each factor
%   estimated as a relative frequency in the training rows of that class.

    likelihood = ones(size(classes));
    for i = 1:numel(classes)
        rows = x_given_y(X, Y, classes(i));
        for j = 1:numel(x)
            likelihood(i) = likelihood(i) * sum(rows(:, j) == x(j)) / size(rows, 1);
        end
    end
end
