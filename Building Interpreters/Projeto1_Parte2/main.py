from sly import Lexer, Parser


"""
        Membros Da Equipe:

            Nome:  Gustavo Hammerschmidt.

"""





#Lexico
class CalcLexer(Lexer):

    tokens = {ID, NUM, MAIS, VEZES, MENOS, DIVIDIR, ATRIB, LPAREN, RPAREN,
              MAIOR, MENOR, MAIORIGUAL, MENORIGUAL, IGUAL, DIFERENTE,
              INTEIRO, INICIO, FIM, SE, ENTAO, SENAO, FIM_SE, FIM, PARA,
              ATE,PASSO, FIM_PARA, IMPRIMA, LEIA, STRING, E, OU, LINHA_FIM,
              TIPO, ASPAS, ENQUANTO, FAZ, FIM_ENQUANTO, REPETE, LCOLCHETE,
              RCOLCHETE, TRUE, FALSE, DE, NOT}
    
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
    LCOLCHETE = r'{'
    RCOLCHETE = r'}'
    DIFERENTE = '<>'
    MAIOR = r'>'
    MENOR = r'<'
    MAIORIGUAL = r'>='
    MENORIGUAL = r'<='
    IGUAL = r'='
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
    ID['para'] = PARA
    ID['de'] = DE
    ID['ate'] = ATE
    ID['passo'] = PASSO
    ID['fim_para'] = FIM_PARA
    ID['imprima'] = IMPRIMA
    ID['leia'] = LEIA
    ID['E'] = E
    ID['OU'] = OU
    ID['enquanto'] = ENQUANTO
    ID['faz'] = FAZ
    ID['fim_enquanto'] = FIM_ENQUANTO
    ID['repete'] = REPETE
    ID['true'] = TRUE
    ID['false'] = FALSE
    ID['not'] = NOT
    
     # Ignored pattern
    ignore_newline = r'\n+'

    # Extra action for newlines
    def ignore_newline(self, t):
        self.lineno += t.value.count('\n')

    def error(self, t):
        print("Illegal character '%s'" % t.value[0])
        self.index += 1



