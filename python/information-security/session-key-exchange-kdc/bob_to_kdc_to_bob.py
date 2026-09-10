from kdc import confirm_bob                  # Bob asks for access to the function: confirm_bob
from kdc import session_key                  # Bob asks for access to the function: session_key
from persona_bob import K_bob                # Bob asks for access to the key: K_bob
from persona_bob import alpha                # Bob asks for access to the vector: alpha
from persona_bob import m                    # Bob asks for access to the message: m
from functions import encrypt                # Bob asks for access to the function: encrypt
from functions import key                    # Bob asks for access to the function: key

# Gustavo Hammerschmidt

m_encrypt = encrypt(m, key(alpha, K_bob))  # <- Message encrypted with Bob's key

print("\n Typed message -> ", m,
      "\n Encrypted message -> ", m_encrypt,
      "\n")

# Message encrypted with Bob's key and Alice's key:
message_to_alice = encrypt(m, key(alpha, session_key(K_bob, confirm_bob(K_bob))))

# Print the encrypted message for Alice
print(message_to_alice)
