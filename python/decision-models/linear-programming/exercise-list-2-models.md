# Linear programming models – exercise list 2

Mathematical Decision Models course, PUCPR, 2021. Team: Gustavo Foroutan Raposo, Gustavo Hammerschmidt, João Felipe Schwab Teixeira de Andrade, Matheus Wilhelm Siqueira, Ricardo Naoki Tanji.

Sixteen problems from the professor's list, each formulated as a linear programming model (variables, objective, constraints). The models were solved with Excel Solver in `exercise-list-2-solver.xlsx`; the full report with the solutions and sensitivity analysis is `exercise-list-2-report.pdf` (in Portuguese). Units: m.u. = monetary units; "contos" and "Esc" (escudos) are the currencies used in the original Portuguese problems.

## Exercise 1 – bicycles and scooters

```
Variables:
    x1 -> bicycle
    x2 -> scooter
    W1 -> workshop 1
    W2 -> workshop 2

Model:
    Max. Profit (y €) = (30 € * x1) + (40 € * x2)

Constraints:
    (W1 120 h) >= (x1 * 6 h) + (x2 * 4 h)
    (W2 180 h) >= (x1 * 3 h) + (x2 * 1 h)
```

## Exercise 2 – three products on three machine groups

```
Variables:
    x1 -> P1
    x2 -> P2
    x3 -> P3

Model:
    Max. Profit (y m.u.) = (x1 * 33 m.u.) + (x2 * 12 m.u.) + (x3 * 19 m.u.)

Constraints:
    x2 = 20
    (milling machines 500 h) >= (x1 * 9 h) + (x2 * 3 h) + (x3 * 5 h)
    (lathes 350 h)           >= (x1 * 5 h) + (x2 * 4 h) + (x3 * 0 h)
    (drills 150 h)           >= (x1 * 3 h) + (x2 * 0 h) + (x3 * 2 h)
```

## Exercise 3 – two products on five machines

```
Variables:
    x1 -> P1
    x2 -> P2

Model:
    Max. Gross margin (y €) = (x1 * 6 €) + (x2 * 15 €)

Constraints:
    (Machine A 39 h) >= (x1 * 0 h)   + (x2 * 3 h)
    (Machine B 60 h) >= (x1 * 1.5 h) + (x2 * 4 h)
    (Machine C 57 h) >= (x1 * 2 h)   + (x2 * 3 h)
    (Machine D 70 h) >= (x1 * 3 h)   + (x2 * 2 h)
    (Machine E 57 h) >= (x1 * 3 h)   + (x2 * 0 h)
```

## Exercise 4 – fresh, smoked and premium-smoked meats

```
Variables:
    x1 -> fresh ham
    x2 -> fresh salami
    x3 -> fresh loin
    x4 -> smoked ham
    x5 -> smoked salami
    x6 -> smoked loin
    x7 -> premium smoked ham
    x8 -> premium smoked salami
    x9 -> premium smoked loin

Model:
    Max. Profit (y) = (800 * a) + (400 * b) + (400 * c) +
                      (1400 * x4) + (1200 * x5) + (1300 * x6) +
                      (1100 * x7) + (700 * x8) + (900 * x9)

Constraints:
    x1 <= 480
    x2 <= 400
    x3 <= 230

    x4 + x7 <= x1
    x5 + x8 <= x2
    x6 + x9 <= x3

    a = x1 - (x4 + x7)        (ham sold fresh)
    b = x2 - (x5 + x8)        (salami sold fresh)
    c = x3 - (x6 + x9)        (loin sold fresh)

    x4 + x5 + x6 <= 420       (smoking capacity)
    x7 + x8 + x9 <= 250       (premium smoking capacity)
```

## Exercise 5 – cheapest vegetable diet

```
Variables:
    x1 -> green beans
    x2 -> carrots
    x3 -> broccoli
    x4 -> cabbage
    x5 -> turnips
    x6 -> potatoes

Model:
    Min. Cost (y $) = ($ 50 * x1) + ($ 50 * x2) +
                      ($ 80 * x3) + ($ 20 * x4) +
                      ($ 60 * x5) + ($ 30 * x6)

Constraints:
    Iron 6 mg        < (x1 * 0.45 mg) + (x2 * 0.45 mg) + (x3 * 1.05 mg) +
                       (x4 * 0.4 mg)  + (x5 * 0.5 mg)  + (x6 * 0.5 mg)

    Phosphorus 325 mg < (x1 * 10 mg) + (x2 * 28 mg) + (x3 * 50 mg) +
                        (x4 * 25 mg) + (x5 * 22 mg) + (x6 * 75 mg)

    Vit. A 17500 mg  < (x1 * 415 mg) + (x2 * 9065 mg) + (x3 * 2550 mg) +
                       (x4 * 75 mg)  + (x5 * 15 mg)   + (x6 * 235 mg)

    Vit. B 245 mg    < (x1 * 8 mg)  + (x2 * 3 mg) + (x3 * 53 mg) +
                       (x4 * 27 mg) + (x5 * 5 mg) + (x6 * 8 mg)

    Vit. C 5 mg      < (x1 * 0.3 mg)  + (x2 * 0.35 mg) + (x3 * 0.6 mg) +
                       (x4 * 0.15 mg) + (x5 * 0.25 mg) + (x6 * 0.8 mg)

    x4 < 3
    Max(x1, x2, x3, x5, x6) < 5
```

