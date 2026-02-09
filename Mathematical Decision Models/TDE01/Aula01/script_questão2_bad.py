


def lucro(x1, x2, x3, x4, x5, x6):
	return 275*x1 + 250*x2 + 350*x3 + 250*x4 + 350*x5 + 150*x6


def fabric2(x1, x2, x3):
	return ((x1/4000 + x2/3000 + x3/2000) <= 1)

def fabric3(x4, x5):
	return (((x4/3000 + x5/8000)) <= 1)


def check(x1, x2, x3, x4, x5, x6):

	if (x1+x4+x6) <= 800 and x6 <= 500 and 800 <= x3 and x3 <= 2300 and fabric2(x1,x2,x3) and fabric3(x4,x5): 
		return True

	return False


def matcher(lima, limb):
	return [x for x in range(0, (limb-lima))]



f2a, f2b, f2c = matcher(0, 4000-3000), matcher(0, 3000-2400), matcher(0, 2000-1000)
f3a, f3b = matcher(0, 3000-2000), matcher(0, 8000-5000)

fi = matcher(0+400, 500)

spectrum = []

print("II")

counter = 0
for a in f2a:
	for b in f2b:
		for c in f2c:
			for d in f3a:
				for e in f3b:
					for f in fi:
						if check(a,b,c,d,e,f):

							print(len(spectrum))

							spectrum.append([a,b,c,d,e,f])
					
					counter += 500
	
				if counter == 1000000:
					break

print("III")


prit(len(spectrum))
print("IIII")


for i in valid:
	print("Lucro: ", lucro(i), " || keys: ", i)


