function prior = p_y(Y, classes)
%P_Y Prior probability of each class, estimated from the labels in Y.
%   prior(i) = (number of rows labelled classes(i)) / (number of rows)

    prior = zeros(size(classes));
    for i = 1:numel(classes)
        prior(i) = sum(Y == classes(i)) / size(Y, 1);
    end
end
