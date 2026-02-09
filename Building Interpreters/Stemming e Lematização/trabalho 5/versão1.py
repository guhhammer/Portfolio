import nltk
from nltk import tokenize
from nltk.corpus import stopwords
from collections import Counter

# Gustavo Hammerschmidt.

def edit(texto):
    return texto.replace("\n", " ").replace("\t", " ").replace(",", " ").replace(".", " ")

def normalizar(texto):
    return edit(texto).lower()

def tokenizar(texto):
    return tokenize.word_tokenize(texto, language="portuguese")

def contarPalavras(texto):
    for cont in Counter(tokenizar(normalizar(texto))).items():
        print("\t",cont)

def stem(texto, download = True):
    if download == True:
        nltk.download('rslp')
    stemmer = nltk.stem.RSLPStemmer()
    for s in tokenizar(normalizar(texto)):
        print("\t",stemmer.stem(s))

def getStopwords(download = True):
    if download == True:
        nltk.download('stopwords')
    return (stopwords.words('portuguese'))

def removerStopWords(texto, download = True):

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

    print("\tPalavras Removidas:  ",aux3,"\n")
    return aux2   


def execute():
    print("\n\nDigite o texto de entrada:  ",end="")
    texto = input()

    norm = normalizar(texto)
    
    print("\n\nTexto Digitado:  ",texto,"\n")
    print("\n\nTexto Normalizado:  ",norm,"\n")
    print("\n\nTexto pós-tokenização:  ",tokenizar(norm),"\n")

    print("\n\nContador de Palavras:\n")
    contarPalavras(texto)

    print("\n\nStemming:  ")
    stem(texto, False)

    print("\n\nStopwords: \n\n", getStopwords(False))

    print("\n\nRemoção de Stopwords do texto e aplicação de Stemming: \n")
    print("\nPalavras(Início): \n")
    stem(" ".join(removerStopWords(texto, False)), False)
    print("\nPalavras(Fim) \n")
    
    print("\n\n")

execute()


