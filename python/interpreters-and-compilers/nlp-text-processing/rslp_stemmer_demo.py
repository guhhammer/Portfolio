from collections import Counter
import nltk
from nltk import tokenize



# RSLP stemmer (Portuguese)

nltk.download('rslp')
stemmer = nltk.stem.RSLPStemmer()
print(stemmer.stem("abóbora"))
print(stemmer.stem("maçã"))
print(stemmer.stem("Curitiba"))
