import math
import nltk
from nltk import tokenize
from nltk.corpus import stopwords
import matplotlib
import matplotlib.pyplot as plt


# MEMBROS DA EQUIPE: Gustavo Hammerschmidt.


# Recomenda-se que as bibliotecas acima sejam instaladas.
# Os inputs e a execução do código estão nas últimas linhas deste código.



# FUNÇÕES COMPLEMENTARES: INÍCIO.

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
    return texto.replace("\n", " ").replace("\t", " ").replace(",", " ")

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

# FUNÇÕES COMPLEMENTARES: FIM.


# TRABALHO PARTE 1: INÍCIO.

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
    print("\n\nTrabalho 7: Parte 1.\n")
    print("\n\nStopwords:\n\n",getStopwords(True),"\n\n")

    corpus = []
    
    for i in docs:
        printRemovidas(insereDoc(i, corpus))

    tfidfDeCadaTermo(corpus)

# FUNÇÃO PARA EXECUÇÃO E OUTPUT.

# TRABALHO PARTE 1: FIM.



# TRABALHO PARTE 2: INÍCIO.

# FUNÇÃO PARA NORMALIZAÇÃO:
def normalizarCorpus(corpus):
    corpusAux = []
    for i in corpus:
        aux = normalizar(i)
        for j in classificador(aux):
            corpusAux.append(j)
    return corpusAux

# FUNÇÃO PARA NORMALIZAÇÃO.

# FUNÇÃO PARA EXTRAÇÃO DE PALAVRAS:
def vocabulario(documents):
    docs = normalizarCorpus(documents)
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

# FUNÇÃO PARA EXTRAÇÃO DE PALAVRAS.


# FUNÇÃO PARA CÁLCULO DE IDF:
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

# FUNÇÃO PARA CÁLCULO DE IDF.


# FUNÇÃO PARA ATRIBUIR IDF A CADA TERMO DO CORPUS:        
def labelEachDoc(docs):

    docsT = []
    doc = []
    for w in vocabulario(docs):                    
        elem0 = w
        elem1 = idf2(w, normalizarCorpus(docs))
        doc.append(tuple((elem0,elem1)))
        docsT.append(doc)
        doc = []

    return docsT

# FUNÇÃO PARA ATRIBUIR IDF A CADA TERMO DO CORPUS.


# FUNÇÃO PARA CÁLCULO DO COEFICIENTE DE SIMILARIDADE:
def coeficienteSimilaridade(query, doc, corpus):
    soma = 0
    qValue = 0
    jValue = 0
    for j in normalizar(doc).split(" "):
        for i in normalizar(query).split(" "):
            if i == j:
                for k in labelEachDoc(corpus):
                    if k[0][0] == i:
                        qValue = k[0][1]
                        jValue = idf2(j, corpus)
                        soma += qValue*jValue
                break
    return soma

# FUNÇÃO PARA CÁLCULO DO COEFICIENTE DE SIMILARIDADE.


# FUNÇÃO PARA INSERIR DOCUMENTOS EM UM CORPUS:
def inserirDocs(aux):
    global gl
    corpus = []
    for i in aux:
        corpus.append(i)
    return corpus

# FUNÇÃO PARA INSERIR DOCUMENTOS EM UM CORPUS.


def ordenarSimilaridade(auxV, limite):
    ordenado = []
    tamMaximo = len(auxV)
    while(limite > 0):
        maior = 0
        nome = ""
        for i in auxV:
            if i[1] > maior:
                maior = i[1]
        for i in auxV:
            if i[1] == maior:
                ordenado.append(i)
        auxV = list(filter(lambda x : x[1] != maior, auxV))
        if(len(auxV) > tamMaximo - limite ):
            break
    return ordenado


