from KDC import confirma_bob                 # Bob pede acesso à função: confirma_bob
from KDC import k_sessao                     # Bob pede acesso à função: k_sessao
from Persona_Bob import K_bob                # Bob pede acesso à chave: K_bob
from Persona_Bob import alpha                # Bob pede acesso ao vetor: alpha
from Persona_Bob import m                    # Bob pede acesso à mensagem: m
from Funktion import encrypt                 # Bob pede acesso à função: encrypt
from Funktion import key                     # Bob pede acesso à função: key

# Gustavo Hammerschmidt

m_encrypt = encrypt(m, key(alpha, K_bob))  # < - Mensagem encriptada com a chave de K_bob

print("\n Typed message -> ", m,
      "\n Encrypted message -> ", m_encrypt,
      "\n")

# Mensagem encriptografada com a chave de Bob e a chave de Alice:
message_to_alice = encrypt(m, key(alpha, k_sessao(K_bob, confirma_bob(K_bob))))

# Print mensagem encriptografada para alice
print(message_to_alice)
