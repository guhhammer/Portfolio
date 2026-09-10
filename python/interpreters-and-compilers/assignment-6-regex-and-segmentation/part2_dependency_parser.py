# Part 2: segments a text and prints the dependency parse of every sentence with StanfordNLP (pip install stanfordnlp).
import stanfordnlp


# NAME: GUSTAVO HAMMERSCHMIDT.


# Part 1 functions:


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


# Part 1 functions.


# Part 2 function:

print("===================== Dependency Parser ===============")

def dependency_parser(doc):
    print("\n")
    for i in segment_sentences(doc):
        doc = nlp(i)

        print("Sentence: ", doc.text,"\n")


        doc.sentences[0].print_dependencies()


        all_words = doc.sentences[0].words


        print("\n\nall_word_Structure:\n",all_words)
        print("\n")


        # vector = [(word: %s, related_to: %s, relation_type: %s), ...]
        vector = []
        for x in doc.sentences[0].words:
            aux = tuple(("%s" % x.text, "%s" %  x.index,"%s" %  x.dependency_relation))
            vector.append(aux)
            print("(word: %s, related_to: %s, relation_type: %s)" % (x.text, x.index, x.dependency_relation))
            print(x.dependency_relation)


        verb = ""
        index = 0
        for i in vector:
            index += 1
            if i[1] == "0":
                verb = i[0]
                break


        print("\n\nTuplas verb - sujeito(nome):\n\n")
        for i in vector:
            if i[1] == ("%s" % index) and (len(i[0])> 1):
                print(("(%s, %s)" % (verb, i[0])))

        print("\n")


        
doc = "the quick brown fox jumped over the lazy dog."


nlp = stanfordnlp.Pipeline()
dependency_parser(doc)
   

        
