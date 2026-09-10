from alice_verification import save_mes    # Alice asks for access to the function: save_mes
from bob_checks_and_replies import p_alice_ver   # Alice asks for access to the function: p_alice_ver
from alice_verification import cod_ver      # Alice asks for access to the function: cod_ver

# Gustavo Hammerschmidt

alice_cod_ver = save_mes(cod_ver)
bob_cod_ver = p_alice_ver(cod_ver)
print(' ', alice_cod_ver, bob_cod_ver)

aux = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

for i in range(0, len(aux)):
    aux[len(aux) - 1 - i] = bob_cod_ver[i]

bob_cod_ver_true = ''.join(aux)
print(' ', bob_cod_ver_true)
if alice_cod_ver == bob_cod_ver_true:
    print("Verification process completed successfully.")

else:
    print("Verification process was not completed successfully.")
