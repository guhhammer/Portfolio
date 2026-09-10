# Motorauto production plan – linear programming formulation

Mathematical Decision Models course, PUCPR, 2021. Team: Gustavo Foroutan Raposo, Gustavo Hammerschmidt, João Felipe Schwab Teixeira de Andrade, Matheus Wilhelm Siqueira, Ricardo Naoki Tanji.

## Problem statement (translated)

Motorauto S/A manufactures 3 car models in its plants: a 1,100 cc model, a 1,400 cc model and an 1,800 cc model. A labour dispute makes a prolonged strike at plant 1 likely in the very near future. To face this situation, management decided to prepare an exceptional production and sales plan for the next period, assuming there will be no production at plant 1 during that period.

In the same period, the capacity of plant 2 will be 4,000 units of the 1,100 cc model, or 3,000 units of the 1,400 cc model, or 2,000 units of the 1,800 cc model, or any appropriate combination of the 3 models. An appropriate combination could be, for example, 2,000 units of 1,100 cc (50% of capacity), 900 units of 1,400 cc (30% of capacity) and 400 units of 1,800 cc (20% of capacity). Similarly, plant 3 has capacity for 3,000 units of the 1,100 cc model or 8,000 units of the 1,400 cc model, or any appropriate combination of these 2 models; the 1,800 cc model is not produced at this plant.

Each 1,100 cc car sells for $1,150, each 1,400 cc car for $1,450 and each 1,800 cc car for $1,800. The production cost at plant 2 is $875, $1,200 and $1,450 per unit of the 1,100 cc, 1,400 cc and 1,800 cc models respectively. At plant 3 the production cost is $900 per unit of the 1,100 cc model and $1,100 per unit of the 1,400 cc model.

The company has commitments that oblige it to supply 1,000 units of the 1,800 cc model for export. On the other hand, given the drop in demand for the 1,100 cc and 1,800 cc models, the sales department estimates the maximum sales of these 2 models at 1,000 and 2,500 units respectively. Since the 1,400 cc model is currently a great commercial success, there is no limit on its sales. At the beginning of the period the stocks of the 3 models are 200 units of 1,100 cc, 600 units of 1,400 cc and 200 units of 1,800 cc. Under the latest agreements it is possible to import up to 500 units of the 1,100 cc model from Argentina, at $1,000 per imported unit.

Considering that Motorauto's objective is to maximise profit, formulate a linear programming model for the problem.

## Model

```
Aliases:

    P2 -> plant 2
    P3 -> plant 3
    PI -> "plant" import

    Model A -- 1,100 cc
    Model B -- 1,400 cc
    Model C -- 1,800 cc

    P2.M.A. -> plant 2, model A

Profit per unit (price minus cost):

    P2.M.A. -> 275
    P2.M.B. -> 250
    P2.M.C. -> 350
    P3.M.A. -> 250
    P3.M.B. -> 350
    PI.M.A. -> 150 (import)

Objective (maximise):

    Y = Sum([ P2.M.A. * 275,
              P2.M.B. * 250,
              P2.M.C. * 350,
              P3.M.A. * 250,
              P3.M.B. * 350,
              PI.M.A. * 150 ])

Constraints:

    -- production capacity, minimum and maximum production,
       stock and import limits.

    Production capacity (1 = 100% of the plant):

        P2.M.A./4000 + P2.M.B./3000 + P2.M.C./2000 <= 1
        P3.M.A./3000 + P3.M.B./8000 <= 1

    Import capacity:

        PI.M.A. <= 500

    Sales / stock limits:

        P2.M.A. + P3.M.A. + PI.M.A. <= 800
        800 <= P2.M.C. <= 2300
```

The brute-force enumeration in `brute_force_attempt.py` was a first (abandoned) attempt at the same problem; the model above was solved with Excel Solver together with the rest of exercise list 1.
