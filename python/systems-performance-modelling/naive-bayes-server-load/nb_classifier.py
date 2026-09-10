"""Builds and trains the Gaussian Naive Bayes classifier (the part of the exercise left to the student)."""


def classify(X_train, Y_train):

    from sklearn.naive_bayes import GaussianNB

    clf = GaussianNB()

    clf.fit(X_train, Y_train)

    return clf
