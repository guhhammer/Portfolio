
import os, xlwt
from xlwt import Workbook

def acquire(filename):

	values = []
	with open(filename) as f:

		values = [_[:-1] for _ in f.readlines()[0].split("[BREAK]")[:-1]]

		for v in range(0, len(values)):

			values[v] = [int(_) for _ in values[v].split(" ") if _ != '']

	return values

values_crescent = acquire("jtxt/crescent.txt")
values_decrescent = acquire("jtxt/decrescent.txt")
values_random = acquire("jtxt/random.txt")

sizes = [1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000, 1000000, 2000000, 5000000, 
		10000000, 20000000, 30000000, 40000000, 50000000, 60000000, 70000000, 80000000]

print(values_crescent)
print(values_decrescent)
print(values_random)

wb = Workbook()


def plot(values, sheetName):

	global wb, sizes

	sheet = wb.add_sheet(sheetName)

	header = ['RQ', 'RRQ', 'RM', 'RRM', 'RS', 'RRS']
	dsc = ['Recursive Quicksort', 'Random Recursive Quicksort', 
			  'Recursive Mergesort', 'Random Recursive Mergesort', 
			  'Recursive Selectionsort', 'Random Recursive Selectionsort']

	for i in range(-1, len(values)):
		
		if i == -1:

			sheet.write(4, 2, 'n_elems')

		else:

			sheet.write(5+i, 2, sizes[i])

		for j in range(0, 6):

			if i == -1:

				sheet.write(4, 3+j, header[j])

			else:

				sheet.write(5+i, 3+j, values[i][j])

	for k in range(0, 6):

		sheet.write(8+k, 10, header[k])
		sheet.write(8+k, 11, dsc[k])

plot(values_crescent, 'crescent')
plot(values_decrescent, 'decrescent')
plot(values_random, 'random')

wb.save('results.xls')

# https://www.geeksforgeeks.org/writing-excel-sheet-using-python/

# Recursive Quicksort | Random Recursive Quicksort | Recursive Mergesort | Random Recursive Mergesort | Recursive Selectionsort | Random Recursive Selectionsort 
# rq | rrq | rm | rrm | rs | rss


