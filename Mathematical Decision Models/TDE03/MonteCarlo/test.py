

a = "("

for i in range(7, 22):

	a += f"$C${i}"

	if i < 21:

		a +=" + "

print(a+")")