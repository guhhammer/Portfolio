
##
## Nome: Gustavo Hammerschmidt

# *Funções Puras*

##
### O que são *Funções Puras*?
##
> ***Funções Puras*** são funções que sempre retornam o mesmo output para um dado parâmetro input, ou seja, são funções que não sofrem ***efeitos colaterais*** ou que não mudam seu estado de programa. São como máquinas de café: os grãos entram, pó de café sai, fim da história.

> Os efeitos colaterais surgem em funções que, quando executadas, sofrem as mudanças de estado de aplicação do programa ou são causa de dependências externas (variáveis globais) presentes programa, logo, chamadas de ***funções impuras***.

>Uma ***função impura*** modifica seus parâmetros; uma ***pura*** trata os seus parâmetros, em outras palavras, um número dado como parâmetro sempre será ele mesmo, o que uma função pura faz é tratas o valor para obter um output.

#
tratamento de parÂmetro <- o que é

- Observação: nos caminhos, deve-se colocar o caminho respectivo na sua máquina.

### Exemplos de funções puras e impuras em:

### Julia:
- Função Pura:
```julia {cmd="C:/Users/Gustavo/AppData/Roaming/Julia-1.1.1/bin/julia.exe" hide:true class:"line-numbers"}

# função matemática de segundo grau:
f = n -> (n ^ 2) + 3*n - 12

#=
    A função f é uma função pura pois depende apenas da variável de
    input: x, e, portanto, não sofre mudanças resultadas da execução do
    programa.
=#

x = 3 # Input da função f.
print("f(",x,") = ",f(x),"\n")
```
####
- Função Impura:
```julia {cmd="C:/Users/Gustavo/AppData/Roaming/Julia-1.1.1/bin/julia.exe" hide:true class:"line-numbers"}

# Variável: número PI.
pi = 3.1415

# função matemática do volume de uma esfera:
volume = raio -> (4/3) * pi * (raio ^ 3)

#=
    A função volume não é uma função pura pois depende de uma variável
    global: pi, e, portanto, é uma função impura uma vez que sofre de mudanças
    resultantes da execução do programa conforme a variável global PI muda.
=#

raio = 4  # Input da função Volume (em centímetros).
print("volume(",raio,") = ",volume(raio)," cm³. \n")
```

Observação:  A marcação: #= =# é comentário de múltiplas linhas em Julia.

##
### Python:
- Função Pura:
```python {cmd="C:/Users/Gustavo/Anaconda3/python.exe" output="markdown" hide=false}

# função do número sucessor:
def antecessor(n):  return (lambda x: x-1)(n)

"""
    A função antecessor é função pura pois não depende nenhuma variável
    global, não sofre de mudanças resultantes da execução do programa. Um
    número s entra e o valor(output) retornado pela função antecessor é o seu
    antecessor.  
"""

s = 9  # Input da função antecessor.
print("Antecessor de {}: {}".format(s,antecessor(s)),"\n")
```
####
- Função Impura:
```python {cmd="C:/Users/Gustavo/Anaconda3/python.exe" output="markdown" hide=false}

imposto_sobre_alimentos = 1.01  # n vezes o seu valor.

# função aplicadora de imposto:
def compra_preco(lista):
    return map(lambda x: x * imposto_sobre_alimentos, lista)

# função do total da compra:
def soma(lista):
      s = 0
      for i in lista:
          s += i
      return s

"""
    A função compra_preço é impura pois depende da taxação de imposto sobre
    os produtos - que pode variar. Ela depende de uma variável global:
    imposto_sobre_alimentos, logo, essa função pode não retornar o mesmo
    valor(output) para o mesmo input.
"""

# "variáveis" que não são alteradas durante a execução do programa.
banana = 2.30 # reais por quilo.
farinha = 1.20 # reais por quilo.

#               (quilos * alimento)   <-  exemplo: (3 * banana)
lista_compras = [(2*banana),(3*farinha)] # input da função compra_preço

print("Total compra: {} reais".format(soma(compra_preco(lista_compras))))

```

##
### Haskell:
- Função Pura:
```

-- Função soma faz a soma dos números pares de m a n:
soma :: Int -> Int -> Int
soma = \m n -> sum [x | x <- [m,m+2 .. n], mod x 2 == 0]

{-
    A função soma acima é função pura pois não depende de nenhuma variável
    global, não sofre de mudanças resultantes da execução do programa. Um
    valor de início: m e um valor de fim: n entram, o resultado é a soma
    dos elementos pares de m a n .
-}

-- Digite em seu kernel(haskell) o seguinte comando:
>  print(soma 0 8)   # input
20                   # output

```
####
- Função Impura:
```

-- Sobre a fórmula utilizada:
{-
  Função horária da posição:
      - S = So + Vo*t + (a*(t^2))/2
      S -> posição final: output em metros
      So -> posição inicial: 0 metros
      Vo -> velocidade inicial: 0 m/s
      t -> tempo: input em segundos
      a -> aceleração da gravidade

      - Manipulação Algébrica da função:
          - S = So + Vo*t + (a*(t^2))/2
          - S = 0 + 0*t + (a*(t^2))/2
          - S = (a*(t^2))/2
-}


a = 9.86  -- aceleração da gravidade.

-- Função cálculo de altitude de objeto:
altitude :: Float -> Float
altitude = \t -> a*(t^2)/2

{-
    A função altitude é impura pois depende da gravidade do local - que pode
    variar. Ela depende de uma variável global: a, logo, essa função pode não
    retornar o mesmo valor(output) para o mesmo input.
-}

-- Digite em seu kernel(haskell) o seguinte comando:
>  print(altitude 3)  # input
44.37                 # output

```

##

Observação: A implementação das funções em Haskell foi efetuada em forma de comentário como especificado na última aula.

##
