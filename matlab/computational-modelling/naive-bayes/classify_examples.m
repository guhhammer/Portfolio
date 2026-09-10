%% Naive Bayes worked examples
%  Loads the training table and classifies two customers:
%    x1 = [1 2 1 1]  under 30, medium income, student, fair credit
%    x2 = [3 2 2 1]  over 40, medium income, not a student, fair credit

clear; clc
training_data;               % defines X and Y
classes = [1 2];             % 1 = buys, 2 = does not buy

for x = {[1 2 1 1], [3 2 2 1]}
    v = x{1};
    [class, posterior] = nb_classifier(X, Y, v, classes);
    fprintf('x = [%s]\n', num2str(v));
    fprintf('  P(x|c) P(c) per class: %s\n', num2str(posterior));
    fprintf('  per-feature P(x_j|c):\n');
    disp(p_x_given_y_per_feature(X, Y, v, classes));
    fprintf('  predicted class: %d\n\n', class);
end
