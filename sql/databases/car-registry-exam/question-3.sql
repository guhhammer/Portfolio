-- Databases exam, 13 June 2019, question 3: populate the model from question 2
-- and prove with queries that the three pieces of information can be extracted.
-- Student: Gustavo Hammerschmidt.

drop database if exists car_registry_q3;
create database car_registry_q3;
use car_registry_q3;

create table tb_brands (
    brand varchar(50) primary key,
    name  varchar(50)
);

create table tb_fuels (
    fuel_id int primary key,
    name    varchar(15)
);

create table tb_models (
    brand  varchar(50),
    model  varchar(50),
    fuel   int,
    doors  int,
    price  float,
    primary key (model, fuel),
    foreign key (brand) references tb_brands(brand),
    foreign key (fuel)  references tb_fuels(fuel_id)
);

create table tb_cars (
    brand            varchar(50),
    model            varchar(50),
    manufacture_date date,
    plate            char(7),
    colour           varchar(15),
    chassis_number   char(10),
    primary key (chassis_number),
    foreign key (brand) references tb_brands(brand),
    foreign key (model) references tb_models(model)
);

create table tb_owners (
    chassis_number char(10),
    owner_name     varchar(50),
    foreign key (chassis_number) references tb_cars(chassis_number)
);

insert into tb_brands values ('audi',    'R8');
insert into tb_brands values ('BMW',     '320i');
insert into tb_brands values ('Porsche', 'Panamera');

insert into tb_fuels values (1, 'petrol');
insert into tb_fuels values (2, 'ethanol');
insert into tb_fuels values (3, 'diesel');

insert into tb_models values ('audi',    'sport', 1, 2,  87900.00);
insert into tb_models values ('Porsche', 'suv',   3, 4, 115000.00);
insert into tb_models values ('BMW',     'city',  2, 4,  57800.00);

insert into tb_cars values ('audi',    'sport', '2015-10-22', 'ASD1234', 'black',  '1231231232');
insert into tb_cars values ('audi',    'sport', '2015-05-10', 'AAB5544', 'black',  '4445556667');
insert into tb_cars values ('Porsche', 'suv',   '2005-02-15', 'HHH1456', 'silver', '2342342342');
insert into tb_cars values ('BMW',     'city',  '2018-05-23', 'LKW5522', 'black',  '3453453452');

insert into tb_owners values ('1231231232', 'João Silva');
insert into tb_owners values ('2342342342', 'Maria Almeida');
insert into tb_owners values ('3453453452', 'Afonso Pereira');

-- INFORMATION 1 - How many cars of brand X were made in month Y?
--   (audi, October 2015)
select count(*) as cars_made
from tb_cars c
where c.brand = 'audi'
  and year(c.manufacture_date) = 2015
  and month(c.manufacture_date) = 10;

-- INFORMATION 2 - How many brands offer petrol cars with only two doors?
select count(distinct m.brand) as two_door_petrol_brands
from tb_models m
join tb_fuels f on f.fuel_id = m.fuel
where m.doors = 2
  and f.name = 'petrol';

-- INFORMATION 3 - Plate and owner of the oldest car
select c.plate, o.owner_name
from tb_cars c
join tb_owners o on o.chassis_number = c.chassis_number
order by c.manufacture_date
limit 1;
