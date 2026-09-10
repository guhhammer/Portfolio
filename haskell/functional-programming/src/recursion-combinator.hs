-- map, filter and fold derived from one generic recursion combinator.
-- The r_process combinator was presented in class (Prof. Orlando, PUCPR, 2019); the derived functions are the exercise.

module Language where

t_      = \x -> True
f_      = \x -> False

-- r_process proc neuter stop sel next x:
--   stops with `neuter` when `stop x`, applies `proc` to x when `sel x`, and moves on with `next x`
r_process = \proc neuter stop sel next x -> case () of
  _ | stop x -> neuter
    | sel x  -> proc x (r_process proc neuter stop sel next (next x))
    | otherwise -> r_process proc neuter stop sel next (next x)

fold_ = \f v ->
    r_process (\a b -> f (head a) b) v null t_ tail

filter_ = \p ->
    r_process (\a b -> (:) (head a) b) [] null (\l -> p (head l)) tail
-- filter_ (< 4) [1, 2, 3, 4, 5, 6]

map_ = \f ->
    r_process (\a b -> (:) (f (head a)) b) [] null t_ tail
-- map_ succ [1, 2, 3]
