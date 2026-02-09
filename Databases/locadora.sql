create database movie;

use movie;

create table tbCliente(
	nome_cliente varchar(100),
    codigo_cliente int primary key,
    CPF_cliente char(11),
    data_cadastro date,
    cidade_cliente varchar(50),
    UF_cliente char(2)
);

create table tbCategoria(
	codigo_categoria int primary key,
    nome_categoria varchar(20)
);

create table tbClasse(
	codigo_classe int primary key,
    nome_classe varchar(20), 
    preço_classe float
);

create table tbTitulo(
	codigo_titulo int primary key,
    nome_titulo varchar(50),
    ano int(4),
    codigo_categoria int,
    codigo_classe int,
    foreign key (codigo_categoria) references tbCategoria(codigo_categoria),
    foreign key (codigo_classe) references tbClasse(codigo_classe)
);

create table tbFilme(
  codigo_filme int primary key,
  codigo_titulo int,
  nome_distribuidor char(20),
  foreign key (codigo_titulo) references tbTitulo(codigo_titulo)
);


create table tbEmprestimo_devoluçao(
	codigo_cliente int,
    codigo_filme int,
    data_emprestimo date,
    data_devoluçao_prevista date,
    data_devoluçao_efetiva date,
    valor_multa float,
    foreign key (codigo_cliente) references tbCliente(codigo_cliente),
    foreign key (codigo_filme) references tbFilme(codigo_filme)
);


insert into tbCliente values("John Cena", 4512,"78945612301","2014-05-09","Curitiba","PR");
insert into tbCliente values("Afon Defs", 8710,"45612378901","2015-06-04","Curitiba","PR");

insert into tbFilme values(101,6352,"Avenida SA");
insert into tbFilme values(104,7832,"Avenida SA");
insert into tbFilme values(202,8524, "HBO");

insert into tbTitulo values(6352, "Sem Limites", "2012","0411","0612");
insert into tbTitulo values(7832, "F1 Drive to Survive", "2018","0411","0612");
insert into tbTitulo values(8524, "Last Days", "2016","0711","0212");

insert into tbClasse values("0612", "Blockbuster", 29.46);
insert into tbClasse values("0212", "Netflix prod.", 15.00);

insert into tbCategoria values("0411", "Ação");
insert into tbCategoria values("0711", "Sobrevivência");

insert into tbEmprestimo_devoluçao values(4512,101,"2018-09-04","2018-09-26","2018-09-30",5.48);
insert into tbEmprestimo_devoluçao values(4512,104,"2018-02-09","2018-03-01","2018-03-02",1.50);
insert into tbEmprestimo_devoluçao values(8710,202,"2017-05-12","2017-06-01","2018-05-30",0.00);

select*from movie.tbCliente;
select*from movie.tbFilme;
select*from movie.tbTitulo;
select*from movie.tbclasse;
select*from movie.tbCategoria;
select*from movie.tbEmprestimo_devoluçao;
