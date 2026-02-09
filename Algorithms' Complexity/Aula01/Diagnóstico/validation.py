


for i in range(0, 20):

	su = 0
	for j in range(0, i+1):

		su += 2*j
		su -= 1
		
	print(su, (i+1)*(i-1), "n=",i)

	if (su != (i+1)*(i-1)):
		print("ERRO!!")