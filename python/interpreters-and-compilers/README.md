# Interpreters, compilers and text processing (Python)

Coursework from the "Building Interpreters" course at PUCPR (2019): formal languages and automata, a lexer and parser for Portugol (the Portuguese-keyword teaching language) built with SLY, and the natural-language-processing half of the course (tokenization, stemming, n-grams, regular expressions, TF-IDF search).

For a non-technical reader: this is where a program learns to read another program (or a text), split it into words, check its grammar and act on it. The same techniques power compilers, search engines and chatbots.

| Folder | What it is | Run |
| --- | --- | --- |
| `portugol-interpreter/` | Lexer + parser for Portugol with SLY: declarations, assignment, `se/senao`, `enquanto`, `para`, `repete`, `imprima`, `leia`, boolean and arithmetic expressions. Keywords stay in Portuguese because they are the language; the code around them is English. Includes the project report and an earlier notebook version | `pip install sly && python3 portugol.py` (parses `example.portugol`) |
| `lexer-exercises/` | Two earlier lexers: arithmetic expressions with an `inteiro` keyword, and the first Portugol token set | `python3 arithmetic_lexer.py` |
| `assignment-1-formal-languages/` | Alphabets, words, prefixes/suffixes, language operations and regular expressions for reserved words, numbers and identifiers | `python3 answers.py` |
| `assignment-2-finite-automata/`, `exercises-finite-automata-2019-09-09/` | Finite automata drawn in JFLAP (`.jff`) with PNG renders | open in JFLAP |
| `assignment-3-grammars/` | Context-free grammars for eight languages, including a `while` statement and {a^i b^j a^k, j = i + k} | read `answers.md` |
| `assignment-6-regex-and-segmentation/` | Regular expressions for phone numbers, dates, postal codes and emails; a hand-written sentence segmenter; dependency parsing with StanfordNLP | `python3 part1_regex_and_segmentation.py` |
| `assignment-7-tf-idf-search/` | TF and IDF computed from scratch, a similarity-ranked search over 30 news headlines, and a term-frequency chart | `python3 tfidf_and_similarity.py` (needs `nltk`, `matplotlib`) |
| `nlp-text-processing/` | Normalization, tokenization, word counts, stopword removal, RSLP stemming and lemmatization; bigram similarity between words | `python3 ngram_similarity.py` |
| `restaurant-dialogue-system/` | Final project: a rule-based dialogue system that takes table reservations and delivery orders | `python3 restaurant_chatbot.py` |
| `class-notes/` | My notes from the first class (compilers vs interpreters, alphabets, regular expressions, Chomsky hierarchy), translated | read |

The NLP scripts work on Portuguese text on purpose (NLTK's Portuguese stopword list and RSLP stemmer), so their sample sentences and the news corpus are kept in Portuguese. Every script compiles with Python 3.13 (`python -m py_compile`).
