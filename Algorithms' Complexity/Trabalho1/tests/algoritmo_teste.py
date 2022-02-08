from typing import Iterable, Union, Any





"""

    ESTE ALGORITMO NÃO É A RESPOSTA DO TRABALHO. 

    O ALGORITMO FINAL É trabalho1.py.

    CÓDIGO PYTHON USADO PARA TESTES DURANTE O DESENVILVIMENTO.


"""





sequence = []


"""

    De Bruijn sequence for alphabet k and subsequences of length n.
    Complexidde temporal ~ 68.6 s

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

    while(slot*z < len(sequence)): # Controle de complexidade espacial ~ 2.5 GB RAM

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





    def programa2(self, load=False) -> None:

        if load:                                                                                                                                    # C
                                                                                                                                    
            self.sceneryC1 = self.loader('arrs/sceneryC1.pkl')                                                                                      # C

            return;                                                                                                                                 # C

        # Matriz de combinações permitidas.
        matrix = [list(i) for i in list(np.zeros((50,50), int))]                                                                                    # C

        # Definindo permissões,
        matrix = [ [ 1 if j > i else 0 for j in range(0, len(matrix)) ] for i in range(0, len(matrix)) ]                                            # C

        #print("\n", *[ str(matrix[i])+"\n" for i in range(0, len(matrix))], "\n") # Print da Matriz inicializada.

        sequences = [] # Array com as sequências de 5 contendo as de 2.                                                                             # C

        for _ in range(0, len(matrix)): # Percorre todas as linhas.                                                                                 # C

            while sum(matrix[_]) > 0: # Enquanto a linha atual não estiver vazia.                                                                   # C

                up = [a for a in range(0, len(matrix)) if matrix[_][a] == 1][:4] # Os 4 primeiros 1s da linha.                                      # C

                # Pega a linha atual e os 4 ups; todos os slots na matrix de suas combinações são zerados.
                [ matrix[arr[0]].__setitem__(arr[1], 0) for arr in list(combinations( [_, *up] , 2)) ]                                              # C
                
                sequences.append([_, *up]) # Adiciona a sequência ao array.                                                                         # C

        # Removendo todos os arrays com tamanho menor que 5. Ex.: [0,49], [1,49], [2,49].
        [sequences.remove(i) for i in [s for s in sequences if len(s) < 5]]                                                                         # C

        # [0,49], [1,49], [2,49]. Percebeu? 48 / 4 -> 12; repeat a once for 48.
        [ sequences.append(n) for n in [ *[[m, m+1, m+2, m+3, 49] for m in range(0, 48, 4)], [45,46,47,48,49] ] ]                                   # C

        # Subindo os valores do vetor em 1 (limite de 1 a 50).
        sequences = [ list(map((lambda x: x+1), i)) for i in sequences]                                                                             # C

        # Dicionário de combinações 5, 2 a 2.
        gibi = {}                                                                                                                                   # C

        # Inicializando o dicionário.
        [ gibi.__setitem__(str(i[0])+":"+str(i[1]), False) for i in list(combinations(range(1,51), 2)) ]                                            # C


        # Checando os hits no dicionário.
        [ [ gibi.__setitem__(str(pair[0])+":"+str(pair[1]), True) for pair in c ] for c in [ list(combinations(s, 2)) for s in sequences ]]         # C


        self.sceneryC1 = sequences.copy()                                                                                                           # C

        self.storage(self.sceneryC1, 'arrs/sceneryC1.pkl')                                                                                          # C

        print(f"\nPrograma 2: \n\n\t\tCenário 1: {len(self.sceneryC1)} sequências.")                                                                # C
        print(f"\t\tValores False em gibi: {list(gibi.values()).count(False)} sequências.")                                                         # C
        print("\t\tCall Cenário 1 -> Programas() -> self.sceneryC1.\n")                                                                             # C

        del matrix, sequences, gibi                                                                                                                 # C




#x = (de_bruijn( ''.join([ chr(250+i) for i in range(1, 51) ]), 5))

#print(x)



    
    def programa3(self) -> None:
        
        conjunto, conjunto_matrix = list(), {}

        for k in self.combinations_c:

            conjunto_matrix[str(k[0])+":"+str(k[1])+":"+str(k[2])] = False

        from functools import reduce

        take5 = lambda arr: not reduce( (lambda a, b: a or b), arr) # All true
        take4 = lambda arr: arr.count(False) == 4
        take3 = lambda arr: arr.count(False) == 1
        
        control = take5 # Aplica nova regra.
        
        print("\nPrograma3: \n\n\t\t{\n\t\t  Execution: \n\t\t\t  ", end='')

        next_ = 4

        while True:

            for _ in self.combinations_a:

                indexes = list(map(
                                    (lambda i: str(i[0])+":"+str(i[1])+":"+str(i[2])), 
                                    list(combinations( _ , 3))
                                   )
                              )

                taken = list(map((lambda i: conjunto_matrix[i]), indexes ))

                if control(taken):

                    [ conjunto_matrix.__setitem__(i, True) for i in indexes ]

                    conjunto.append(_)
            
            print(f"\n\t\t\t   Phase take{next_+1} -> len(conjunto) == {len(conjunto)} sequências.", end='')

            if list(conjunto_matrix.values()).count(False) == 0:  # comb(50,3) = 19600

                break 

            else:
    
                control = locals()['take'+str((next_))]

                next_ -= 1

        self.sceneryC2 = conjunto.copy()

        print("\n\n\t\t}\n\n\t\tCenário 2: ", len(self.sceneryC2), "sequências.")
        print(f"\t\tValores False em conjunto_matrix: {list(conjunto_matrix.values()).count(False)} sequências.")
        print("\t\tCall Cenário 2 -> Programas() -> self.sceneryC2.\n")

        del conjunto_matrix, conjunto, control, take5, take4, take3, next_


