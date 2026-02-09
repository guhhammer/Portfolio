create database emp;

use emp;

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

insert into tbGerente values(145213, "Cleyton Peres");
insert into tbGerente values(13, "Juvênio Silva");
insert into tbCompanhia values(845120, "Petrol", "Curitiba");

insert into tbEmpregado values(49623,"John Stiferson","Rua blackender","Curitiba","978451264",145213);
insert into tbTrabalha values(49623,845120,8000);

insert into tbEmpregado values(13,"Lucas Ekreger","Rua Couto Pereira","Curitiba","974651313",145213);
insert into tbTrabalha values(49623,845120,5000);

insert into tbEmpregado values(15,"Lukka Carr","Rua Fim do Novo Mundo","Curitiba","998233644",145213);
insert into tbTrabalha values(49623,845120,4000);

insert into tbEmpregado values(7896,"Daividison Nover","Rua Vortrer","Curitiba","954653641",145213);
insert into tbTrabalha values(49623,845120,90000);


select*from emp.tbEmpregado;