import hashlib

## NOME: GUSTAVO HAMMERSCHMIDT

# Funções:

###################
## Função de cadastro:


def cadastrar(nome, senha):

    arc = open("Info_De_{}.txt".format(nome.upper()), "w")

    arc.writelines("Nome: "+"\n")

    arc.writelines("{}".format(nome)+"\n")

    arc.writelines("\n")

    arc.writelines("Senha(sem Hash): "+"\n")

    arc.writelines("{}".format(senha))

    arc.writelines("\n"+"\n")

    arc.writelines("Senha(com Hash): "+"\n")

    arc.writelines("{}".format(hash_encrypt(senha)))

    arc.close()


## Função de Cadastro.
###################


###################
## Função de Mostrar:


def mostrar(nome):

    arc = open("Info_De_{}.txt".format(nome.upper()), "r")
    print(arc.read())
    arc.close()


## Função de Mostrar:
###################


###################
## Função Pega Senha:


def pegar_senha(archive):

    with open('Info_De_{}.txt'.format(archive.upper()), 'r') as f:
        k = 0
        for line in f:
            k += 1
            if k == 5:
                return line.rstrip('\n')


## Função Pega Senha:
###################


###################
## Função Pega Hash:


def pegar_hash(archive):
    with open('Info_De_{}.txt'.format(archive.upper()), 'r') as f:
        k = 0
        for line in f:
            k += 1
            if k == 8:
                return line.rstrip('\n')


## Função Pega Hash:
###################


###################
## Função de Hash:


def hash_encrypt(senha):
    hashKey = hashlib.md5(senha.encode()).hexdigest()
    return hashKey


## Função de Hash:
###################


###################
## Salvar dados:


def banco_De_Dados(nome):
    arc = open("banco_De_Dados", "a")
    arc.writelines("Nome: {}".format(nome)+"\n")
    arc.writelines("Hash de senha de Info_De_{}:".format(nome)+"\n")
    arc.writelines("{}".format(hash_encrypt(pegar_senha(nome))))
    arc.writelines("\n"+"\n")
    arc.close()


## Salvar dados:
###################

###################
## Só o hash:


def only_hash(archive):
    with open('Info_De_{}.txt'.format(archive.upper()), 'r') as f:
        k = 0
        for line in f:
            k += 1
            if k == 8:
                chave =  line.rstrip('\n')

    arc = open("hash_Banco_Dados.txt", "a")

    arc.writelines("{}".format(chave)+"\n")

    arc.close()


## Só o hash
###################


###################
##  Main call:


def mainCall():
    K = True

    nomeCadastro = []

    while K:

        nome = str(input("Digite seu nome: "))
        senha = str(input("\nDigite sua senha:"))
        nomeCadastro.append(nome)
        cadastrar(nome, senha)

        banco_De_Dados(nome)

        only_hash(nome)

        y = str(input("\nDeseja ver Hash da senha(s/n): "))
        if y.lower() == 's':
            print(hash_encrypt(pegar_senha(nome)))
        else:
            pass

        x = str(input("\nDeseja sair(s/n): "))
        if x.lower() == 's':
            K = False
        else:
            K = True


## Main call:
#################