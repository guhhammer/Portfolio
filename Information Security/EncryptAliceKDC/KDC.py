from Persona_Bob import K_bob       # Chave de Bob
from Persona_Alice import K_alice   # chave de Alice
from random import *                # import de função randômica
from Persona_Bob import alpha       # vetor: alfabeto

# Gustavo Hammerschmidt

bob = K_bob
alice = K_alice

ajuste = randint(0, len(alpha)-1)      # Chave aleatória para gerar chaves de sessões aleatórias


def confirma_bob(k_bob):       # Função para encriptografar sem ter a chave de alice
    if k_bob == bob:
        return K_alice


def confirma_alice(k_alice):    # Função para encriptografar sem ter a chave de bob
    if k_alice == alice:
        return K_bob


def k_sessao(bob, alice):                        #  chave do KCD
    if (bob + alice - ajuste) < 0:
        K_sessao = -1 * (bob + alice - ajuste)
        if K_sessao > len(alpha)-1:
            change = K_sessao - len(alpha)-2
            print("\nValor de K_sessao -> ", change, "\n")
            return change

        else:
            print("\nValor de K_sessao -> ", K_sessao, "\n")
            return K_sessao
    else:
        K_sessao = (bob + alice - ajuste)
        if K_sessao > len(alpha)-1:
            change = K_sessao - len(alpha)-2
            print("\nValor de K_sessao -> ", change, "\n")
            return change

        else:
            print("\nValor de K_sessao -> ", K_sessao, "\n")
            return K_sessao

# End
