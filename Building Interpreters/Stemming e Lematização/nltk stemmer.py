from collections import Counter
import nltk
from nltk import tokenize



# Stemmer RSLP

nltk.download('rslp')
stemmer = nltk.stem.RSLPStemmer()
print(stemmer.stem("abóbora"))
print(stemmer.stem("maçã"))
print(stemmer.stem("Curitiba"))
