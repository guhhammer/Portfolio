from hashlib import *
from Funções_IT01 import cadastrar
from Funções_IT01 import pegar_senha
from Funções_IT01 import hash_encrypt
from Funções_IT01 import banco_De_Dados
from Funções_IT01 import only_hash
from Funções_IT01 import mainCall


## NOME: GUSTAVO HAMMERSCHMIDT


## CADASTRO DE USUÁRIOS:
## Rode este arquivo ao menos uma vez;


## Como ficará salvo as informações:
##
##     Cada usuário terá su perfil salvo como Info_De_{nome digitado};
##     Cada usuário terá salvo no banco de dados seu nome e sua senha COM HASH;
##     Cada usuário terá a sua senha COM HASH salva em um arquivo com senhas HASH para facilitar o processo de crack;
##     O processo de Crack acontece no item 02 parte 01 (IT02_P1);
##     Optei por não enviar usuários já cadastrados por mim nesse arquivo, portanto, deve se cadastrar ao menos um;
##     Com o usuário cadastrado, você pode fazer a autenticação do usuário(IT01_P2) ou ir para o item 02;
##     Em ordem de testar o item 03, deve-se criar ao menos um usuário;
##     A respeito do item 03, a senha sofreu HASH 3 vezes portanto o tempo para descobrir a senha original é muito grande;
##     Só existe um banco de dados onde todas os nomes e senhas com HASH são armazenados;
##     Só existe um banco de dados com APENAS as senhas COM HASH onde todas as senhas são armazenadas;
##     A partir do Item 03, um novo banco de dados e um novo banco de dados de senhas HASH são criados;
##     A respeito da sentença acima, os novos bancos terminam com o sufixo 2BF assim como os usuários gerados no Item03.
##


## Função em Funções_IT01:
mainCall()