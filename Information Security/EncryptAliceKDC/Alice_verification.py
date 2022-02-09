from Persona_Alice import K_alice          # Alice pede acesso à chave: K_alice
from Persona_Alice import alpha            # Alice pede acesso ao vetor: alpha
from Funktion import key                   # Alice pede acesso à função: key
from KDC import confirma_alice             # Alice pede acesso à função: confirma_Alice
from KDC import k_sessao                   # Alice pede acesso à função: k_sessao
from random import *                       # import de função randômica

# Gustavo Hammerschmidt


def envia_bob_ver(aaa, bbb):     # função que gera uma mensagem criptografada para ser usada no processo de verificação

    cod_alice = key(alpha, k_sessao(aaa, bbb))       # retorna alfabeto de alice encriptografado com a k_sessao

    aux = ['', '', '', '', '5', '', '', '', '', '10', '', '', '', '', '15', '16', '17', '', '', '20']

    for i in range(0, len(aux)):
        k = randint(0, len(alpha)-1)
        for j in range(0, len(cod_alice)):
            if j == k:
                aux[i] = alpha[j]

    codigo_ver = ''.join(aux)
    print(codigo_ver)
    return aux


def save_mes(bob_env):     # função para separar caracteres especiais da mensagem
    x = bob_env
    a = x[:1]          # pos 1
    b = x[15:16]         # pos 15
    c = x[17:18]         # pos 17
    d = x[13:14]         # pos 13
    e = x[10:11]         # pos 10
    f = x[11:12]         # pos 11
    g = x[8:9]       # pos 2
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


cod_ver = envia_bob_ver(confirma_alice(K_alice), K_alice)      # chave encriptografada
