"""Pigeonhole principle, permutations and combinations (PUCPR, 2019-05-17).

Class activity followed by the exercise list. Every answer is a small
function so the numbers can be recomputed; textual answers are returned as
strings.

Author: Gustavo Hammerschmidt
"""


# helper: factorial
def factorial(n):
    if n == 0:
        return 1
    else:
        a = 1
        for i in range(1, n + 1):
            a *= i
        return a

"""
Exercises:
    a) How many people must be in a group to guarantee that two of them
       share a birthday (ignore the year)?
    b) Prove that if 4 numbers are chosen from the set {1, 2, 3, 4, 5, 6},
       at least one pair must add up to 7.
    c) If 12 cards are drawn from a standard deck, can we state that two
       of them have the same value, regardless of suit?

"""
def pigeonhole_a():
    return ("366 people are needed: a year has 365 days, so if every person in the group\n"
            "were born on a different day, person number 366 would be born on the same day\n"
            "as someone else in the group. \n")

def pigeonhole_b():
    return ("{(number, its_match)} -> { (1,a), (2,b), (3,c), (4,c), (5,b), (6,a) }\n"
            " At least 4 numbers are needed to get a match: we could draw one a, one b\n"
            " and one c, and the fourth number necessarily shares its match with one \n"
            "of the other 3 numbers selected.\n")

def pigeonhole_c():
    return ("No, because each suit has 13 cards. To be certain of having two cards with the\n"
            "same value, regardless of suit, 14 cards are needed \n"
            "(pigeonhole principle).\n\n")

"""

Exercises:
    a) How many three-letter words (not necessarily meaningful) can be
       formed with the letters of the word "compilar", if letters cannot
       be repeated?
    b) How many distinct permutations of the word TESTE exist?
    c) In how many ways can the first, second and third prizes of a pie
       contest be assigned to 15 contestants?
    d) In how many ways can six people sit in a room with six chairs?

"""
def permutation_a(letters, size):
    return (factorial(letters) / (factorial(letters - size) * 1.0))

# TESTE has two repeated letters, T and E (each appears twice)
def permutation_b():
    return factorial(len("TESTE")) / (factorial(2) * factorial(2) * 1.0)

# first, second and third ->   15 * 14 * 13
def permutation_c(contestants):
    return factorial(contestants) / (factorial(contestants - 3) * 1.0)

def permutation_d(n):
    return factorial(n) / (factorial(n - n) * 1.0)

"""
    Exercises:
        a) In how many ways can a committee of three people be chosen from
           a group of 12?
        b) Quality control wants to test 25 microprocessor chips out of the
           300 produced daily. In how many ways can that be done?
        c) In how many ways can a jury of five men and seven women be
           selected from a pool of 17 men and 23 women? (hint: combine with
           the product rule)
"""
def combination_a(people, group):
    return factorial(people) / (factorial(group) * factorial(people - group)) * 1.0

def combination_b(total, amount):
    a = 1
    b = 1
    for i in range(total - amount, total + 1):
        a *= i

    for i in range(1, amount + 1):
        b *= i

    return a / b


def combination_c(total_women, group_women, total_men, group_men):
    return combination_b(total_women, group_women) * combination_b(total_men, group_men)

"""
    Exercise list:
        1) A family has 12 children.
            a) Prove that at least two children were born on the same day
               of the week.
            b) Prove that at least two family members (including mother
               and father) were born in the same month.
            c) Assuming the house has 4 bedrooms for the children, show
               that at least 3 children sleep in at least one of the rooms.
        2) A game studio has 500 employees. Show that at least two of them
           were born on the same day of the year.
        3) A forest has 800,000 trees. No tree has more than 600,000
           leaves. Show that at least two trees have the same number of
           leaves.

        4) Ana, Paula, Carlos, João and Alessandro want to take a photo in
           which three of the five friends are lined up. How many different
           photos are possible?
        5) In how many ways can you choose a president, a secretary and a
           treasurer for a club from 12 candidates, if every candidate is
           eligible for every position but no candidate can hold 2
           positions? (hint: the hierarchy of the roles matters).
        6) In how many ways can you arrange 5 mathematics books on a
           shelf?
        7) A committee of eight students must be selected from a class of
           19 freshmen and 34 seniors.
            a) In how many ways can three freshmen and five seniors be
               selected?
            b) In how many ways can committees with exactly one freshman
               be selected?
            c) In how many ways can committees with at most one freshman
               be selected?
            d) In how many ways can committees with at least one freshman
               be selected?
        8) Of a company's staff, seven work in design, 14 in production,
           four in testing, five in sales, two in accounting and three in
           marketing. A six-person committee must be formed for a meeting
           with the supervisor.
            a) In how many ways can the committee be formed if it must
               have one member from each department?
        9) A computer network has 60 nodes. In how many ways can one or
           two nodes fail?
"""
def exercise_1a():
    return "In a family with 12 children, at least 2 children were born on the same weekday,\nsince a week has 7 days.\n\n"

