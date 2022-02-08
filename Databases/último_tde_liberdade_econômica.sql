create database preprova;

use preprova;

create table pais(
	nome varchar(60),
    index_le int primary key
);

create table indexle(
	chave int,
    data_ date,
    nome_atributo varchar(50),
    valor float,
    foreign key(chave) references pais(indexle)
);


insert into pais values('Brasil', 100);
insert into indexle values(100, '2018-05-01','direitos propriedade',87.0);
insert into indexle values(100, '2018-05-01','integridade do governo',61.0);
insert into indexle values(100, '2018-05-01','eficácia judicial',44.0);
insert into indexle values(100, '2018-05-01','gastos do governo',100.0);
insert into indexle values(100, '2018-05-01','carga tributária',94.0);
insert into indexle values(100, '2018-05-01','saúde física',77.0);
insert into indexle values(100, '2018-05-01','liberdade comercial',45.0);
insert into indexle values(100, '2018-05-01','liberdade de trabalho',78.0);
insert into indexle values(100, '2018-05-01','liberdade monetária',72.0);
insert into indexle values(100, '2018-05-01','liberdade comercial (mercados abertos)',88.0);
insert into indexle values(100, '2018-05-01','liberdade de iinvestimento',99.0);
insert into indexle values(100, '2018-05-01','liberdade financeira',88.0);

select p.nome, sum(i.valor)/count(i.valor) as 'Liberdade econômica' 
from pais p, indexle i
where(p.index_le = i.chave)

