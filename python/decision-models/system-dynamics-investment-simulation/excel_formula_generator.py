"""Generates the Excel scoring formulas of the investment-simulation workbook
(investment-simulation.xlsx).

Each asset class occupies a column (K, M, O, ... AE) and its score is the sum
of seven IF() tests on the INDEXES sheet: whether an indicator is flagged
("SIM" = yes), whether a trend is high ("(+) Alta"), whether a signal is
positive ("POSITIVO"), and whether a ratio exceeds 1.2. The formulas are
written for a Portuguese-locale Excel (SE = IF, ';' as argument separator,
',' as decimal separator), matching the workbook.
"""

columns = [

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


def make(index, number, offset):

    i = index
    l = number
    a = offset

    x = f"""=(SE((INDEXES!${i}{str(51+a)})="SIM";$K$3{l};$L$3{l})+SE((INDEXES!${i}{str(6+a)})="(+) Alta";$M$3{l};$N$3{l})+"""
    x += f"""SE((INDEXES!${i}{str(21+a)})="(+) Alta";$O$3{l};$P$3{l})+SE((INDEXES!${i}{str(66+a)})="SIM"; $Q$3{l};$R$3{l})+"""
    x += f"""SE((INDEXES!${i}{str(36+a)})="POSITIVO"; $S$3{l};$T$3{l})+SE((INDEXES!$I{str(82+a)})="POSITIVO"; $U$3{l};$V$3{l})+SE($D{str(12+a)}>1,2; $W$3{l};$X$3{l}))"""

    return x


for column in columns:

    print(f"column {column}:")

    print(make(column, 2, 0))
    print(make(column, 2, 1))
    print(make(column, 2, 2))

    print(make(column, 3, 3))
    print(make(column, 3, 4))
    print(make(column, 3, 5))

    print(make(column, 4, 6))
    print(make(column, 4, 7))

    print(make(column, 5, 8))
    print(make(column, 5, 9))

    print("\n\n")
