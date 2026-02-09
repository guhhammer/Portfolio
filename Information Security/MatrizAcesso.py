
matrizAcesso = [ ["",       "Foto.png", "Readme.txt",   "Programa.exe" ],
                 ["vilmar", "read",     "write",        ""],
                 ["maria",  "read",     "",             "execute"],
                 ["pedro",  "read",     "write",        "execute"]]

usuarioDigitado = "vilmar"
acaoDigitada = "read"
recursoDigitado = "Foto.png"

acesso = False
for i in range(1, len(matrizAcesso)):
    usuarioMatriz = matrizAcesso[i][0]
    if usuarioDigitado == usuarioMatriz:
        #O usuario eh valido
        for j in range (1, len(matrizAcesso[0])):
            recursoMatriz = matrizAcesso[0][j]
            if( recursoDigitado == recursoMatriz):
                acaoMatriz = matrizAcesso[i][j]
                if acaoDigitada == acaoMatriz:
                    acesso = True

if( acesso ):
    print("Acesso permitido!")
else:
    print("Acesso negado!")
