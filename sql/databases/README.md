# Databases course (SQL)

Coursework from the Databases course at PUCPR (2019): conceptual modelling (entity-relationship diagrams, data dictionary, logical model), physical models in MySQL and query writing. The SQL is translated to English with proper joins and was run end to end on MariaDB 11; each script creates its own database, so any of them can be executed on its own:

```sh
mysql -u root -p < tech-events/tech-events.sql
```

## Tech events (`tech-events/`)

The main project of the course, built by a team of two and then queried in an in-class "gincana" (query contest between teams) and in a proof-of-concept exam. People register for an event with a role (attendee, speaker, organizer), attend talks presented by companies and may be affiliated to those companies; a person is a student, an academic or a non-academic, modelled as one table per subtype.

`tech-events.sql` holds the 16-table schema, the sample data and 35 queries in three groups:

- the eight questions our team wrote (most attended talks, speakers affiliated to a company, roles that register or pay the most);
- the ten exam questions (does the company presenting a talk also sponsor the event, the most common course or institution among participants, which group pays the least on average, the last talk of the event), using common table expressions, `UNION ALL`, `CASE` and correlated subqueries;
- the challenges posed by the other teams, answered during the contest.

```
-- 7. Which group (students, academics, non-academics) paid the least on average?
+-------------------+-------------+
| participant_group | average_fee |
+-------------------+-------------+
| academics         |           0 |
+-------------------+-------------+
```

## Schemas (`schemas/`)

| File | What it models |
| --- | --- |
| `online-store.sql` | Customers, suppliers, products, categories, purchases and sales history, with ten queries (purchases per customer, verified suppliers, customers of legal age, units sold per day, revenue per supplier). |
| `video-rental.sql` | Titles, physical copies, price classes, categories and rentals with due dates and late fees; one report query. |
| `company.sql` | Employees, managers, companies and salaries. |
| `student-enrolment.sql` | Courses, students, mandatory and elective subjects, enrolments, financing and prerequisites. |
| `shopping-mall.sql` | The "shopping mall" mini-world of activity 2 (shops, products, customers, car park). |
| `economic-freedom-index.sql` | The twelve indicators of the Economic Freedom Index as one wide table (team topic 8). |
| `economic-freedom-index-eav.sql` | The same index in an entity-attribute-value layout, with Brazil's 2018 scores and the query that averages them. |

## Car-registry exam (`car-registry-exam/`)

Three questions from the June 2019 exam, with the reasoning kept as comments: `question-1.sql` lists the defects of a physical model handed out with the exam and fixes them; `question-2.sql` extends the model (fuel, doors, manufacture date, owner) and argues that three pieces of information can be extracted from it; `question-3.sql` populates it and proves the point with the three queries (cars of a brand made in a given month, brands with two-door petrol cars, the oldest car and its owner).

## Documents (`documents/`)

Written in Portuguese, as submitted: the ER diagrams (`er-diagram*.pdf/.png`, `entity-relationship.jpg`, the brModelo file `exam-er-model.brM3`), the data dictionary and logical model, the video-rental model report, a reverse-engineering exercise, the chess-game and shopping-mall scenarios, the proof-of-concept write-ups with the query screenshots for the tech-events project and for the car-registry exam, and the first exam's questions re-done as a self-correction exercise.
