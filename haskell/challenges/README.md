# Haskell one-liners for coding challenges

Solutions to Edabit-style challenges written as dense point-free Haskell, meant to be loaded in GHCi (`ghci alphabet_soup.hs`, then call the function).

| File | Challenge |
| --- | --- |
| `alphabet_soup.hs` | `alphabet_soup "hello"` sorts the letters of a word, treating upper-case letters through a lookup table |
| `armstrong_number.hs` | `ann 153 3` checks whether a number equals the sum of its digits raised to a power (with typed helpers for digit splitting and powers) |
| `karaca.hs` | `encrypt "apple"` implements "Karaca's encryption": reverse, replace vowels by digits, append "aca" |
| `pentagonal_number.hs` | `pn n` gives the n-th pentagonal number and `pns n` their running sum |
| `sum_of_power.hs` | `sop n` prints the list of consecutive odd numbers whose sum is n squared |

The same Armstrong-number problem is solved in Python, C++ and Java under `python/challenges`, `cpp/challenges` and `java/challenges`.
