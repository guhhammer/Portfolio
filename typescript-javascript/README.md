# TypeScript and JavaScript

Front-end and full-stack web work: a React + TypeScript site, a PHP/JavaScript management app built with a team during an exchange semester in the United States, and the web-programming course where I learned HTML, CSS, JavaScript, jQuery, PHP and XML.

TypeScript is also used for the desktop front ends of the Rust projects (Tauri + React in [`../rust/`](../rust/)), the Solana test suites in [`../blockchain/solana/`](../blockchain/solana/), and the React dapp of [Diskify](../blockchain/solidity/diskify-music-nft-marketplace/).

| Folder | What it is | Stack |
| --- | --- | --- |
| [`portfolio-landing-page/`](portfolio-landing-page/) | My personal landing page (this portfolio's companion site) | React, TypeScript, Tailwind CSS, Create React App, GitHub Pages |
| [`gonavy-nrotc-management-app/`](gonavy-nrotc-management-app/) | "GoNavy!", a management app for a naval ROTC unit: training events, watch bills, leave requests ("chits") with approval chains, documents, messaging and a calendar. Team project for a Software Engineering course (2020, taught in English). | PHP 8 + MySQL backend, jQuery, Knockout.js, FullCalendar |
| [`web-programming-course/`](web-programming-course/) | Nine classes of exercises, an exam and the final project: "Bubble", a webmail client with inbox, drafts, favorites, junk mail, archive and user accounts, stored as XML files server-side | HTML, CSS, JavaScript, jQuery, PHP, XML |

## Verification

- The landing page installs, type-checks (`tsc --noEmit`) and builds (`npm run build`) with Node 24.
- Every PHP file in this folder passes `php -l` (PHP 8.4). Two long-standing syntax errors in the GoNavy chit workflow were fixed during the clean-up.
