from sly import Lexer, Parser

class CalcLexer(Lexer):
    tokens = { ID, NUM, PLUS, TIMES, MINUS, DIVIDE, ATRIB, LPAREN, RPAREN, INTEIRO }
    ignore = ' \t'

    # Tokens
    ID = r'[a-zA-Z_][a-zA-Z0-9_]*'
    NUM = r'\d+'
    PLUS = r'\+'
    MINUS = r'-'
    TIMES = r'\*'
    DIVIDE = r'/'
    ATRIB = r'='
    LPAREN = r'\('
    RPAREN = r'\)'


    # Special cases (ex.: palavras reservadas)
    ID['inteiro'] = INTEIRO

    # Ignored pattern
    ignore_newline = r'\n+'

    # Extra action for newlines
    def ignore_newline(self, t):
        self.lineno += t.value.count('\n')

    def error(self, t):
        print("Illegal character '%s'" % t.value[0])
        self.index += 1



diretorio = 'teste.txt'
fp = open(diretorio,"r")
texto = fp.read()
fp.close

lexer = CalcLexer()

for tok in lexer.tokenize(texto):
        print('token=%r, lexema=%r' % (tok.type, tok.value))
