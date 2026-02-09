
from itertools import combinations
from functools import reduce
import matplotlib.pyplot as plt, numpy as np, pickle, csv, sys, time, threading

sys.path.append('C:\\Users\\Gustavo\\Anaconda3_32bits\\Lib\\site-packages')


###################################################################################################
###################################################################################################

values = list(range(1,51))

combinations_a = list(combinations(values, 5))
combinations_b = list(combinations(values, 4))
combinations_c = list(combinations(values, 3))
combinations_d = list(combinations(values, 2))																				

###################################################################################################
###################################################################################################

sceneryC1, sceneryC2, sceneryC3 = None, None, None

over = False

database, database_header = [], []
db_backtest, db_backtest_header = [], []

all_gains, apostas = {}, {}

###################################################################################################
###################################################################################################


###################################################################################################
###################################################################################################

from numba import jit, cuda, stencil, njit
from numba import int32, float32    
from numba.experimental import jitclass

import warnings

warnings.filterwarnings('ignore')

