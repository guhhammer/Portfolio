drop database dbEventos;
create database dbEventos;
use dbEventos;


create table tbIndividuo(
	CPF char(11),
    Nome varchar(100),
	primary key(CPF)
);

create table tbCompanhia(
	UID int,
    Nome varchar(100),
	primary key(UID)
);

create table tbEstudante(
	CPF char(11),
    Periodo int,
    Curso varchar(50),
    Instituicao varchar(75),
    primary key(CPF),
    foreign key (CPF) references tbIndividuo(CPF)
);

create table tbAcademico(
	CPF char(11),
    Area_Estudo varchar(40),
    Instituicao varchar(75),
    primary key(CPF),
    foreign key (CPF) references tbIndividuo(CPF)
);

create table tbNaoAcademico(
	CPF char(11),
    Ocupacao varchar(50),
    primary key(CPF),
    foreign key (CPF) references tbIndividuo(CPF)
);

/*create table tbVariavel(
	UID int,
    Dado varchar(40),
    Descr varchar(40),
    CPF_Individuo char(11),
	primary key(UID),
    foreign key (CPF_Individuo) references tbIndividuo(CPF)
);*/

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
    Data_Palestra datetime,
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

insert into tbIndividuo values("13829381928","Jose Silva");
insert into tbEstudante values("13829381928", 1, "Ciencia da Computacao","PUCPR");

insert into tbIndividuo values("29381928391","Amanda Souza");
insert into tbEstudante values("29381928391", 1, "Ciencia da Computacao","UFPR");

insert into tbIndividuo values("92839182902","Joao Henrique");
insert into tbEstudante values("92839182902", 1, "Engenharia da Computacao","UTFPR");

insert into tbIndividuo values("88291829182","Gabriela Farias");
insert into tbEstudante values("88291829182", 1, "Jogos Digitais","PUCPR");

insert into tbIndividuo values("82739182738","Carlos Borges");
insert into tbAcademico values("82739182738", "Matematica", "PUCPR");

insert into tbIndividuo values("82889910928","Fernanda Silveira");
insert into tbAcademico values("82889910928", "Matematica", "USP");

insert into tbIndividuo values("82888991029","Vinicius Nunes");
insert into tbAcademico values("82888991029", "Fisica","UFPR");

insert into tbIndividuo values("29102938192","Maria Ferreira");
insert into tbNaoAcademico values("29102938192", "Freelancer");

insert into tbIndividuo values("82918299809","Rodrigo Pereira");
insert into tbNaoAcademico values("82918299809", "Freelancer");

insert into tbIndividuo values("82938173928","Eduardo Lopes");
insert into tbNaoAcademico values("82938173928", "Empresario");

insert into tbEvento values(1, "Super Semana da Computacao", "Avenida Kennedy, 1337, Centro, Curitiba, Parana, Brasil","2018-03-01");

insert into tbCompanhia values(1, "Microsoft");
insert into tbFiliacao values("29102938192", 1, "2017-05-28");

insert into tbCompanhia values(2, "IBM-Brasil");
insert into tbFiliacao values("82938173928", 2, "2018-01-01");
insert into tbFiliacao values("82888991029", 2, "2018-01-02");
insert into tbFiliacao values("82918299809", 2, "2017-08-28");

insert into tbPalestra values(1, "AZURE - Microsoft","2018-03-03 14:00:00",1,1);
insert into tbPalestra values(2, "IA - IBM","2018-03-04 16:00:00",2,1);
insert into tbPalestra values(3, "Deep Learning","2018-03-05 15:00:00",1,1);

insert into tbInscricao values("13829381928",1,"Ouvinte","2018-02-15", 50.0);
insert into tbInscricao values("82739182738",1,"Palestrante","2018-01-10", 0.0);
insert into tbInscricao values("29102938192",1,"Palestrante","2018-02-01", 0.0);
insert into tbInscricao values("82938173928",1,"Palestrante","2018-01-20", 0.0);

insert into tbInscricao values("29381928391",1,"Ouvinte","2018-02-02", 45.0);
insert into tbInscricao values("92839182902",1,"Organizador","2018-01-12", 0.0);
insert into tbInscricao values("88291829182",1,"Ouvinte","2018-02-23", 40.0);
insert into tbInscricao values("82888991029",1,"Palestrante","2018-01-10", 0.0);
insert into tbInscricao values("82918299809",1,"Palestrante","2018-01-10", 0.0);
insert into tbInscricao values("82889910928",1,"Palestrante","2018-02-11", 0.0);

insert into tbPatrocinio_Comp_Evento values(1,1);
insert into tbPatrocinio_Comp_Evento values(2,1);

insert into tbPresenca values("13829381928",1);
insert into tbPresenca values("13829381928",2);
insert into tbPresenca values("29102938192",2);
insert into tbPresenca values("82938173928",1);

insert into tbPresenca values("29381928391",2);
insert into tbPresenca values("92839182902",2);
insert into tbPresenca values("88291829182",2);
insert into tbPresenca values("82888991029",2);
insert into tbPresenca values("82889910928",2);

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
from tbIndividuo i, tbEstudante v, tbPalestra p, tbPresenca pr
where i.CPF = pr.CPF_Individuo and p.UID = pr.UID_Palestra and v.CPF = i.CPF and v.Periodo = 1
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

use dbEventos;

