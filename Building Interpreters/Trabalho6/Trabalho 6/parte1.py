
import re


# NOME: GUSTAVO HAMMERSCHMIDT.


num = r"\([0-9][0-9]\) [0-9]{5}-[0-9]{4}\b"


data = r"([1-2][0-9]|[0][1-9]|[3][0-1])\/([0][1-3]|[0][5-9]|[1][0-2])\/([0-9]{4}\b)|([1-2][0-9]|[0][1-9])\/([0][4])\/([0-9]{4}\b)"

cep = r"[0-9]{2}\.[0-9]{3}-[0-9]{3}\b"

email = (r"[a-zA-Z0-9]+@[a-zA-Z0-9]+\.com(\.br)?\b")

test_str = ("PUCPR/PPGIA\n"
            "Bloco 8 - Parque Tecnológico - 20 andar\n"
            "Rua Imaculada Conceição, 1155 - Prado Velho\n"
            "CEP 80.215-901 - Curitiba - PR\n"
            "Professor Sherlock Holmes "
            "Email: sherlocked@queen.com "
            "Data: 12/04/2018 "
            "Telefone para contato: (41) 99443-5421")

matchesNUM = re.finditer(num, test_str, re.MULTILINE)
matchesDATA = re.finditer(data, test_str, re.MULTILINE)
matchesCEP = re.finditer(cep, test_str, re.MULTILINE)
matchesEMAIL = re.finditer(email, test_str, re.MULTILINE)

print("\n\n\tImplementação dos REGEXs: num, data, cep e  email: \n\n")
print("Regex NUM: %s \nRegex DATA: %s \nRegex CEP: %s \nRegex EMAIL: %s \n" % (num,data,cep,email))

for matchNum, match in enumerate(matchesNUM):
    matchNum = matchNum + 1
    print("Match (matchNum) encontrado em {start}-{end}: {match}"
          .format(matchNum = matchNum, start = match.start(),
                  end = match.end(), match = match.group()))

for matchNum, match in enumerate(matchesDATA):
    matchNum = matchNum + 1
    print("Match (matchNum) encontrado em {start}-{end}: {match}"
          .format(matchNum = matchNum, start = match.start(),
                  end = match.end(), match = match.group()))

for matchNum, match in enumerate(matchesCEP):
    matchNum = matchNum + 1
    print("Match (matchNum) encontrado em {start}-{end}: {match}"
          .format(matchNum = matchNum, start = match.start(),
                  end = match.end(), match = match.group()))


for matchNum, match in enumerate(matchesEMAIL):
    matchNum = matchNum + 1
    print("Match (matchNum) encontrado em {start}-{end}: {match}"
          .format(matchNum = matchNum, start = match.start(),
                  end = match.end(), match = match.group()))


def p2(i):
    if i == ".":
        return True
    else:
        return False

def p1(i):
    if i == "?" or i == "!" or i ==":":
        return True
    else:
        return False


def pontuacao(texto):
    auxS = ""
    aux = []
    for i in range(0,len(texto)):

        flag = True
        
        if p2(texto[i]):
            aux.append(auxS)
            auxS = ""
            flag = False
            
        if p1(texto[i]):
            aux.append(auxS)
            auxS = ""
            flag = False
        
        if not p2(texto[i]) and flag:
            auxS+=texto[i]
            flag = False
            
        if not p1(texto[i]) and flag:
            auxS+=texto[i]
                
        
            
    aux.append(auxS)
    aux2 = []
    for i in aux:
        if i[-1:] == "." or i[-1:] == "?" or i[-1:] == ":":
            aux2.append(i[:-1])
        else:
            aux2.append(i)
    
    return aux2

def blank(texto):
    auxS, passer = "",0
    aux = []
    for i in range(0,len(texto)):
        if passer == 0:
            if texto[i] == '\n' and texto[i] == '\n':
                aux.append(auxS)
                auxS = ""
                passer == 1
            else:
                auxS += texto[i]
        else:
            passer -= 1
        
    aux.append(auxS)
    return aux 


def whiteSpace(texto):
    auxS = ""

    i = 0
    while i < len(texto):
        if texto[i] != " ":
            auxS += texto[i]
            i += 1
        else:
            while i < len(texto) and texto[i] == " ":
                i += 1
            auxS += " "

    return auxS.lstrip(" ").rstrip(" ")




def removeETC(texto):
    aux = []
    for i in texto.split(" "):
        aux.append(i.replace("etc",""))
    return ' '.join(aux)



def classificador(texto):
    hold = []
    for i in blank(texto):
        if len(i) != 0:
            hold.append(i)

    hold2 = []
    for i in hold:
       for j in pontuacao(i):
           hold2.append(j)

    hold3 = []
    for i in hold2:
        hold3.append(whiteSpace(i)) #error

    hold4 = []
    for i in hold3:
        hold4.append(removeETC(i))

    hold3 = []
    for i in hold4:
        hold3.append(whiteSpace(i))

    holdF = []
    for i in hold3:
        if not len(i.strip(" ")) == 0:
            holdF.append(i)
            
    return holdF
            
    

print("\n\n\tÁrvore de Decisão (Classification):\n\n")

texto = ("Sherlock Holmes discute com Watson:\n\n"
         "Quem é esse Bertrand Russel?\n"
         "Seu etc oponente.\n"
         "etc etc etc\n"
         "Elementar, caro Watson!\n")

print("Texto pré-classificação:\n\n%s \n\nTexto pós-classificação:\n\n" % texto)


print(classificador(texto),"\n\n")


