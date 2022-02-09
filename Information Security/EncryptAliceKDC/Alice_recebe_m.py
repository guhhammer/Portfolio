from KDC import k_sessao                         # Alice pede acesso à função: k_sessao
from KDC import confirma_alice                   # Alice pede acesso à função: confirma_Alice
from Bob_to_KDC_to_Bob import message_to_alice   # Alice pede acesso à mensagem: message_to alice
from Funktion import decrypt                     # Alice pede acesso à função: decrypt
from Funktion import key                         # Alice pede acesso à função: key
from Persona_Alice import K_alice                # Alice pede acesso à chave: K_alice
from Persona_Alice import alpha                  # Alice pede acesso ao vetor: alpha

# Gustavo Hammerschmidt

ma = message_to_alice                      # Mensagem Recebida

# Print mensagem encriptografada de bob recebida
print(" \n Essa a mensagem de Bob encriptografada recebida: ", ma)

# Mensagem descriptografada:
decrypt(ma, key(alpha, k_sessao(confirma_alice(K_alice), K_alice)))
