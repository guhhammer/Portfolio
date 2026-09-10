-- Economic Freedom Index (topic 8), wide layout: one row per country and date
-- holding the twelve indicators published by the index.
-- Team: Gustavo Hammerschmidt, Eduardo Goto, Pedro Henrique Churata,
--       Matheus Teixeira de Souza.

drop database if exists economic_freedom;
create database economic_freedom;
use economic_freedom;

create table country (
    name     varchar(50),
    index_id int primary key
);

create table freedom_score (
    country_index          int,
    score_date             date,   -- scores change over time, so every row is dated
    property_rights        int,
    government_integrity   int,
    judicial_effectiveness int,
    government_spending    int,
    tax_burden             int,
    fiscal_health          int,
    business_freedom       int,    -- regulatory efficiency
    labour_freedom         int,
    monetary_freedom       int,
    trade_freedom          int,    -- open markets
    investment_freedom     int,
    financial_freedom      int,
    foreign key (country_index) references country(index_id)
);
