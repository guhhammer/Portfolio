from bob_to_kdc import *
from alice_key import K_alice

bob = K_bob
alice = K_alice


def his_key(K_bob):
    return K_alice


def her_key(K_Alice):
    return K_bob


def session_key(bob, alice):
    if (bob + alice - 4) < 0:
        K_session = -1 * (bob + alice - 4)
        print(K_session)
        return K_session
    else:
        K_session = (bob + alice - 4)
        print(K_session)
        return K_session
