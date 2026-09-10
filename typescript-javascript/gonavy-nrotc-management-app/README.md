# GoNavy! NROTC management app

A web application for running a Naval ROTC unit, built by a student team for the Software Engineering course of my 2020 exchange semester in the United States. The unit's staff and midshipmen use it to publish training events, manage watch bills, request and approve leave ("chits") through a chain of command, share documents, message each other and see everything on a calendar.

## Modules

| Module | Front end | Back end |
| --- | --- | --- |
| Homepage | `HTML/Homepage`, `JAVASCRIPT/Homepage` | `PHP/Homepage/Divs/*.php` render personal, academic, service, command and contact information blocks |
| Chits (leave requests) | `HTML/Chits`, `JAVASCRIPT/Chits` | `PHP/Chits/Create_Chits` files a request and computes the next approver from the requester's billet; `Chits` and `Cmd_Mgmt_Chits` list pending and completed requests for users and commanders |
| Training | `HTML/Training`, `JAVASCRIPT/Training` | create events, list "my training" |
| Watchbill | `HTML/Watchbill`, `JAVASCRIPT/Watchbill` | view and edit duty rosters |
| Documents | `HTML/Documents`, `JAVASCRIPT/Documents` | `PHP/Documents` upload, download, create and delete files |
| Communication | `HTML/Communication`, `JAVASCRIPT/Communication` | `PHP/Communication` send and retrieve messages |
| Leadership, Calendar | `HTML/Leadership`, `HTML/Calendar` | `PHP/Header/get_page_rank.php` decides what each rank may see; `PHP/Calendar/retrieve.php` feeds FullCalendar |
| Database layer | | `PHP/Database/selector.php`: a small query builder (select, update, where, flags) over `mysqli`; `config.php` holds the connection settings |

Front-end libraries: jQuery, Knockout.js (data binding), FullCalendar and Moment.js (calendar), plain CSS per module. Page switching is done client-side (`JAVASCRIPT/Switch_Pages`).

## Run

1. Create a MySQL database named `navy` and import the schema (the ER diagram and screenshots are in [`../../academic-writing/exchange-semester/gonavy-project-presentation/`](../../academic-writing/exchange-semester/gonavy-project-presentation/)).
2. Edit `PHP/Database/config.php` with your own host, user and password (the committed file contains placeholders only).
3. Serve the folder with Apache + PHP (XAMPP was used during the course) and open `index.html`.

All PHP files pass `php -l` on PHP 8.4.
