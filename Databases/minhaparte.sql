use dbEventos;

/*
Grupo: André Mayer, Kalebe Rodrigues, Vitor Luis

Questão: 5 - Que tema foi o mais assistido pelos estudantes?
*/
select p.Tema, count(*)
from tbEstudante e, tbPalestra p, tbpresenca pr, tbindividuo i
where (e.CPF = i.CPF and i.CPF = pr.CPF_Individuo and p.UID = pr.UID_Palestra)
order by count(*) desc limit 1;


/*

Grupo: Giovane Matheus Kayser Fernandes, Jonathan Oliveira Dias.

Questão: 6 - Qual o nome dos estudantes que foram a palestra de um dado instituto?

*/
select distinct i.Nome
from tbIndividuo i, tbEstudante e, tbPalestra p, tbCompanhia c
where i.CPF = e.CPF and p.UID_Companhia = c.UID and c.Nome = "Microsoft"
order by(i.nome);


/*

Grupo: Matheus Teixeira, Luan Magalhães, Lucas Yanaga, João Pedro de Lima

 Questão: 7 - Qual empresa patrocinou mais vezes um evento?

*/
select c.Nome, count(*)
from tbCompanhia c, tbEvento e , tbPatrocinio_Comp_Evento p, tbPalestra pl
where c.UID = pl.UID_Companhia and p.UID_Evento = e.UID
group by(c.Nome)
order by(count(*)) desc limit 1;


/*
Grupo:  Joao Vitor Andrioli de Souza, Eduardo Eiji Goto, Andrey Silva Silveira, Felipe Vieira da Silva, Alexandre Schellman

Questão: 8 - horario das palestras que um estudante em particular participou
*/
select i.Nome, p.Tema, time(p.Data_Palestra)
from tbPalestra p, tbEstudante e, tbIndividuo i, tbPresenca pr
where e.CPF = i.CPF and pr.CPF_Individuo = i.CPF and pr.UID_Palestra = p.UID and i.Nome = "Jose Silva"
order by (i.Nome);

/*
Grupo: Iago Santos Barreto

Questão: 8 - Quais são o nome dos palestrantes acadêmicos?
*/
select i.nome
from tbIndividuo i, tbAcademico a, tbInscricao ins
where i.CPF = a.CPF and i.CPF = ins.CPF_Individuo and ins.Papel_Individuo = "Palestrante"
order by (i.nome);


/*
Grupo: Enzo Moro e Luiz Henrique Baldão Filho

Questão: 1 -  O modelo consegue extrair o número total de pessoas que estiveram presentes em um dia de evento?
*/
select count(pr.CPF_Individuo)
from tbEvento e, tbPresenca pr, tbIndividuo i, tbPalestra p
where pr.CPF_Individuo = i.CPF and pr.UID_Palestra = p.UID and date(p.Data_Palestra) = "2018-03-03"

