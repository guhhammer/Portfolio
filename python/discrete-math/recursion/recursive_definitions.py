"""Recursive definitions - assignment 04 (Discrete Problem Solving, PUCPR 2019).

Each function implements one recursively defined operation or sequence from
the assignment (the statements are in assignment-04-recursion.docx).
"""

# a) multiplication of m and n by repeated addition
def a(m, n):
    if(n == 0):
        return 0
    else:
        return m + a(m, n-1)

# b) Fibonacci-like sequence starting with 1, 2
def b(n):
    if(n == 1):
        return 1
    elif(n == 2):
        return 2
    else:
        return b(n-1)+b(n-2)

# c) sum of a geometric progression (first term a, ratio r, n terms)
def c(a,r,n):
    if(n == 0):
        return a
    else:
        return a*(r**n) + a*(r**(n-1))

# d) reverse of a string W
def d(r, s, W):
    try:
        return d(str(W[s])+r,s+1,W)
    except:
        return r

# e) length of a string W
def e(n, s, W):
    if(n == W):
        return s
    else:
        return e(n+str(W[s]), s+1, W)

# f) factorial of n, n >= 0
def f(n):
    if(n == 0):
        return 1
    else:
        return n * f(n-1)

# g) the sequence 1, 2, 4, 7, 11, 16, ...
def g(n):
    if(n == 0):
        return 1
    else:
        return g(n-1) + (n-1)

# h) S(n) = {p, p - q, p + q, p - 2q, p + 2q, p - 3q, ...}
def h(n):
    if(n == 1):
        return "p "
    elif( n%2 == 0):
        return h(n-1) + (" - "+str((n-1))+"*q")
    else:
        return h(n-1) + (" + "+str((n-1))+"*q")

# i) a population that quadruples every period, starting at 50,000
def i(per):
    if(per == 0):
        return 50000
    else:
        return 4*i(per-1)

# j) j(n) = (n - 1) j(n - 1) + (n - 2) j(n - 2), with j(1) = 3 and j(2) = 5
def j(n):
    if(n == 1):
        return 3
    elif(n == 2):
        return 5
    else:
        return (n-1)*j(n-1) + (n-2)*j(n-2)

# Output:

m, n = 5,4
print("\na) multiplication of m and n: \n")
print("\tM * N  = ",m," * ",n," = ",a(5,4),"\n")

# Note: the sequence starts at 0
bx = 4
print("b)  b(x) in the Fibonacci sequence: \n")
print("\t b(",bx,") = ",b(bx),"\n")

ca,cr,cn = 1,2,7
print("c) sum of the geometric progression: \n")
print("\tSum_G.P.(a : ",ca,", r : ",cr,", n : ",cn,") = ", c(ca,cr,cn),"\n")

ss = "fish"
print("d) the reverse of a string w: \n")
print("\tReverse of ",ss," : ", d("",0,ss))

es = "Hello"
print("e) string length: \n")
print("\tlen(",es,") = ",e("",0,es),"\n")

fx = 5
print("f)  factorial n!, for n >= 0: \n")
print("\tf(",fx,") = ", f(fx),"\n")

gx = 5
print("g) g(x) for the sequence: 1,2,4,7,11,16 ... \n")
print("\tg(",gx,") = ",g(gx),"\n")

hx = 5
print("h) S(x) = {p, p-q, p+q, p-2q, p+2q, p-3q, ...}: ")
print("\tS(",hx,") = ",h(hx),"\n")

per,acc = 0,0
print("i) From which period on is the population > 3,200,000?\n ")
while( per <= 3200000):
    per = per + i(acc)
    acc += 1
print("\tFrom period ",acc," on. \n")

print("\nj) first 5 values:\n")
for k in range(1,6):
    print("\t j(",k,") = ",j(k))
