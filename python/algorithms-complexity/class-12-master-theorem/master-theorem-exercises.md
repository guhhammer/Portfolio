# Classes 12 and 13: master theorem exercises (translated)

For each recurrence: a = number of subproblems, b = division factor, k = exponent of f(n); then compare log_b(a) with k and, for case 3, find the constant c1 with a*f(n/b) <= c1*f(n).

**A3:** T(0) = 1; T(n) = 2T(n/2) + n^2. log2 2 = 1 < k = 2, so case 3: Theta(n^2). Regularity: 2*(n^2/4) <= c1*n^2 gives c1 >= 0.5.

**A5:** T(n) = 3T(n/3) + n^2/2. log3 3 = 1 < 2, case 3: Theta(n^2). 3*(n^2/18) <= c1*n^2/2 gives c1 >= 2/9 = 0.223.

**A7:** T(n) = 3T(n/3) + n^3. log3 3 = 1 < 3, case 3: Theta(n^3). 3*(n^3/27) <= c1*n^3 gives c1 >= 1/9 = 0.112.

**A9:** T(n) = 4T(n/4) + 4n. log4 4 = 1 = k, case 2: Theta(n log n). 4*(n) <= c1*4n gives c1 >= 1.

**A11:** T(n) = 4T(n/4) + n^4 + 4n. log4 4 = 1 < 4, case 3: Theta(n^4). 4*(n^4/256 + n) <= c1*(n^4 + 4n); as n grows the left side over the right tends to 1/65, so c1 >= 0.0154.
