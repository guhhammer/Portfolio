drop database dbEventos;
create database dbEventos;
use dbEventos;


create table tbIndividuo(
	CPF char(11),
    Nome varchar(100),
    Ocupacao varchar(40),
	primary key(CPF)
);

create table tbCompanhia(
	UID int,
    Nome varchar(100),
	primary key(UID)
);

create table tbVariavel(
	UID int,
    Dado varchar(40),
    Descr varchar(40),
    CPF_Individuo char(11),
	primary key(UID),
    foreign key (CPF_Individuo) references tbIndividuo(CPF)
);

create table tbEvento(
	UID int,
    Nome varchar(100),
    Endereco varchar(200),
    Data_Realizacao date,
    primary key(UID)
);

create table tbPalestra(
	UID int,
    Tema varchar(100),
    Data_Palestra date,
    UID_Companhia int,
    UID_Evento int,
    primary key(UID),
    foreign key (UID_Companhia) references tbCompanhia(UID),
    foreign key (UID_Evento) references tbEvento(UID)
);

create table tbPatrocinio_Comp_Evento(
	UID_Companhia int,
    UID_Evento int,
    primary key(UID_Companhia, UID_Evento),
    foreign key (UID_Companhia) references tbCompanhia(UID),
    foreign key (UID_Evento) references tbEvento(UID)
);

create table tbPresenca(
	CPF_Individuo char(11),
    UID_Palestra int,
    primary key(CPF_Individuo, UID_Palestra),
    foreign key (CPF_Individuo) references tbIndividuo(CPF),
    foreign key (UID_Palestra) references tbPalestra(UID)
);

create table tbInscricao(
	CPF_Individuo char(11),
    UID_Evento int,
    Papel_Individuo varchar(40),
    Data_Insc date,
    Valor float,
    primary key (CPF_Individuo, UID_Evento),
    foreign key (CPF_Individuo) references tbIndividuo(CPF),
    foreign key (UID_Evento) references tbEvento(UID)
);
create table tbFiliacao(
	CPF_Individuo char(11),
    UID_Empresa int,
    Data_Filiacao date,
    primary key(CPF_Individuo, UID_Empresa, Data_Filiacao),
    foreign key (CPF_Individuo) references tbIndividuo(CPF),
    foreign key (UID_Empresa) references tbCompanhia(UID)
);

insert into tbIndividuo values("13829381928","Jose Silva","Estudante");
insert into tbVariavel values(1, "1", "Periodo","13829381928");

insert into tbIndividuo values("82739182738","Carlos Borges","Academico");
insert into tbVariavel values(2, "Professor de Matematica", "Area","82739182738");

insert into tbIndividuo values("29102938192","Maria Ferreira","Nao-Academico");
insert into tbIndividuo values("82938173928","Eduardo Lopes","Nao-Academico");

insert into tbEvento values(1, "Super Semana da Computacao", "Avenida Kennedy, 1337, Centro, Curitiba, Parana, Brasil","2018-03-01");

insert into tbCompanhia values(1, "Microsoft");
insert into tbFiliacao values("29102938192", 1, "2017-05-28");

insert into tbCompanhia values(2, "IBM-Brasil");
insert into tbFiliacao values("82938173928", 2, "2018-01-01");

insert into tbPalestra values(1, "AZURE - Microsoft","2018-03-03",1,1);
insert into tbPalestra values(2, "IA - IBM","2018-03-04",2,1);

insert into tbInscricao values("13829381928",1,"Ouvinte","2018-02-15", 50.0);
insert into tbInscricao values("82739182738",1,"Organizador","2018-01-10", 0.0);
insert into tbInscricao values("29102938192",1,"Palestrante","2018-02-01", 0.0);
insert into tbInscricao values("82938173928",1,"Palestrante","2018-01-20", 0.0);

insert into tbPatrocinio_Comp_Evento values(1,1);
insert into tbPatrocinio_Comp_Evento values(2,1);

insert into tbPresenca values("13829381928",1);
insert into tbPresenca values("13829381928",2);
insert into tbPresenca values("29102938192",2);
insert into tbPresenca values("82938173928",1);

-- Qual foi o tema das duas palestras que mais pessoas presentes estavam em 2018?
select p.Tema, count(*)
from tbPalestra p, tbIndividuo i, tbPresenca pr
where p.UID = pr.UID_Palestra and i.CPF = pr.CPF_Individuo
group by pr.UID_Palestra
order by count(*) desc
limit 2;

-- Quais foram os palestrantes em 2017 que eram filiados a IBM-Brasil?
select i.Nome
from tbIndividuo i, tbEvento e, tbInscricao ins, tbFiliacao f, tbCompanhia c
where i.CPF = ins.CPF_Individuo and e.UID = ins.UID_Evento and ins.Papel_Individuo = "Palestrante" and  f.UID_Empresa = c.UID and c.Nome = "IBM-Brasil"
group by f.UID_Empresa
order by i.Nome;

-- Quantas pessoas estavam presentes na palestra sobre o AZURE – Microsoft?
select count(*)
from tbPalestra p, tbPresenca pr, tbIndividuo i
where p.UID = pr.UID_Palestra and i.CPF = pr.CPF_Individuo and p.Tema = "AZURE - Microsoft"
group by pr.UID_Palestra
order by count(*);

-- Em 2018 a maior presença nas palestras foi de estudantes do primeiro período?
select p.Tema,count(*)
from tbIndividuo i, tbVariavel v, tbPalestra p, tbPresenca pr
where i.CPF = pr.CPF_Individuo and p.UID = pr.UID_Palestra and v.CPF_Individuo = i.CPF and v.Dado = "1" and v.Descr = "Periodo"
group by pr.UID_Palestra
order by count(*) desc
limit 1;

-- Que palestras foram dadas no dia 04/03/2018?
select Tema
from tbPalestra
where Data_Palestra = "2018-03-04"
order by Tema;

-- Pessoas com qual papel mais se inscreveram em um evento?
select i.Papel_Individuo, count(*)
from tbInscricao i,tbIndividuo n, tbEvento e
where i.CPF_Individuo = n.CPF and e.UID = i.UID_Evento
group by i.Papel_Individuo
order by count(*) desc
limit 1;

-- Pessoas com que papel (em todos os eventos) mais se filiaram a companhias?
select i.Papel_Individuo, count(*)
from tbInscricao i, tbIndividuo n, tbEvento e, tbCompanhia c, tbFiliacao f
where i.CPF_Individuo = n.CPF and e.UID = i.UID_Evento and f.CPF_Individuo = n.CPF and f.UID_Empresa = c.UID
group by i.Papel_Individuo
order by count(*) desc
limit 1;
-- Qual papel paga mais nos eventos?
select i.Papel_Individuo
from tbInscricao i, tbIndividuo n, tbEvento e
where i.CPF_Individuo = n.CPF and e.UID = i.UID_Evento
group by i.Papel_Individuo
order by i.Valor desc
limit 1;