# FUNÇÃO PARA IMPRIMIR A LISTA ORDENADA DE SIMILARIDADE:
gl = 1
def printListOfSimilarity(query, docs, limite = 20):
    global gl
    lista = []
    for i in docs:
        lista.append(tuple((("d%s" %str(gl)),i)))
        gl += 1
    gl = 0
    maior = -1
    nome = ""
    ordenada = []
    count = 0

    for i in lista:
        hold = coeficienteSimilaridade(query,i[1],docs)
        nome = i[0]
        ordenada.append(tuple((nome, hold)))
        nome = ""
        
    return ordenarSimilaridade(ordenada,limite)

# FUNÇÃO PARA IMPRIMIR A LISTA ORDENADA DE SIMILARIDADE.        


# FUNÇÃO PARA CÁLCULO DE FREQUÊNCIA:
def frequence(corpus):
    aux = []
    docs = normalizarCorpus(corpus)
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

# FUNÇÃO PARA CÁLCULO DE FREQUÊNCIA.


# FUNÇÃO PARA ORDENAR AS FREQUÊNCIAS:
def orderer(auxV):

    ordenado = []
    while(len(auxV) != 0):
        maior = 0
        for i in auxV:
            if i[1] > maior:
                maior = i[1]
        for i in auxV:
            if i[1] == maior:
                ordenado.append(i)
        auxV = list(filter(lambda x : x[1] != maior, auxV))
        
    return ordenado

# FUNÇÃO PARA ORDENAR AS FREQUÊNCIAS.


# FUNÇÃO PARA PLOTAR O GRÁFICO:            
def plotGraphic(corpus):
    indice = 0
    index = []
    values = []
    for i in orderer(frequence(corpus)):
        index.append(indice)
        values.append(i[1])
        indice += 1

    fig, ax = plt.subplots()
    ax.plot(index,values)


    ax.set(xlabel='# de termos', ylabel="frequência", title="Frequência termos do Corpus")
    ax.grid()

    fig.savefig("GráficoDasFrequências.png")
    plt.show()

# FUNÇÃO PARA PLOTAR O GRÁFICO.  


# FUNÇÃO PARA EXECUTAR A PARTE 2 DO TRABALHO:
def executerParte2(queryList, corpus, limite):
    docs = normalizarCorpus(corpus)
    print("\n\nTrabalho 7: Parte 2.\n")
    print("\n\nTempo estimado de execução: 70 segundos.\n\nA Query de \"mortos\" demora mais que as outras!")
    print("\n\nOs documentos que não aparecerem no kernel tem coef. de similaridade igual a 0.\n\n")
    for query in queryList:
        print("\tQuery desta busca: %s\n\n" %query)
        print("\tLimite de documentos mostrados = %s" % limite)
        print("\t!!Demora um pouco mesmo!!\n\n")
        aux = printListOfSimilarity(normalizar(query), docs,limite)
        print("\n\tLista ordenada de maior similaridade com a query: %s\n\n" % query)
        for i in aux:
            print("\t\tDocumento %s tem %s de similaridade com a query." %(i[0],str(i[1])))
        print("\n\n")
    print("\n\nVale ressaltar que, quando o corpus sofre a normalização, há subdivisões em seus documentos",
          "o que resulta em um \"número de documentos\" maior do que 30.\n\n")

    plotGraphic(corpus) # PLOTAGEM DO GRÁFICO.

    
# FUNÇÃO PARA EXECUTAR A PARTE 2 DO TRABALHO.

# TRABALHO PARTE 2: FIM.



#INPUTS:


# INPUT PARTE 1:

d1 = "O rato roeu a roupa do rei de Roma."
d2 = "Nenhum rato rói a roupa do rei de Romas sem punição."
d3 = "A rota de fuga do rato foi rápida."

# INPUT PARTE 2:

# meu algoritmo separa os documentos do corpus na normalização o que faz com o indentificador diga
# que há mais de 30 documentos.

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

querys = [q1,q2,q3]



# Execução:


# Os documentos serão ordenados conforme a ordem no vetor:
# d1 será d1; d2, d2; ...

executer([d1,d2,d3])

print("Digite qualquer coisa e de enter para continuar: ")
x = str(input())

executerParte2(querys, corpus,20)

print("\n\nFim da Execução !!")






