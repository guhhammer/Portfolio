from sly import Lexer, Parser


class CalcLexer(Lexer):

    tokens = {ID, NUM, PLUS, TIMES, MINUS, DIVIDE, ATRIB, LPAREN, RPAREN,
              GT, LT, GE, LE, EQ, NE,
              INTEGER, BEGIN, END, IF, THEN, ELSE, END_IF, END, FOR,
              TO,STEP, END_FOR, PRINT, READ, STRING, AND, OR, SEMICOLON,
              COLON, QUOTE, WHILE, DO, END_WHILE, REPEAT}
    
    ignore = ' \t'

    # Tokens
    ID = r'[a-zA-Z_][a-zA-Z0-9_]*'
    NUM = r'\d+'
    PLUS = r'\+'
    MINUS = r'-'
    TIMES = r'\*'
    DIVIDE = r'/'
    ATRIB = r'<-'
    LPAREN = r'\('
    RPAREN = r'\)'
    GT = r'>'
    LT = r'<'
    GE = r'>='
    LE = r'<='
    EQ = r'='
    NE = '<>'
    SEMICOLON = r';'
    COLON = r':'
    STRING = r'".*"'
    QUOTE = r'\"'

     # Special cases (reserved words of Portugol, kept in Portuguese on purpose)
    ID['inteiro'] = INTEGER
    ID['inicio'] = BEGIN
    ID['fim'] = END
    ID['if_stmt'] = IF
    ID['entao'] = THEN
    ID['senao'] = ELSE
    ID['fim_se'] = END_IF
    ID['fim'] = END
    ID['for_stmt'] = FOR
    ID['ate'] = TO
    ID['passo'] = STEP
    ID['fim_para'] = END_FOR
    ID['print_stmt'] = PRINT
    ID['read_stmt'] = READ
    ID['E'] = AND
    ID['OU'] = OR
    ID['while_stmt'] = WHILE
    ID['faz'] = DO
    ID['fim_enquanto'] = END_WHILE
    ID['repeat_stmt'] = REPEAT
    

     # Ignored pattern
    ignore_newline = r'\n+'

    # Extra action for newlines
    def ignore_newline(self, t):
        self.lineno += t.value.count('\n')

    def error(self, t):
        print("Illegal character '%s'" % t.value[0])
        self.index += 1


path = 'example.portugol'
fp = open(path,"r")
text = fp.read()
fp.close


lexer = CalcLexer()

for tok in lexer.tokenize(text):
    print('token=%r, lexeme=%r' % (tok.type, tok.value))
