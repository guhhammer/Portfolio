# Class 4: proof by induction (question 4, translated)

Claim: 2*1 + 2*2 + 2*3 + ... + 2*n = n^2 + n for n >= 1.

**Base case:** 2*1 = 1^2 + 1 = 2. True.

**Inductive step:** assume the formula holds for n = k (k >= 1); show it holds for n = k + 1:

2*1 + 2*2 + ... + 2*n + 2*(n+1) = n^2 + n + 2*(n+1)

Write the series twice, once reversed, and add position by position: every pair sums to 2*(n+2), and there are n+1 pairs, so the double sum is (n+1)*2*(n+2) and the sum itself is (n+1)*(n+2).

Check against the claim: (n+1)*(n+2) = n^2 + n + 2*(n+1) = n^2 + 3n + 2. Both sides are n^2 + 3n + 2. The inductive case holds.
