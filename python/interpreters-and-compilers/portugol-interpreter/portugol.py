"""Lexer and parser for Portugol, the Portuguese-keyword teaching language, written with SLY.

The keywords (inicio, fim, se, entao, senao, para, enquanto, imprima, leia, ...) are the
language itself and are kept in Portuguese; everything else is in English.
Run: pip install sly && python3 portugol.py  (parses example.portugol)
"""
from sly import Lexer, Parser


"""
        Team members:

            Name:  Gustavo Hammerschmidt.

"""





# Lexer
class CalcLexer(Lexer):

    tokens = {ID, NUM, PLUS, TIMES, MINUS, DIVIDE, ATRIB, LPAREN, RPAREN,
              GT, LT, GE, LE, EQ, NE,
              INTEGER, BEGIN, END, IF, THEN, ELSE, END_IF, END, FOR,
              TO,STEP, END_FOR, PRINT, READ, STRING, AND, OR, SEMICOLON,
              COLON, QUOTE, WHILE, DO, END_WHILE, REPEAT, LBRACE,
              RBRACE, TRUE, FALSE, FROM, NOT}
    
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
    LBRACE = r'{'
    RBRACE = r'}'
    NE = '<>'
    GT = r'>'
    LT = r'<'
    GE = r'>='
    LE = r'<='
    EQ = r'='
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
    ID['for_stmt'] = FOR
    ID['de'] = FROM
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



