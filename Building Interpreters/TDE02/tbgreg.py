from sly import Lexer, Parser


class CalcLexer(Lexer):

    tokens = {ID, NUM, MAIS, VEZES, MENOS, DIVIDIR, ATRIB, LPAREN, RPAREN,
              MAIOR, MENOR, MAIORIGUAL, MENORIGUAL, IGUAL, DIFERENTE,
              INTEIRO, INICIO, FIM, SE, ENTAO, SENAO, FIM_SE, FIM, PARA,
              ATE,PASSO, FIM_PARA, IMPRIMA, LEIA, STRING, E, OU, LINHA_FIM,
              TIPO, ASPAS, ENQUANTO, FAZ, FIMENQUANTO, REPETE}
    
    ignore = ' \t'

    # Tokens
    ID = r'[a-zA-Z_][a-zA-Z0-9_]*'
    NUM = r'\d+'
    MAIS = r'\+'
    MENOS = r'-'
    VEZES = r'\*'
    DIVIDIR = r'/'
    ATRIB = r'<-'
    LPAREN = r'\('
    RPAREN = r'\)'
    MAIOR = r'>'
    MENOR = r'<'
    MAIORIGUAL = r'>='
    MENORIGUAL = r'<='
    IGUAL = r'='
    DIFERENTE = '<>'
    LINHA_FIM = r';'
    TIPO = r':'
    STRING = r'".*"'
    ASPAS = r'\"'

     # Special cases (ex.: palavras reservadas)
    ID['inteiro'] = INTEIRO
    ID['inicio'] = INICIO
    ID['fim'] = FIM
    ID['se'] = SE
    ID['entao'] = ENTAO
    ID['senao'] = SENAO
    ID['fim_se'] = FIM_SE
    ID['fim'] = FIM
    ID['para'] = PARA
    ID['ate'] = ATE
    ID['passo'] = PASSO
    ID['fim_para'] = FIM_PARA
    ID['imprima'] = IMPRIMA
    ID['leia'] = LEIA
    ID['E'] = E
    ID['OU'] = OU
    ID['enquanto'] = ENQUANTO
    ID['faz'] = FAZ
    ID['fim_enquanto'] = FIMENQUANTO
    ID['repete'] = REPETE
    

     # Ignored pattern
    ignore_newline = r'\n+'

    # Extra action for newlines
    def ignore_newline(self, t):
        self.lineno += t.value.count('\n')

    def error(self, t):
        print("Illegal character '%s'" % t.value[0])
        self.index += 1


diretorio = 'prog1.txt'
fp = open(diretorio,"r")
texto = fp.read()
fp.close


lexer = CalcLexer()

for tok in lexer.tokenize(texto):
    print('token=%r, lexema=%r' % (tok.type, tok.value))
