import sys, requests, json

# Looks up a Brazilian postal code (CEP) in a public web service and prints the address.
if len(sys.argv) != 2 or len(sys.argv[1]) != 8:

	print('\nUsage: postal_code_lookup.py 00000000\n'); sys.exit()

ans = requests.get(f"http://cep.republicavirtual.com.br/web_cep.php?cep={str(sys.argv[1])}&formato=json").json()

print(f"{ans['logradouro']}\n{ans['bairro']}\n{ans['cidade']}-{ans['uf']}")
