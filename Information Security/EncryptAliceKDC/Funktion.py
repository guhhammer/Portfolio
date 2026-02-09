from Persona_Bob import m        # mensagem de Bob (decrypt)
from Persona_Bob import alpha    # alpha de decrypt

# Gustavo Hammerschmidt

""" Área das funções"""

# Retorna um código de encriptação


def key(alphabet, key_number):  # parâmetros, respectivamente, alpha e key number

    alpha_code = ['', '', '', '', '5', '', '', '', '', '10', '', '', '', '', '15', '', '', '', '', '20',
                  '', '', '', '', '25', '', '', '', '', '30', '', '', '', '', '35', '', '', '', '', '40',
                  '', '', '', '', '45', '', '', '', '', '50', '', '', '', '', '55', '', '', '', '', '60',
                  '', '', '', '', '65', '', '', '', '', '70', '', '', '', '', '75', '', '', '', '', '80',
                  '', '', '', '', '85', '', '', '', '', '90', '', '', '', '', '95', '', '', '', '', '100',
                  '', '', '', '', '105', '', '', '', '', '110', '', '', '', '', '115', '', '', '', '', '120',
                  '', '', '', '', '125', '', '', '', '', '130', '', '', '', '', '135', '', '', '', '', '140',
                  '', '', '', '', '145', '', '', '', '', '150', '', '', '', '', '155', '', '', '', '', '160',
                  '', '', '', '', '165', '', '', '', '', '170', '', '', '', '', '175', '', '', '', '', '180',
                  '', '', '', '', '185', '', '', '', '', '190', '', '', '', '', '195', '', '', '', '', '200',
                  '', '', '', '', '205', '', '', '', '', '210', '', '', '', '', '215', '', '', '', '', '220',
                  '', '', '223', '224', '225']    # alfabeto com a criptografia

    for i in range(0, len(alphabet)):
        if (i+key_number) > len(alpha)-1:
            alpha_code[i] = alphabet[0+((i+key_number) - len(alpha))]
        else:
            alpha_code[i] = alphabet[i + key_number]
    return alpha_code              # key = alpha + code -->  alpha_code


# Função que encripta a mensagem


def encrypt(m_entry, alpha_code):

    aux = []
    for i in range(0, len(m_entry)):
        for j in range(0, len(alpha_code)):
            if m_entry[i] == alpha[j]:
                aux.append(alpha_code[j])

    m_crypt = ''.join(aux)
    return m_crypt


# Função que decripta a mensagem


def decrypt(m_crypt_to_undo, alpha_code):

    aux2 = []
    print("\n Decrypting algorithm: \n")
    for i in range(0, len(encrypt(m, alpha_code))):
        for j in range(0, len(alpha_code)):
            if m_crypt_to_undo[i] == alpha_code[j]:
                aux2.append(alpha[j])
                print(" m_crypt_to_undo[", i, "]", " = ", m_crypt_to_undo[i], " -> m[", i, "] = ", alpha[j])

    m_decrypted = ''.join(aux2)
    print('\n Essa é a mensagem de bob -> ', m_decrypted)
    return m_decrypted

# End
