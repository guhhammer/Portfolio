from CesarCodeVars import *

####################################
# Key function

# Key for encrypting messages:


def key(alphabet, key_number):  # variables in parameters, in order, alpha and code.

    alpha_code = ['', '', '', '', '5', '', '', '', '', '10', '', '', '', '', '15',
                  '', '', '', '', '20', '', '', '', '', '25', '']

    for i in range(0, len(alphabet)):
        if (i+key_number) > 25:
            alpha_code[i] = alphabet[0+((i+key_number)-26)]
        else:
            alpha_code[i] = alphabet[i + key_number]
    return alpha_code              # key = alpha + code -->  alpha_code

# Key function
# prints


print("\n Typed code -> ", code,
      "\n Typed message -> ", m,
      "\n \n alphabet -> ", alpha,
      "\n alphabet with code -> ", key(alpha, code),
      "\n")

####################################


########################################################################################


####################################
# Encrypt function:

print("\n", "Encrypting algorithm: ")
#


def encrypt(m_entry, alpha_code):

    aux = []
    for i in range(0, len(m_entry)):
        for j in range(0, len(alpha_code)):
            if m_entry[i] == alpha[j]:
                aux.append(alpha_code[j])
                # print("\n m[", i, "]", " = ", m[i], " -> m_crypt[", i, "] = ", alpha_code[i])

    m_crypt = ''.join(aux)
    return m_crypt


print("\n Typed message -> ", m, "\n Encrypted message -> ", encrypt(m, key(alpha, code)),
      "\n", "End: Encrypting algorithm")

# Encrypt function
####################################


########################################################################################


####################################
# Decrypt function
# m_crypt_to_undo = encrypt(m, key(alpha, code))


def decrypt(m_crypt_to_undo, alpha_code):

    aux2 = []
    print("\n Decrypting algorithm: \n")
    for i in range(0, len(encrypt(m, alpha_code))):
        for j in range(0, len(alpha_code)):
            if m_crypt_to_undo[i] == alpha_code[j]:
                aux2.append(alpha[j])
                print(" m_crypt_to_undo[", i, "]", " = ", m_crypt_to_undo[i], " -> m[", i, "] = ", alpha[j])

    m_decrypted = ''.join(aux2)
    return m_decrypted


print("\n Encrypted message -> ", encrypt(m, key(alpha, code)),
      "\n Decrypted message -> ", decrypt(encrypt(m, key(alpha, code)), key(alpha, code)),
      "\n", "End: Decrypting algorithm")


# Decrypt function
####################################