## Exercise 6 – cheapest grain mix

```
Variables:
    x1 -> barley
    x2 -> peanuts
    x3 -> sesame

Model:
    Min. Cost (y) = (25 * x1 tonnes) +
                    (41 * x2 tonnes) +
                    (39 * x3 tonnes)

Constraints:
    (protein) (0.22*x1 + 0.52*x2 + 0.42*x3) >= 0.22  * (x1 + x2 + x3) tonnes
    (fat)     (0.02*x1 + 0.02*x2 + 0.10*x3) >= 0.036 * (x1 + x2 + x3) tonnes
```

## Exercise 7 – transportation problem (3 plants, 5 warehouses)

```
Variables:
    x1  -> plant A to warehouse 1
    x2  -> plant B to warehouse 1
    x3  -> plant C to warehouse 1
    x4  -> plant A to warehouse 2
    x5  -> plant B to warehouse 2
    x6  -> plant C to warehouse 2
    x7  -> plant A to warehouse 3
    x8  -> plant B to warehouse 3
    x9  -> plant C to warehouse 3
    x10 -> plant A to warehouse 4
    x11 -> plant B to warehouse 4
    x12 -> plant C to warehouse 4
    x13 -> plant A to warehouse 5
    x14 -> plant B to warehouse 5
    x15 -> plant C to warehouse 5

Model:
    Min. Cost (y per tonne) = (x1 * 4)  + (x2 * 6)  + (x3 * 5) +
                              (x4 * 1)  + (x5 * 4)  + (x6 * 2) +
                              (x7 * 2)  + (x8 * 3)  + (x9 * 6) +
                              (x10 * 6) + (x11 * 5) + (x12 * 4) +
                              (x13 * 9) + (x14 * 7) + (x15 * 8)

Constraints:
    (supply)  x1 + x4 + x7 + x10 + x13 = 100 tonnes
              x2 + x5 + x8 + x11 + x14 = 120 tonnes
              x3 + x6 + x9 + x12 + x15 = 120 tonnes

    (demand)  x1 + x2 + x3    = 40 tonnes
              x4 + x5 + x6    = 50 tonnes
              x7 + x8 + x9    = 70 tonnes
              x10 + x11 + x12 = 90 tonnes
              x13 + x14 + x15 = 90 tonnes
```

## Exercise 8 – two types of yarn

```
Variables:
    x1 -> yarn type A
    x2 -> yarn type B

Model:
    Max. Profit (y contos) = (5 contos * (x1 / 100 kg)) +
                             (10 contos * (x2 / 100 kg))

Constraints:
    x1 >= 3000 kg
    (x1 / 100 kg) * 2 h + (x2 / 100 kg) * 1.5 h <= 15 h
    (x1 / 100 kg) * 1 h + (x2 / 100 kg) * 2 h   <= 12 h
```

## Exercise 9 – workforce mix

```
Variables:
    x1 -> skilled worker
    x2 -> unskilled worker
    x3 -> trainee

Model:
    Max. Daily output (y pieces/day) = (x1 * 20 pieces) +
                                       (x2 * 16 pieces) +
                                       (x3 * 12 pieces)

Constraints:
    (x1 + x2 + x3) * 0.3 <= x1          (at least 30% skilled)
    x2 <= x3
    x1 <= 4
    x2 <= 7
    x3 <= 9
    (x1 * 8 * 5) + (x2 * 6 * 5) + (x3 * 4 * 5) <= 400 contos/week   (payroll)
    (x1 * 10 a) + (x2 * 6 a) + (x3 * 1 a) >= 60 a                    (experience)
```

## Exercise 10 – wardrobes and dressers with drawers, two workshop sections

