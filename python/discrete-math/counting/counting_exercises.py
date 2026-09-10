"""Counting exercises - Discrete Problem Solving course (PUCPR, May 2019).

Every exercise keeps its statement as a docstring, followed by the reasoning
and a small function that computes the answer. The decision trees for the
coin-toss, election and play-off exercises are drawn in ASCII.

Author: Gustavo Hammerschmidt
"""

print("\n\n NAME: Gustavo Hammerschmidt. ")
print("\n\n All exercises from the slides preceding the 'Counting exercises' list: \n\n")


# --------------------------------------------------------------------------------------------------
"""
1a) In a city, vehicle licence plates consist of two letters followed by
three digits. How many plates can exist in that city?
"""

# 1A)

# Total possibilities:  _ _  _ _ _   (Letter Letter Digit Digit Digit)

# (Letter Letter Digit Digit Digit) -> (26 26 10 10 10) -> 676,000 possibilities.

# Letters -> 26; Digits -> 10.

def possibilities_a(letters, digits):
    return letters * letters * digits * digits * digits


print("\n1A total possibilities -> ", possibilities_a(26, 10), "\n")
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
1b) A researcher needs to visit every Brazilian state capital for a
population study. In how many orders can he do that?
"""

# 1B)  27 factorial  (first, then second, then third ...) -> (1 * 2 * 3 * ... * 27) -> (27!)

#    27!   ->   1.088886945 * (10 ^ 28)   ->   10888869450418352160768000000 possibilities.

def possibilities_b(n):
    aux = 1
    for i in range(1, n + 1):
        aux *= i
    return aux


print("1B total possibilities -> ", possibilities_b(27), "\n")
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
1c) A password is built from the elements of a set of 8 special characters
and of the set of digits. The allowed format is: four digits followed by
2 special characters. How many passwords can be defined?
"""

# 1C)  Digit set (DS), 8 special characters (8)  ->  (DS DS DS DS 8 8)

#    (10 10 10 10 8 8)  ->  640,000 possibilities.

# Digit set -> DS; special characters -> SC

def possibilities_c(DS, SC):
    return DS * DS * DS * DS * SC * SC


print("1C total possibilities -> ", possibilities_c(10, 8), "\n")
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
2a) A student can choose a computing project from four lists. The four
lists contain 17, 28, 7 and 20 possible projects respectively. No project
appears in more than one list. How many projects are there to choose from?
"""

# sum rule -> 2A and 2B
def total(values):
    aux = 0
    for i in values:
        aux += i
    return aux

print("Possibilities 2A -> ", total([17, 28, 7, 20]), " possibilities. \n")
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
2b) A restaurant offers 18 meat dishes, 10 fish dishes and 5 vegetarian
dishes. How many dishes can be chosen?
"""

print("Possibilities 2B -> ", total([18, 10, 5]), " possibilities. \n")
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
3a) Lana wants to go to Manaus. She can choose between 3 bus services or 2
flights from home to Cuiabá. From there, she can choose between 2 bus
services or 3 flights to Manaus. In how many ways can Lana reach Manaus?
"""

# 3A

def prod(A, B):
    return A * B

print("Possibilities 3A -> ", total([prod(3, 2), prod(3, 3), prod(2, 3), prod(2, 2)]), " possibilities. \n")
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
3b) Passwords in a system are strings of 6 to 8 characters.
- Each character is a letter or a digit.
- Each password must contain at least one digit.
- How many passwords are possible?
"""

# 3B
def product(values):
    aux = 1
    for i in values:
        aux *= i
    return aux


print("Possibilities 3B -> ",
total([(product([36, 36, 36, 36, 36, 36]) - product([26, 26, 26, 26, 26, 26])),
       (product([36, 36, 36, 36, 36, 36, 36]) - product([26, 26, 26, 26, 26, 26, 26])),
       (product([36, 36, 36, 36, 36, 36, 36, 36]) - product([26, 26, 26, 26, 26, 26, 26, 26]))]), " possibilities. \n")
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
4a) How many integers between 1 and 1500 are divisible by 3 or by 5?
"""


print("Possibilities 4A (divisible by 3 or by 5) -> ", (1500/3 + 1500/5 - 1500/15), " possibilities. \n")
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
r"""

Inclusion-exclusion: definition
      - Number of ways to choose an element from A1 or from A2:
        |A1 (union) A2| = |A1| + |A2| - |A1 (intersection) A2|


