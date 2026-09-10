# Pure functions

*Essay written for the Functional Programming course at PUCPR (2019); translated from Portuguese. The Haskell examples are also in [`../src/pure-and-impure-functions.hs`](../src/pure-and-impure-functions.hs).*

## What are pure functions?

> **Pure functions** always return the same output for a given input: they have no **side effects** and do not change the state of the program. They are like coffee machines: beans go in, ground coffee comes out, end of story.

> Side effects appear in functions that, when executed, are affected by changes in the application state or depend on something external to them (global variables). Such functions are called **impure**.

> An impure function modifies its parameters; a pure one only *treats* them. In other words, a number given as a parameter will always be itself; what a pure function does is process that value to obtain an output.

## Examples of pure and impure functions

### Julia

Pure:

```julia
# a quadratic function:
f = n -> (n ^ 2) + 3*n - 12

#=
    f is pure because it depends only on its input x, so nothing that
    happens during the execution of the program can change its result.
=#

x = 3 # input of f
print("f(", x, ") = ", f(x), "\n")
```

Impure:

```julia
# variable: the number pi
pi = 3.1415

# volume of a sphere:
volume = radius -> (4/3) * pi * (radius ^ 3)

#=
    volume is not pure because it depends on a global variable, pi: its
    result changes whenever the global PI changes during the execution.
=#

radius = 4  # input of volume (in centimetres)
print("volume(", radius, ") = ", volume(radius), " cm³. \n")
```

Note: `#= =#` is a multi-line comment in Julia.

### Python

Pure:

```python
# predecessor of a number:
def predecessor(n):  return (lambda x: x-1)(n)

"""
    predecessor is pure because it depends on no global variable and is
    not affected by the execution of the program: a number s goes in and
    its predecessor comes out.
"""

s = 9  # input of predecessor
print("Predecessor of {}: {}".format(s, predecessor(s)), "\n")
```

Impure:

```python
food_tax = 1.01  # n times the value

# applies the tax to a list of prices:
def purchase_price(prices):
    return map(lambda x: x * food_tax, prices)

# total of the purchase:
def total(prices):
    s = 0
    for i in prices:
        s += i
    return s

"""
    purchase_price is impure because it depends on the food tax, which
    can vary: it reads the global variable food_tax, so it may not return
    the same output for the same input.
"""

# "variables" that do not change during the execution
banana = 2.30  # per kilo
flour = 1.20   # per kilo

#                (kilos * food)   <-  for example: (3 * banana)
shopping_list = [(2*banana), (3*flour)]  # input of purchase_price

print("Total: {}".format(total(purchase_price(shopping_list))))
```

### Haskell

Pure:

```haskell
-- sum of the even numbers from m to n:
sumEvens :: Int -> Int -> Int
sumEvens = \m n -> sum [x | x <- [m,m+2 .. n], mod x 2 == 0]

{-
    sumEvens is pure: it depends on no global variable and nothing in the
    execution of the program can change it. A start m and an end n go in,
    the sum of the even numbers between them comes out.
-}

> print (sumEvens 0 8)   -- input
20                       -- output
```

Impure:

```haskell
-- Position as a function of time: S = S0 + V0*t + (a*t^2)/2
-- with S0 = 0 and V0 = 0 it simplifies to S = (a*t^2)/2

a = 9.86  -- gravitational acceleration

-- height fallen by an object after t seconds:
altitude :: Float -> Float
altitude = \t -> a*(t^2)/2

{-
    altitude is impure because it depends on the local gravity, which can
    vary: it reads the global variable a, so it may not return the same
    output for the same input.
-}

> print (altitude 3)   -- input
44.37                  -- output
```