-- 1)	Quantos acadêmicos compareceram na palestra “IA – IBM”?
select count(*)
from tbAcademico a, tbpalestra p, tbindividuo i, tbpresenca pr
where a.CPF = i.CPF and i.CPF = pr.CPF_Individuo and p.UID = pr.UID_Palestra and p.Tema = "IA - IBM"
order by count(*);
-- 2)	A empresa que patrocina a palestra “Deep Learning” também patrocina o evento?
select case
when p.tema = "Deep Learning" and p.UID_Companhia = c.UID and c.UID = ce.UID_Companhia and ce.UID_Evento = e.UID then "SIM" else "NAO" end as resposta
from tbpalestra p, tbCompanhia c, tbpatrocinio_comp_evento ce, tbEvento e
group by resposta
order by count(*)
limit 1;

-- 3)	Qual o curso mais comum entre os estudantes que participaram do evento “Super Semana da Computação”?
select es.curso, count(*)
from tbEstudante es, tbIndividuo i, tbInscricao sc, tbEvento e
where es.CPF = i.CPF and i.CPF = sc.CPF_Individuo and sc.UID_Evento = e.UID
group by es.curso
order by count(*) desc
limit 1;

-- 4)	Quantas palestras foram patrocinadas pela Microsoft no evento “Super Semana da Computação”?
select count(*)
from tbCompanhia c, tbPalestra p, tbEvento e, tbPatrocinio_Comp_Evento ce
where c.UID = p.UID_Companhia and c.UID = ce.UID_Companhia and e.UID = ce.UID_Evento and c.Nome = "Microsoft" and e.Nome = "Super Semana da Computacao"
order by count(p.UID);

-- 5)	Quantos freelancers (não-acadêmicos) participaram como palestrantes no evento “Super Semana da Computação”?
select count(*)
from tbNaoAcademico na, tbInscricao i, tbIndividuo ind, tbEvento e
where na.CPF = ind.CPF and ind.CPF = i.CPF_Individuo and e.UID = i.UID_Evento and i.Papel_Individuo = "Palestrante" and e.Nome = "Super Semana da Computacao" and na.Ocupacao = "Freelancer"
order by count(i.Papel_Individuo);

-- 6)	Qual instituição é a mais comum entre estudantes e acadêmicos no evento “Super Semana da Computação”?
with tbInstituicao(Instituicao) as
	(select e.Instituicao from tbEstudante e
    union all
    select a.Instituicao from tbAcademico a)
select i.Instituicao
from tbInstituicao i
group by i.Instituicao
order by count(*) desc
limit 1;

-- 7)	Qual classe pagou menos (em média) no evento “Super Semana da Computação”?
with tbTemporariaE(estudantes) as
	(select ins.Valor
    from tbInscricao ins, tbEstudante e, tbEvento ev
    where e.CPF = ins.CPF_Individuo and ev.UID = ins.UID_Evento and ev.Nome = "Super Semana da Computacao"),
    tbTemporariaA(academicos) as
	(select ins.Valor
    from tbInscricao ins, tbAcademico a, tbEvento ev
    where a.CPF = ins.CPF_Individuo and ev.UID = ins.UID_Evento and ev.Nome = "Super Semana da Computacao"),
    tbTemporariaNA(naoAcademicos) as
	(select ins.Valor
    from tbInscricao ins, tbNaoAcademico na, tbEvento ev
    where na.CPF = ins.CPF_Individuo and ev.UID = ins.UID_Evento and ev.Nome = "Super Semana da Computacao")
select case
when sum(te.estudantes) <= sum(ta.academicos) and sum(te.estudantes) <= sum(tna.naoAcademicos) then "Estudantes"
when sum(ta.academicos) <= sum(te.estudantes) and sum(ta.academicos) <= sum(tna.naoAcademicos) then "Academicos"
when sum(tna.naoAcademicos) <= sum(ta.academicos) and sum(tna.naoAcademicos) <= sum(te.Estudantes) then "Nao Academicos"
else "Erro"
end as MenosPaga
from tbTemporariaE te, tbTemporariaA ta, tbTemporariaNA tna;

-- 8)	Quantas pessoas filiadas à IBM-Brasil se inscreveram no evento “Super Semana da Computação” no dia 10/01/2018?
select count(f.CPF_Individuo)
from tbFiliacao f, tbIndividuo i, tbInscricao ins, tbEvento e, tbCompanhia c
where f.CPF_Individuo = i.CPF and f.UID_Empresa = c.UID 
	and i.CPF = ins.CPF_Individuo and ins.UID_Evento = e.UID and e.Nome = "Super Semana da Computacao"
    and c.Nome = "IBM-Brasil" and ins.Data_Insc = "2018-01-10";

-- 9)	Qual a área de estudo mais comum entre os acadêmicos que são palestrantes no evento “Super Semana da Computação”?
select a.Area_Estudo
from tbAcademico a, tbInscricao ins, tbEvento e
where a.CPF = ins.CPF_Individuo and ins.UID_Evento = e.UID and e.Nome = "Super Semana da Computacao"
	and ins.Papel_Individuo = "Palestrante"
group by a.Area_Estudo
order by count(*) desc
limit 1;

-- 10)	Qual foi a última palestra do evento “Super Semana da Computação”?
select p.Tema
from tbPalestra p, tbEvento e
where p.UID_Evento = e.UID and e.Nome = "Super Semana da Computacao"
order by p.Data_Palestra desc
limit 1;