a) Carlos is playing heads or tails. Each toss results in heads (H) or
tails (T). In how many ways can he toss the coin five times without getting
two consecutive heads?

Question A:


Tree with every possibility:
                                                    *
                           /                                                \
                          H                                                  T
                 /                      \                           /                      \
                H                        T                         H                        T
          /          \             /          \               /          \             /          \
         H            T           H            T             H            T           H            T
       /   \        /   \       /   \        /   \         /   \        /   \       /   \        /   \
      H     T      H     T     H     T      H     T       H     T      H     T     H     T      H     T
     / \   / \    / \   / \   / \   / \    / \   / \     / \   / \    / \   / \   / \   / \    / \   / \
    H   T H   T  H   T H   T H   T H   T  H   T H   T   H   T H   T  H   T H   T H   T H   T  H   T H   T


Tree with only the possibilities that respect the rule of the question:
                                                    *
                           /                                                \
                          H                                                  T
                                        \                           /                      \
                                         T                         H                        T
                                   /          \                          \             /          \
                                  H            T                          T           H            T
                                    \        /   \                      /   \           \        /   \
                                     T      H     T                    H     T           T      H     T
                                    / \      \   / \                    \   / \         / \      \   / \
                                   H   T      T H   T                    T H   T       H   T      T H   T

            Possibilities ->  HTHTH, HTHTT, HTTHT, HTTTH, HTTTT, THTHT, THTTH, THTTT, TTHTH, TTHTT, TTTHT, TTTTH, TTTTT
            Possibilities ->  13 possibilities.

"""
# --------------------------------------------------------------------------------------------------

print("\n\nCounting exercises (1 to 9): \n\n")

# --------------------------------------------------------------------------------------------------
"""
1) Suppose the last four digits of a phone number must include at least
one repeated digit. How many numbers of that kind exist?

"""

# 1 -> Phone number -> example: _ _ _ _ -> all numbers -> 10 ^ 4 | numbers without repetition -> 10*9*8*7
def ex1():
    print("\nNumber of phone numbers (exercise 1): ", (10**4 - 10*9*8*7), " numbers. \n")

ex1()
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
2) How many four-digit integers (numbers between 1000 and 9999) are even?
"""

# 2 -> between 1000 and 9999 that are even -> _ _ _ *  (* -> only 0, 2, 4, 6 or 8 (5 digits in total))
#      9999 - 1000 -> 8999 / 2 -> 4449 numbers.
def ex2():
    print("\nEven numbers between 1000 and 9999 (exercise 2): ", ((9999-1)-1000)/2, " numbers. \n")

ex2()
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
3) How many CPF numbers (the 11-digit Brazilian taxpayer id) are possible?

"""

# 3 -> How many CPF numbers are possible?   _ _ _ . _ _ _ . _ _ _ - _ _  -> 10 10 10 10 10 10 10 10 10 10 10 -> 10^11
def ex3():
    print("\nPossible CPF numbers (exercise 3): ", 10**11, " possibilities. \n")

ex3()
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
4) A computer game starts by making selections in each of three menus. The
first menu (number of players) has four options, the second (difficulty
level) has eight, and the third (speed) has six. With how many
configurations can the game be played?

"""

# 4 ->  first: 4, second: 8, third: 6
def ex4():
    print("\nNumber of configurations (exercise 4): ", 4*8*6, " configurations. \n")

ex4()
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
5) A variable name in BASIC must be either a single letter or a letter
followed by another letter or a digit. How many variables can be defined?
"""

# 5 -> either a letter, or a letter followed by a letter or digit  ->  26 + 26 * 36
def ex5():
    print("\nNumber of variables (exercise 5): ", 26+26*36, " variables.\n")

ex5()
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
6) What is the value of Counter after the execution of the following
program fragment? (two nested loops of 3 and 5 iterations)
"""

def ex6():
    print("Counter value after the loops (exercise 6):  counter = ", 3*5, ". \n")

ex6()
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
r"""
7) An election is held with green, white and blue slips of paper placed in
a hat. The slips are drawn one at a time, and the first colour to receive
two votes wins. Draw a decision tree to find the number of ways the vote
can turn out.
"""


r"""
B -> Blue, W -> White, G -> Green.

                                                 *
                  /                              |                               \
                 B                               W                                G
         /       |         \            /        |         \            /         |          \
        B        W          G          B         W          G          B          W           G
               / | \      / | \      / | \                / | \      / | \      / | \
              B  W  G    B  W  G    B  W  G              B  W  G    B  W  G    B  W  G
                   /|\     /|\           /|\            /|\           /|\     /|\
                  B W G   B W G         B W G          B W G         B W G   B W G

"""