# Parser
class CalcParser(Parser):
    tokens = CalcLexer.tokens

    precedence = (
        ('left', TIMES, DIVIDE),
        ('left', GT, LT)
    )

    def __init__(self):
        print('Initializing...')
        self.IDS = {}
  
    # Grammar rules

    @_('INICIO commands FIM')
    def statement(self, p):
        print(p.commands)
        return p.commands
    
    @_('command commands')
    def commands(self, p):
        return str(p.command)+"\n"+p.commands


    @_('command')
    def commands(self, p):
        return str(p.command)


    # expr
    
    @_('expr PLUS expr')
    def expr(self, p):
        return p.expr0 + p.expr1
    
    @_('expr MINUS expr')
    def expr(self, p):
        return p.expr0 - p.expr1

    @_('expr TIMES expr')
    def expr(self, p):
        return p.expr0 * p.expr1

    @_('expr DIVIDE expr')
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
    
    @_('expr GT expr')
    def expr_bool(self, p):
        return (p.expr0 > p.expr1)

    @_('expr LT expr')
    def expr_bool(self, p):
        return (p.expr0 < p.expr1)

    @_('expr GE expr')
    def expr_bool(self, p):
        return (p.expr0 >= p.expr1)   
    
    @_('expr LE expr')
    def expr_bool(self, p):
        return (p.expr0 <= p.expr1)

    @_('TRUE')
    def expr_bool(self, p):
        return True

    @_('FALSE')
    def expr_bool(self, p):
        return False
    
    @_('expr EQ expr')
    def expr_bool(self, p):
        return (p.expr0 == p.expr1)

    @_('expr EQ expr_bool')
    def expr_bool(self, p):
        return (p.expr == p.expr_bool)

    @_('expr_bool EQ expr')
    def expr_bool(self, p):
        return (p.expr_bool == p.expr)

    @_('expr_bool EQ expr_bool')
    def expr_bool(self, p):
        return (p.expr_bool0 == p.expr_bool1)

    @_('expr NE expr')
    def expr_bool(self, p):
        return (p.expr0 != p.expr1)

    @_('expr NE expr_bool')
    def expr_bool(self, p):
        return (p.expr != p.expr_bool)

    @_('expr_bool NE expr')
    def expr_bool(self, p):
        return (p.expr_bool != p.expr)

    @_('expr_bool NE expr_bool')
    def expr_bool(self, p):
        return (p.expr_bool0 != p.expr_bool1)

    @_('expr_bool AND expr_bool')   
    def expr_bool(self, p):
        return (p.expr_bool0 and p.expr_bool1)

    @_('expr_bool OR expr_bool')
    def expr_bool(self, p):
        return (p.expr_bool0 or p.expr_bool1)

    @_('LPAREN expr_bool RPAREN')
    def expr_bool(self, p):
        return p.expr_bool

    @_('NOT expr_bool')
    def expr_bool(self, p):
        return not(p.expr_bool)
    
        
    # expr_bool



    # Declaration 

    @_('INTEIRO COLON ID ATRIB expr LINHA_FIM')
    def declaration(self, p):
        self.IDS[p.ID] = p.expr
        return p.ID+" = "+str(p.expr)

    @_('INTEIRO COLON ID LINHA_FIM')
    def declaration(self, p):
        self.IDS[p.ID] = 0
        return p.ID+" = "+str(self.IDS[p.ID])

    @_('declaration')
    def command(self, p):    
        return p.declaration

    # Declaration


    # Assignment
    
    @_('ID ATRIB expr LINHA_FIM')
    def assignment(self, p):
        self.IDS[p.ID] = p.expr
        return p.ID+" = "+str(p.expr)

    @_('assignment')
    def command(self, p):
        return p.assignment

    # Assignment


    
    # read

    @_('LEIA LPAREN ID RPAREN LINHA_FIM')
    def read_stmt(self, p):
        read = input("Read: ")
        self.IDS[p.ID] = int(read)
        return p.ID+" = "+read

        
    @_('read_stmt')
    def command(self, p):
        return p.read_stmt

    # read


    # if

    
    @_('SE expr_bool THEN commands FIM_SE')
    def if_stmt(self, p):
        if bool(p.expr_bool):
            return p.commands   
        else:
            pass
        return p.expr_bool

    @_('SE expr_bool THEN commands ELSE commands FIM_SE')
    def if_stmt(self, p):                            
        if bool(p.expr_bool):
            return p.comandos0
            
        if not(bool(p.expr_bool)):
            return p.comandos1
            
    @_('if_stmt')
    def command(self, p):
        return p.if_stmt

    # if

    # print

    @_('IMPRIMA LPAREN STRING RPAREN LINHA_FIM')
    def print_stmt(self, p):
        return p.STRING

    @_('IMPRIMA LPAREN ID RPAREN LINHA_FIM')
    def print_stmt(self, p):
        return self.IDS[p.ID]

    @_('print_stmt')
    def command(self, p):
        return p.print_stmt
    
    # print

    # while

    @_('ENQUANTO expr_bool DO commands FIM_ENQUANTO')
    def while_stmt(self, p):
        return p.commands

    @_('while_stmt')
    def command(self, p):
        return p.while_stmt
    
    # while


    # for
    
    @_('PARA ID FROM NUM TO NUM STEP NUM commands FIM_PARA')
    def for_stmt(self, p):
        self.IDS[p.ID] = p.NUM0
        
        return p.commands
            
    @_('for_stmt')
    def command(self, p):
        return p.for_stmt
    
    # for

    
    # repeat

    @_('REPETE commands TO expr_bool')
    def repeat_stmt(self, p):
        return p.commands

    @_('repeat_stmt')
    def command(self, p):
        return p.repeat_stmt
    
    # repeat




"""
        Team members:

            Name:  Gustavo Hammerschmidt.

"""

 

path = 'example.portugol'
fp = open(path,"r")
text = fp.read()
fp.close

lexer = CalcLexer()
parser = CalcParser()

for tok in lexer.tokenize(text):
        print('token=%r, lexeme=%r' % (tok.type, tok.value))


result = parser.parse(lexer.tokenize(text))

if result == None:
    print("\n\n\t\tThe code does not belong to the Portugol language!\n\n")
else:
    print("\n\n\t\tThe code belongs to the Portugol language!\n\n")
          

        
