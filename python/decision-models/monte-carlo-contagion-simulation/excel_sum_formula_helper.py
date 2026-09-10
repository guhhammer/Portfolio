"""Builds the Excel formula that sums cells C7 to C21 (one term per simulated
day) for the Monte Carlo spreadsheet, so it did not have to be typed by hand.
"""

a = "("

for i in range(7, 22):

    a += f"$C${i}"

    if i < 21:

        a += " + "

print(a + ")")
