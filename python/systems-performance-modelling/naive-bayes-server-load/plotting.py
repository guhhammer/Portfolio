#!/usr/bin/python

# Provides the functions training_figure and test_figure

import warnings

warnings.filterwarnings("ignore")

import matplotlib

# matplotlib.use('agg')

import matplotlib.pyplot as plt
import pylab as pl
import numpy as np


# Function training_figure
# Arguments
#      training attributes (X_train)
#      training labels (Y_train)
# Saves the figure "training.png":
#      scatter plot of the training set
#
def training_figure(X_train, y_train):
    ### the training data (X_train, y_train) mixes "normal load" and "high load" points;
    ### separate them to give each class a colour in the scatter plot
    cpu_normal = [X_train[ii][0] for ii in range(0, len(X_train)) if y_train[ii] == 0]
    memory_normal = [X_train[ii][1] for ii in range(0, len(X_train)) if y_train[ii] == 0]
    cpu_high = [X_train[ii][0] for ii in range(0, len(X_train)) if y_train[ii] == 1]
    memory_high = [X_train[ii][1] for ii in range(0, len(X_train)) if y_train[ii] == 1]

    # axis limits
    plt.xlim(0.0, 1.0)
    plt.ylim(0.0, 1.0)

    # scatter plot
    plt.scatter(cpu_normal, memory_normal, color="b", label="normal load")
    plt.scatter(cpu_high, memory_high, color="r", label="high load")

    # legend
    plt.legend()
    plt.xlabel("cpu utilisation")
    plt.ylabel("memory utilisation")

    # save the figure
    plt.savefig("training.png")
    plt.close()


def test_figure(clf, X_test, y_test):
    x_min = 0.0;
    x_max = 1.0
    y_min = 0.0;
    y_max = 1.0

    #####################################################################
    # this block plots the decision boundary
    ######################################################################

    # build a grid of points over the x and y axes between 0.0 and 1.0, with step h = 0.01
    h = .01  # step of the mesh
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h), np.arange(y_min, y_max, h))

    ## predict every grid point and store the result in Z
    Z = clf.predict(np.c_[xx.ravel(), yy.ravel()])

    ## the predicted value in Z sets the colour of each point (cmap argument)
    Z = Z.reshape(xx.shape)
    plt.xlim(xx.min(), xx.max())
    plt.ylim(yy.min(), yy.max())
    plt.pcolormesh(xx, yy, Z, cmap=pl.cm.seismic)

    ###############################################################
    # this block prepares the test points
    ###############################################################

    ### the test data (X_test, y_test) mixes "normal load" and "high load" points;
    ### separate them to give each class a colour so they can be told apart
    cpu_normal = [X_test[ii][0] for ii in range(0, len(X_test)) if y_test[ii] == 0]
    memory_normal = [X_test[ii][1] for ii in range(0, len(X_test)) if y_test[ii] == 0]
    cpu_high = [X_test[ii][0] for ii in range(0, len(X_test)) if y_test[ii] == 1]
    memory_high = [X_test[ii][1] for ii in range(0, len(X_test)) if y_test[ii] == 1]

    ### label 0 (normal load) in blue, label 1 (high load) in red
    plt.scatter(cpu_normal, memory_normal, color="b", label="normal load")
    plt.scatter(cpu_high, memory_high, color="r", label="high load")

    #############################################################
    # end of the test-point preparation
    ############################################################

    # legend
    plt.legend()
    plt.xlabel("cpu utilisation")
    plt.ylabel("memory utilisation")

    # save the figure
    plt.savefig("test.png")
    plt.close()
