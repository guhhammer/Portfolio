-- Video rental store: customers borrow physical copies of titles; every title
-- has a category and a price class; late returns carry a fee.

drop database if exists video_rental;
create database video_rental;
use video_rental;

create table customer (
    name          varchar(100),
    customer_id   int primary key,
    cpf           char(11),
    registered_on date,
    city          varchar(50),
    state         char(2)
);

create table category (
    category_id int primary key,
    name        varchar(20)
);

create table price_class (
    class_id int primary key,
    name     varchar(20),
    price    float
);

create table title (
    title_id    int primary key,
    name        varchar(50),
    release_year int,
    category_id int,
    class_id    int,
    foreign key (category_id) references category(category_id),
    foreign key (class_id)    references price_class(class_id)
);

-- A physical copy of a title
create table copy (
    copy_id     int primary key,
    title_id    int,
    distributor char(20),
    foreign key (title_id) references title(title_id)
);

create table rental (
    customer_id int,
    copy_id     int,
    rented_on   date,
    due_on      date,
    returned_on date,
    late_fee    float,
    foreign key (customer_id) references customer(customer_id),
    foreign key (copy_id)     references copy(copy_id)
);

insert into customer values ('John Cena', 4512, '78945612301', '2014-05-09', 'Curitiba', 'PR');
insert into customer values ('Afon Defs', 8710, '45612378901', '2015-06-04', 'Curitiba', 'PR');

insert into price_class values (612, 'Blockbuster',        29.46);
insert into price_class values (212, 'Netflix production', 15.00);

insert into category values (411, 'Action');
insert into category values (711, 'Survival');

insert into title values (6352, 'Limitless',            2012, 411, 612);
insert into title values (7832, 'F1: Drive to Survive', 2018, 411, 612);
insert into title values (8524, 'Last Days',            2016, 711, 212);

insert into copy values (101, 6352, 'Avenida SA');
insert into copy values (104, 7832, 'Avenida SA');
insert into copy values (202, 8524, 'HBO');

insert into rental values (4512, 101, '2018-09-04', '2018-09-26', '2018-09-30', 5.48);
insert into rental values (4512, 104, '2018-02-09', '2018-03-01', '2018-03-02', 1.50);
insert into rental values (8710, 202, '2017-05-12', '2017-06-01', '2018-05-30', 0.00);

-- Every rental with customer, title, price class, how late it came back and the fee charged
select c.name as customer, t.name as title, pc.name as price_class, pc.price,
       r.rented_on, r.returned_on, datediff(r.returned_on, r.due_on) as days_late, r.late_fee
from rental r
join customer    c  on c.customer_id = r.customer_id
join copy        cp on cp.copy_id = r.copy_id
join title       t  on t.title_id = cp.title_id
join price_class pc on pc.class_id = t.class_id
order by r.rented_on;
