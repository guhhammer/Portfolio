"""Abandoned brute-force attempt at the Motorauto production-plan problem
(exercise 2 of list 1, see motorauto-production-plan.md).

It enumerates candidate production quantities for the six decision variables
and keeps the feasible combinations, printing their profit. The search space
is far too large for this to finish in reasonable time, so the exercise was
solved as a proper linear programming model with Excel Solver instead. Kept
for the record, with its typos fixed so that it runs.
"""


def profit(x1, x2, x3, x4, x5, x6):
    return 275 * x1 + 250 * x2 + 350 * x3 + 250 * x4 + 350 * x5 + 150 * x6


def plant2_capacity(x1, x2, x3):
    return ((x1 / 4000 + x2 / 3000 + x3 / 2000) <= 1)


def plant3_capacity(x4, x5):
    return (((x4 / 3000 + x5 / 8000)) <= 1)


def feasible(x1, x2, x3, x4, x5, x6):

    if (x1 + x4 + x6) <= 800 and x6 <= 500 and 800 <= x3 and x3 <= 2300 and plant2_capacity(x1, x2, x3) and plant3_capacity(x4, x5):
        return True

    return False


def value_range(lower, upper):
    return [x for x in range(0, (upper - lower))]


f2a, f2b, f2c = value_range(0, 4000 - 3000), value_range(0, 3000 - 2400), value_range(0, 2000 - 1000)
f3a, f3b = value_range(0, 3000 - 2000), value_range(0, 8000 - 5000)

fi = value_range(0 + 400, 500)

spectrum = []

print("II")

counter = 0
for a in f2a:
    for b in f2b:
        for c in f2c:
            for d in f3a:
                for e in f3b:
                    for f in fi:
                        if feasible(a, b, c, d, e, f):

                            print(len(spectrum))

                            spectrum.append([a, b, c, d, e, f])

                    counter += 500

                if counter == 1000000:
                    break

print("III")


print(len(spectrum))
print("IIII")


for i in spectrum:
    print("Profit: ", profit(*i), " || keys: ", i)
