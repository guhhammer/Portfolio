#  Questões TDE04 em python

# a)
def a(m, n):
    if(n == 0):
        return 0
    else:
        return m + a(m, n-1)

# b)
def b(n):
    if(n == 1):
        return 1
    elif(n == 2):
        return 2
    else:
        return b(n-1)+b(n-2)

# c)
def c(a,r,n):
    if(n == 0):
        return a
    else:
        return a*(r**n) + a*(r**(n-1))

# d)
def d(r, s, W):
    try:
        return d(str(W[s])+r,s+1,W)
    except:
        return r

# e)
def e(n, s, W):
    if(n == W):
        return s
    else:
        return e(n+str(W[s]), s+1, W)

# f)
def f(n):
    if(n == 0):
        return 1
    else:
        return n * f(n-1)

# g)
def g(n):
    if(n == 0):
        return 1
    else:
        return g(n-1) + (n-1)

# f)
def h(n):
    if(n == 1):
        return "p "
    elif( n%2 == 0):
        return h(n-1) + (" - "+str((n-1))+"*q")
    else:
        return h(n-1) + (" + "+str((n-1))+"*q")

# i)
def i(per):
    if(per == 0):
        return 50000
    else:
        return 4*i(per-1)

# j)
def j(n):
    if(n == 1):
        return 3
    elif(n == 2):
        return 5
    else:
        return (n-1)*j(n-1) + (n-2)*j(n-2)

# Prints:

m, n = 5,4
print("\na) multiplicação de m e n: \n")
print("\tM * N  = ",m," * ",n," = ",a(5,4),"\n")

# Obs.:  A Sequência começa em 0
bx = 4
print("b)  b(x) na sequência de fibonacci: \n")
print("\t b(",bx,") = ",b(bx),"\n")

ca,cr,cn = 1,2,7
print("c) soma da progressão geométrica: \n")
print("\tSomatório_P.G.(a : ",ca,", r : ",cr,", n : ",cn,") = ", c(ca,cr,cn),"\n")

ss = "peixe"
print("d) o reverso de uma string w: \n")
print("\tReverso de ",ss," : ", d("",0,ss))

es = "Olá"
print("e) comprimento de string: \n")
print("\tlen(",es,") = ",e("",0,es),"\n")

fx = 5
print("f)  fatorial de n!, para n >= 0: \n")
print("\tf(",fx,") = ", f(fx),"\n")

gx = 5
print("g) g(x) para a sequência: 1,2,4,7,11,16 ... \n")
print("\tg(",gx,") = ",g(gx),"\n")

hx = 5
print("h) S(x) = {p, p-q, p+q, p-2q, p+2q, p-3q, ...}: ")
print("\tS(",hx,") = ",h(hx),"\n")

per,acc = 0,0
print("i) A partir de que período: pop. > 3200000?\n ")
while( per <= 3200000):
    per = per + i(acc)
    acc += 1
print("\tA partir do ",acc," período. \n")

print("\nj) 5 primeiros exemplos:\n")
for i in range(1,6):
    print("\t j(",i,") = ",j(i))

