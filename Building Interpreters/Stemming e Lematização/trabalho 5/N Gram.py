# NOME: Gustavo Hammerschmidt.

def digrafo(palavra):

    aux = palavra[:1]
    vetorAux = []
    i = 1
    while(aux != palavra):
        var = (palavra[i-1:i]+palavra[i:i+1])
        aux = aux+palavra[i:i+1]
        vetorAux.append(var)
        i += 1

    return vetorAux


def unicas(vetor):

    vetorAux = []
    for i in vetor:
        if i not in vetorAux:
            vetorAux.append(i)

    return vetorAux


def iguais(vetorA, vetorB):

    vetorC = []
    for x in vetorA:
        for y in vetorB:
            if x == y and x not in vetorC:
                vetorC.append(x)

    return vetorC



# s = 2C / (A + B)
def similaridade(palavra, lista):

    digr = unicas(digrafo(palavra))

    palavraSemelhante = ""
    maiorValor = 0
    for i in lista:
        palDigr = unicas(digrafo(i))
        digrIguais = iguais(digr, palDigr)
        S = (2*len(digrIguais) / (len(digr)+len(palDigr)))
        if  S > maiorValor:
            maiorValor = S
            palavraSemelhante = i

    return palavraSemelhante




lista = ['abacate', 'abacaxi', 'abóbora', 'abobrinha',
         'ananás', 'maçã', 'mamão', 'manga',
         'melancia', 'melão', 'mexerica', 'morango']   



def Ngram():
    print("\nLista: ",lista)
    print("\nDefina a palavra para Cálculo de semelhança: ")
    palavra = input()

    print("\nPalavra: ", palavra)

    print("\nPalavra Semelhante em Lista: ",similaridade(palavra,lista),"\n\n")
            


Ngram()


    
