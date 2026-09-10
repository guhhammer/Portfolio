# Lazy and eager evaluation

*Essay written for the Functional Programming course at PUCPR (2019); translated from Portuguese. The Haskell examples are also in [`../src/lazy-quicksort.hs`](../src/lazy-quicksort.hs).*

## What are lazy and eager evaluation?

> **Eager evaluation** is the evaluation strategy used by most traditional programming languages; it is also called **strict evaluation**. In eager evaluation a function is evaluated as soon as it is bound to its arguments. Imperative languages, where the order of execution is implicitly defined by the structure of the source code, almost always use it.
>
> An advantage is that there is no need to keep references to unevaluated expressions, and the programmer can easily dictate the order of execution.
>
> A disadvantage is that it forces the evaluation of expressions that may not be needed at run time, or delays the evaluation of expressions that are needed sooner. It also forces the developer to organise the source code around the order of execution.

> **Lazy evaluation**, or call by need, is a strategy in which an expression is not evaluated until its first use: evaluation is deferred until demanded. It is more about expressiveness, since it delays the computation of a value; it is built from lambdas (anonymous functions).
>
> Some advantages: it makes it possible to add new constructs to a language without macros; it can improve performance by avoiding unnecessary evaluation; it can help resolve circular dependencies; and it gives access to infinite data structures.
>
> Some disadvantages: bugs can be harder to find, because the programmer does not control the execution; space complexity can grow, since pending operations must be stored; and it is harder to code than the conventional approach.

## Examples

### Julia

Lazy (contrast):

```julia
# head of a list:
function head(list)
    return list[1]
end

#=
  In Julia every function is evaluated eagerly, so head is not lazy: the
  whole list is evaluated before a value is returned. The Haskell
  equivalent below is lazy because it takes only the head and leaves the
  tail suspended.

  gethead :: [Integer] -> Integer
  gethead (x:y) = x
=#

list = [1,2,3,4,5,7]
print("Head of ", list, ": ", head(list))
```

Eager:

```julia
# evaluates the whole vector first, then sums it
sum_1_to_n(n) = reduce(+, [i for i in 1:n])

# g multiplies two parameters; expressions built from it
g = (a,b) -> a * b
triple = x -> g(x,3)
cube = x -> g(x,g(x,x))

#=
  g computes its result as soon as the variables are bound, i.e. it is
  evaluated eagerly; triple and cube are eager too.
=#

value = 5
print("Sum from 1 to ", value, ": ", sum_1_to_n(value), "\n")
print("Triple of 2 => ", triple(2), ".\n")
print("Cube of 4 => ", cube(4), ".\n")
```

### Python

Lazy:

```python
# even numbers from 0 to 100:
lazy_evens = range(0, 100, 2)

"""
    The sequence is not evaluated in full: range is lazy, so it returns an
    expression instead of a computed list, and each value is produced when
    it is first used.
"""

print("Unevaluated expression (lazy evaluation): {}.\n".format(lazy_evens))
print("Value returned (index {}): {}.\n".format(3, lazy_evens[3]))
```

Eager:

```python
# odd numbers from 1 to 20:
eager_odds = list(range(1, 20, 2))

"""
    Here the sequence is evaluated in full: list forces range to compute
    every element before print receives a value instead of an expression.
"""

print("Evaluated expression (eager evaluation): {}.\n".format(eager_odds))
print("Value returned (index {}): {}.\n".format(2, eager_odds[2]))
```

### Haskell

Lazy:

```haskell
-- quicksort of a list:
quicksort [] = []
quicksort (x:y) = quicksort (filter (< x) y) ++ [x] ++ quicksort (filter (>= x) y)

-- only the first (smallest) element of the sorted list:
smallest list = head (quicksort list)

{-
   Thanks to lazy evaluation only the head of the list is computed:
   quicksort takes the first element, filters the ones smaller than it and
   recurses on that part only; the rest of the list is never sorted, which
   makes the program faster.
-}

> quicksort [41,5,23,67,34,12,56,90,22]      -- input
[5,12,22,23,34,41,56,67,90]                  -- output

> smallest [32,49,40,45,10,23]               -- input
10                                           -- output
```

Eager:

```haskell
-- sums the elements of a list with an accumulator:
sumAcc :: [Integer] -> Integer -> Integer
sumAcc [] acc = acc
sumAcc (x:y) acc = sumAcc y (acc+x)

sumList :: [Integer] -> Integer
sumList list = sumAcc list 0

{-
  sumList uses eager evaluation: it calls sumAcc with the accumulator set
  to 0, and at every recursive call the head of the list is added to the
  accumulator, forcing (acc + x) to be evaluated immediately (strictly).
-}

> sumList [41,5,23,67,34,12,56,90,22]       -- input
350                                         -- output
```
