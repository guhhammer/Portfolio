"""AHP (Analytic Hierarchy Process) pairwise-comparison normalisation.

Used to produce the tables of the YouTube-channel project (paper.tex): for
each criterion, the 6x6 matrix of Saaty-scale comparisons between the six
alternatives is normalised column by column, the row means give the priority
vector, and the rows are printed as LaTeX table lines. The criteria-vs-criteria
matrix (cxc) is treated the same way, and the final ranking multiplies the
alternative priorities by the criterion weights.

Matrices c1 and c2 are given as numbers; c3, c4, c5 and cxc were pasted from
the LaTeX tables and are parsed by mix().
"""

all_means = []


def sumOfRow(arr):
    # column sums (one per alternative)
    rowSum = []

    for j in range(len(arr[0])):

        rs = 0

        for i in range(len(arr)):

            rs += arr[i][j]

        rowSum.append(rs)

    return rowSum



def divv(arr, arr2):
    # element-wise division of a row by the column sums
    ret = []
    for i in range(len(arr)):

        ret.append((arr[i] / arr2[i]))

    return ret


def mean(arr):

    return (sum(arr) / 6)


def maker(arr, cc=False):
    # normalises the matrix, appends the row mean (priority) and prints LaTeX rows;
    # cc=True for the criteria-vs-criteria matrix (5 columns instead of 6)
    sums = sumOfRow(arr)

    normals = []
    for row in range(len(arr)):


        normals.append(divv(arr[row], sums))

        k = divv(arr[row], sums)


        normals[row].append(mean(k))



    means_arr = []

    for i in range(len(normals)):

        if not cc:
            print("A%d & %.4f & %.4f & %.4f & %.4f & %.4f & %.4f & %.4f \\\\ \\hline" % ((i+1), normals[i][0], normals[i][1], normals[i][2],
                normals[i][3], normals[i][4], normals[i][5], normals[i][6]))

            means_arr.append(normals[i][6])

        else:

            print("C%d & %.4f & %.4f & %.4f & %.4f & %.4f & %.4f \\\\ \\hline" % ((i+1), normals[i][0], normals[i][1], normals[i][2],
                normals[i][3], normals[i][4], normals[i][5]))

            means_arr.append(normals[i][5])

    all_means.append(means_arr)


def formatMeans(mns):
    # one row per alternative with its priority under each criterion
    for i in range(len(all_means)):

        print("A%d & %.4f & %.4f & %.4f & %.4f & %.4f & %.4f \\\\ \\hline" % ((i+1), all_means[i][0], all_means[i][1], all_means[i][2],
            all_means[i][3], all_means[i][4], all_means[i][5]))


def last_comparison(table):
    # weights the alternative priorities by the criterion weights
    mean_multi = [0.0309, 0.2206, 0.1548, 0.0480, 0.3790]

    labels = []

    for i in range(5):

        for j in range(6):

            table[i][j] = mean_multi[i] * table[i][j]

    print(table)

    values = []
    for j in range(6):

        k = 0
        for i in range(5):

            k += table[i][j]

        values.append(k)

    return values


def last_table(arr):

    str_ = ""
    k = 0
    for i in arr:

        str_ += f"A{k+1} & {round(i, 4)} \\\\ \\hline\n"
        k += 1

    return str_


c1 = [


 [1, 5, 1/5, 1/7, 1/7, 1/8],
 [1/5, 1, 1/3, 6, 1/7, 1/3],
 [5,3,1,3,1/4,1/4],
 [7,1/6,1/3,1,1/3,1/2],
 [7,7,4,3,1,1/2],
 [8,3,4,2,2,1]

]

c2 = [


[1, 6, 8, 2, 3, 6],

    [1/6, 1, 1/2, 1/2, 1/4, 1/8 ],

 [1/8, 2, 1, 6, 1/3, 1/4 ],

 [1/2, 2, 1/6, 1, 1/7, 1/7 ],

 [3, 4, 3, 7, 1, 4],

 [6, 8, 4, 7, 1/4, 1 ]


]


