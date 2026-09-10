-- Tech events database (team project, "gincana" and proof-of-concept exam, May 2019).
--
-- People register for an event with a role (attendee, speaker, organizer),
-- attend talks given on behalf of companies, and may be affiliated to those
-- companies.  A person is a student, an academic or a non-academic, each with
-- its own attributes (a specialisation, modelled with one table per subtype).
--
-- Contents: schema, sample data, the eight questions written by our team,
-- the ten exam questions and the challenges posed by the other teams.
-- Runs on MySQL 8 / MariaDB 10.2+ (uses common table expressions).

drop database if exists tech_events;
create database tech_events;
use tech_events;

-- ---------------------------------------------------------------- schema

create table person (
    cpf  char(11) primary key,   -- Brazilian taxpayer id
    name varchar(100)
);

create table company (
    company_id int primary key,
    name       varchar(100)
);

create table course (
    course_id int primary key,
    name      varchar(50)
);

create table institution (
    institution_id int primary key,
    name           varchar(75)
);

create table occupation (
    occupation_id int primary key,
    name          varchar(50)
);

create table research_field (
    field_id int primary key,
    name     varchar(50)
);

-- Person subtypes -------------------------------------------------------

create table student (
    cpf            char(11) primary key,
    term           int,
    course_id      int,
    institution_id int,
    foreign key (cpf)            references person(cpf),
    foreign key (course_id)      references course(course_id),
    foreign key (institution_id) references institution(institution_id)
);

create table academic (
    cpf            char(11) primary key,
    field_id       int,
    institution_id int,
    foreign key (cpf)            references person(cpf),
    foreign key (field_id)       references research_field(field_id),
    foreign key (institution_id) references institution(institution_id)
);

create table non_academic (
    cpf           char(11) primary key,
    occupation_id int,
    foreign key (cpf)           references person(cpf),
    foreign key (occupation_id) references occupation(occupation_id)
);

-- Events, talks and relationships --------------------------------------

create table event (
    event_id int primary key,
    name     varchar(100),
    address  varchar(200),
    held_on  date
);

create table talk (
    talk_id    int primary key,
    topic      varchar(100),
    starts_at  datetime,
    company_id int,               -- company presenting the talk
    event_id   int,
    foreign key (company_id) references company(company_id),
    foreign key (event_id)   references event(event_id)
);

create table sponsorship (
    company_id int,
    event_id   int,
    primary key (company_id, event_id),
    foreign key (company_id) references company(company_id),
    foreign key (event_id)   references event(event_id)
);

create table attendance (
    cpf     char(11),
    talk_id int,
    primary key (cpf, talk_id),
    foreign key (cpf)     references person(cpf),
    foreign key (talk_id) references talk(talk_id)
);

create table registration (
    cpf           char(11),
    event_id      int,
    role          varchar(40),   -- Attendee, Speaker or Organizer
    registered_on date,
    fee           float,
    primary key (cpf, event_id),
    foreign key (cpf)      references person(cpf),
    foreign key (event_id) references event(event_id)
);

create table affiliation (
    cpf        char(11),
    company_id int,
    since      date,
    primary key (cpf, company_id, since),
    foreign key (cpf)        references person(cpf),
    foreign key (company_id) references company(company_id)
);

-- ------------------------------------------------------------ sample data

insert into course values (0, 'Computer Science');
insert into course values (1, 'Computer Engineering');
insert into course values (2, 'Game Design');
insert into course values (3, 'Medicine');

insert into institution values (0, 'PUCPR');
insert into institution values (1, 'UFPR');
insert into institution values (2, 'UTFPR');
insert into institution values (3, 'USP');

insert into research_field values (0, 'Mathematics');
insert into research_field values (1, 'Physics');

insert into occupation values (0, 'Freelancer');
insert into occupation values (1, 'Entrepreneur');

-- students (cpf, term, course, institution)
insert into person  values ('13829381928', 'Jose Silva');
insert into student values ('13829381928', 1, 0, 0);
insert into person  values ('29381928391', 'Amanda Souza');
insert into student values ('29381928391', 1, 0, 0);
insert into person  values ('92839182902', 'Joao Henrique');
insert into student values ('92839182902', 1, 1, 2);
insert into person  values ('88291829182', 'Gabriela Farias');
insert into student values ('88291829182', 1, 2, 0);
insert into person  values ('88884444222', 'Gabriel Frias');
insert into student values ('88884444222', 2, 3, 0);

-- academics (cpf, field, institution)
insert into person   values ('82739182738', 'Carlos Borges');
insert into academic values ('82739182738', 0, 0);
insert into person   values ('82889910928', 'Fernanda Silveira');
insert into academic values ('82889910928', 0, 3);
insert into person   values ('82888991029', 'Vinicius Nunes');
insert into academic values ('82888991029', 1, 1);

