
from caesar_vars import alpha
from caesar_vars import m
from bridge import to_solve

"""
    Process to break the Caesar cipher:

        1- I know that the encryption is based on the Caesar cipher.
        2- Taking the first statement for granted, I can presume there is a shift, a number from 1 to 26 or,
           to be more precise, 0 to 25.
        3- So I have 26 different candidate phrases, therefore I can face ambiguity with short
           sentences such as "Like" or "Love you".
        4- I don't have the shift but I know I can find the message by trying every combination; a useful
           shortcut would be scoring each candidate with the letter frequencies of English to pick the
           one most likely to be the real message.
        5- I have to start from the point where only the alphabet and the message are given.
        6- First I try the method: breaking the key.

"""


def breaking_rules(text, alphabet, ax=26):
    for a in range(0, ax):  # shift attempts

        aux = []
        for b in range(0, len(text)):  # message length
            for c in range(0, len(alphabet)):  # find the matching letter in the alphabet
                if (c + a) > 26:
                    module = c + a - 26
                    if m[b] == alphabet[c]:
                        aux.append(alphabet[module])

                else:
                    if m[b] == alphabet[c]:
                        module = c + a - 26
                        if module > 0:
                            aux.append(alphabet[module * (-1)])
                        else:
                            aux.append(alphabet[module])

        if ax == 1:
            attempt = ''.join(aux)
            return attempt

        else:
            attempt = ''.join(aux)
            print(a + 1, "th attempt -> ", attempt)


breaking_rules(to_solve, alpha)
print('\n')
print("This is the decrypted word: ", breaking_rules(to_solve, alpha, 1))
