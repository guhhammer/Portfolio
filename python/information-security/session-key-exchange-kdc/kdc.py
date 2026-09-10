from persona_bob import K_bob       # Bob's key
from persona_alice import K_alice   # Alice's key
from random import *                # random function import
from persona_bob import alpha       # vector: alphabet

# Gustavo Hammerschmidt

bob = K_bob
alice = K_alice

adjust = randint(0, len(alpha) - 1)      # Random value used to produce random session keys


def confirm_bob(k_bob):       # Function used to encrypt without holding Alice's key
    if k_bob == bob:
        return K_alice


def confirm_alice(k_alice):    # Function used to encrypt without holding Bob's key
    if k_alice == alice:
        return K_bob


def session_key(bob, alice):                        # the KDC's key
    if (bob + alice - adjust) < 0:
        K_session = -1 * (bob + alice - adjust)
        if K_session > len(alpha) - 1:
            change = K_session - len(alpha) - 2
            print("\nValue of K_session -> ", change, "\n")
            return change

        else:
            print("\nValue of K_session -> ", K_session, "\n")
            return K_session
    else:
        K_session = (bob + alice - adjust)
        if K_session > len(alpha) - 1:
            change = K_session - len(alpha) - 2
            print("\nValue of K_session -> ", change, "\n")
            return change

        else:
            print("\nValue of K_session -> ", K_session, "\n")
            return K_session

# End
