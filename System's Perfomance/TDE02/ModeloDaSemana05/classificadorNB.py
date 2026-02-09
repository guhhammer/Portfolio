# Este arquivo foi adaptado do curso Introductio to Machine Learn do Udacity
# Com Sebastian Thrun e Katie Malone
# Contem a funcao classifica que voce deve modificar
# O que deve ser feito esta explicado no inicio da funcao


def classifica(X_trein, Y_trein):
    
    from sklearn.naive_bayes import GaussianNB

    clf = GaussianNB()

    clf.fit(X_trein, Y_trein)

    return clf