-- non-academics (cpf, occupation)
insert into person       values ('29102938192', 'Maria Ferreira');
insert into non_academic values ('29102938192', 0);
insert into person       values ('82918299809', 'Rodrigo Pereira');
insert into non_academic values ('82918299809', 0);
insert into person       values ('82938173928', 'Eduardo Lopes');
insert into non_academic values ('82938173928', 1);
insert into person       values ('82938173000', 'João Silva');
insert into non_academic values ('82938173000', 1);

insert into event values (1, 'Computing Super Week', 'Kennedy Avenue, 1337, Downtown, Curitiba, Paraná, Brazil', '2018-03-01');

insert into company values (1, 'Microsoft');
insert into company values (2, 'IBM Brazil');

insert into affiliation values ('29102938192', 1, '2017-05-28');
insert into affiliation values ('82938173928', 2, '2018-01-01');
insert into affiliation values ('82888991029', 2, '2018-01-02');
insert into affiliation values ('82918299809', 2, '2017-08-28');

insert into talk values (1, 'Azure - Microsoft',  '2018-03-03 14:00:00', 1, 1);
insert into talk values (4, 'Python Workshop',    '2018-03-03 18:05:00', 1, 1);
insert into talk values (2, 'AI - IBM',           '2018-03-04 16:00:00', 2, 1);
insert into talk values (5, 'Simple Tutorials',   '2018-03-04 19:00:00', 2, 1);
insert into talk values (3, 'Deep Learning',      '2018-03-05 15:00:00', 1, 1);
insert into talk values (6, 'Entrepreneurship',   '2018-03-05 16:00:00', 1, 1);

insert into registration values ('13829381928', 1, 'Attendee',  '2018-02-15', 50.0);
insert into registration values ('82739182738', 1, 'Speaker',   '2018-01-10',  0.0);
insert into registration values ('29102938192', 1, 'Speaker',   '2018-02-01',  0.0);
insert into registration values ('82938173928', 1, 'Speaker',   '2018-01-20',  0.0);
insert into registration values ('29381928391', 1, 'Organizer', '2018-02-02', 45.0);
insert into registration values ('92839182902', 1, 'Organizer', '2018-01-12',  0.0);
insert into registration values ('88884444222', 1, 'Organizer', '2018-01-13',  0.0);
insert into registration values ('88291829182', 1, 'Attendee',  '2018-02-23', 40.0);
insert into registration values ('82888991029', 1, 'Speaker',   '2018-01-10',  0.0);
insert into registration values ('82918299809', 1, 'Speaker',   '2018-01-10',  0.0);
insert into registration values ('82889910928', 1, 'Speaker',   '2018-02-11',  0.0);
insert into registration values ('82938173000', 1, 'Attendee',  '2018-02-12', 40.0);

insert into sponsorship values (1, 1);
insert into sponsorship values (2, 1);

insert into attendance values ('13829381928', 1);
insert into attendance values ('13829381928', 2);
insert into attendance values ('29102938192', 2);
insert into attendance values ('82938173928', 1);
insert into attendance values ('88884444222', 1);
insert into attendance values ('92839182902', 1);
insert into attendance values ('29381928391', 2);
insert into attendance values ('92839182902', 2);
insert into attendance values ('88291829182', 2);
insert into attendance values ('82888991029', 2);
insert into attendance values ('82889910928', 2);

-- ------------------------------------------------ our team's questions

-- 1. Which two talks of 2018 had the most people present?
select t.topic, count(*) as attendees
from attendance a
join talk t on t.talk_id = a.talk_id
where year(t.starts_at) = 2018
group by t.talk_id, t.topic
order by attendees desc
limit 2;

-- 2. Which speakers are affiliated to IBM Brazil, and since when?
select p.name, af.since
from person p
join registration r  on r.cpf = p.cpf and r.role = 'Speaker'
join affiliation  af on af.cpf = p.cpf
join company      c  on c.company_id = af.company_id
where c.name = 'IBM Brazil'
order by p.name;

-- 3. How many people were present at the "Azure - Microsoft" talk?
select count(*) as attendees
from attendance a
join talk t on t.talk_id = a.talk_id
where t.topic = 'Azure - Microsoft';

-- 4. Were first-term students the largest group at the talks of 2018?
--    (number of first-term students present at each talk)
select t.topic, count(*) as first_term_students
from attendance a
join talk    t on t.talk_id = a.talk_id
join student s on s.cpf = a.cpf
where s.term = 1 and year(t.starts_at) = 2018
group by t.talk_id, t.topic
order by first_term_students desc;

