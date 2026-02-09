import stanfordnlp


# NOME: GUSTAVO HAMMERSCHMIDT.


# Funções da Parte 1:


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


# Funções da Parte 1.


# Função da Parte 2:

print("===================== Dependency Parser ===============")

def dependencyParser(doc):
    print("\n")
    for i in classificador(doc):
        doc = nlp(i)

        print("Sentence: ", doc.text,"\n")


        doc.sentences[0].print_dependencies()


        all_word_Structure = doc.sentences[0].words


        print("\n\nall_word_Structure:\n",all_word_Structure)
        print("\n")


        # vector = [(word: %s, relacao_com: %s, tipo_relacao: %s), ...]
        vector = []
        for x in doc.sentences[0].words:
            aux = tuple(("%s" % x.text, "%s" %  x.index,"%s" %  x.dependency_relation))
            vector.append(aux)
            print("(word: %s, relacao_com: %s, tipo_relacao: %s)" % (x.text, x.index, x.dependency_relation))
            print(x.dependency_relation)


        verbo = ""
        index = 0
        for i in vector:
            index += 1
            if i[1] == "0":
                verbo = i[0]
                break


        print("\n\nTuplas verbo - sujeito(nome):\n\n")
        for i in vector:
            if i[1] == ("%s" % index) and (len(i[0])> 1):
                print(("(%s, %s)" % (verbo, i[0])))

        print("\n")


        
doc = "the quick brown fox jumped over the lazy dog."


dependencyParser(doc)
   

        
