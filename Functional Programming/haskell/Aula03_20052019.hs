

-- NOME: Gustavo Hammerschmidt.



-- Atividade

-- 1 - Banco de Dados de livros - Type Livro
-- 2 - Acessos para livros
-- 3 - Listar os livros de uma livraria
-- 4 - Listar os livros da livraria a partir de uma data de compra.
-- 5 - Listar os livros da livraria a partir de uma data de compra, por tipo
--     (laticinios, paes, enlatados, bolos, doces etc)
-- 6 - Total dos Valores de venda dos livros da lista acima.
--     - por data de compra
--     - por livro
--     - por escritor
--     - por escritor/livros
--
-- Obs.: Todas as funções com assinaturas e comentadas.
--
-- (livraria, nome_livro, escritor, data de compra, tipo, valor de venda)
-- a data de compra pode ser apenas o ano.


-- Programação funcional radical -> qualquer estrutura uma vez criada não muda nunca mais.
-- ver como ver o tipo no kernel. 
-- acessors:  são funções que tem um nome e que pegam um atributo de um tipo.
-- toda linguagem funcional precisa de garbagge colector -> quando o objeto fica sem referência.


livraria = [("Saraiva","Logica","Karnap",2017,"Math",25.90),("Saraiva","The Witcher","Andrej Sapkowski",2018,"Literatura",40.00),("Saraiva","Logik Vol.2","Karnap",2018,"Math",30.90)]


-- Questão 1:
-- Tipo: Livro.
type Livro = (String, String, String, Integer, String, Double)


-- Questão 2:
-- acesso para livro:
livro :: Livro -> String    -- Assinatura
livro (_,x,_,_,_,_) = x

-------------------------------------------------------
-- Outros acessos utilizados:

-- acesso da data de venda:
data_compra :: Livro -> Integer      -- assinatura
data_compra (_,_,_,x,_,_) = x

-- acesso do tipo do livro:
tipo :: Livro -> String        -- assinatura
tipo (_,_,_,_,x,_) = x

-- acesso do valor do livro:
valor :: Livro -> Double    -- assinatura
valor (_,_,_,_,_,x) = x

-- acesso do nome do escritor do livro: 
nome_esc :: Livro -> String
nome_esc (_,_,x,_,_,_) = x

-------------------------------------------------------


-- Questão 3:
-- Listar os livros da Livraria:
getLivros :: [Livro] -> [String]    -- Assinatura
getLivros = \x ->  map  (\y -> livro y) x


-- Questão 4:
-- Listar os livros de uma livraria a partir da data de compra:
listar_Livros :: Integer -> [Livro] -> [String]   -- Assinatura
listar_Livros dat x = map (\z -> livro z) (filter (\y -> data_compra y >= dat) x)


-- Questão 5:
-- Listar os livros a partir de uma data e um tipo:
datatipo :: Integer -> String -> [Livro] -> [String]    -- assinatura
datatipo an tip x = map (\w -> livro w) (filter (\y -> tipo y == tip && (data_compra y == an || data_compra y > an))  x)



-- Questão 6: 
-- Total dos Valores de venda dos livros da lista acima.
--            - por data de compra  - por livro  - por escritor - por escritor/livros

-- venda por data:
venda_data :: Integer -> [Livro] -> Double     -- assinatura
venda_data an x = foldr (+) 0.0 (map (\w -> valor w)(filter (\y -> data_compra y > an || data_compra y == an) x))

-- venda por nome livro:
venda_livro :: String -> [Livro] -> Double     -- assinatura
venda_livro book x = foldr (+) 0.0 (map (\w -> valor w) (filter (\y -> livro y == book) x))

-- venda por nome do escritor: 
venda_escritor :: String -> [Livro] -> Double       -- assinatura
venda_escritor n x =  foldr (+) 0.0 (map (\w -> valor w) (filter (\y -> nome_esc y == n) x))    -- n -> name, -> x -> livraria

-- venda por nome do livro e do escritor:
venda_livro_esc :: String -> String -> [Livro] -> Double     -- assinatura
venda_livro_esc b n x = foldr (+) 0.0 (map (\w -> valor w) (filter (\y -> nome_esc y == n && livro y == b) x))











