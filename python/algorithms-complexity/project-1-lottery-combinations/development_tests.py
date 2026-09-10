from typing import Iterable, Union, Any





"""

    THIS FILE IS NOT THE ANSWER TO THE ASSIGNMENT.

    THE FINAL ALGORITHM IS lottery_combinations.py.

    PYTHON CODE USED FOR TESTS DURING DEVELOPMENT.


"""





sequence = []


"""

    De Bruijn sequence for alphabet k and subsequences of length n.
    Running time ~ 68.6 s

"""
def de_bruijn(k: Union[Iterable[Any], int], n: int) -> str:
    
    # Two kinds of alphabet input: an integer expands
    # to a list of integers as the alphabet..
    if isinstance(k, int):
        alphabet = list(map(str, range(k)))
    else:
        # While any sort of list becomes used as it is
        alphabet = k
        k = len(k)

    a = [0] * k * n
    
    global sequence

    def db(t, p):
        if t > n:
            if n % p == 0:
                sequence.extend(a[1 : p + 1])
        else:
            a[t] = a[t - p]
            db(t + 1, p)
            for j in range(a[t - p] + 1, k):
                a[t] = j
                db(t + 1, t)

    db(1, 1)

    string_ret = ""
        
    start, z = 0, 1
    slot = int(len(sequence) / 100)

    while(slot*z < len(sequence)): # Space control ~ 2.5 GB RAM

        string_ret += "".join(alphabet[i] for i in sequence[start:slot*z])
        start = slot*z
        z += 1

        print("z=", z)

    sequence = []

    return string_ret #"".join(alphabet[i] for i in sequence)






#https://en.wikipedia.org/wiki/De_Bruijn_sequence
#https://duckduckgo.com/?q=bit+shift+algorithm&atb=v193-1&ia=web
#https://www.tutorialspoint.com/all-pairs-shortest-paths
#x = (de_bruijn(''.join([ chr(250+i) for i in range(1, 51)]), 5))





    def program2(self, load=False) -> None:

        if load:                                                                                                                                    # C
                                                                                                                                    
            self.sceneryC1 = self.loader('arrs/sceneryC1.pkl')                                                                                      # C

            return;                                                                                                                                 # C

        # Matrix of allowed combinations.
        matrix = [list(i) for i in list(np.zeros((50,50), int))]                                                                                    # C

        # Setting permissions,
        matrix = [ [ 1 if j > i else 0 for j in range(0, len(matrix)) ] for i in range(0, len(matrix)) ]                                            # C

        #print("\n", *[ str(matrix[i])+"\n" for i in range(0, len(matrix))], "\n") # Print of the initialized matrix.

        sequences = [] # 5-number sequences that cover the 2-number ones.                                                                             # C

        for _ in range(0, len(matrix)): # Walks every row.                                                                                 # C

            while sum(matrix[_]) > 0: # While the current row is not empty.                                                                   # C

                up = [a for a in range(0, len(matrix)) if matrix[_][a] == 1][:4] # The first four 1s of the row.                                      # C

                # Takes the current row and the 4 ups; every slot of their combinations is zeroed in the matrix.
                [ matrix[arr[0]].__setitem__(arr[1], 0) for arr in list(combinations( [_, *up] , 2)) ]                                              # C
                
                sequences.append([_, *up]) # Adds the sequence to the array.                                                                         # C

        # Removes every array shorter than 5, e.g. [0,49], [1,49], [2,49].
        [sequences.remove(i) for i in [s for s in sequences if len(s) < 5]]                                                                         # C

        # [0,49], [1,49], [2,49]. Notice: 48 / 4 -> 12; repeat once for 48.
        [ sequences.append(n) for n in [ *[[m, m+1, m+2, m+3, 49] for m in range(0, 48, 4)], [45,46,47,48,49] ] ]                                   # C

        # Shifts the values up by 1 (range 1 to 50).
        sequences = [ list(map((lambda x: x+1), i)) for i in sequences]                                                                             # C

        # Dictionary of 5-choose-2 combinations.
        gibi = {}                                                                                                                                   # C

        # Initializing the dictionary.
        [ gibi.__setitem__(str(i[0])+":"+str(i[1]), False) for i in list(combinations(range(1,51), 2)) ]                                            # C


        # Checking the hits in the dictionary.
        [ [ gibi.__setitem__(str(pair[0])+":"+str(pair[1]), True) for pair in c ] for c in [ list(combinations(s, 2)) for s in sequences ]]         # C


        self.sceneryC1 = sequences.copy()                                                                                                           # C

        self.storage(self.sceneryC1, 'arrs/sceneryC1.pkl')                                                                                          # C

        print(f"\nProgram 2: \n\n\t\tScenario 1: {len(self.sceneryC1)} sequences.")                                                                # C
        print(f"\t\tFalse values in gibi: {list(gibi.values()).count(False)} sequences.")                                                         # C
        print("\t\tCall Scenario 1 -> Programs() -> self.sceneryC1.\n")                                                                             # C

        del matrix, sequences, gibi                                                                                                                 # C




#x = (de_bruijn( ''.join([ chr(250+i) for i in range(1, 51) ]), 5))

#print(x)



    
    def program3(self) -> None:
        
        chosen, coverage = list(), {}

        for k in self.combinations_c:

            coverage[str(k[0])+":"+str(k[1])+":"+str(k[2])] = False

        from functools import reduce

        take5 = lambda arr: not reduce( (lambda a, b: a or b), arr) # All true
        take4 = lambda arr: arr.count(False) == 4
        take3 = lambda arr: arr.count(False) == 1
        
        control = take5 # Applies a new rule.
        
        print("\nProgram3: \n\n\t\t{\n\t\t  Execution: \n\t\t\t  ", end='')

        next_ = 4

        while True:

            for _ in self.combinations_a:

                indexes = list(map(
                                    (lambda i: str(i[0])+":"+str(i[1])+":"+str(i[2])), 
                                    list(combinations( _ , 3))
                                   )
                              )

                taken = list(map((lambda i: coverage[i]), indexes ))

                if control(taken):

                    [ coverage.__setitem__(i, True) for i in indexes ]

                    chosen.append(_)
            
            print(f"\n\t\t\t   Phase take{next_+1} -> len(chosen) == {len(chosen)} sequences.", end='')

            if list(coverage.values()).count(False) == 0:  # comb(50,3) = 19600

                break 

            else:
    
                control = locals()['take'+str((next_))]

                next_ -= 1

        self.sceneryC2 = chosen.copy()

        print("\n\n\t\t}\n\n\t\tScenario 2: ", len(self.sceneryC2), "sequences.")
        print(f"\t\tFalse values in coverage: {list(coverage.values()).count(False)} sequences.")
        print("\t\tCall Scenario 2 -> Programs() -> self.sceneryC2.\n")

        del coverage, chosen, control, take5, take4, take3, next_