# Number of ways the vote can turn out: 33 ways.

def ex7():
    print("\nNumber of ways the vote can turn out (exercise 7): 33 ways.")


ex7()
# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
r"""
8) Draw a decision tree (use teams A and B) to find the number of ways a
basketball play-off can go, where the winner is the first team to win four
games out of seven.


"""

r"""

Left side of the tree:

                                                                         *
                                                         /
                                                        A
                    /                                                                  \
                  A                                                                      B
         /                 \                                          /                                    \
       A                    B                                        A                                       B
     /   \          /                  \                     /                \                     /                 \
    A     B        A                    B                   A                  B                   A                    B
         / \      / \              /         \             / \            /         \         /         \            /    \
        A   B    A   B            A           B           A   B          A           B       A           B          A       B
           / \      / \          / \         / \             / \        / \         / \     / \         / \       /   \
          A   B    A   B        A   B       A   B           A   B      A   B       A   B   A   B       A   B     A     B
             / \      / \          / \     / \                 / \        / \     / \         / \     / \       / \
            A   B    A   B        A   B   A   B               A   B      A   B   A   B       A   B   A   B     A   B


Right side of the tree (not mirrored):

                                                                         *
                                                         /
                                                        B
                    /                                                                  \
                  B                                                                      A
         /                 \                                          /                                    \
       B                    A                                        B                                       A
     /   \          /                  \                     /                \                     /                 \
    B     A        B                    A                   B                  A                   B                    A
         / \      / \              /         \             / \            /         \         /         \            /    \
        B   A    B   A            B           A           B   A          B           A       B           A          B       A
           / \      / \          / \         / \             / \        / \         / \     / \         / \       /   \
          B   A    B   A        B   A       B   A           B   A      B   A       B   A   B   A       B   A     B     A
             / \      / \          / \     / \                 / \        / \     / \         / \     / \       / \
            B   A    B   A        B   A   B   A               B   A      B   A   B   A       B   A   B   A     B   A


"""


# Number of ways the series can be won: 70 ways.

def ex8():
    print("\nNumber of ways the series can be won (exercise 8): 70 ways.")

ex8()

# --------------------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------------------------
"""
9) Main computer addresses are of one of three types:
   - Class A: address with a 7-bit netid (netid != 17) and a 24-bit hostid.
   - Class B: address with a 14-bit netid and a 16-bit hostid.
   - Class C: address with a 21-bit netid and an 8-bit hostid.

   - Hostids that are all 0s or all 1s are not allowed.
   - How many valid IP addresses are there?

    Class A total IPs ->  (2 ** 7) * (2 ** 24)
    Class B total IPs ->  (2 ** 14) * (2 ** 16)
    Class C total IPs ->  (2 ** 21) * (2 ** 8)


    Hostids that are all 0s or all 1s are not allowed:
        Class A -> (000.000.000), (111.111.111)
        Class B -> (000.000), (111.111)
        Class C -> (000), (111)

    Class A total ->  (2 ** 7) * (2 ** 24)  - (2 ** 7) * 2
    Class B total ->  (2 ** 14) * (2 ** 16) - (2 ** 14) * 2
    Class C total ->  (2 ** 21) * (2 ** 8)  - (2 ** 21) * 2


"""
def ex9():
    print("\n(exercise 9)\n Class A total IPs: ", (2**7)*(2**24), " IPs. \n",
    "Class B total IPs: ", (2**14)*(2**16), " IPs. \n",
    "Class C total IPs: ", (2**21)*(2**8), " IPs. \n\n")
    print("(exercise 9)\n Class A total (IPs without all-0s or all-1s hostids): ", (2**7)*(2**24)-((2**7)*2), " IPs.\n",
    "Class B total (IPs without all-0s or all-1s hostids): ", (2**14)*(2**16)-((2**14)*2), " IPs. \n",
    "Class C total (IPs without all-0s or all-1s hostids): ", (2**21)*(2**8)-((2**21)*2), " IPs. \n\n")

ex9()
# --------------------------------------------------------------------------------------------------

#  Open question:
#           Why, when you divide 1500 / 3 for example, is the result the number of
#           integers up to 1500 that are divisible by 3 (what is the logic behind it)?
