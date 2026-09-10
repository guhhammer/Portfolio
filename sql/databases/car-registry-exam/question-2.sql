-- Databases exam, 13 June 2019, question 2: from the physical model, can the
-- following information be extracted?  Student: Gustavo Hammerschmidt.

drop database if exists car_registry_q2;
create database car_registry_q2;
use car_registry_q2;

/*
INFORMATION 1 - How many cars of brand X were made in month Y?
    Yes: through the chassis number and the manufacture date, with a select.

INFORMATION 2 - How many brands offer petrol cars with only two doors?
    Yes: through the fuel table and the chassis number.

INFORMATION 3 - What are the plate and the owner's name of the oldest car?
    Yes: through the chassis number and the owner table.

Explanation of information 1: it is possible to find the number of cars of one
specific brand made in one specific month, that is, how many cars a maker
produced in a month.
*/

create table tb_brands (
    brand varchar(50) primary key,
    name  varchar(50)
);

create table tb_fuels (
    fuel_id int primary key,
    name    varchar(15)
);

create table tb_models (
    model  varchar(50),
    fuel   int,
    doors  int,
    price  float,
    primary key (model, fuel),
    foreign key (fuel) references tb_fuels(fuel_id)
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

/*
The chassis number tracks a specific car inside the factory: the manufacturer
can find one particular vehicle through this surrogate key.
*/