-- 5. Which talks were given on 4 March 2018?
select topic, time(starts_at) as starts
from talk
where date(starts_at) = '2018-03-04'
order by topic;

-- 6. Which role registered most often for an event?
select role, count(*) as registrations
from registration
group by role
order by registrations desc
limit 1;

-- 7. People with which role are most often affiliated to a company?
select r.role, count(*) as affiliations
from registration r
join affiliation af on af.cpf = r.cpf
group by r.role
order by affiliations desc
limit 1;

-- 8. Which role pays the most to take part (average fee)?
select role, avg(fee) as average_fee
from registration
group by role
order by average_fee desc
limit 1;

-- ------------------------------------------------------ exam questions

-- 1. How many academics attended the "AI - IBM" talk?
select count(*) as academics_present
from attendance a
join academic ac on ac.cpf = a.cpf
join talk     t  on t.talk_id = a.talk_id
where t.topic = 'AI - IBM';

-- 2. Does the company presenting the "Deep Learning" talk also sponsor the event?
select c.name as company,
       case when s.company_id is null then 'no' else 'yes' end as sponsors_the_event
from talk t
join company c on c.company_id = t.company_id
left join sponsorship s on s.company_id = c.company_id and s.event_id = t.event_id
where t.topic = 'Deep Learning';

-- 3. What is the most common course among the students who registered for
--    "Computing Super Week"?
select c.name as course, count(*) as students
from registration r
join student s on s.cpf = r.cpf
join course  c on c.course_id = s.course_id
join event   e on e.event_id = r.event_id
where e.name = 'Computing Super Week'
group by c.name
order by students desc
limit 1;

-- 4. How many talks did Microsoft present at "Computing Super Week"?
select count(*) as microsoft_talks
from talk t
join company c on c.company_id = t.company_id
join event   e on e.event_id = t.event_id
where c.name = 'Microsoft' and e.name = 'Computing Super Week';

-- 5. How many freelancers (non-academics) took part as speakers at
--    "Computing Super Week"?
select count(*) as freelancer_speakers
from registration r
join non_academic n on n.cpf = r.cpf
join occupation   o on o.occupation_id = n.occupation_id
join event        e on e.event_id = r.event_id
where r.role = 'Speaker'
  and o.name = 'Freelancer'
  and e.name = 'Computing Super Week';

-- 6. Which institution is the most common among the students and academics
--    registered for "Computing Super Week"?
with participants as (
    select s.institution_id
    from registration r
    join student s on s.cpf = r.cpf
    join event   e on e.event_id = r.event_id
    where e.name = 'Computing Super Week'
    union all
    select a.institution_id
    from registration r
    join academic a on a.cpf = r.cpf
    join event    e on e.event_id = r.event_id
    where e.name = 'Computing Super Week'
)
select i.name as institution, count(*) as people
from participants p
join institution i on i.institution_id = p.institution_id
group by i.name
order by people desc
limit 1;

-- 7. Which group (students, academics, non-academics) paid the least on
--    average at "Computing Super Week"?
with fees as (
    select 'students' as participant_group, r.fee
    from registration r
    join student s on s.cpf = r.cpf
    join event   e on e.event_id = r.event_id
    where e.name = 'Computing Super Week'
    union all
    select 'academics', r.fee
    from registration r
    join academic a on a.cpf = r.cpf
    join event    e on e.event_id = r.event_id
    where e.name = 'Computing Super Week'
    union all
    select 'non-academics', r.fee
    from registration r
    join non_academic n on n.cpf = r.cpf
    join event        e on e.event_id = r.event_id
    where e.name = 'Computing Super Week'
)
select participant_group, avg(fee) as average_fee
from fees
group by participant_group
order by average_fee
limit 1;

-- 8. How many people affiliated to IBM Brazil registered for
--    "Computing Super Week" on 10 January 2018?
select count(distinct r.cpf) as ibm_registrations
from registration r
join affiliation af on af.cpf = r.cpf
join company     c  on c.company_id = af.company_id
join event       e  on e.event_id = r.event_id
where c.name = 'IBM Brazil'
  and e.name = 'Computing Super Week'
  and r.registered_on = '2018-01-10';

-- 9. What is the most common research field among the academics who speak
--    at "Computing Super Week"?
select f.name as research_field, count(*) as speakers
from registration r
join academic       a on a.cpf = r.cpf
join research_field f on f.field_id = a.field_id
join event          e on e.event_id = r.event_id
where r.role = 'Speaker' and e.name = 'Computing Super Week'
group by f.name
order by speakers desc
limit 1;