def exercise_1b():
    return ("In a family with 12 children, including the father and mother, there are 14\n "
            "people. In that group at least two of them were born in the same month,\n"
            " since a year has 12 months\n\n")

def exercise_1c():
    return "In a family with 12 children and 4 bedrooms, (12/4 -> 3) at least 3 children sleep in one of the rooms.\n\n"

def exercise_2():
    return ("In a studio with 500 employees, ((500/365) > 1 -> true) at least\n"
            " 2 employees have their birthday on the same day.\n\n")


def exercise_3():
    return ("In a forest with 800,000 trees - none with more than 600,000 leaves -,\n"
            "\ttrees have from 0 to 600,000 leaves. Since there are more trees than\n"
            "\tpossible leaf counts, at least two trees have the same number of leaves, by the\n"
            "\tpigeonhole principle.\n\n")



def exercise_4(friends, in_photo):
    return factorial(friends) / (factorial(in_photo) * factorial(friends - in_photo)) * 1.0

def exercise_5(members, elected):
    return factorial(members) / (factorial(members - elected) * 1.0)

def exercise_6(books):
    return factorial(books) / (factorial(books - books) * 1.0)

def comb(n, r):
    return factorial(n) / (factorial(r) * factorial(n - r) * 1.0)

# 3 freshmen and 5 seniors.
def exercise_7a(freshmen, seniors, n_freshmen, n_seniors):
    return (comb(freshmen, n_freshmen) * comb(seniors, n_seniors))

def exercise_7b(freshmen, seniors, n_freshmen, n_seniors):
    return int(comb(freshmen, n_freshmen) * comb(seniors, n_seniors))

def exercise_7c(freshmen, seniors, n_freshmen, n_seniors):
    return exercise_7b(freshmen, seniors, n_freshmen, n_seniors) + comb(seniors, n_freshmen + n_seniors)

def exercise_7d(freshmen, seniors, committee):
    return comb(freshmen + seniors, committee) - comb(seniors, committee)

# 7 -> design, 14 -> production, 4 -> testing, 5 -> sales,
# 2 -> accounting and 3 -> marketing. 6 -> committee
def exercise_8a(design, production, testing, sales, accounting, marketing):
    return design * production * testing * sales * accounting * marketing

# 60!/1!*59! -> 60; 60!/2!*58! -> 60*59/2
def exercise_9():
    return 60 * (60 * 59 / 2)

# ------------------------------------------------------------------------
print("\n\nPigeonhole exercise a:\n\t", pigeonhole_a())
print("\nPigeonhole exercise b:\n\t", pigeonhole_b())
print("\nPigeonhole exercise c:\n\t", pigeonhole_c())
print("\n\nPermutation exercise a:\n\t", permutation_a(8, 3), " ways.\n")
print("\nPermutation exercise b:\n\t", permutation_b(), " ways.\n")
print("\n\nPermutation exercise c:\n\t", permutation_c(15), " ways.\n")
print("\nPermutation exercise d:\n\t", permutation_d(6), " ways.\n\n")
print("\n\nCombination exercise a:\n\t", combination_a(12, 3), " ways.\n")
print("\nCombination exercise b:\n\t", combination_b(300, 25), " ways.\n")
print("\nCombination exercise c:\n\t", combination_c(23, 7, 17, 5), " ways.\n\n")

# Exercise list:
print("\n\nQuestion 1a: \n", exercise_1a())
print("\n\nQuestion 1b: \n", exercise_1b())
print("\n\nQuestion 1c: \n", exercise_1c())
print("\n\nQuestion 2: \n", exercise_2())
print("\n\nQuestion 3:\n\t", exercise_3())
print("\n\nQuestion 4:\n\t", exercise_4(5, 3), " different photos are possible.\n\n")
print("\n\nQuestion 5:\n\t", exercise_5(12, 3), " ways.\n\n")
print("\n\nQuestion 6:\n\t", exercise_6(5), " ways.\n\n")
print("\n\nQuestion 7a:\n\t", exercise_7a(19, 34, 3, 5), " ways.\n\n")
print("\n\nQuestion 7b:\n\t", exercise_7b(19, 34, 1, 7), " ways.\n\n")
print("\n\nQuestion 7c:\n\t", exercise_7c(19, 34, 1, 7), " ways.\n\n")
print("\n\nQuestion 7d:\n\t", exercise_7d(19, 34, 8), " ways.\n\n")
print("\n\nQuestion 8:\n\t", exercise_8a(7, 14, 4, 5, 2, 3), " ways.\n\n")
print("\n\nQuestion 9:\n\t", exercise_9(), " ways.\n\n")
