# Linear programming models – exercise list 1

Mathematical Decision Models course, PUCPR, 2021. Team: Gustavo Foroutan Raposo, Gustavo Hammerschmidt, João Felipe Schwab Teixeira de Andrade, Matheus Wilhelm Siqueira, Ricardo Naoki Tanji.

Each exercise of the professor's list is formulated as a linear programming model: decision variables, objective function and constraints. Notation: `x1 -> barley` reads "x1 is the amount of barley".

## Exercise 1 – cheapest animal feed

```
Variables:
    x1 -> barley
    x2 -> oats
    x3 -> soy
    x4 -> corn

Model:
    Min. Feed (y cost/kg) =
        (x1 * 30 cost/kg) + (x2 * 48 cost/kg) + (x3 * 44 cost/kg) + (x4 * 56 cost/kg)

Constraints:
    y = 10,000 kg
    (protein)  0.069 * x1 + 0.085 * x2 + 0.09 * x3 + 0.271 * x4 >= 0.15 * y
    (fibre)    0.06 * x1 + 0.11 * x2 + 0.11 * x3 + 0.14 * x4 > 0.08 * y
    x4 >= (0.2 * y kg)
    x3 <= (0.12 * y kg)
    1100 cal/kg <= (1760 * x1 + 1700 * x2 + 1056 * x3 + 1400 * x4) / y <= 2250 cal/kg
```

## Exercise 2 – Motorauto production plan

See [`motorauto-production-plan.md`](motorauto-production-plan.md) for the full statement.

```
Variables:
    P2 -> plant 2
    P3 -> plant 3
    PI -> import

    Model A -- 1,100 cc
    Model B -- 1,400 cc
    Model C -- 1,800 cc
    (P2.M.A. -> plant 2, model A)

    Profit per unit:
        P2.M.A. -> 275
        P2.M.B. -> 250
        P2.M.C. -> 350
        P3.M.A. -> 250
        P3.M.B. -> 350
        PI.M.A. -> 150 (import)

Model:
    Max. Profit (y) = P2.M.A. * 275 + P2.M.B. * 250 + P2.M.C. * 350 +
                      P3.M.A. * 250 + P3.M.B. * 350 + PI.M.A. * 150

Constraints:
    P2.M.A./4000 + P2.M.B./3000 + P2.M.C./2000 <= 1
    P3.M.A./3000 + P3.M.B./8000 <= 1
    PI.M.A. <= 500
    P2.M.A. + P3.M.A. + PI.M.A. <= 800
    800 <= P2.M.C. <= 2300
```

## Exercise 3 – course portfolio

```
Variables:
    x1 -> short course
    x2 -> medium course
    x3 -> long course

Model:
    Max. Profit (y $M) = (x1 * $0.23M) + (x2 * $0.30M) + (x3 * $0.42M)

Constraints:
    (x1 * $3.5M) + (x2 * $5M) + (x3 * $6.7M) <= $150M
    x1 >= 0
    x2 >= 0
    x3 >= 0
    x1 + x2 + x3 <= 30
    x1 + (x2 * 4/3) + (x3 * 5/3) <= 40   (maintenance)
```

## Exercise 4 – multi-year investment plan

```
Variables:
    x1 -> activity A
    x2 -> activity B
    x3 -> activity C
    x4 -> activity D
    (x1_y1: amount invested in x1 in year 1)

Model:
    Max. Amount (y $) = 10000 + ((x1_y1 + x1_y2 + x1_y3 + x1_y4) * 0.4) +
                                ((x2_y1 + x2_y2 + x2_y3) * 0.7) +
                                (x3_y2 * 1) + (x4_y5 * 0.3)

Constraints:
    x1_y1 + x2_y1 <= 10000
    x1_y2 + x2_y2 + x3_y2 <= 10000 - (x1_y1 + x2_y1)
    x1_y3 + x2_y3 <= 10000 + (x1_y1 * 0.4) - (x2_y1 + x1_y2 + x2_y2 + x3_y2)
    x1_y4 <= 10000 + ((x1_y1 + x1_y2) * 0.4) + (x2_y1 * 0.7) - (x2_y2 + x3_y2 + x1_y3 + x2_y3)
    x4_y5 <= 10000 + ((x1_y1 + x1_y2 + x1_y3) * 0.4) + ((x2_y1 + x2_y2) * 0.7) - (x3_y2 + x2_y3 + x1_y4)
```
