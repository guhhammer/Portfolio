from KDCtoBOB import message_to_alice
from KDCtoBOB import encrypt
from alice_key import key
from KDC import k_sessao
from alice_key import K_alice
from KDC import chave_ela

alpha = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
         'w', 'x', 'y', 'z']

ma = message_to_alice
print(ma)


def decrypt(m_crypt_to_undo, alpha_code):

    aux2 = []
    print("\n Decrypting algorithm: \n")
    for i in range(0, len(encrypt(ma, alpha_code))):
        for j in range(0, len(alpha_code)):
            if m_crypt_to_undo[i] == alpha_code[j]:
                aux2.append(alpha[j])
                print(" m_crypt_to_undo[", i, "]", " = ", m_crypt_to_undo[i], " -> m[", i, "] = ", alpha[j])

    m_decrypted = ''.join(aux2)
    print('\n mensagem de bob -> ', m_decrypted)
    return m_decrypted


decrypt(ma, key(alpha, k_sessao(chave_ela(K_alice), K_alice)))


# fazer verificação
