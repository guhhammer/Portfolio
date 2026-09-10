"""Two-dice simulation (class 01): probability of rolling a 3 and a 6, vectorised with NumPy.

10,000 throws of two dice are drawn at once; the simulated probability of the
pair (3, 6) in either order is compared with the theoretical value 2/36.
"""
import numpy as np


throws = lambda tries: np.random.randint(1, 7, (tries, 2))

dice = lambda throws, dice_a, dice_b: (sum([1 for throw in throws if (throw[0] == dice_a and throw[1] == dice_b)
                                            or (throw[1] == dice_a and throw[0] == dice_b)]) / len(throws))

run = lambda: [print(_) for _ in
               ["\n\tdice_simulation.py>>>",
                "\n\n\t\tSimulated probability:\t\t{:.4f}".format(dice(throws(10000), 3, 6)),
                "\n\t\tTheoretical probability:\t\t{:.4f}".format(2/36),
                "\n\n"
                ]
               ]

run()
