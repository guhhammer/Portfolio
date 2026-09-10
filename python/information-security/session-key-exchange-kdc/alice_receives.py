from kdc import session_key                      # Alice asks for access to the function: session_key
from kdc import confirm_alice                    # Alice asks for access to the function: confirm_alice
from bob_to_kdc_to_bob import message_to_alice   # Alice asks for access to the message: message_to_alice
from functions import decrypt                    # Alice asks for access to the function: decrypt
from functions import key                        # Alice asks for access to the function: key
from persona_alice import K_alice                # Alice asks for access to the key: K_alice
from persona_alice import alpha                  # Alice asks for access to the vector: alpha

# Gustavo Hammerschmidt

ma = message_to_alice                      # Received message

# Print the encrypted message received from Bob
print(" \n This is the encrypted message received from Bob: ", ma)

# Decrypted message:
decrypt(ma, key(alpha, session_key(confirm_alice(K_alice), K_alice)))
