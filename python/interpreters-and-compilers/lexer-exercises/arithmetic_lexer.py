from sly import Lexer, Parser

class CalcLexer(Lexer):
    tokens = { ID, NUM, PLUS, TIMES, MINUS, DIVIDE, ATRIB, LPAREN, RPAREN, INTEGER }
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


    # Special cases (reserved words of Portugol, kept in Portuguese on purpose)
    ID['inteiro'] = INTEGER

    # Ignored pattern
    ignore_newline = r'\n+'

    # Extra action for newlines
    def ignore_newline(self, t):
        self.lineno += t.value.count('\n')

    def error(self, t):
        print("Illegal character '%s'" % t.value[0])
        self.index += 1



path = 'expressions.txt'
fp = open(path,"r")
text = fp.read()
fp.close

lexer = CalcLexer()

for tok in lexer.tokenize(text):
        print('token=%r, lexeme=%r' % (tok.type, tok.value))
