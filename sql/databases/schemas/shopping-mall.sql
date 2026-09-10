-- Shopping mall mini-world (activity 2): shops sell products to customers,
-- who use the mall car park while they visit.

drop database if exists shopping_mall;
create database shopping_mall;
use shopping_mall;

create table car_park (
    spaces int
);

create table shop (
    shop_id int primary key,
    name    varchar(100)
);

create table product (
    product_id int primary key,
    name       varchar(100),
    shop_id    int,
    foreign key (shop_id) references shop(shop_id)
);

create table customer (
    customer_id       int,
    name              varchar(100),
    car_park_entry    date,
    car_park_exit     date,
    purchased_product int,
    foreign key (purchased_product) references product(product_id)
);
