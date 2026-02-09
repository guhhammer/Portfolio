from BOBtoKDC import *
from alice_key import K_alice

bob = K_bob
alice = K_alice


def chave_ele(K_bob):
    return K_alice


def chave_ela(K_Alice):
    return K_bob


def k_sessao(bob, alice):
    if (bob + alice - 4) < 0:
        K_sessao = -1*(bob+alice-4)
        print(K_sessao)
        return K_sessao
    else:
        K_sessao = (bob + alice - 4)
        print(K_sessao)
        return K_sessao
