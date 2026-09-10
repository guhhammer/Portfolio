#!/usr/bin/python

# Provides the function server_data, which builds a random dataset of CPU and
# memory utilisation proportions for a server and the label of each pair
# (0 = normal load, 1 = high load).

import random


def server_data(n_points=1000):
    ###############################################################################
    ### build the dataset
    ### number of points = n_points

    ### draw the CPU and memory utilisation proportions
    random.seed(42)
    cpu_util = [random.random() for ii in range(0, n_points)]
    memory_util = [random.random() for ii in range(0, n_points)]

    ### draw some noise (error) to produce the y values (labels);
    ### after this step y is 0 or 1 depending on the CPU and memory utilisation and on the noise
    error = [random.random() for ii in range(0, n_points)]
    y = [round(cpu_util[ii] * memory_util[ii] + 0.3 + 0.1 * error[ii]) for ii in range(0, n_points)]

    ### the most critical cases are forced to y = 1
    for ii in range(0, len(y)):
        if cpu_util[ii] > 0.9 or memory_util[ii] > 0.9:
            y[ii] = 1.0

    ### split into train and test sets: 75% training, 25% test
    X = [[gg, ss] for gg, ss in zip(cpu_util, memory_util)]
    split = int(0.75 * n_points)
    X_train = X[0:split]
    X_test = X[split:]
    Y_train = y[0:split]
    Y_test = y[split:]

    return X_train, Y_train, X_test, Y_test
