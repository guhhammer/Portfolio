# Part 1: TF-IDF of every term of three documents. Part 2: rank a corpus of news headlines by similarity to a query and plot term frequencies.
import math
import nltk
from nltk import tokenize
from nltk.corpus import stopwords
import matplotlib
import matplotlib.pyplot as plt


# TEAM MEMBERS: Gustavo Hammerschmidt.


# The libraries above must be installed (nltk, matplotlib).
# The inputs and the execution are in the last lines of this file.



# HELPER FUNCTIONS: START.

# SENTENCE SEGMENTATION FUNCTIONS:

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
        hold3.append(collapse_whitespace(i)) #error

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

# SENTENCE SEGMENTATION FUNCTIONS.

# NORMALIZATION FUNCTIONS:

def edit(text):
    return text.replace("\n", " ").replace("\t", " ").replace(",", " ")

def normalize(text):
    return edit(text).lower()


def get_stopwords(download = True):
    if download == True:
        nltk.download('stopwords')
    return (stopwords.words('portuguese'))

removed_words = []
def remove_stopwords(text, download = True, show = False):

    global removed_words
    
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

    if show == True:
        removed_words = aux3

    return aux2  

# NORMALIZATION FUNCTIONS.

# HELPER FUNCTIONS: END.


# ASSIGNMENT PART 1: START.

# TF-IDF FUNCTIONS:

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
            
# TF-IDF FUNCTIONS.

# TF-IDF FOR EACH TERM:

def tfidf_for_each_term(corpus):
    print("\n\nTF-IDF of each term in the corpus:\n\n")
    for i in corpus:
        for j in i[1].split(" "):
            print("TFIDF (\"%s\", %s) = " % (j, i[0]),tf(j, i[1])*idf(j, corpus))
    print("\n\n")       

# TF-IDF FOR EACH TERM.

# INSERT DOCUMENT AND PRINT REMOVED WORDS:

index = 1
downloadFirstTry = True
def insert_document(doc, corpus = []):
    global index
    global downloadFirstTry

    if downloadFirstTry == True:
        get_stopwords(downloadFirstTry)
        downloadFirstTry = False

    removed_by_document = []
    for i in segment_sentences(doc):
        elem0 = ("d%s" % str(index))
        elem1 = (" ".join(remove_stopwords(i, False, True)))
        removed_by_document.append(tuple((elem0, removed_words)))
        corpus.append(tuple((elem0,elem1)))
        index += 1

    return removed_by_document

def print_removed(aux):
    for i in aux:
        print("Palavras removed_words de",i[0],": ",i[1])

# INSERT DOCUMENT AND PRINT REMOVED WORDS.


# EXECUTION AND OUTPUT:

def run_part_1(docs):
    print("\n\nAssignment 7: Part 1.\n")
    print("\n\nStopwords:\n\n",get_stopwords(True),"\n\n")

    corpus = []
    
    for i in docs:
        print_removed(insert_document(i, corpus))

    tfidf_for_each_term(corpus)

# EXECUTION AND OUTPUT.

# ASSIGNMENT PART 1: END.



# ASSIGNMENT PART 2: START.

# CORPUS NORMALIZATION:
def normalize_corpus(corpus):
    normalized = []
    for i in corpus:
        aux = normalize(i)
        for j in segment_sentences(aux):
            normalized.append(j)
    return normalized

# CORPUS NORMALIZATION.

# VOCABULARY EXTRACTION:
def vocabulary(documents):
    docs = normalize_corpus(documents)
    vocab = []
    for i in docs:
        for j in i.split(" "):
            flag = True
            for k in vocab:
                if j == k:
                    flag = False
            if flag:
                vocab.append(j)
    return vocab

# VOCABULARY EXTRACTION.


# IDF CALCULATION:
def idf2(word, corpus):
    countDoc = 0
    for k in corpus:    
        countDoc += 1

    countDocWithWord = 0
    for i in corpus:
        for j in i.split(" "):
            if j == word:
                countDocWithWord += 1
                break
            
    result = 0
    if countDocWithWord == 0:
        result = 0
    else:
        result = (math.log10(((countDoc*1.0)/countDocWithWord)))

    return result

