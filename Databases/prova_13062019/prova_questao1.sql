create database carros;


use carros;
-- NOME: GUSTAVO HAMMERSCHMIDT.

/*
Exercício:

create table tb_carros(
	marca varchar(100),
	modelo varchar(100),
	ano_fabricacao int,
	placa char(7);
	preco numeric(6,2);
	cor varchar(100)
	primary key (marca, modelo);
	foreign key (marca)references tb_marcas(marca)
);

create table tb_marcas(
	marca varchar(50),
	nome varchar(50)
); 
*/

/*
	Erros observados na tabela acima:
		- integridade estrutural, problema no desenvolvimento lógico do banco; uma tabela foi referenciada sem ter sido criada,
		  então coloquei a tabela tb_marcas acima da tb_carros.
		- o espaço alocado para muitos atributos é incondizente com o atributo, por exemplo, cor varchar(100) (qual cor tem esse tamanho?).
        - Dois ponto e vírgulas foram usados para separar atributos, o que gera problemas de integridade estrutural.
        - O tipo do atributo ano é pouco usual, afinal, só será usados números de 4 dígitos. Não há necessidade de usar um valor int para este caso.
		- Faltou o uso de vígulas para separar atributos também. Logo, após o atributo cor.
*/

create table tb_marcas(
	marca varchar(50),    -- Está OK.
    nome varchar(50)      -- Está OK.
);


create table tb_modelo(
	modelo varchar(50) primary key,
    ano_fabricação char(4),        -- Alterei de int para char(4); motivo: são apenas núneros de 4 dígitos.
	preço float                    -- Alterei de numeric(6,2) para float.
);


create table tb_carros(
		marca  varchar(50),     -- Eu reduzi o espaço alocado; motivo: era muito grande para o tópico em questão.
        modelo varchar(50),     -- Eu reduzi o espaço alocado; motivo: era muito grande para o tópico em questão.
        placa char(7),          -- Eu removi o ponto e vírgula; motivo: causava problemas de integridade estrutural.
        cor varchar(15),        -- Eu reduzi o espaço alocado; motivo: era um espaço muito grande para um atributo de tamanho pequeno.
        
        numero_chassi varchar(100),   -- Adicionei um atributo que pode ser usado para localizar os carros de uma fabricante. É uma chave sorrogada.
        
        primary key (marca, modelo),    
        foreign key (marca) references tb_marcas(marca),   -- Eu coloquei a tabela tb_marcas acima desta; motivo: Modelo Lógico, integridade.
		foreign key (modelo) references tb_modelo(modelo)  -- referencia a tabela tb_modelo.
);

--  Talvez ano_fabricação não dependa do modelo.

/*
   O atributo numero do chassi serve de rastreador de um carro específico dentro da fábrica, ou seja, a fábrica de carros pode localizar um 
   veículo específico utilizando esta chave sorrogada.
*/