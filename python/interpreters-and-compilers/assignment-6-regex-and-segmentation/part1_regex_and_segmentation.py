# Part 1: regular expressions for phone numbers, dates, postal codes and emails, plus a hand-written sentence segmenter.

import re


# NAME: GUSTAVO HAMMERSCHMIDT.


phone = r"\([0-9][0-9]\) [0-9]{5}-[0-9]{4}\b"


date = r"([1-2][0-9]|[0][1-9]|[3][0-1])\/([0][1-3]|[0][5-9]|[1][0-2])\/([0-9]{4}\b)|([1-2][0-9]|[0][1-9])\/([0][4])\/([0-9]{4}\b)"

postal_code = r"[0-9]{2}\.[0-9]{3}-[0-9]{3}\b"

email = (r"[a-zA-Z0-9]+@[a-zA-Z0-9]+\.com(\.br)?\b")

test_str = ("PUCPR/PPGIA\n"
            "Bloco 8 - Parque Tecnológico - 20 andar\n"
            "Rua Imaculada Conceição, 1155 - Prado Velho\n"
            "CEP 80.215-901 - Curitiba - PR\n"
            "Professor Sherlock Holmes "
            "Email: sherlocked@queen.com "
            "Date: 12/04/2018 "
            "Contact phone: (41) 99443-5421")

matchesNUM = re.finditer(phone, test_str, re.MULTILINE)
matchesDATA = re.finditer(date, test_str, re.MULTILINE)
matchesCEP = re.finditer(postal_code, test_str, re.MULTILINE)
matchesEMAIL = re.finditer(email, test_str, re.MULTILINE)

print("\n\n\tRegex implementation: phone number, date, postal code and email: \n\n")
print("Regex PHONE: %s \nRegex DATE: %s \nRegex POSTAL CODE: %s \nRegex EMAIL: %s \n" % (phone, date, postal_code, email))

for matchNum, match in enumerate(matchesNUM):
    matchNum = matchNum + 1
    print("Match {matchNum} found at {start}-{end}: {match}"
          .format(matchNum = matchNum, start = match.start(),
                  end = match.end(), match = match.group()))

for matchNum, match in enumerate(matchesDATA):
    matchNum = matchNum + 1
    print("Match {matchNum} found at {start}-{end}: {match}"
          .format(matchNum = matchNum, start = match.start(),
                  end = match.end(), match = match.group()))

for matchNum, match in enumerate(matchesCEP):
    matchNum = matchNum + 1
    print("Match {matchNum} found at {start}-{end}: {match}"
          .format(matchNum = matchNum, start = match.start(),
                  end = match.end(), match = match.group()))


for matchNum, match in enumerate(matchesEMAIL):
    matchNum = matchNum + 1
    print("Match {matchNum} found at {start}-{end}: {match}"
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


def split_on_punctuation(text):
    buffer = ""
    aux = []
    for i in range(0,len(text)):

        flag = True
        
        if p2(text[i]):
            aux.append(buffer)
            buffer = ""
            flag = False
            
        if p1(text[i]):
            aux.append(buffer)
            buffer = ""
            flag = False
        
        if not p2(text[i]) and flag:
            buffer+=text[i]
            flag = False
            
        if not p1(text[i]) and flag:
            buffer+=text[i]
                
        
            
    aux.append(buffer)
    aux2 = []
    for i in aux:
        if i[-1:] == "." or i[-1:] == "?" or i[-1:] == ":":
            aux2.append(i[:-1])
        else:
            aux2.append(i)
    
    return aux2

def blank(text):
    buffer, passer = "",0
    aux = []
    for i in range(0,len(text)):
        if passer == 0:
            if text[i] == '\n' and text[i] == '\n':
                aux.append(buffer)
                buffer = ""
                passer == 1
            else:
                buffer += text[i]
        else:
            passer -= 1
        
    aux.append(buffer)
    return aux 


def collapse_whitespace(text):
    buffer = ""

    i = 0
    while i < len(text):
        if text[i] != " ":
            buffer += text[i]
            i += 1
        else:
            while i < len(text) and text[i] == " ":
                i += 1
            buffer += " "

    return buffer.lstrip(" ").rstrip(" ")




def remove_etc(text):
    aux = []
    for i in text.split(" "):
        aux.append(i.replace("etc",""))
    return ' '.join(aux)



def segment_sentences(text):
    hold = []
    for i in blank(text):
        if len(i) != 0:
            hold.append(i)

    hold2 = []
    for i in hold:
       for j in split_on_punctuation(i):
           hold2.append(j)

    hold3 = []
    for i in hold2:
        hold3.append(collapse_whitespace(i))

    hold4 = []
    for i in hold3:
        hold4.append(remove_etc(i))

    hold3 = []
    for i in hold4:
        hold3.append(collapse_whitespace(i))

    holdF = []
    for i in hold3:
        if not len(i.strip(" ")) == 0:
            holdF.append(i)
            
    return holdF
            
    

print("\n\n\tSentence segmentation (classification):\n\n")

text = ("Sherlock Holmes argues with Watson:\n\n"
         "Who is this Bertrand Russell?\n"
         "Your etc opponent.\n"
         "etc etc etc\n"
         "Elementary, my dear Watson!\n")

print("Text before segmentation:\n\n%s \n\nText after segmentation:\n\n" % text)


print(segment_sentences(text),"\n\n")


