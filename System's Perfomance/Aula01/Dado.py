
import numpy as np


throws = lambda tries: np.random.randint(1, 7, (tries, 2))

dados = lambda throws, diceA, diceB: (sum([1 for throw in throws if (throw[0] == diceA and throw[1] == diceB)
												 or (throw[1] == diceA and throw[0] == diceB)])/len(throws))

run = lambda: [print(_) for _ in 
					["\n\tDado.py>>>", 
					 "\n\n\t\tProbabilidade simulada:\t\t{:.4f}".format(dados(throws(10000), 3, 6)),
					 "\n\t\tProbabilidade teórica:\t\t{:.4f}".format(2/36),
					 "\n\n"
					]
			]

run()
