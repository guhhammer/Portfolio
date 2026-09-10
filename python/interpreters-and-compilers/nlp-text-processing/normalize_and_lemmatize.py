# Same pipeline with lemmatization through StanfordNLP (pip install stanfordnlp; downloads the Portuguese model).
import nltk
from nltk import tokenize
from nltk.corpus import stopwords
from collections import Counter
import stanfordnlp

# Gustavo Hammerschmidt.


def edit(text):
    return text.replace("\n", " ").replace("\t", " ").replace(",", " ").replace(".", " ")

def normalize(text):
    return edit(text).lower()

def tokenize_text(text):
    return tokenize.word_tokenize(text, language="portuguese")

def count_words(text):
    for cont in Counter(tokenize_text(normalize(text))).items():
        print("\t",cont)
        
def extract_lemmas(text):
    parsed_text = {'word':[], 'lemma':[]}
    for sent in doc.sentences:
        for wrd in sent.words:
            parsed_text['word'].append(wrd.text)
            parsed_text['lemma'].append(wrd.lemma)
    return parsed_text
            
def lemmatize(text, download = True):
    if download == True:
        stanfordnlp.download('pt')
        
    nlp = stanfordnlp.Pipeline(lang="pt")
    print(extract_lemmas(nlp(text)))

def get_stopwords(download = True):
    if download == True:
        nltk.download('stopwords')
    return (stopwords.words('portuguese'))

def remove_stopwords(text, download = True):

    aux = normalize(text).split(" ")
    aux2 = []
    aux3 = []
    sw = list(get_stopwords(download))
    for i in aux:
        flag = True
        for j in sw:
            if flag == True and i == j:
                flag = False
                aux3.append(i)
        if flag == True:
            aux2.append(i)

    print("\tRemoved words:  ", aux3, "\n")
    return aux2   




def execute():
    print("\n\nDigite o text de entrada:  ",end="")
    text = input()

    norm = normalize(text)
    
    print("\n\nTyped text:  ", text, "\n")
    print("\n\nNormalized text:  ", norm, "\n")
    print("\n\nTokenized text:  ", tokenize_text(norm), "\n")

    print("\n\nWord counter:\n")
    count_words(text)

    print("\n\nLemmatization:  ")
    lemmatize(text, False)

    print("\n\nStopwords: \n\n", get_stopwords(False))
    lemmatize(" ".join(remove_stopwords(text, False)),False)
    print("\n\nStopword removal followed by lemmatization: \n")
    print("\nWords (start): \n")
    
    print("\nWords (end) \n")
    
    print("\n\n")

execute()
