-- Company: employees, their managers and the companies they work for.

drop database if exists company;
create database company;
use company;

create table manager (
    manager_id int primary key,
    name       varchar(100)
);

create table employee (
    employee_id int primary key,
    name        varchar(100),
    street      varchar(100),
    city        varchar(100),
    phone       varchar(25),
    manager_id  int,
    foreign key (manager_id) references manager(manager_id)
);

create table company (
    company_id int primary key,
    name       varchar(100),
    city       varchar(100)
);

create table works_for (
    employee_id int,
    company_id  int,
    salary      int,
    foreign key (employee_id) references employee(employee_id),
    foreign key (company_id)  references company(company_id)
);

insert into manager values (145213, 'Cleyton Peres');
insert into manager values (13,     'Juvênio Silva');
insert into company values (845120, 'Petrol', 'Curitiba');

insert into employee  values (49623, 'John Stiferson',   'Blackender Street',        'Curitiba', '978451264', 145213);
insert into works_for values (49623, 845120, 8000);

insert into employee  values (13,    'Lucas Ekreger',    'Couto Pereira Street',     'Curitiba', '974651313', 145213);
insert into works_for values (13, 845120, 5000);

insert into employee  values (15,    'Lukka Carr',       'Fim do Novo Mundo Street', 'Curitiba', '998233644', 145213);
insert into works_for values (15, 845120, 4000);

insert into employee  values (7896,  'Daividison Nover', 'Vortrer Street',           'Curitiba', '954653641', 145213);
insert into works_for values (7896, 845120, 90000);

-- Every employee with manager, company and salary, best paid first
select e.name as employee, m.name as manager, c.name as company, w.salary
from employee e
join manager   m on m.manager_id = e.manager_id
join works_for w on w.employee_id = e.employee_id
join company   c on c.company_id = w.company_id
order by w.salary desc;
