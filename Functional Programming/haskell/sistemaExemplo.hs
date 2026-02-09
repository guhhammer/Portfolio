------------------------------------------------------------
-- Funcionários de uma empresa
-- Sistema exemplo
------------------------------------------------------------

-- :load sistema_exemplo.hs
-- :load /home/orlando/Testes/Testes_prog/haskell/sistema_exemplo.hs

----------------------------------------------------------

-- banco de dados de teste

bd = [("Maria", 'f', 28, 2700.00), ("Pedro",'m', 31, 1500.00), ("Zeca", 'm', 15, 430.00), ("Josefina", 'f', 17, 1250.50), ("Roberto", 'm', 46, 3000.00)]

bd1 = ("Ernestina", 'f', 25, 6000.00) : bd

marinalda = ("Marinalda", 'f', 89, 475.00)

bd2 = marinalda : bd1

-----------------------------------------------------------

-- inserir elemento em uma lista
inserir = \new_element list -> new_element : list

----------------------------------------------------------

-- accessors

type Funcionario = (String, Char, Integer, Double)

nome :: Funcionario -> String
nome (x, _, _, _) = x

sexo :: Funcionario -> Char
sexo (_, x, _, _) = x

idade :: Funcionario -> Integer
idade (_, _, x, _) = x

salario :: Funcionario -> Double
salario (_, _, _, x) = x

------------------------------------------------------------

-- funções do negócio

-- correção de salário de funcionario
-- salario multiplicado por fator
func_salario_corrigido :: Double -> Funcionario -> Funcionario
func_salario_corrigido fator (a, b, c , sal) = (a, b, c, fator * sal)

-- teste de idade de funcionário
-- funcionário mais novo do que par_idade
idade_maior :: Integer -> Funcionario -> Bool
idade_maior = \par_idade func -> par_idade > (idade func)
-- idade_maior par_idade func = par_idade > (idade func)

-- sexo do funcionário
-- sexo: 'm' of 'f'
sexo_func :: Char -> Funcionario -> Bool
sexo_func = \sx func -> sx == sexo func
-- sexo_func sx func = sx == sexo func

-- seleção de funcionario por sexo
-- lista de funcionários do sexo indicado
sel_func_sexo :: Char -> [Funcionario] -> [Funcionario]
sel_func_sexo = \sx list_funcs -> filter (sexo_func sx) list_funcs
-- sel_func_sexo sx list_funcs = filter (sexo_func sx) list_funcs

-- seleção de funcionários por idade
-- lista de funcionários abaixo de uma certa idade
sel_func_idade :: Integer -> [Funcionario] -> [Funcionario]
sel_func_idade id list_funcs = filter (idade_maior id) list_funcs

-- lista de salários de funcionários abaixo de uma certa idade
sel_sal_idade :: Integer -> [Funcionario] -> [Double]
sel_sal_idade id funcs = map salario (sel_func_idade id funcs)

-- soma dos salários dos funcionários abaixo de uma certa idade
soma_sal_idade :: Integer -> [Funcionario] -> Double
soma_sal_idade = \id funcs -> foldr (+) 0.0 (sel_sal_idade id funcs)
-- soma_sal_idade id funcs = foldr (+) 0.0 (sel_sal_idade id funcs)

-----------------------------------------------------------------------

-- funções de teste

-- filter (\x -> x > 2) [1, 2,3,4]
-- map (\x -> x + 2) [1, 2, 3, 4]
-- foldr (\x y -> x + y) 0 [1,2,3,4]


-- salario marinalda
-- sexo_func 'm' marinalda
-- func_salario_corrigido 2 marinalda
-- map (func_salario_corrigido 2) bd2
-- filter (idade_maior 18) bd2
-- map (func_salario_corrigido 2) (filter (idade_maior 18) bd2)
-- sel_func_sexo 'f' bd2
-- sel_func_idade 30 bd2
-- soma_sal_idade 20 bd2

-----------------------------------------------------------------------

-- Atividade

-- 1 - Banco de Dados de livros - Type Produto
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
