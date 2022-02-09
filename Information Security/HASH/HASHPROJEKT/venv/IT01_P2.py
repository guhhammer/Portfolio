from Funções_IT01 import pegar_hash
from Funções_IT01 import hash_encrypt

## NOME: GUSTAVO HAMMERSCHMIDT

## AUTENTICAÇÃO:
## Caso não tenha cadastrado uma conta ainda, volte ao IT01_P1.

cad = str(input("Digite seu nome: "))
key = str(input("Digite sua senha: "))


k = True
while k:
    try:
        conf1 = hash_encrypt(key)
        conf2 = pegar_hash(cad)

        if conf1 == conf2:
            print("Autenticação Efetivada!")
            k = False
        else:
            print("Senhas não correspondem!")
            k = False
    except:
        print("Nome de usuário não existe, tente novamente! ")
        k = False
