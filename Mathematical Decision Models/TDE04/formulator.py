
a = [

	"K",
	"M",
	"O",
	"Q",
	"S",
	"U",
	"W",
	"Y",
	"AA",
	"AC",
	"AE",

]


def make(index, number, a):

	i = index
	l = number

	x = f"""=(SE((INDEXES!${i}{str(51+a)})="SIM";$K$3{l};$L$3{l})+SE((INDEXES!${i}{str(6+a)})="(+) Alta";$M$3{l};$N$3{l})+"""
	x += f"""SE((INDEXES!${i}{str(21+a)})="(+) Alta";$O$3{l};$P$3{l})+SE((INDEXES!${i}{str(66+a)})="SIM"; $Q$3{l};$R$3{l})+""" 
	x += f"""SE((INDEXES!${i}{str(36+a)})="POSITIVO"; $S$3{l};$T$3{l})+SE((INDEXES!$I{str(82+a)})="POSITIVO"; $U$3{l};$V$3{l})+SE($D{str(12+a)}>1,2; $W$3{l};$X$3{l}))"""

	return x 


for i in a:

	b = 0

	print(f"column {i}:")

	print(make(i, 2, 0))
	print(make(i, 2, 1))
	print(make(i, 2, 2))

	print(make(i, 3, 3))
	print(make(i, 3, 4))
	print(make(i, 3, 5))

	print(make(i, 4, 6))
	print(make(i, 4, 7))

	print(make(i, 5, 8))
	print(make(i, 5, 9))

	print("\n\n")


