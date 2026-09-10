from persona_alice import K_alice          # Alice asks for access to the key: K_alice
from persona_alice import alpha            # Alice asks for access to the vector: alpha
from functions import key                  # Alice asks for access to the function: key
from kdc import confirm_alice              # Alice asks for access to the function: confirm_alice
from kdc import session_key                # Alice asks for access to the function: session_key
from random import *                       # random function import

# Gustavo Hammerschmidt


def send_bob_verification(aaa, bbb):     # generates an encrypted message used in the verification process

    cod_alice = key(alpha, session_key(aaa, bbb))       # returns Alice's alphabet encrypted with the session key

    aux = ['', '', '', '', '5', '', '', '', '', '10', '', '', '', '', '15', '16', '17', '', '', '20']

    for i in range(0, len(aux)):
        k = randint(0, len(alpha) - 1)
        for j in range(0, len(cod_alice)):
            if j == k:
                aux[i] = alpha[j]

    verification_code = ''.join(aux)
    print(verification_code)
    return aux


def save_mes(bob_env):     # picks the special characters out of the message
    x = bob_env
    a = x[:1]          # pos 1
    b = x[15:16]         # pos 15
    c = x[17:18]         # pos 17
    d = x[13:14]         # pos 13
    e = x[10:11]         # pos 10
    f = x[11:12]         # pos 11
    g = x[8:9]       # pos 8
    h = x[2:3]         # pos 2
    i = x[5:6]         # pos  5
    j = x[18:19]      # pos 20

    print('\n a (pos 1) -> {} \n b (pos 15) -> {} \n c (pos 17) -> {} \n d (pos 13) -> {} \n e (pos 10) -> {} \n '
          'f (pos 11) -> {} \n g (pos 8) -> {} \n h (pos 2) -> {} \n i (pos 5) -> {} \n '
          'j (pos 20) -> {} \n '.format(a, b, c, d, e, f, g, h, i, j))
    aux = ['', '', '', '', '', '', '', '', '', '']
    chars = [a, b, c, d, e, f, g, h, i, j]
    for i in range(0, len(chars)):
        aux[i] = (chars[i])[0]

    message = ''.join(aux)
    print(x, message)
    return message


cod_ver = send_bob_verification(confirm_alice(K_alice), K_alice)      # encrypted key