```
Variables:
    (produced)                                            | profit:
    x1  -> wardrobe                                       | p1  -> 45 m.u.
    x2  -> dresser                                        | p2  -> 35 m.u.

    (purchased and finished)                              | profit:
    x3  -> wardrobe + 1 large drawer                      | p3  -> 41 m.u.
    x4  -> wardrobe + 2 large drawers                     | p4  -> 37 m.u.

    x5  -> dresser + 1 large drawer + 0 small drawers     | p5  -> 31 m.u.
    x6  -> dresser + 1 large drawer + 1 small drawer      | p6  -> 28 m.u.
    x7  -> dresser + 1 large drawer + 2 small drawers     | p7  -> 25 m.u.

    x8  -> dresser + 2 large drawers + 0 small drawers    | p8  -> 27 m.u.
    x9  -> dresser + 2 large drawers + 1 small drawer     | p9  -> 24 m.u.
    x10 -> dresser + 2 large drawers + 2 small drawers    | p10 -> 21 m.u.

    x11 -> dresser + 3 large drawers + 0 small drawers    | p11 -> 23 m.u.
    x12 -> dresser + 3 large drawers + 1 small drawer     | p12 -> 20 m.u.
    x13 -> dresser + 3 large drawers + 2 small drawers    | p13 -> 17 m.u.

    x14 -> dresser + 0 large drawers + 1 small drawer     | p14 -> 32 m.u.
    x15 -> dresser + 0 large drawers + 2 small drawers    | p15 -> 29 m.u.

    oh :: overtime hours, defined in the constraints.

Model:
    Max. Profit (y m.u./week) = ( sum for i from 1 to 15 of ( xi * pi ) m.u. ) - ( oh m.u. )

Constraints:
    (s1_t_x1 : section 1, time spent on x1)

    s1_t_x1 = (6 h + 6 h + 4 h) * x1;   s2_t_x1 = (8 h + 6 h + 4 h) * x1;
    s1_t_x2 = (6 h + 6 h + 2 h) * x2;   s2_t_x2 = (4 h + 6 h + 4 h) * x2;
    s1_t_x3 = (6 h + 6 h + 2 h) * x3;   s2_t_x3 = (8 h + 6 h + 2 h) * x3;
    s1_t_x4 = (6 h + 6 h) * x4;         s2_t_x4 = (8 h + 6 h) * x4;
    s1_t_x5 = (6 h + 4 h + 2 h) * x5;   s2_t_x5 = (4 h + 4 h + 4 h) * x5;
    s1_t_x6 = (6 h + 4 h + 1 h) * x6;   s2_t_x6 = (4 h + 4 h + 2 h) * x6;
    s1_t_x7 = (6 h + 4 h) * x7;         s2_t_x7 = (4 h + 4 h) * x7;
    s1_t_x8 = (6 h + 2 h + 2 h) * x8;   s2_t_x8 = (4 h + 2 h + 4 h) * x8;
    s1_t_x9 = (6 h + 2 h + 1 h) * x9;   s2_t_x9 = (4 h + 2 h + 2 h) * x9;
    s1_t_x10 = (6 h + 2 h) * x10;       s2_t_x10 = (4 h + 2 h) * x10;
    s1_t_x11 = (6 h + 2 h) * x11;       s2_t_x11 = (4 h + 4 h) * x11;
    s1_t_x12 = (6 h + 1 h) * x12;       s2_t_x12 = (4 h + 2 h) * x12;
    s1_t_x13 = (6 h) * x13;             s2_t_x13 = (4 h) * x13;
    s1_t_x14 = (6 h + 6 h + 1 h) * x14; s2_t_x14 = (4 h + 6 h + 2 h) * x14;
    s1_t_x15 = (6 h + 6 h) * x15;       s2_t_x15 = (4 h + 6 h) * x15;

    sum for j from 1 to 15 of ( s1_t_xj ) <= 200 h/week
    sum for j from 1 to 15 of ( s2_t_xj ) <= 230 h/week   [150 h + 80 h overtime]

    a  = sum for j from 1 to 15 of ( s2_t_xj )
    oh = (a // 150) * (a - 150)

    (oh : overtime hours of section 2)
    |
    |     oh_x1 -> sum for j from 1 to 15 of ( s2_t_xj )
    |
    | ->  ( oh_x1 // 150 ) * (oh_x1 - 150)
    |
    |   Note:
    |   The "//" operator finds how many times 150 fits in oh_x1 [1 when it is
    |   greater than 150]. The overtime hours worked are then computed as the
    |   difference, and 1 m.u. is discounted for each hour.
```

## Exercise 11 – cutting 70 cm bars into 22 cm and 20 cm pieces

