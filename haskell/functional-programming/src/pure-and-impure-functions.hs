-- Pure and impure functions: the code examples of the "pure functions" essay in ../notes.
-- Functional Programming course, PUCPR (2019).

y = 10

-- impure: depends on the global y
test :: Int -> Int
test = \x -> x*y

s = \x y -> x + y

-- pure: the sum of the even numbers from m to n depends only on its arguments
sumEvens :: Int -> Int -> Int
sumEvens = \m n -> sum [x | x <- [m,m+2 .. n], mod x 2 == 0]

-- About the formula used below:
{-
  Position as a function of time:
      - S = S0 + V0*t + (a*(t^2))/2
      S  -> final position: output in metres
      S0 -> initial position: 0 metres
      V0 -> initial velocity: 0 m/s
      t  -> time: input in seconds
      a  -> gravitational acceleration

      - Algebraic simplification:
          - S = S0 + V0*t + (a*(t^2))/2
          - S = 0 + 0*t + (a*(t^2))/2
          - S = (a*(t^2))/2
-}

a = 9.86  -- gravitational acceleration

-- height fallen by an object after t seconds
altitude :: Float -> Float
altitude = \t -> a*(t^2)/2

{-
    altitude is impure because it depends on the local gravity, which can vary: it reads the global a,
    so it may not return the same output for the same input.
-}