# IDF CALCULATION.


# ASSIGN AN IDF TO EVERY TERM OF THE CORPUS:        
def label_each_document(docs):

    labelled = []
    doc = []
    for w in vocabulary(docs):                    
        elem0 = w
        elem1 = idf2(w, normalize_corpus(docs))
        doc.append(tuple((elem0,elem1)))
        labelled.append(doc)
        doc = []

    return labelled

# ASSIGN AN IDF TO EVERY TERM OF THE CORPUS.


# SIMILARITY COEFFICIENT:
def similarity_coefficient(query, doc, corpus):
    total = 0
    qValue = 0
    jValue = 0
    for j in normalize(doc).split(" "):
        for i in normalize(query).split(" "):
            if i == j:
                for k in label_each_document(corpus):
                    if k[0][0] == i:
                        qValue = k[0][1]
                        jValue = idf2(j, corpus)
                        total += qValue*jValue
                break
    return total

# SIMILARITY COEFFICIENT.


# INSERT DOCUMENTS INTO A CORPUS:
def insert_documents(aux):
    global gl
    corpus = []
    for i in aux:
        corpus.append(i)
    return corpus

# INSERT DOCUMENTS INTO A CORPUS.


def sort_by_similarity(auxV, limit):
    ordered = []
    max_size = len(auxV)
    while(limit > 0):
        largest = 0
        name = ""
        for i in auxV:
            if i[1] > largest:
                largest = i[1]
        for i in auxV:
            if i[1] == largest:
                ordered.append(i)
        auxV = list(filter(lambda x : x[1] != largest, auxV))
        if(len(auxV) > max_size - limit ):
            break
    return ordered


# RANKED SIMILARITY LIST:
gl = 1
def ranked_similarity(query, docs, limit = 20):
    global gl
    lista = []
    for i in docs:
        lista.append(tuple((("d%s" %str(gl)),i)))
        gl += 1
    gl = 0
    largest = -1
    name = ""
    ranked = []
    count = 0

    for i in lista:
        hold = similarity_coefficient(query,i[1],docs)
        name = i[0]
        ranked.append(tuple((name, hold)))
        name = ""
        
    return sort_by_similarity(ranked,limit)

# RANKED SIMILARITY LIST.        


# TERM FREQUENCY:
def term_frequencies(corpus):
    aux = []
    docs = normalize_corpus(corpus)
    for i in docs:
        for j in i.split(" "):
            flag = True
            for k in aux:
                if k[0] == j:
                    flag = False

            if flag:
                aux.append(tuple((j,1)))
            else:
                for l in aux:
                    if j == l[0]:
                        t = tuple((j,(list(l)[1] + 1)))
                        aux = list(filter(lambda x : x[0] != j, aux))
                        aux.append(t)
    return aux

# TERM FREQUENCY.


# SORT FREQUENCIES:
def sort_by_frequency(auxV):

    ordered = []
    while(len(auxV) != 0):
        largest = 0
        for i in auxV:
            if i[1] > largest:
                largest = i[1]
        for i in auxV:
            if i[1] == largest:
                ordered.append(i)
        auxV = list(filter(lambda x : x[1] != largest, auxV))
        
    return ordered

# SORT FREQUENCIES.


# PLOT:            
def plot_frequencies(corpus):
    idx = 0
    index = []
    values = []
    for i in sort_by_frequency(term_frequencies(corpus)):
        index.append(idx)
        values.append(i[1])
        idx += 1

    fig, ax = plt.subplots()
    ax.plot(index,values)


    ax.set(xlabel='term index', ylabel="frequency", title="Term frequencies in the corpus")
    ax.grid()

    fig.savefig("term-frequencies.png")
    plt.show()

# PLOT.  


