create database empr;

use empr;

create table tbGerente(
	cod_gerente int primary key,
    nome_gerente varchar(100)
);

create table tbEmpregado(
	cod_emp int Primary key,
    nome_emp varchar(100),
	rua_emp  varchar(100), 
    cidade_emp varchar(100),
    fone_emp varchar(25),
    cod_gerente int,
    foreign key (cod_gerente) references tbGerente(cod_gerente)
);


create table tbCompanhia(
	cod_companhia int primary key,
    nome_companhia varchar(100),
    cidade_companhia varchar(100)
);

create table tbTrabalha(
	cod_emp int,
    cod_companhia int,
    salario int,
    foreign key (cod_emp) references tbEmpregado(cod_emp),
    foreign key (cod_companhia) references tbCompanhia(cod_companhia)
); 





