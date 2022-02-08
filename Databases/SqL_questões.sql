use loja_virtual;


/* pessoa comprou quantidade objeto, (categoria objeto).*/
select cli.nome, cp.quantidade, cat.nome, cp.preço, pr.nome
from tbCLiente cli, tbCompra cp, tbCategoria cat, tbProduto pr 
where(cli.CPF = cp.CPF_cliente and 
cp.ID_produto = cat.ID_prod and cp.ID_produto = pr.ID)
group by(cli.nome)
order by(cp.quantidade);

/* Se o fornecedor é verificado: selo -> 1.*/
select f.nome, f.selo
from  tbFornecedor f
where(f.selo = 1);

/* Comprador maior de idade.*/
select c.nome, c.data_nasc
from tbCliente c
where(year(current_date()) - year(c.data_nasc) >= 18);

/*info do fornecedor. */
select f.nome, f.num_cel, f.email
from tbFornecedor f
order by(f.nome);

/*Quantidade produtos vendidos no dia.*/
select h.data_venda, p.nome, count(h.ID_produto)
from tbHistorico h, tbProduto p
where( h.ID_produto = p.ID)
group by(h.data_venda)
order by(p.nome);

/*fornecedor de? */
select f.nome, cat.nome, p.nome
from tbCompra cp, tbFornecedor f, tbCategoria cat, tbProduto p
where(f.CPF = cp.CPF_forn and cp.ID_produto = cat.ID_prod and
cp.ID_produto = p.ID)
group by(f.nome);


/*Preço objeto fornecedor*/
select f.nome, p.nome, cp.preço
from tbFornecedor f, tbProduto p, tbCompra cp
where(f.CPF = cp.CPF_forn and p.ID = cp.ID_produto)
group by(f.nome);

/*Número do cartão e email para contato*/
select cli.nome, cli.credit_card, cli.num_cel
from tbCLiente cli;

/* Comprou quanto e do que? */
select cli.nome, p.nome, count(h.ID_produto)
from tbCLiente cli, tbHistorico h, tbProduto p
where( cli.CPF = h.CPF_cliente and h.ID_produto = p.ID)
group by(cli.nome)
order by(p.nome);


/* Faturamento */
select f.nome, h.data_venda, p.nome, count(h.ID_produto)*(cp.preço)
from tbFornecedor f, tbHistorico h, tbProduto p, tbCompra cp
where( h.ID_produto = p.ID and h.CPF_forn = cp.CPF_forn and f.CPF = h.CPF_forn)
group by(h.data_venda)
order by(f.nome);
