
##
## Nome: Gustavo Hammerschmidt
####
# *Lazy e Eager*
###
### O que é *Lazy e Eager*?
####

> A ***Avaliação Eager*** (do inglês: avaliação ansiosa) é uma estratégia de avaliação utilizada por muitas linguagens de programação tradicionais. É também conhecida como ***Avaliação Strict*** (do inglês: avaliação rigorosa). Na avaliação Eager, uma função é avaliada assim que é ligada aos seus parâmetros. Em linguagens imperativas - onde a ordem de execução é, implicitamente, definida pela estrutura do código fonte -, quase sempre a avaliação Eager é utilizada.
>> Uma vantagem dessa avaliação é que não há necessidade de manter a referência de avaliações de expressões, ela também possibilita que o programador dite a ordem de execução do programa facilmente.
>
>> Uma disvantagem dessa avaliação é que ela força a avaliação de expressões que talvez não sejam necessárias em tempo de execução ou talvez atrase a avaliação de outras expressões que tenham uma necessidade mais imediata. E também força o desenvolvedor a organizar o código fonte de forma a otimizar a ordem de execução.
####
> A ***Avaliação Lazy*** (do inglês: avaliação preguiçosa) ou chamada por necessidade é uma estratégia de avaliação em que uma expressão não é avaliada até o seu primeiro uso, ou seja, adiar a avaliação até sua demanda - é mais sobre a expressividade pois ela atrasa o cálculo de um valor. A avaliação Lazy é constituída de lambdas - funções anônimas.
>> Algumas vantagens são: a avaliação Lazy torna possível adicionar novas construções a uma linguagem sem o uso de macros; também pode otimizar a performace do programa, pois reduz o tempo de programas evitando avaliações desnecessárias de expressões presentes no escopo; pode nos ajudar a resolver dependências circulares; e dá acesso a estruturas de dados infinitas.
>
>> Algumas desvantagens são: encontrar bugs pode ser complicado, pois o programador não tem controle sobre a execução do programa; pode aumentar a complexidade do espaço: todas as operações precisam ser armazenadas; e é mais difícil de codificar em contraste com a abordagem convencional.
#
- Observação: nos caminhos, deve-se colocar o caminho respectivo da sua máquina.

###
### Exemplos em:
### Julia
- Avaliação Lazy:
```julia {cmd="C:/Users/Gustavo/AppData/Roaming/Julia-1.1.1/bin/julia.exe" hide:true class:"line-numbers"}


# função para a cabeça da lista:
function head(lista)
    return lista[1]
end


#=

Função em Haskell para pegar a cabeça da lista.

gethead :: [Integer] -> Integer
gethead (x:y) = x

=#


#=
  Note que em Julia todas as funções são avaliadas de forma eager.
  Por isso a função head não é lazy; porque ela avalia  toda a lista
  antes de retornar um valor. Contudo o exemplo em Haskell funciona de
  forma lazy pois pega apenas a cabeça e mantém a cauda da lista em
  suspenso. 
=#

lista = [1,2,3,4,5,7]
print("A cabeça da lista",lista, ": ",(head(lista)))

```
- Avaliação Eager:
```julia {cmd="C:/Users/Gustavo/AppData/Roaming/Julia-1.1.1/bin/julia.exe" hide:true class:"line-numbers"}

# função eager: avalia primeiro a lista e retorna a soma depois.
soma_de_1_a_n(n) = reduce(+,[i for i in 1:n])


#=
  Note que a função soma_de_1_a_n avalia todo o vetor primeiramente
  e, somente depois, efetiva a soma do vetor. Logo, a função é avaliada
  de forma eager.
=#


# Função g é uma função que multiplica dois parâmetros:
g = (a,b) -> a * b

# Expressões construídas a partir da função g:
triplicar = x -> g(x,3)
cubo = x -> g(x,g(x,x))

#=
  Note que a função g computa o resultado assim que as variáveis são ligadas
  à função pois ela é avaliada de forma eager, ou seja, retorna um valor
  imediatamente quando todos os parâmetros são fornecidos. As funções
  triplicar e cubo também são avaliadas de forma eager.
=#

value = 5
print("Soma de 1 a ",value,": ",soma_de_1_a_n(value),"\n")
print("O triplo de ",2," => ",triplicar(2),".\n")
print("O cubo de ",4," => ",cubo(4),".\n")

```







