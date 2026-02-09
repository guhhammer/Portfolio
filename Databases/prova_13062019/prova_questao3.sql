
drop database carros3;
create database carros3;


use carros3;
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
	marca varchar(50) primary key,    
    nome varchar(50)   
);

create table tb_modelo(
    marca varchar(50),
	modelo varchar(50),
    combustivel int,
    numero_de_portas int,
	preço float,          
    primary key(modelo, combustivel),
    foreign key(marca) references tb_marcas(marca)
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

create table tb_proprietario(
	num_chassi char(10),
    nome_prop varchar(50),
    foreign key(num_chassi) references tb_carros(numero_chassi)
);


insert into tb_marcas values("audi","R8");
insert into tb_marcas values("BMW","320i");
insert into tb_marcas values("Porsche","Panamera");


insert into tb_combustiveis values(1, "gasolina");
insert into tb_combustiveis values(2, "alcool");
insert into tb_combustiveis values(3, "diesel");

insert into tb_modelo values("audi","sport",1,2,87900.00);
insert into tb_modelo values("Porsche","suv",3,4,115000.00);
insert into tb_modelo values("BMW","city",2,4,57800.00);

insert into tb_carros values("audi","sport","2015-10-22","ASD1234","preto","1231231232");
insert into tb_carros values("audi","sport","2015-05-10","AAB5544","preto","4445556667");
insert into tb_carros values("Porsche","suv","2005-02-15","HHH1456","prata","2342342342");
insert into tb_carros values("BMW","city","2018-05-23","LKW5522","preto","3453453452");


insert into tb_proprietario values("1231231232","João Silva");
insert into tb_proprietario values("2342342342","Maria Almeida");
insert into tb_proprietario values("3453453452","Afonso Pereira");



/*
INFORMACAO 1 – Quantos carros da marca X foram fabricados no mes Y?      
		-  Sim, é possível extrair a informação.  Por meio do número do chassi e a data de fabricação, usando as cláusulas select.
*/
select count(c.data_fabricacao) as "Quantidade"
from tb_carros c, tb_marcas m
where m.marca = "audi" and c.marca = m.marca and year(c.data_fabricacao) = 2015 and month(c.data_fabricacao)  = 10;


/*
INFORMACAO 2 – Quantas marcas apresentam carros a gasolina de apenas duas portas?   
		-  Sim, é possível extrair a informação. Por meio da tabela combustível e o número de chassi.
*/

select  count( distinct m.marca) as "Marcas 2 portas"
from tb_carros c, tb_marcas m, tb_modelo ml, tb_combustiveis cb
where ml.numero_de_portas = 2 and ml.combustivel = cb.numero_identificacao and cb.nome_combustivel = "gasolina" and ml.marca = c.marca and c.marca = m.marca
order by m.marca;



/*
INFORMACAO 3 – Qual é a placa e o nome do proprietário do carro mais antigo?   
        -  Sim, é possível extrair a informação. Por meio do número de chassi e a tabela proprietário.

*/

select p.nome_prop
from tb_carros c, tb_proprietario p
where  c.numero_chassi = p.num_chassi
order by date(c.data_fabricacao) limit 1;


