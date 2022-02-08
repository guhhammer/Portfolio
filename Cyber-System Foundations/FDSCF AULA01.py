def dec_to_hex(dividendo):
    divisor = 16
    digitos = '0123456789ABCDEF'
    resposta = ''

    while True:
        quociente = int(dividendo/divisor)
        resto = dividendo%divisor
        print("Quociente: {}  Resto: {}".format(quociente, resto))
        resposta = digitos[resto]+resposta
        dividendo = quociente
        if quociente ==0:
            break

    print("Resultado: {}h".format(resposta))

#dec_to_hex(421)

def some_to_dec(base):
    num = input("digite o número a ser convertido(3 dígitos):").upper()

    if len(num) != 3:
        print("Erro: máximo de três dígitos: ")
        exit()

    #base = int(input("Digite a base: "))
    if base <2 or base>16:
        print("Base deve estar entre 2 e 16.")
        exit()


    digitos = "0123456789ABCDEF"
    a = (digitos.find(num[0]))*(base*base)
    b = (digitos.find(num[1]))*(base)
    c = (digitos.find(num[2]))*(1)
    soma = a+b+c

    print("Resultado: {}".format(soma))

some_to_dec(10)







