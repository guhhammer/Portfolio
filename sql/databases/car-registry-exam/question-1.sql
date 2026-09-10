-- Databases exam, 13 June 2019, question 1: find and fix the errors in a
-- physical model handed out with the exam.  Student: Gustavo Hammerschmidt.

drop database if exists car_registry_q1;
create database car_registry_q1;
use car_registry_q1;

/*
Tables as given in the exam:

create table tb_cars(
    brand varchar(100),
    model varchar(100),
    manufacture_year int,
    plate char(7);
    price numeric(6,2);
    colour varchar(100)
    primary key (brand, model);
    foreign key (brand) references tb_brands(brand)
);

create table tb_brands(
    brand varchar(50),
    name varchar(50)
);
*/

/*
Problems found:
  - Structural integrity: tb_brands is referenced before it exists, so it must
    be created first.
  - Column sizes do not match the data: colour varchar(100) is far too wide
    (which colour name has 100 characters?).
  - Semicolons were used to separate the plate and price columns, which breaks
    the statement.
  - manufacture_year only ever holds four-digit numbers, so int is more than
    it needs.
  - A comma is missing after the colour column.
  - tb_brands.brand is referenced by a foreign key, so it needs to be a key.
*/

create table tb_brands (
    brand varchar(50) primary key,   -- key added so it can be referenced
    name  varchar(50)
);

create table tb_models (
    model            varchar(50) primary key,
    manufacture_year char(4),   -- changed from int: only four-digit numbers are stored
    price            float      -- changed from numeric(6,2)
);

create table tb_cars (
    brand          varchar(50),    -- size reduced: 100 characters is too much for a brand
    model          varchar(50),    -- size reduced
    plate          char(7),        -- stray semicolon removed
    colour         varchar(15),    -- size reduced to match the data
    chassis_number varchar(100),   -- added: a surrogate key that lets the maker locate one specific car
    primary key (brand, model),
    foreign key (brand) references tb_brands(brand),   -- tb_brands is now created before this table
    foreign key (model) references tb_models(model)
);

-- Perhaps manufacture_year does not depend on the model.

/*
The chassis number tracks a specific car inside the factory: the manufacturer
can find one particular vehicle through this surrogate key.
*/
