

---------------------------------------------------------

module Language where

import IO

---------------------------------------------------------

t_      = \x -> True
f_      = \x -> False

r_process = \proc neuter stop sel next x -> case () of
  _ | stop x -> neuter
    | sel x  -> proc x (r_process proc neuter stop sel next (next x))
    | otherwise -> r_process proc neuter stop sel next (next x)


fold_ = \f v ->
    r_process (\a b -> f (head a) b) v null t_ tail

filter_ = \p ->
    r_process (\a b -> (:) (head a) b) [] null (\l -> p (head l)) tail
--filter_ (menor 4) [1, 2, 3, 4, 5, 6]

map_ = \f ->
    r_process (\a b -> (:) (f (head a)) b) [] null t_ tail
--map_ succ [1, 2, 3]
