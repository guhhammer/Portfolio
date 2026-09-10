"""Naive Bayes classifier for server load (class 05, reused in assignment 02).

Generates a synthetic dataset of CPU and memory utilisation pairs labelled
"normal load" (0) or "high load" (1), trains a Gaussian Naive Bayes classifier,
computes accuracy and the per-class and support-weighted F1 scores by hand,
and saves the training scatter plot (training.png) and the decision boundary
with the test points (test.png).

The data generator and the plotting helpers are adapted from Udacity's
"Introduction to Machine Learning" course (Sebastian Thrun, Katie Malone),
as handed out by the professor; nb_classifier.py and the metrics below are
the student's part.
"""

from server_data import server_data
from plotting import training_figure, test_figure
from nb_classifier import classify

# build the training and test sets
X_train, Y_train, X_test, Y_test = server_data()

# scatter plot of the training set, saved to training.png
training_figure(X_train, Y_train)

# train the Gaussian Naive Bayes classifier and predict the test set
clf = classify(X_train, Y_train)
Y_pred = clf.predict(X_test)


from sklearn.metrics import f1_score, accuracy_score


acc = accuracy_score(Y_test, Y_pred)


tp = 0
tn = 0
fp = 0
fn = 0

for i in range(len(Y_pred)):

    if int(Y_pred[i]) == int(Y_test[i]) == 1:
        tp += 1
        continue

    if int(Y_pred[i]) == int(Y_test[i]) == 0:
        tn += 1
        continue

    if int(Y_pred[i]) == 0 and 1 == int(Y_test[i]):
        fn += 1
        continue

    if int(Y_pred[i]) == 1 and 0 == int(Y_test[i]):
        fp += 1
        continue


print(tp, tn, fp, fn)


support_0 = fp + tn
support_1 = tp + fn


prec_0 = tn / (tn + fn)
rec_0 = tn / (tn + fp)

F1_0 = (2 * prec_0 * rec_0) / (prec_0 + rec_0)

prec_1 = tp / (tp + fp)
rec_1 = tp / (tp + fn)
F1_1 = (2 * prec_1 * rec_1) / (prec_1 + rec_1)


F1_weighted = (F1_0 * support_0 + F1_1 * support_1) / (support_0 + support_1)


# f1_weighted = f1_score(Y_test, Y_pred, average='weighted')


print('acc: ', acc, '\nf1_weighted: ', F1_weighted)


# decision boundary coloured by the trained classifier, with the test points on top, saved to test.png
test_figure(clf, X_test, Y_test)
