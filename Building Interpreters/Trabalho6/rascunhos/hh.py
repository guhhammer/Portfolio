
import re


regex = r"[a-z]"


num = r"\([0-9][0-9]\) [0-9]{5}-[0-9]{4}\b"

data = (r"([1-2][0-9]|[0][1-9]|[3][0-1])"
        "\/([0][1-3]|[0][5-9]|[1][0-2])"
        "\/([0-9]{4}\b)|([1-2][0-9]|[0][1-9])"
        "\/([0][4])\/([0-9]{4}\b)")

cep = r"[0-9]{2}\.[0-9]{3}-[0-9]{3}\b"

email = (r"([a-zA-Z0-9]+@[a-zA-Z0-9]+\.com\.(br)$\b)"
         "{1}|([a-zA-Z0-9]+@[a-zA-Z0-9]+\.(com)$\b){1}")

test_str = ("PUCPR/PPGIA\n"
            "Bloco 8 - Parque Tecnológico - 20 andar\n"
            "Rua Imaculada Conceição, 1155 - Prado Velho\n"
            "CEP 80215-901 - Curitiba - PR\n")


matchesNUM = re.finditer(num, test_str, re.MULTILINE)
matchesDATA = re.finditer(data, test_str, re.MULTILINE)
matchesCEP = re.finditer(cep, test_str, re.MULTILINE)
matchesEMAIL = re.finditer(email, test_str, re.MULTILINE)



def classificador(s, default = 1, vAux = []):

    aux = ""
    
    passer = 0
    for i in range(0, len(s)):
        if passer == 0:
            if s[i] == " " and s[i+1] == " " and s[i+2]== " ":
                vAux.append(aux)
                aux = ""
                passer = 1
            else:
                if s[i] == " " and s[i+1] == " ":
                    aux += s[i]+"abacate"
                    passer = 1
                else:
                    if s[i] == "?" or s[i] == "!" or s[i]==":":
                        break
                    else:
                        if s[i] ==".":
                            break
                        else:
                            if s[i]=="e" and s[i+1]=="t" and s[i+2]=="c":
                                passer = 2
                            else:
                                aux += s[i]
        else:
            passer -= 1

    if default == 1:
        return classificador(aux, 0, vAux)
    else:
        return vAux


#for matchNum, match in enumerate(matches):
 #   matchNum = matchNum + 1
    #print("Match (matchNum) encontrado em {start}-{end}: {match}".format(matchNum = matchNum,
                                                                         #start = match.start(),
                                                                         #end = match.end(),
                                                                         #match = match.group()))


texto = "Texto não   representa etc valor algum?               "

print(classificador(texto))









