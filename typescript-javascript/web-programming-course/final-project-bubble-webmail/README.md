# Bubble: a webmail client (final project)

A webmail application written from scratch with HTML, CSS, JavaScript/jQuery on the client and PHP on the server, using XML files instead of a database. Users create an account, log in, write messages to other Bubble users, and manage them across the usual folders.

For a non-technical reader: a small Gmail-like site, built to learn how browsers and servers talk to each other (AJAX requests) before using frameworks.

## Features

- Account creation, login and password change (`homeScreen`, `homeScreenOptions`, `php/createLogin.php`, `updateLogin.php`, `checkLogin.php`, `validate.php`).
- Compose, save as draft, send, reply and forward (`composeMessage`, `composeMessage2`, `php/makeEmail.php`, `makeDraft.php`).
- Folders: inbox, sent items, drafts, favorites, junk mail, deleted items and archive, each with its own page and script (`html/`, `javascript/`).
- Search across messages with highlighting (`javascript/searcher.js`), message viewer (`viewMessageScreen`), colour-coded read/unread flags (`php/updateGreenCode.php`).
- Server-side storage as XML: one file per account in `xml/AllEmails/`, one folder per user and mailbox in `xml/emails/<user>/<folder>/`, a counter in `info/code.xml`, and "bridge" files that pass the current selection between pages.

## Layout

```
index.html               splash screen that redirects to the login page
html/                    one page per screen (login, options, inbox, compose, ...)
javascript/              page logic and AJAX calls (jQuery), plus searcher, redirect and minimize helpers
php/                     endpoints called by the pages: create/check login, make/draft/send email, mark spam, favorite, delete, code counters
CSS/                     base layout and per-screen styles
xml/                     sample data: five test accounts (cc, dd, ff, gg, rr) with English sample messages
images/, notes/          icons and the icon-source note
```

## Run

Serve the folder with PHP (XAMPP, or `php -S localhost:8000` inside this directory) and open `http://localhost:8000/`. Log in with one of the sample accounts, for example user `cc` with password `cc`.

The code and file names were translated from Portuguese to English in 2026; behaviour is unchanged and every PHP file passes `php -l`.
