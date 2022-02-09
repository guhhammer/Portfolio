K_alice = 8

alpha = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
         'w', 'x', 'y', 'z']


def key(alphabet, key_number):  # variables in parameters, in order, alpha and code.

    alpha_code = ['', '', '', '', '5', '', '', '', '', '10', '', '', '', '', '15',
                  '', '', '', '', '20', '', '', '', '', '25', '']

    for i in range(0, len(alphabet)):
        if (i+key_number) > 25:
            alpha_code[i] = alphabet[0+((i+key_number)-26)]
        else:
            alpha_code[i] = alphabet[i + key_number]
    return alpha_code              # key = alpha + code -->  alpha_code


def encrypt(m_entry, alpha_code):

    aux = []
    for i in range(0, len(m_entry)):
        for j in range(0, len(alpha_code)):
            if m_entry[i] == alpha[j]:
                aux.append(alpha_code[j])
                # print("\n m[", i, "]", " = ", m[i], " -> m_crypt[", i, "] = ", alpha_code[i])

    m_crypt = ''.join(aux)
    return m_crypt


print("Alice chave -> ", K_alice, "\n Alpha -> ", alpha)