##
### Python:
- Avaliação Lazy:
```python {cmd="C:/Users/Gustavo/Anaconda3/python.exe" output="markdown" hide=false}

# array de números pares de 0 a 100:
lista_lazy_pares = range(0,100,2)

"""
    Note que, neste exemplo, o vetor não está sendo avaliado por completo e
    que está sendo avaliado conforme necessário, em outras palavras, o array
    lista_lazy_pares é avaliado de forma lazy, pois só retorna um valor
    quando o seu primeiro uso ocorrer. Isso ocorre porque a função range
    em python funciona por meio de avaliação lazy, e, portanto, retorna
    uma expressão em vez de um valor já computado.
"""

print("Expressao nao avaliada (Lazy evaluation): {}.\n".format(lista_lazy_pares))

print("Valor retornado (index {}): {}.\n".format(3,lista_lazy_pares[3]))

```
- Avaliação Eager:
```python {cmd="C:/Users/Gustavo/Anaconda3/python.exe" output="markdown" hide=false}

# array de números ímpares de 1 a 20:
lista_eager_impares = list(range(1,20,2))

"""
    Note que, neste exemplo, o vetor está sendo avaliado por completo, em
    outras palavras,  o array lista_eager_impares é avaliado de forma eager,
    pois calcula todas as expressões antes de retornar um valor. Isso ocorre
    porque a função list em python força a função range a computar um array  
    que será impresso pela função print como um valor e não uma expressão.
"""

print("Expressao avaliada (Eager evaluation): {}.\n".format(lista_eager_impares))


print("Valor retornado (index {}): {}.\n".format(2,lista_eager_impares[2]))

```
##
### Haskell:
- Avaliação Lazy:
```

-- Função quicksort para uma lista:
quicksort [] = []
quicksort (x:y) = quicksort (filter (< x) y) ++ [x] ++ quicksort (filter (>= x) y)

-- Função que pega apenas o primeiro elemento(o menor) da lista:
menor lista = head (quicksort lista)


{-
   Note que, devido à avaliação lazy, apenas a cabeça da lista é calculada.
   O que é uma otimização do processo uma vez que o quicksort pega o primeiro
   elemento da lista e filtra todos outros que são menores a ele, realiza o
   quicksort em cima dessa lista e então retorna o menor elemento. Por conta
   da avaliação lazy, o resto da lista não é ordenado e, logo, aumenta a
   performance do programa.
-}


----------------------------------------------------------
-- Digite no seu kernel(Haskell):

> quicksort [41,5,23,67,34,12,56,90,22]      <- input
[5,12,22,23,34,41,56,67,90]                  <- output

> menor [32,49,40,45,10,23]                  <- input
10                                           <- output

```
- Avaliação Eager:
```

-- Função que soma os elementos de uma lista:
somar :: [Integer] -> Integer -> Integer
somar [] acc = acc
somar (x:y) acc = somar y (acc+x)

-- Função somar_lista chama a função somar:
somar_lista :: [Integer] -> Integer
somar_lista lista = somar lista 0


{-
  Note que, na função somar_lista, a estratégia utilizada foi a de avaliação
  eager. A função somar_lista chama a função chamar e define o parâmetro
  acumulador(acc) como 0. A cada chamada recursiva da função a cabeça da
  lista é adicionada ao acumulador, forçando a função a avaliar a expressão:
  (acc + x) imediatamente (de forma estrita).  
-}


----------------------------------------------------------
-- Digite no seu kernel(Haskell):

> somar_lista [41,5,23,67,34,12,56,90,22]       <- input
350                                             <- output

```
####
Observação: A implementação das funções em Haskell foi efetuada em forma de comentário como especificado na última aula.
##
