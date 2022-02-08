create database Teste;

use Teste;

create table TBEstacionamento(
	Num_vagas int
);

create table TBLojas(
	ID_loja int primary key,
    nome varchar(100)
);

create table TBProdutos(
	ID_Prod int primary key,
    nome varchar(100),
    ID_loja int,
    foreign key (ID_loja) references TBLojas (ID_loja)
);

create table TBCliente(
	ID_cliente int,
    nome varchar(100),
    data_ent_est date,
    data_sai_est date,
    ID_produto_compra int,
    foreign key (ID_produto_compra) references TBProdutos (ID_Prod)
);