-- 10. What was the last talk of "Computing Super Week"?
select t.topic, t.starts_at
from talk t
join event e on e.event_id = t.event_id
where e.name = 'Computing Super Week'
order by t.starts_at desc
limit 1;

-- ------------------------------------- challenges posed by other teams

-- Which topic was watched by the most students?
select t.topic, count(*) as students
from attendance a
join student s on s.cpf = a.cpf
join talk    t on t.talk_id = a.talk_id
group by t.talk_id, t.topic
order by students desc
limit 1;

-- Names of the students who attended a talk presented by a given company
-- (Microsoft)
select distinct p.name
from attendance a
join student s on s.cpf = a.cpf
join person  p on p.cpf = a.cpf
join talk    t on t.talk_id = a.talk_id
join company c on c.company_id = t.company_id
where c.name = 'Microsoft'
order by p.name;

-- Which company sponsored the most events?
select c.name, count(*) as events_sponsored
from sponsorship s
join company c on c.company_id = s.company_id
group by c.name
order by events_sponsored desc
limit 1;

-- Times of the talks that one particular student (Jose Silva) attended
select p.name, t.topic, time(t.starts_at) as starts
from attendance a
join student s on s.cpf = a.cpf
join person  p on p.cpf = a.cpf
join talk    t on t.talk_id = a.talk_id
where p.name = 'Jose Silva'
order by t.starts_at;

-- Names of the academic speakers
select p.name
from registration r
join academic a on a.cpf = r.cpf
join person   p on p.cpf = r.cpf
where r.role = 'Speaker'
order by p.name;

-- Total number of distinct people present on one day of the event (3 March 2018)
select count(distinct a.cpf) as people_present
from attendance a
join talk t on t.talk_id = a.talk_id
where date(t.starts_at) = '2018-03-03';

-- The talk that starts latest on each day of the event
select date(t.starts_at) as day, t.topic, time(t.starts_at) as starts
from talk t
where t.starts_at = (select max(t2.starts_at)
                     from talk t2
                     where date(t2.starts_at) = date(t.starts_at))
order by day;

-- Which term are most of the student organizers in?
select s.term, count(*) as organizers
from registration r
join student s on s.cpf = r.cpf
join event   e on e.event_id = r.event_id
where e.name = 'Computing Super Week' and r.role = 'Organizer'
group by s.term
order by organizers desc
limit 1;

-- Which students were at the "Azure - Microsoft" talk?
select p.name
from attendance a
join student s on s.cpf = a.cpf
join person  p on p.cpf = a.cpf
join talk    t on t.talk_id = a.talk_id
where t.topic = 'Azure - Microsoft'
order by p.name;

-- How many academics were at talk "AI - IBM" on 4 March 2018?
select count(*) as academics
from attendance a
join academic ac on ac.cpf = a.cpf
join talk     t  on t.talk_id = a.talk_id
where t.topic = 'AI - IBM' and date(t.starts_at) = '2018-03-04';

-- How many people were at the "AI - IBM" talk?
select count(*) as people
from attendance a
join talk t on t.talk_id = a.talk_id
where t.topic = 'AI - IBM';

-- How many students attended talks at which a speaker affiliated to
-- Microsoft was present?
with microsoft_talks as (
    select distinct a.talk_id
    from attendance a
    join registration r  on r.cpf = a.cpf and r.role = 'Speaker'
    join affiliation  af on af.cpf = a.cpf
    join company      c  on c.company_id = af.company_id
    where c.name = 'Microsoft'
)
select count(*) as students
from attendance a
join student s on s.cpf = a.cpf
join microsoft_talks m on m.talk_id = a.talk_id;

-- Which talk was attended by the most non-academics?
select t.topic, count(*) as non_academics
from attendance a
join non_academic n on n.cpf = a.cpf
join talk         t on t.talk_id = a.talk_id
group by t.talk_id, t.topic
order by non_academics desc
limit 1;

-- Which talks did the professors (academics) attend?
select p.name, t.topic
from attendance a
join academic ac on ac.cpf = a.cpf
join person   p  on p.cpf = a.cpf
join talk     t  on t.talk_id = a.talk_id
order by p.name;

-- Names of the non-academic attendees
select p.name
from registration r
join non_academic n on n.cpf = r.cpf
join person       p on p.cpf = r.cpf
where r.role = 'Attendee'
order by p.name;

-- Which talk did Amanda Souza attend?
select p.name, t.topic
from attendance a
join person p on p.cpf = a.cpf
join talk   t on t.talk_id = a.talk_id
where p.name = 'Amanda Souza';

-- How many talks took place in 2018?
select count(*) as talks_in_2018
from talk
where year(starts_at) = 2018;