# RUN PART 2:
def run_part_2(queryList, corpus, limit):
    docs = normalize_corpus(corpus)
    print("\n\nAssignment 7: Part 2.\n")
    print("\n\nEstimated running time: 70 seconds.\n\nThe query \"mortos\" takes longer than the others!")
    print("\n\nDocuments that do not appear have a similarity coefficient of 0.\n\n")
    for query in queryList:
        print("\tQuery of this search: %s\n\n" % query)
        print("\tMaximum number of documents shown = %s" % limit)
        print("\t(this takes a while)\n\n")
        aux = ranked_similarity(normalize(query), docs,limit)
        print("\n\tDocuments ranked by similarity to the query: %s\n\n" % query)
        for i in aux:
            print("\t\tDocument %s has similarity %s with the query." % (i[0], str(i[1])))
        print("\n\n")
    print("\n\nNote: normalization splits the documents into sentences,",
          "which is why the \"number of documents\" is larger than 30.\n\n")

    plot_frequencies(corpus) # PLOT.

    
# RUN PART 2.

# ASSIGNMENT PART 2: END.



# INPUTS:


# INPUT PART 1:

d1 = "O rato roeu a roupa do rei de Roma."
d2 = "Nenhum rato rói a roupa do rei de Romas sem punição."
d3 = "A rota de fuga do rato foi rápida."

# INPUT PART 2 (a corpus of 30 Brazilian news headlines, kept in Portuguese because the stopword list and stemmer are Portuguese):

# the normalization step splits every document into sentences, so the identifiers go beyond d30.

