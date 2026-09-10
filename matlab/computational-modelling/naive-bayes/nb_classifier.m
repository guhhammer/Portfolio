function [class, posterior] = nb_classifier(X, Y, x, classes)
%NB_CLASSIFIER Naive Bayes classification of one observation.
%   [class, posterior] = NB_CLASSIFIER(X, Y, x, classes)
%     X        observed feature values, one observation per row
%     Y        observed class label of each row of X
%     x        feature vector to classify
%     classes  vector with the possible class values (positive integers)
%   Returns the class with the largest unnormalised posterior
%   P(x | class) * P(class), plus the vector of those posteriors.

    posterior = p_x_given_y(X, Y, x, classes) .* p_y(Y, classes);
    [~, best] = max(posterior);
    class = classes(best);
end
