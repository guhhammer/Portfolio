
from CesarCodeVars import alpha
from CesarCodeVars import m
from bridge import to_solve

"""
    Processes to hack the Cesar's Code:
        
        1- I Know that the encrypting process is based on Cesar's Code.
        2- Taking the first affirmative for granted, I can presume there's a code or a number going from 1 to 26 or, 
           to be more precise, 0 to 25.
        3- So I have 26 different combinations for possible phrases, therefore I can face ambiguity when using small 
           sentences like: Like or Love you. 
        4- I don't have the code but I know I can find the message trying different combinations, a useful shortcut 
           would be using cross-entropy based on the english language to test which result is more likely to be the 
           real message typed in english.
        5- I have to start from the point were it's only given to me the variables alpha and message.
        6- First I will try the method: hacking the key.
          
"""


def breaking_rules(text, alphabet, ax=26):
    for a in range(0, ax):  # code tentatives

        aux = []
        for b in range(0, len(text)):  # size message
            for c in range(0, len(alphabet)):  # to find a corresponding letter in alphabet
                if (c+a) > 26:
                    module = c + a - 26
                    if m[b] == alphabet[c]:
                        aux.append(alphabet[module])

                else:
                    if m[b] == alphabet[c]:
                        module = c + a - 26
                        if module > 0:
                            aux.append(alphabet[module*(-1)])
                        else:
                            aux.append(alphabet[module])

        if ax == 1:
            tentative = ''.join(aux)
            return tentative

        else:
            tentative = ''.join(aux)
            print(a + 1, "ª tentative -> ", tentative)


breaking_rules(to_solve, alpha)
print('\n')
print("This is the word decrypted: ", breaking_rules(to_solve, alpha, 1))
