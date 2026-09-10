function rows = x_given_y(X, Y, y)
%X_GIVEN_Y Rows of X whose label in Y equals y.

    rows = X(Y == y, :);
end
