from kdc import *
from bob_to_kdc import *

def key(alphabet, key_number):  # parameters, in order: the alphabet and the shift.

    alpha_code = ['', '', '', '', '5', '', '', '', '', '10', '', '', '', '', '15',
                  '', '', '', '', '20', '', '', '', '', '25', '']

    for i in range(0, len(alphabet)):
        if (i + key_number) > 25:
            alpha_code[i] = alphabet[0 + ((i + key_number) - 26)]
        else:
            alpha_code[i] = alphabet[i + key_number]
    return alpha_code              # key = alpha + shift -->  alpha_code


def encrypt(m_entry, alpha_code):

    aux = []
    for i in range(0, len(m_entry)):
        for j in range(0, len(alpha_code)):
            if m_entry[i] == alpha[j]:
                aux.append(alpha_code[j])

    m_crypt = ''.join(aux)
    return m_crypt


print("\n Typed message -> ", m, "\n Encrypted message -> ", encrypt(m, key(alpha, K_bob)),
      "\n", "End: Encrypting algorithm")

code = key(alpha, session_key(K_bob, his_key(K_bob)))   # K_alice comes from the KDC; K_bob from bob_to_kdc

message_to_alice = encrypt(m, code)
# now it goes to Alice

# TODO: draw the shift at random between 0 and 52
