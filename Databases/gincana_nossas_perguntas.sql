use dbEventos;

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