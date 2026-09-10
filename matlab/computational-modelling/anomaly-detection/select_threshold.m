function [best_epsilon, best_f1] = select_threshold(yval, pval)
%SELECT_THRESHOLD Best probability threshold for flagging anomalies.
%   [best_epsilon, best_f1] = SELECT_THRESHOLD(yval, pval) scans candidate
%   thresholds between min(pval) and max(pval) and keeps the one with the
%   highest F1 score on the cross-validation set.
%
%   yval  true labels (1 = anomaly, 0 = normal)
%   pval  density of each cross-validation example under the fitted model;
%         examples with pval < epsilon are predicted as anomalies

best_epsilon = 0;
best_f1      = 0;

step = (max(pval) - min(pval)) / 1000;
for epsilon = min(pval):step:max(pval)

    predictions = (pval < epsilon);

    tp = sum((predictions == 1) & (yval == 1));   % true positives
    fp = sum((predictions == 1) & (yval == 0));   % false positives
    fn = sum((predictions == 0) & (yval == 1));   % false negatives

    if tp == 0
        continue;                                 % precision/recall undefined
    end

    precision = tp / (tp + fp);
    recall    = tp / (tp + fn);
    f1        = (2 * precision * recall) / (precision + recall);

    if f1 > best_f1
        best_f1      = f1;
        best_epsilon = epsilon;
    end
end

end
