import hashlib

## NOME: GUSTAVO HAMMERSCHMIDT

# Funções:

###################
## Função de cadastro:


def cadastrar2(nome, senha):

    arc = open("Info_De_{}2BF.txt".format(nome.upper()), "w")

    arc.writelines("Nome: "+"\n")

    arc.writelines("{}".format(nome)+"\n")

    arc.writelines("\n")

    arc.writelines("Senha(sem Hash): "+"\n")

    arc.writelines("{}".format(senha))

    arc.writelines("\n"+"\n")

    arc.writelines("Senha(com Hash): "+"\n")

    arc.writelines("{}".format(hash_encrypt2(senha)))

    arc.close()


## Função de Cadastro.
###################


###################
## Função de Mostrar:


def mostrar2(nome):

    arc = open("Info_De_{}_2BF.txt".format(nome.upper()), "r")
    print(arc.read())
    arc.close()


## Função de Mostrar:
###################


###################
## Função Pega Senha:


def pegar_senha2(archive):

    with open('Info_De_{}2BF.txt'.format(archive.upper()), 'r') as f:
        k = 0
        for line in f:
            k += 1
            if k == 5:
                return line.rstrip('\n')


## Função Pega Senha:
###################


###################
## Função Pega Hash:


def pegar_hash2(archive):
    with open('Info_De_{}2BF.txt'.format(archive.upper()), 'r') as f:
        k = 0
        for line in f:
            k += 1
            if k == 8:
                return line.rstrip('\n')


## Função Pega Hash:
###################


###################
## Função de Hash:


def hash_encrypt2(senha):
    hashKey = hashlib.md5(senha.encode()).hexdigest()
    hashKey2 = hashlib.md5(hashKey.encode()).hexdigest()
    hashKey3 = hashlib.md5(hashKey2.encode()).hexdigest()
    return hashKey3


## Função de Hash:
###################


###################
## Salvar dados:


def banco_De_Dados2(nome):
    arc = open("banco_De_Dados2BF", "a")
    arc.writelines("Nome: {}".format(nome)+"\n")
    arc.writelines("Hash de senha de Info_De_{}2BF:".format(nome)+"\n")
    arc.writelines("{}".format(hash_encrypt2(pegar_senha2(nome))))
    arc.writelines("\n"+"\n")
    arc.close()


## Salvar dados:
###################

###################
## Só o hash:


def only_hash2(archive):
    with open('Info_De_{}2BF.txt'.format(archive.upper()), 'r') as f:
        k = 0
        for line in f:
            k += 1
            if k == 8:
                chave =  line.rstrip('\n')

    arc = open("hash_Banco_Dados2BF.txt", "a")

    arc.writelines("{}".format(chave)+"\n")

    arc.close()


## Só o hash
###################


###################
##  Main call:


def mainCall2():
    K = True

    nomeCadastro2 = []

    while K:

        nome = str(input("Digite seu nome: "))
        senha = str(input("\nDigite sua senha:"))
        nomeCadastro2.append(nome)
        cadastrar2(nome, senha)

        banco_De_Dados2(nome)

        only_hash2(nome)

        y = str(input("\nDeseja ver Hash da senha(s/n): "))
        if y.lower() == 's':
            print(hash_encrypt2(pegar_senha2(nome)))
        else:
            pass

        x = str(input("\nDeseja sair(s/n): "))
        if x.lower() == 's':
            K = False
        else:
            K = True


## Main call:
#################