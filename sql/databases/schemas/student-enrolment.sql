-- University enrolment: courses, students, subjects (mandatory or elective),
-- enrolments with grade and attendance, financing and prerequisites.

drop database if exists student_enrolment;
create database student_enrolment;
use student_enrolment;

create table course (
    course_id int primary key,
    name      varchar(100)
);

create table student (
    registration int primary key,
    name         varchar(100),
    birth_date   date,
    course_id    int,
    enrolled_on  date,
    foreign key (course_id) references course(course_id)
);

create table subject (
    course_id  int,
    subject_id int primary key,
    name       varchar(100),
    foreign key (course_id) references course(course_id)
);

create table enrolment (
    registration int,
    subject_id   int,
    enrolled_on  date,
    grade        int,
    attendance   int,
    foreign key (registration) references student(registration),
    foreign key (subject_id)   references subject(subject_id)
);

create table elective (
    course_id  int,
    subject_id int,
    foreign key (course_id)  references course(course_id),
    foreign key (subject_id) references subject(subject_id)
);

create table mandatory (
    course_id  int,
    subject_id int,
    foreign key (course_id)  references course(course_id),
    foreign key (subject_id) references subject(subject_id)
);

create table financing (
    registration int,
    total_amount int,
    discount     int,
    final_amount int,
    foreign key (registration) references student(registration)
);

create table prerequisite (
    subject_id       int,
    has_prerequisite bool,   -- this subject requires another one
    is_prerequisite  bool,   -- this subject is required by another one
    foreign key (subject_id) references subject(subject_id)
);
