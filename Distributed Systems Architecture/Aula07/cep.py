import sys, requests, json

if len(sys.argv) != 2 or len(sys.argv[1]) != 8:
	
	print('\nDigitie cep.py 00000000 dessa forma!\n'); sys.exit()

ans = requests.get(f"http://cep.republicavirtual.com.br/web_cep.php?cep={str(sys.argv[1])}&formato=json").json()

print(f"{ans['logradouro']}\n{ans['bairro']}\n{ans['cidade']}-{ans['uf']}")