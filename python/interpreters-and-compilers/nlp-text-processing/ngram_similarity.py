# Bigram (n-gram) similarity between a typed word and a list of Portuguese fruit names: s = 2C / (A + B).
# NAME: Gustavo Hammerschmidt.

def bigrams(word):

    aux = word[:1]
    result = []
    i = 1
    while(aux != word):
        var = (word[i-1:i]+word[i:i+1])
        aux = aux+word[i:i+1]
        result.append(var)
        i += 1

    return result


def unique(vector):

    result = []
    for i in vector:
        if i not in result:
            result.append(i)

    return result


def common(vector_a, vector_b):

    vector_c = []
    for x in vector_a:
        for y in vector_b:
            if x == y and x not in vector_c:
                vector_c.append(x)

    return vector_c



# s = 2C / (A + B)
def most_similar(word, word_list):

    word_bigram_set = unique(bigrams(word))

    similar_word = ""
    best_score = 0
    for i in word_list:
        word_bigrams = unique(bigrams(i))
        common_bigrams = common(word_bigram_set, word_bigrams)
        S = (2*len(common_bigrams) / (len(word_bigram_set)+len(word_bigrams)))
        if  S > best_score:
            best_score = S
            similar_word = i

    return similar_word




word_list = ['abacate', 'abacaxi', 'abóbora', 'abobrinha',
         'ananás', 'maçã', 'mamão', 'manga',
         'melancia', 'melão', 'mexerica', 'morango']   



def Ngram():
    print("\nList: ", word_list)
    print("\nDefina a word para Cálculo de semelhança: ")
    word = input()

    print("\nWord: ", word)

    print("\nMost similar word in the list: ", most_similar(word, word_list), "\n\n")
            


Ngram()


    
