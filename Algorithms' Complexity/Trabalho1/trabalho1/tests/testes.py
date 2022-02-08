
import sys
sys.path.append('C:\\Users\\Gustavo\\Anaconda3_32bits\\Lib\\site-packages')


from numba import jit, cuda
# conda install numba
# conda install -c sklam cudatoolkit

@jit(nopython=True)
def pow(a,b):
	print(range(0,100000))



pow(2,40)