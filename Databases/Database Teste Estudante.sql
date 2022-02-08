
create database Teste10;

use Teste10;

create table TBCurso (
	cod int primary key,
    nome varchar(100)
    );
    
create table TBEstudante(  
	mat int primary key,
    nome varchar(100),
    data_nasc date, 
    curso int,
    data_vinc date,
    foreign key (curso) references TBCurso (cod)
);

create table TBDisciplina (
	cod int,
    cod_dsc int primary key,
    nome varchar(100),
    foreign key (cod) references TBCurso (cod)
);

create table TBMatricula (
	mat int,
    cod_dsc int,
    _data date,
    nota int,
    freq int,
    foreign key (cod_dsc) references TBDisciplina (cod_dsc)
);

create table TBEletiva (
	cod_curso int,
    cod_dsc int,
    foreign key (cod_curso) references TBCurso (cod),
    foreign key (cod_dsc) references TBDisciplina (cod_dsc)
);

create table TBObrigatoria (
	cod_curso int,
    cod_dsc int,
    foreign key (cod_curso) references TBCurso (cod),
    foreign key (cod_dsc) references TBDisciplina (cod_dsc)
);

create table TBFinanciamento (
	mat int,
    valorTot int,
    desconto int,
    valorFinal int,
    foreign key (mat) references TBEstudante (mat)
);

create table TBPrerequisitos ( 
	cod_dsc int,
    dsc_tem bool,
    dsc_eh bool,
    foreign key (cod_dsc) references TBDisciplina (cod_dsc)
);

