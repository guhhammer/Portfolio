import nltk
from nltk import tokenize
from nltk.corpus import stopwords
from collections import Counter
import standfordnlp

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
        
def extrairLemma(texto):
    parsed_text = {'word':[], 'lemma':[]}
    for sent in doc.sentences:
        for wrd in sent.words:
            parsed_text['word'].append(wrd.text)
            parsed_text['lemma'].append(wrd.lemma)
    return parsed_text
            
def lematizar(texto, download = True):
    if download == True:
        standfordnlp.download('pt')
        
    nlp = standfordnlp.Pipeline(lang="pt")
    print(extrairLemma(nlp(texto)))

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

    print("\n\nLematização:  ")
    lematizar(texto, False)

    print("\n\nStopwords: \n\n", getStopwords(False))
    lematizar(" ".join(removerStopWords(texto, False)),False)
    print("\n\nRemoção de Stopwords do texto e Lematização: \n")
    print("\nPalavras(Início): \n")
    
    print("\nPalavras(Fim) \n")
    
    print("\n\n")

execute()
