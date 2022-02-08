create database ELIndex;

use ELIndex;


/*
Nomes grupo:  
		- Gustavo Hammerschmidt,
        - Eduardo goto,
        - Pedro Henrique Churata,
        - Matheus Teixeira de Souza.
*/



create table tbPais(
	nome varchar(50),
    index_le int primary key
);


create table tbLiberdade(
	chave int,
    date_ date,      -- deveria ter data para avaliar os valores em tempo real.
	dir_prop int,    -- direitos de propriedade,
    int_gov int,     -- integridade do governo,
    efic_jur int,    -- eficácia judicial,
    gast_gov int,    -- gastos do governo,
    carg_trib int,   -- carga tributária,
    saud_fis int,    -- saúde fisca,
    lib_com1 int,     -- liberdade comercial (eficiência regulatória)
    lib_trab int,    -- liberdade de trabalho,
    lib_mon int,     -- liberdade monetária,
    lib_com2 int,    -- liberdade comercial (mercados abertos),
    lib_inv int,     -- liberdade de investimento,
    lib_fin int,      -- liberdade financeira.
	foreign key (chave) references tbPais(index_le)
);