c3 = [
r"C3 & A1 & A2 & A3 & A4 & A5 & A6 & \\ \hline",

r"A1 & 1 & 6 & 1/8 & 1/6 & 1/9 & 1/7 & \\ \hline",

r"A2 & 1/6 & 1 & 2 & 4 & 1/3 & 1/8 & \\ \hline",

r"A3 & 8 & 1/2 & 1 & 1/2 & 1/3 & 1/3 & \\ \hline",

r"A4 & 6 & 1/4 & 2 & 1 & 1/3 & 1/5 & \\ \hline",

r"A5 & 9 & 3 & 3 & 3 & 1 & 1/7 & \\ \hline",

r" A6 & 7 & 8 & 3 & 5 & 7 & 1 & \\ \hline",
]




def mix(arr, slicef=[1,7]):
    # parses LaTeX table rows ("A1 & 1 & 1/6 & ...") into a numeric matrix
    arr = [ i.split(' & ') for i in arr ]

    arr = [ arr[i][slicef[0]:slicef[1]] for i in range(len(arr)) if i > 0]

    arr = [ [ float(j[0])/float(j[2]) if '/' in j else float(j) for j in i ] for i in arr]

    return arr


c4 = [
 r"C4 & A1 & A2 & A3 & A4 & A5 & A6 & \\ \hline",
 r"A1 & 1 & 1/5 & 1/4 & 8 & 1/9 & 1/3 & \\ \hline",

r"A2 & 5 & 1 & 8 & 9 & 3 & 1/2 & \\ \hline",

    r"A3 & 4 & 1/8 & 1 & 1/3 & 1/3 & 1/3 & \\ \hline",

    r"A4 & 1/8 & 1/9 & 3 & 1 & 1/2 & 1/4 & \\ \hline",

    r"A5 & 9 & 1/3 & 3 & 2 & 1 & 1/2 & \\ \hline",

    r"A6 & 3 & 2 & 3 & 1/4 & 2 & 1 & \\ \hline"

]


c5 = [

r"C5 & A1 & A2 & A3 & A4 & A5 & A6 & \\ \hline",

    r"A1 & 1 & 2 & 3 & 6 & 1/3 & 1/2 & \\ \hline",

    r"A2 & 1/2 & 1 & 1/2 & 1/6 & 1/7 & 1/9 & \\ \hline",

    r"A3 & 1/3 & 2 & 1 & 1/7 & 1/7 & 1/8 & \\ \hline",

    r"A4 & 1/6 & 6 & 7 & 1 & 1/2 & 1/5 & \\ \hline",

    r"A5 & 3 & 7 & 7 & 2 & 1 & 1/5 & \\ \hline",

    r"A6 & 2 & 9 & 8 & 5 & 5 & 1 & \\ \hline"
]


print('\n\nc1\n')
maker(c1)
print('\n\nc2\n')
maker(c2)


print("\n\nc3\n")
maker(mix(c3))


print("\n\nc4\n")
maker(mix(c4))


print("\n\nc5\n")
maker(mix(c5))


print("\n\nmeans\n")
formatMeans(all_means)



cxc = [

r"x & C1 & C2 & C3 & C4 & C5 & \\ \hline",

    r"C1 & 1 & 1/9 & 1/7 & 1/2 & 1/7 & \\ \hline",

    r"C2 & 9 & 1 & 2 & 5 & 1/3 & \\ \hline",

    r"C3 & 7 & 1/2 & 1 & 4 & 1/3 & \\ \hline",

    r"C4 & 2 & 1/5 & 1/4 & 1 & 1/7 & \\ \hline",

    r"C5 & 7 & 3 & 3 & 7 & 1 & \\ \hline"

]


print('\n\ncxc\n')
maker(mix(cxc, slicef=[1,6]), cc=True)




s = last_table( last_comparison(all_means))


print("\n\n", s)
