
# Gustavo Hammerschmidt


def p_alice_ver(code):         # Função de Bob para receber o código e enviá-lo de volta à alice
    x = code
    j = x[:1]  # pos 1
    i = x[15:16]  # pos 15
    h = x[17:18]  # pos 17
    g = x[13:14]  # pos 13
    f = x[10:11]  # pos 10
    e = x[11:12]  # pos 11
    d = x[8:9]  # pos 8
    c = x[2:3]  # pos 2
    b = x[5:6]  # pos  5
    a = x[18:19]  # pos 20
    print('\n a (pos 20) -> {} \n b (pos 5) -> {} \n c (pos 2) -> {} \n d (pos 8) -> {} \n e (pos 11) -> {} \n '
          'f (pos 10) -> {} \n g (pos 13) -> {} \n h (pos 17) -> {} \n i (pos 15) -> {} \n '
          'j (pos 1) -> {} \n'.format(a, b, c, d, e, f, g, h, i, j))
    bobaux = ['', '', '', '', '', '', '', '', '', '']
    chars = [a, b, c, d, e, f, g, h, i, j]

    for i in range(0, len(bobaux)):
        bobaux[i] = (chars[i])[0]

    message = ''.join(bobaux)
    print("\n", x, message)
    return message
