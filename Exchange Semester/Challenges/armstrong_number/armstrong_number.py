
armstrong_number = lambda number, base:  number == int(sum([int(i)**base for i in list(str(number))]))

print(armstrong_number(153,3))