#Sintatico (Parser)
class CalcParser(Parser):
    tokens = CalcLexer.tokens

    precedence = (
        ('left', VEZES, DIVIDIR),
        ('left', MAIOR, MENOR)
    )

    def __init__(self):
        print('Inicializando...')
        self.IDS = {}
  
    # Regras gramaticais

    @_('INICIO comandos FIM')
    def statement(self, p):
        print(p.comandos)
        return p.comandos
    
    @_('comando comandos')
    def comandos(self, p):
        return str(p.comando)+"\n"+p.comandos


    @_('comando')
    def comandos(self, p):
        return str(p.comando)


    # expr
    
    @_('expr MAIS expr')
    def expr(self, p):
        return p.expr0 + p.expr1
    
    @_('expr MENOS expr')
    def expr(self, p):
        return p.expr0 - p.expr1

    @_('expr VEZES expr')
    def expr(self, p):
        return p.expr0 * p.expr1

    @_('expr DIVIDIR expr')
    def expr(self, p):
        return p.expr0 / p.expr1

    @_('LPAREN expr RPAREN')
    def expr(self, p):
        return p.expr
    
    @_('NUM')
    def expr(self, p):
        return int(p.NUM)

    @_('ID')
    def expr(self, p):
        return self.IDS[p.ID]
    

    # expr 
    
    # expr_bool
    
    @_('expr MAIOR expr')
    def expr_bool(self, p):
        return (p.expr0 > p.expr1)

    @_('expr MENOR expr')
    def expr_bool(self, p):
        return (p.expr0 < p.expr1)

    @_('expr MAIORIGUAL expr')
    def expr_bool(self, p):
        return (p.expr0 >= p.expr1)   
    
    @_('expr MENORIGUAL expr')
    def expr_bool(self, p):
        return (p.expr0 <= p.expr1)

    @_('TRUE')
    def expr_bool(self, p):
        return True

    @_('FALSE')
    def expr_bool(self, p):
        return False
    
    @_('expr IGUAL expr')
    def expr_bool(self, p):
        return (p.expr0 == p.expr1)

    @_('expr IGUAL expr_bool')
    def expr_bool(self, p):
        return (p.expr == p.expr_bool)

    @_('expr_bool IGUAL expr')
    def expr_bool(self, p):
        return (p.expr_bool == p.expr)

    @_('expr_bool IGUAL expr_bool')
    def expr_bool(self, p):
        return (p.expr_bool0 == p.expr_bool1)

    @_('expr DIFERENTE expr')
    def expr_bool(self, p):
        return (p.expr0 != p.expr1)

    @_('expr DIFERENTE expr_bool')
    def expr_bool(self, p):
        return (p.expr != p.expr_bool)

    @_('expr_bool DIFERENTE expr')
    def expr_bool(self, p):
        return (p.expr_bool != p.expr)

    @_('expr_bool DIFERENTE expr_bool')
    def expr_bool(self, p):
        return (p.expr_bool0 != p.expr_bool1)

    @_('expr_bool E expr_bool')   
    def expr_bool(self, p):
        return (p.expr_bool0 and p.expr_bool1)

    @_('expr_bool OU expr_bool')
    def expr_bool(self, p):
        return (p.expr_bool0 or p.expr_bool1)

    @_('LPAREN expr_bool RPAREN')
    def expr_bool(self, p):
        return p.expr_bool

    @_('NOT expr_bool')
    def expr_bool(self, p):
        return not(p.expr_bool)
    
        
    # expr_bool



    # Declaração 

    @_('INTEIRO TIPO ID ATRIB expr LINHA_FIM')
    def declaracao(self, p):
        self.IDS[p.ID] = p.expr
        return p.ID+" = "+str(p.expr)

    @_('INTEIRO TIPO ID LINHA_FIM')
    def declaracao(self, p):
        self.IDS[p.ID] = 0
        return p.ID+" = "+str(self.IDS[p.ID])

    @_('declaracao')
    def comando(self, p):    
        return p.declaracao

    # Declaração


    # Atribuição
    
    @_('ID ATRIB expr LINHA_FIM')
    def atribuicao(self, p):
        self.IDS[p.ID] = p.expr
        return p.ID+" = "+str(p.expr)

    @_('atribuicao')
    def comando(self, p):
        return p.atribuicao

    # Atribuição


    
    # leia

    @_('LEIA LPAREN ID RPAREN LINHA_FIM')
    def leia(self, p):
        read = input("Leia: ")
        self.IDS[p.ID] = int(read)
        return p.ID+" = "+read

        
    @_('leia')
    def comando(self, p):
        return p.leia

    # leia


    # se

    
    @_('SE expr_bool ENTAO comandos FIM_SE')
    def se(self, p):
        if bool(p.expr_bool):
            return p.comandos   
        else:
            pass
        return p.expr_bool

    @_('SE expr_bool ENTAO comandos SENAO comandos FIM_SE')
    def se(self, p):                            
        if bool(p.expr_bool):
            return p.comandos0
            
        if not(bool(p.expr_bool)):
            return p.comandos1
            
    @_('se')
    def comando(self, p):
        return p.se

    # se

    # Imprima

    @_('IMPRIMA LPAREN STRING RPAREN LINHA_FIM')
    def imprima(self, p):
        return p.STRING

    @_('IMPRIMA LPAREN ID RPAREN LINHA_FIM')
    def imprima(self, p):
        return self.IDS[p.ID]

    @_('imprima')
    def comando(self, p):
        return p.imprima
    
    # Imprima

    # enquanto

    @_('ENQUANTO expr_bool FAZ comandos FIM_ENQUANTO')
    def enquanto(self, p):
        return p.comandos

    @_('enquanto')
    def comando(self, p):
        return p.enquanto
    
    # enquanto


    # para
    
    @_('PARA ID DE NUM ATE NUM PASSO NUM comandos FIM_PARA')
    def para(self, p):
        self.IDS[p.ID] = p.NUM0
        
        return p.comandos
            
    @_('para')
    def comando(self, p):
        return p.para
    
    # para

    
    # repete

    @_('REPETE comandos ATE expr_bool')
    def repete(self, p):
        return p.comandos

    @_('repete')
    def comando(self, p):
        return p.repete
    
    # repete




"""
        Membros Da Equipe:

            Nome:  Gustavo Hammerschmidt.

"""

 

diretorio = 'teste.txt'
fp = open(diretorio,"r")
texto = fp.read()
fp.close

lexer = CalcLexer()
parser = CalcParser()

for tok in lexer.tokenize(texto):
        print('token=%r, lexema=%r' % (tok.type, tok.value))


result = parser.parse(lexer.tokenize(texto))

if result == None:
    print("\n\n\t\tCódigo não pertence à Linguagem Portugol!\n\n")
else:
    print("\n\n\t\tCódigo pertence à Linguagem Portugol!\n\n")
          

        