corpus=[("Brasil poderá ter uma presidente mulher, diz Dilma: Declaração foi dada após encontro com Michelle Bachelet em SP. A sociedade está madura para isso, disse a ministra. "),
        ("Justiça brasileira existe e se fez valer, diz Protógenes: Delegado da PF comentou arquivamento do processo do juiz De Sanctis. Em festa de 1º de Maio, ele disse que está na iminência do desemprego. "),
        ("1,7 milhão já trocaram de operadora sem mudar número de telefone: Dados foram divulgados nesta segunda-feira (27) pela ABR Telecom. Total de solicitações de portabilidade numérica já é de 2,3 milhões. "),
        ("2,1 mil servidores foram expulsos do governo desde 2003: Expulsões ocorreram devido a envolvimento em atos de corrupção. CGU diz que cultura de impunidade\' está mudando. "),
        ("50 quilos de droga são apreendidos no Paraná: Crack, pasta base de cocaína e haxixe estavam em caminhonete. Motorista disse que receberia R$ 6 mil para levar droga a Curitiba. "),
        ("50 soldados sírios morrem em explosão de carro-bomba, diz grupo ativista. Pelo menos 50 pessoas ligadas ao governo do presidente da Síria, Bashar Al Assad, morreram nesta semana. "),
        ("70% do Conselho de Ética é suspeito de irregularidades: Levantamento de jornal mostra 21 ligados a escândalos recentes. Senadores decidirão sobre abertura de processo de cassação de Sarney. "),
        ("9 cidades do AM terão recursos para combater DSTs e hepatites virais. Portaria que autoriza o repasse foi publicada no Diário Oficial da União. Valor anual chega a R$ 2.876.461,0, diz Ministério da Saúde. "),
        ("A 8 dias da Copa, rotatória perto da Arena Pantanal ainda está em obras. Ampliação na frente do Círculo Militar começou a ser feita no mês passado. Primeiro jogo do mundial em Cuiabá será no dia 13, entre Chile e Austrália. "),
        ("Abastecimento dágua em 14 bairros de Natal será normalizado nesta quarta. Informação é da Companhia de Águas e Esgotos do Rio Grande do Norte. Vazamentos foram causados pelas obras de mobilidade e defeito em adutora. "),
        ("Abbas pede a Israel o fim da escalada militar contra Gaza. Foguete lançado por Gaza caiu no sul de Israel, sem deixar feridos. Outros projéteis foram lançados, mas não atingiram o país. "),
        ("Ação desmantela quadrilha suspeita de explorar prostituição de travestis. Caso registrado em Cascavel (PR) vem sendo investigado há 13 meses. Segundo a polícia, vítimas eram obrigadas a pagar por ponto e serviços. "),
        ("Ação nas praias da Paraíba quer ajudar na preservação do peixe-boi. Campanha que está abordando banhistas já passou por 28 praias. Abordagem ensina como proceder ao encontrar animal encalhado. "),
        ("Achado arqueológico sugere que lepra surgiu na Índia há cerca de 4.000 anos: Pesquisadores debatiam se origem da doença era asiática ou africana. Apesar de fácil detecção e cura, moléstia ainda persiste no mundo. "),
        ("Acidente com ônibus e trem mata 47 crianças no sul do Egito. Outras 13 crianças ficaram gravemente feridas. Também morreram o motorista do veículo e duas pessoas responsáveis pelos alunos. "),
        ("Acidente de helicóptero deixa 4 mortos na Inglaterra. Um acidente de helicóptero causou a morte de quatro pessoas ontem à noite no condado de Norfolk, no leste da Inglaterra, informou nesta sexta-feira a polícia. "),
        ("Acidente de ônibus mata 16 na Bulgária: Veículo teve falha nos freios, segundo testemunhas. Vinte pessoas ficaram feridas. "),
        ("Acidente de ônibus mata pelo menos 15 pessoas na Colômbia. Veículo teria explodido e a maioria dos mortos são crianças. Presidente do país lamentou a tragédia por meio de uma rede social. "),
        ("Acidente deixa 2 mortos e 51 feridos no Texas. O desastre ocorreu no feriado de Ação de Graças, quando milhões de norte-americanos pegam as estradas e viajam. "),
        ("Acidente deixa 4 mortos e 2 feridos em rodovia mineira: Segundo a PRF, carreta tombou em uma curva na BR-452. Veículo arrastou um carro e outros três caminhões. "),
        ("Acidente deixa três mortos e dois feridos na BR-101. Uma colisão envolvendo uma carreta, uma caçamba e um carro-forte deixou três mortos e dois feridos na manhã desta sexta-feira, no km 207, da BR-101, trecho de Governador Mangabeira (a 119 quilômetros de Salvador). "),
        ("Acidente em mina deixa 22 mortos na China. Acidente ocorreu em uma mina de carvão no sudoeste do país. Causa foi um escapamento de gás. "),
        ("Acidente entre caminhão e dois carros deixa quatro feridos no DF. Colisão aconteceu na BR-080, na entrada de Brazlândia, sentido Ceilândia. Uma das vítimas ficou presa nas ferragens, segundo o Corpo de Bombeiros. "),
        ("Acidente entre carretas deixa dois mortos em MT: Dois veículos transportavam soja e óleo lubrificante. Segundo a polícia, vítimas morreram após colisão em uma curva. "),
        ("Acidente entre van e caminhão deixa 2 mortos em MG: Veículo com 12 passageiros bateu em caminhão e capotou na pista. Outras cinco pessoas ficaram feridas. "),
        ("Acidente mata dois homens e criança de seis anos em rodovia de MT. Dois motoristas e um menino morreram após acidente na MT-235. Mulher foi socorrida em estado grave até hospital da região. "),
        ("Acidentes com motos matam 10 mil em um ano, diz pesquisa: Nos últimos dez anos, o número de mortes aumentou 1.000%. Em 14 estados, óbitos de motociclistas superaram os de pedestres. "),
        ("Aciub contesta lei que altera tamanho de lotes para indústrias em Uberlândia. Representante da associação diz que faltou comunicação com entidades. Lei visa alterar tamanho dos terrenos de 2,5 mil metros quadrados para mil. "),
        ("Acusado de matar menina de 5 anos diz não lembrar onde está o corpo. Britânico Mark Bridger é acusado de assassinar April Jones, de 5 anos. "),
        ("Acusado de queimar e jogar corpo de ex em lixão pega 27 anos de prisão. Crime ocorreu em 2011 na cidade de Brasiléia. Defesa criticou pena e recorreu da decisão. ")]



q1 = "Brasil"
q2 = "mortos"
q3 = "governo"

queries = [q1,q2,q3]



# Execution:


# Documents are numbered in the order of the list: d1, d2, ...

run_part_1([d1,d2,d3])

print("Type anything and press enter to continue: ")
x = str(input())

run_part_2(queries, corpus,20)

print("\n\nEnd of execution!")






