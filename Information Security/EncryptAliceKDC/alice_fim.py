from Alice_verification import save_mes    # Alice pede acesso à função: save_mes
from Bob_ve_e_responde import p_alice_ver   # Alice pede acesso à função: p_alice_ver
from Alice_verification import cod_ver      # Alice pede acesso à função: cod_ver

# Gustavo Hammerschmidt

alice_cod_ver = save_mes(cod_ver)
bob_cod_ver = p_alice_ver(cod_ver)
print(' ', alice_cod_ver, bob_cod_ver)

aux = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

for i in range(0, len(aux)):
    aux[len(aux)-1-i] = bob_cod_ver[i]

bob_cod_ver_true = ''.join(aux)
print(' ', bob_cod_ver_true)
if alice_cod_ver == bob_cod_ver_true:
    print("Proceso de Verificação concluído com êxito.")

else:
    print("Proceso de Verificação não foi concluído com êxito.")
