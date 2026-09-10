-- Economic Freedom Index, entity-attribute-value layout (final assignment).
-- Instead of twelve columns, every indicator is stored as a row (attribute
-- name + value), so a new indicator can be added without changing the schema.

drop database if exists economic_freedom_eav;
create database economic_freedom_eav;
use economic_freedom_eav;

create table country (
    name     varchar(60),
    index_id int primary key
);

create table indicator (
    country_index int,
    score_date    date,
    attribute     varchar(50),
    value         float,
    foreign key (country_index) references country(index_id)
);

insert into country values ('Brazil', 100);

insert into indicator values (100, '2018-05-01', 'property rights',        87.0);
insert into indicator values (100, '2018-05-01', 'government integrity',   61.0);
insert into indicator values (100, '2018-05-01', 'judicial effectiveness', 44.0);
insert into indicator values (100, '2018-05-01', 'government spending',   100.0);
insert into indicator values (100, '2018-05-01', 'tax burden',             94.0);
insert into indicator values (100, '2018-05-01', 'fiscal health',          77.0);
insert into indicator values (100, '2018-05-01', 'business freedom',       45.0);
insert into indicator values (100, '2018-05-01', 'labour freedom',         78.0);
insert into indicator values (100, '2018-05-01', 'monetary freedom',       72.0);
insert into indicator values (100, '2018-05-01', 'trade freedom',          88.0);
insert into indicator values (100, '2018-05-01', 'investment freedom',     99.0);
insert into indicator values (100, '2018-05-01', 'financial freedom',      88.0);

-- Overall score: the average of the twelve indicators
select c.name, avg(i.value) as economic_freedom
from country c
join indicator i on i.country_index = c.index_id
group by c.name;
