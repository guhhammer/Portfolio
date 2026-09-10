-- Online store: customers buy products offered by suppliers.
-- Schema, sample data and the queries written for the exercise.
-- Runs on MySQL / MariaDB (tested on MariaDB 11).

drop database if exists online_store;
create database online_store;
use online_store;

create table customer (
    cpf         char(11) primary key,   -- Brazilian taxpayer id, used as the customer key
    email       varchar(30),
    mobile      varchar(15),
    birth_date  date,
    name        varchar(50),
    credit_card char(16)
);

create table supplier (
    cpf      char(11) primary key,
    name     varchar(30),
    verified bool,                      -- verified-seller badge
    mobile   varchar(15),
    email    varchar(30)
);

create table product (
    supplier_cpf char(11),
    id           char(20) primary key,
    name         varchar(25),
    foreign key (supplier_cpf) references supplier(cpf)
);

-- A product can sit in more than one category, so the key is the pair.
create table category (
    product_id char(20),
    id         char(10),
    name       varchar(20),
    primary key (product_id, id),
    foreign key (product_id) references product(id)
);

create table purchase (
    customer_cpf char(11),
    supplier_cpf char(11),
    product_id   char(20),
    quantity     int,
    price        float,
    foreign key (customer_cpf) references customer(cpf),
    foreign key (supplier_cpf) references supplier(cpf),
    foreign key (product_id)   references product(id)
);

create table sale_history (
    customer_cpf char(11),
    supplier_cpf char(11),
    product_id   char(20),
    sale_date    date,
    foreign key (customer_cpf) references customer(cpf),
    foreign key (supplier_cpf) references supplier(cpf),
    foreign key (product_id)   references product(id)
);

-- Sample data (fictional people and card numbers)
insert into customer values ('78895563312', 'customer1@example.com', '988334422', '2010-05-22', 'João Amaral',    '5555444433336666');
insert into customer values ('88855563399', 'customer2@example.com', '933442288', '1990-06-20', 'Steve Eastwood', '7777444422226666');
insert into customer values ('99995944422', 'customer3@example.com', '988777711', '2000-09-10', 'Kleiv Sik',      '9999444433332222');

insert into supplier values ('77722233344', 'Mônica Silva',   true,  '999954444', 'supplier1@example.com');
insert into supplier values ('55522266622', 'Afonso Pereira', false, '988884422', 'supplier2@example.com');

insert into product values ('77722233344', '55555444442222233333', 'iPhone X');
insert into product values ('77722233344', '88888444445555533333', 'Samsung S10');
insert into product values ('55522266622', '99999444442222211111', 'Rainbow Six Siege');

insert into category values ('55555444442222233333', '1234564444', 'Smartphones');
insert into category values ('88888444445555533333', '1234564444', 'Smartphones');
insert into category values ('99999444442222211111', '4421255555', 'Games');

insert into purchase values ('78895563312', '77722233344', '55555444442222233333', 1, 4000.00);
insert into purchase values ('88855563399', '77722233344', '88888444445555533333', 2, 2703.50);
insert into purchase values ('99995944422', '55522266622', '99999444442222211111', 1, 250.00);

insert into sale_history values ('78895563312', '77722233344', '55555444442222233333', '2015-02-02');
insert into sale_history values ('88855563399', '77722233344', '88888444445555533333', '2015-06-20');
insert into sale_history values ('99995944422', '55522266622', '99999444442222211111', '2015-08-22');

-- 1. What each customer bought: product, category, quantity and unit price
select c.name as customer, p.name as product, cat.name as category, pu.quantity, pu.price
from purchase pu
join customer c   on c.cpf = pu.customer_cpf
join product  p   on p.id = pu.product_id
join category cat on cat.product_id = p.id
order by pu.quantity desc;

-- 2. Suppliers with the verified badge
select name, verified
from supplier
where verified = true;

-- 3. Customers of legal age
select name, birth_date
from customer
where timestampdiff(year, birth_date, current_date()) >= 18;

-- 4. Supplier contact details
select name, mobile, email
from supplier
order by name;

-- 5. Units sold per day and product
select h.sale_date, p.name as product, count(*) as units
from sale_history h
join product p on p.id = h.product_id
group by h.sale_date, p.name
order by h.sale_date, p.name;

-- 6. Which supplier offers which product, by category
select s.name as supplier, cat.name as category, p.name as product
from product p
join supplier s   on s.cpf = p.supplier_cpf
join category cat on cat.product_id = p.id
order by s.name, p.name;

-- 7. Price charged by each supplier for each product
select s.name as supplier, p.name as product, pu.price
from purchase pu
join supplier s on s.cpf = pu.supplier_cpf
join product  p on p.id = pu.product_id
order by s.name;

-- 8. Billing contact: card number and phone of every customer
select name, credit_card, mobile
from customer;

-- 9. How many times each customer bought each product
select c.name as customer, p.name as product, count(*) as purchases
from sale_history h
join customer c on c.cpf = h.customer_cpf
join product  p on p.id = h.product_id
group by c.name, p.name
order by c.name;

-- 10. Revenue per supplier and day
select s.name as supplier, h.sale_date, sum(pu.quantity * pu.price) as revenue
from sale_history h
join purchase pu on pu.customer_cpf = h.customer_cpf and pu.product_id = h.product_id
join supplier s  on s.cpf = h.supplier_cpf
group by s.name, h.sale_date
order by s.name, h.sale_date;
