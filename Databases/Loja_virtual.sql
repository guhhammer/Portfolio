create database loja_virtual;

use loja_virtual;

create table tbCliente(
	CPF char(11) primary key,
    email varchar(30),
    num_cel varchar(15),
    data_nasc  date,
    nome varchar(50),
    credit_card char(16)
);

create table tbFornecedor(
	CPF char(11) primary key,
    nome varchar(30),
    selo bool,
    num_cel varchar(15),
    email varchar(30)
);

create table tbProduto(
	CPF_forn char(11),
    ID char(20) primary key,
    nome varchar(25),
    foreign key (CPF_forn) references tbFornecedor(CPF)
);

create table tbCategoria(
	ID_prod char(20),
	ID char(10) primary key,
    nome varchar(20),
    foreign key(ID_prod) references tbProduto(ID)
);

create table tbCompra(	
	CPF_cliente char(11),
    CPF_forn char(11),
    ID_produto char(20),
    quantidade int,
    preço float,
    foreign key (CPF_cliente) references tbCliente(CPF),
    foreign key (CPF_forn) references tbFornecedor(CPF),
    foreign key (ID_produto) references tbProduto(ID)
);

create table tbHistorico(
	CPF_cliente char(11),
    CPF_forn char(11),
    ID_produto char(20),
    data_venda date,
    foreign key (CPF_cliente) references tbCliente(CPF),
    foreign key (CPF_forn) references tbFornecedor(CPF),
    foreign key (ID_produto) references tbProduto(ID)
);



insert into tbCLiente values("78895563312","cliente1@gmail.com","988334422","2010-05-22","João arrm","5555444433336666");
insert into tbCLiente values("88855563399","cliente2@gmail.com","933442288","1990-06-20","Steve Eastwood","7777444422226666");
insert into tbCLiente values("99995944422","cliente3@gmail.com","988777711","2000-09-10","Kleiv sik","9999444433332222");

insert into tbFornecedor values("77722233344","Mônica Silva", true, "999954444","forn1@gmail.com");
insert into tbFornecedor values("55522266622","Afonso Pereira", false, "988884422","forn2@gmail.com");

insert into tbProduto values("78895563312","55555444442222233333","Iphone X");
insert into tbProduto values("78895563312","88888444445555533333","Samsung S10");
insert into tbProduto values("88855563399","99999444442222211111","Rainbow Six Siege");

insert into tbCategoria values("55555444442222233333","1234564444","Smartphones");
insert into tbCategoria values("88888444445555533333","1234564444","Smartphones");
insert into tbCategoria values("99999444442222211111","4421255555","Games");
    
insert into tbCompra values("78895563312","77722233344","55555444442222233333",1,4000.00);    
insert into tbCompra values("88855563399","77722233344","88888444445555533333",2,2703.50);
insert into tbCompra values("99995944422","55522266622","99999444442222211111",1,250.00);

insert into tbHistorico values("78895563312","77722233344","55555444442222233333","2015-02-02");
insert into tbHistorico values("88855563399","77722233344","88888444445555533333","2015-06-20");
insert into tbHistorico values("99995944422","55522266622","99999444442222211111","2015-08-22");

select*from loja_virtual.tbCliente;
select*from loja_virtual.tbFornecedor;
select*from loja_virtual.tbProduto;
select*from loja_virtual.tbCategoria;
select*from loja_virtual.tbCompra;
select*from loja_virtual.tbHistorico;




