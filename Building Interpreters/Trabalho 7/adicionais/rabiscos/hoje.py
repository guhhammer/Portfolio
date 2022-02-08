import math
import nltk
from nltk import tokenize
from nltk.corpus import stopwords


# FUNÇÕES DE SEGMENTAÇÃO DE FRASES:

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

# FUNÇÕES DE SEGMENTAÇÃO DE FRASES.

# FUNÇÕES DE NORMALIZAÇÃO:

def edit(texto):
    return texto.replace("\n", " ").replace("\t", " ").replace(",", " ").replace(".", " ")

def normalizar(texto):
    return edit(texto).lower()


def getStopwords(download = True):
    if download == True:
        nltk.download('stopwords')
    return (stopwords.words('portuguese'))

removidas = []
def removerStopWords(texto, download = True, show = False):

    global removidas
    
    aux = normalizar(texto).split(" ")
    aux2 = []
    aux3 = []
    sw = list(getStopwords(download))
    for i in aux:
        flag = True
        for j in sw:
            if flag == True and i == j:
                flag = False
                aux3.append(i)
        if flag == True:
            aux2.append(i)

    if show == True:
        removidas = aux3

    return aux2  

# FUNÇÕES DE NORMALIZAÇÃO.


#FUNÇÕES TF IDF:

def tf(word, document):
    countW = 0
    wordsCount = 0
    for i in document.split(" "):
        if i == word:
            countW += 1
    for i in document:
        wordsCount += 1

    return (countW*1.0)/wordsCount


def idf(word, corpus):

    countDoc = 0
    for k in corpus:    
        countDoc += 1

    countDocWithWord = 0
    for i in corpus:
        for j in i[1].split(" "):
            if j == word:
                countDocWithWord += 1
                break

    result = 0
    if countDocWithWord == 0:
        result = 0
    else:
        result = (math.log10(((countDoc*1.0)/countDocWithWord)))
        
    return result
            
# FUNÇÕES TF IDF.

# FUNÇÃO TFIDF PARA CADA TERMO:

def tfidfDeCadaTermo(corpus):
    print("\n\nTFIDF de cada termo no corpus:\n\n")
    for i in corpus:
        for j in i[1].split(" "):
            print("TFIDF (\"%s\", %s) = " % (j, i[0]),tf(j, i[1])*idf(j, corpus))
    print("\n\n")       

# FUNÇÃO TFIDF PARA CADA TERMO.

# FUNÇÕES INSERE E PRINT REMOVIDAS:

index = 1
downloadFirstTry = True
def insereDoc(doc, corpus = []):
    global index
    global downloadFirstTry

    if downloadFirstTry == True:
        getStopwords(downloadFirstTry)
        downloadFirstTry = False

    auxRemovidas = []
    for i in classificador(doc):
        elem0 = ("d%s" % str(index))
        elem1 = (" ".join(removerStopWords(i, False, True)))
        auxRemovidas.append(tuple((elem0, removidas)))
        corpus.append(tuple((elem0,elem1)))
        index += 1

    return auxRemovidas

def printRemovidas(aux):
    for i in aux:
        print("Palavras removidas de",i[0],": ",i[1])

# FUNÇÕES INSERE E PRINT REMOVIDAS.


# FUNÇÃO PARA EXECUÇÃO E OUTPUT:

def executer(docs):

    print("\n\nStopwords:\n\n",getStopwords(True),"\n\n")

    corpus = []
    
    for i in docs:
        printRemovidas(insereDoc(i, corpus))

    tfidfDeCadaTermo(corpus)

# FUNÇÃO PARA EXECUÇÃO E OUTPUT.

# INPUT:

d1 = "O rato roeu a roupa do rei de Roma."
d2 = "Nenhum rato rói a roupa do rei de Romas sem punição."
d3 = "A rota de fuga do rato foi rápida."


# Os documentos serão ordenados conforme a ordem no vetor:
# d1 será d1; d2, d2; ...

executer([d1,d2,d3])