```
Variables:
    x1 -> 22 cm piece
    x2 -> 20 cm piece
    a, b, c, d -> number of bars cut with each pattern

Model:
    Min. Waste (y cm) = (70 - ( x1 * 2 + x2 * 1 )) * a +
                        (70 - ( x1 * 1 + x2 * 2 )) * b +
                        (70 - ( x1 * 3 )) * c +
                        (70 - ( x2 * 3 )) * d

Constraints:
    (a * 2 + b + c * 3) = 50    (22 cm pieces needed)
    (a + b * 2 + d * 3) = 25    (20 cm pieces needed)

Logic of the formula (cutting patterns with up to 3 pieces per bar):

    Comb(p=3)
    {
        70 <- 22*3  20*0
          16x   48    0

        70 <- 22*2  20*1
          25x   50    25

        70 <- 22*1  20*2
          12x   12    24

        70 <- 22*0  20*3
           8x    0    24
    }
```

## Exercise 12 – two processes producing A and B from inputs C and D

```
Variables:
    x1, x3 -> input C (used by process 1 and process 2)
    x2, x4 -> input D (used by process 1 and process 2)

Model:
    Max. Production (y (A+B)) = a1 + b1 + a2 + b2

Constraints:
    (5 a1) + (2 b1) = 1 * x1 + 3 * x2
    (3 a2) + (8 b2) = 4 * x3 + 2 * x4

    a1 + a2 >= 200            ||    A >= 200
    b1 + b2 >= 75             ||    B >= 75
    1 * x1 + 4 * x3 <= 100    ||    C <= 100
    3 * x2 + 2 * x4 <= 150    ||    D <= 150
```

## Exercise 13 – granules vs. flour

```
Variables:
    x1 -> granules
    x2 -> flour

Model:
    Min. Cost (y Esc/kg) = (x1 * 10 Esc/kg) + (x2 * 5 Esc/kg)

Constraints:
    ( ( (x1 * 20) + (x2 * 50) ) / 200 ) >= 1.0
    ( ( (x1 * 50) + (x2 * 10) ) / 150 ) >= 1.0
    ( ( (x1 * 30) + (x2 * 30) ) / 210 ) >= 1.0
```

## Exercise 14 – housing development

```
Variables:
    x1 -> housing type 1
    x2 -> housing type 2
    x3 -> housing type 3

Model:
    Max. Profit (y contos) = (x1 * 3000) + (x2 * 2000) + (x3 * 1000)

Constraints:
    ( (x1 * 170 m²) + (x2 * 120 m²) ) <= 5100 m²
    x3 >= 20
    (x1 * 170 m²) + (x2 * 120 m²) + (x3 * 70 m²) <= (9900 m² - 2000 m²)
```

## Exercise 15 – assigning four workers to four technicians (affinity)

```
Variables:
    x1 -> worker 1
    x2 -> worker 2
    x3 -> worker 3
    x4 -> worker 4

    xa -> technician a
    xb -> technician b
    xc -> technician c
    xd -> technician d

    Affinity:

            xa  xb  xc  xd
        x1  1   4   1   4
        x2  4   2   2   1
        x3  5   3   5   1
        x4  1   2   3   1

Model:
    Max. Score (y) = max(
                        (map ( \x -> sum_array(x) )
                            (map ( \x -> x $$ [xa, xb, xc, xd] )
                                ( permutations of [1, 2, 3, 4] )
                            )
                        )
                     )

Constraints:

    // Sequences:
    //
    // permutations of [1, 2, 3, 4] ~ [[1,2,3,4], [1,3,2,4], ...]
    //
    // (len([1,2,3,4]))! == len(permutations) == 24
    //
    // because that way you simultaneously lock 1 value per row and get every
    // combination across rows.
    //
    // Each element of the model in turn:
    //
    // max (map (sum) (map (mult) comb))
    //
    // 1:max (2:map (3:sum) (4:map (5:mult) 6:comb))
    //
    // 1:max  -> finds the largest value in a vector.
    // 2:map  -> maps the function (3:sum) over every value of (4:map).
    // 3:sum  -> reduces the vectors inside a vector to their sum.
    // 4:map  -> maps the function (5:mult) over every value of (6:comb).
    // 5:mult -> multiplies two vectors element-wise:
    //        -> [a, b, c] $$ [d, e, f] == [a * d, b * e, c * f]
    //        -> x1 $$ xa returns the affinity value from the variables section.
    // 6:comb -> the vector with every permutation of the values [1, 2, 3, 4].
```

## Exercise 16 – TV and radio advertising

```
Variables:
    x1 -> TV ad
    x2 -> radio ad

Model:
    Max. Profit (y m.u.) = (a * 6 m.u.) + (b * 2 m.u.)

Constraints:
    x1 >= 0
    x2 >= 0
    a = 3 * x1
    b = 2 * x2
    a / 3 = b / 2
    (a * 8 m.u.) + (b * 5 m.u.) <= (58 m.u.)
```
