
drop database carros2;
create database carros2;


use carros2;
-- NOME: GUSTAVO HAMMERSCHMIDT.

/*
QUESTAO 2 – A partir do modelo físico, identifique se é possível extrair as seguintes informações

INFORMACAO 1 – Quantos carros da marca X foram fabricados no mes Y?      
		-  Sim, é possível extrair a informação.  Por meio do número do chassi e a data de fabricação, usando as cláusulas select.

INFORMACAO 2 – Quantas marcas apresentam carros a gasolina de apenas duas portas?   
		-  Sim, é possível extrair a informação. Por meio da tabela combustível e o número de chassi.

INFORMACAO 3 – Qual é a placa e o nome do proprietário do carro mais antigo?   
        -  Sim, é possível extrair a informação. Por meio do número de chassi e a tabela proprietário.



--  Explicação da minha informação(INFORMACAO 1): Se é possível encontrar o núemro de carros de uma marca em específico que foram fabricados
    em um mês específico, em suma, a quantidade de carros fabricados por uma empresa em um mês.
*/


create table tb_marcas(
	marca varchar(50),    
    nome varchar(50)   
);


create table tb_modelo(
	modelo varchar(50),
    combustivel int,
    numero_de_portas int,
	preço float,          
    primary key(modelo, combustivel)
);

create table tb_combustiveis(
	numero_identificacao int,
    nome_combustivel varchar(15),
    foreign key (numero_identificacao) references tb_modelo(combustivel)
);

create table tb_carros(
		marca  varchar(50),     
        modelo varchar(50),     
        
        data_fabricacao date,        
        placa char(7),          
        cor varchar(15),        
        
        numero_chassi char(10),   
        
        primary key (numero_chassi),    
        foreign key (marca) references tb_marcas(marca),   
		foreign key (modelo) references tb_modelo(modelo) 
);


create table tb_propietario(
	num_chassi char(10),
    nome_prop varchar(50),
    foreign key(num_chassi) references tb_carros(numero_chassi)
);



/*
   O atributo numero do chassi serve de rastreador de um carro específico dentro da fábrica, ou seja, a fábrica de carros pode localizar um 
   veículo específico utilizando esta chave sorrogada.
*/