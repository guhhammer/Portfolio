# Gustavo Hammerschmidt — Portfolio

A complete body of work from 2018 to today: professional and personal projects alongside a Computer Science degree. Everything here is organized by technology, and **every folder has its own README** that explains, in plain language, what the work is and how to run it.

The focus is **backend engineering** in Rust, Python and TypeScript, **blockchain** (Ethereum and Solana), and **cloud / DevOps** (Docker, AWS). The rest is university coursework across many languages, kept to show range.

## Start here

If you have five minutes, these best show what I can build:

- **[Encrypted peer-to-peer messenger](rust/p2p-encrypted-messenger/)** (Rust) — end-to-end encryption (X25519, ChaCha20-Poly1305, Ed25519), automatic peer discovery on the local network, a key server, and a Tauri desktop app, in a clean hexagonal architecture.
- **[Gas-station management platform](rust/gas-station-api/)** (Rust) — a Rocket REST API with Argon2/JWT authentication and logging, backed by MongoDB, with a matching Tauri desktop client and a central aggregator service.
- **[Diskify — music NFT marketplace](blockchain/solidity/diskify-music-nft-marketplace/)** (Solidity) — a capstone where artists sell albums as ERC-1155 collectibles and earn royalties on resale, evolved across four contract versions; the academic paper behind it is in [`academic-writing/final-paper-diskify/`](academic-writing/final-paper-diskify/).
- **[Flash-loan arbitrage bot](blockchain/solidity/flashloan-arbitrage-bot/)** (Solidity + JS) — an Aave flash-loan contract and the off-chain bot that drives it.
- **[Self-taught blockchain curriculum](blockchain/solidity/eattheblocks-courses/)** — roughly fifteen courses' worth of DeFi, NFT and smart-contract-security projects, backed by [certificates](certificates/courses/blockchain/).

## By technology

### Backend, blockchain and cloud (primary focus)

- **[`rust/`](rust/)** — 18 crates that all pass `cargo check`: the messenger and gas-station platform above, an algorithms library, distributed-systems experiments, and pointers to public Rust repositories and live sites in [`rust/external-projects/`](rust/external-projects/).
- **[`blockchain/`](blockchain/)** — Ethereum smart contracts (Solidity) and Solana programs (Rust + Anchor): the NFT marketplace, the flash-loan bot, an NFT collectible, a graded curriculum and learning contracts. Contracts compile with matching `solc` versions.
- **[`go/`](go/)** — a terminal-dashboard fuel-station simulator, a steganography CLI, a parallel mergesort, sorting benchmarks and a Wails desktop app; all modules build and vet.
- **[`docker-devops/`](docker-devops/)** — a containerized Python service plus Docker reference notes.
- **[`typescript-javascript/`](typescript-javascript/)** — the [portfolio landing page](typescript-javascript/portfolio-landing-page/) (React + TypeScript + Tailwind, builds clean), a PHP/JS NROTC management app, and a web-programming course including a full webmail front end.

### Python (16 courses and projects)

- **[`python/`](python/)** — backends and distributed systems (Flask REST, RabbitMQ, Pyro, Spark), data science and machine learning (pandas, scikit-learn, TensorFlow, a credit-default model, computer vision, deep learning), algorithms, security, simulation and scientific computing. See the [Python index](python/README.md) for the full breakdown.

### Coursework in other languages (kept to show range)

- **[`java/`](java/)** — AI search algorithms, a randomized-sorting benchmark and a data-structures suite (all rewritten in English and `javac`-verified), plus concurrency, graphs, computer architecture, MapReduce and object-oriented programming.
- **[`cpp/`](cpp/)** — OpenGL 3D graphics, operating systems and computer-architecture memory models.
- **[`haskell/`](haskell/)** · **[`prolog/`](prolog/)** — functional and logic programming, plus polyglot coding challenges.
- **[`matlab/`](matlab/)** — computational modelling: anomaly detection with a multivariate Gaussian, Monte Carlo simulations against closed-form answers, a naive Bayes classifier; every script run in Octave.
- **[`sql/`](sql/)** — relational modelling and SQL: a 16-table tech-events database with 35 verified queries, six smaller schemas and an exam, all executed on MariaDB.

### Writing and credentials

- **[`academic-writing/`](academic-writing/)** — the Diskify thesis, eight LaTeX papers with sources, a feasibility study and AHP evaluation, software architecture and UML models, research methods, entrepreneurship coursework and the exchange-semester software-engineering course. Portuguese documents keep their language; every folder and file name is in English and each folder has an English README.
- **[`certificates/`](certificates/)** — 38 course certificates (IBM DevOps track, Duke Rust specialization, a blockchain curriculum, and more) and 33 talk certificates.

## Notes

- Secrets that were once committed here have been removed and the affected keys retired; the history was rebuilt so they no longer appear.
- Generated files (build artifacts, compiled contract ABIs, `node_modules`) are not committed; each project's README says how to rebuild them.
- Course reports and datasets stay with their code. Where a language is used across several courses, the folders cross-link (for example, the AI and sorting work exists in both `python/` and `java/`).

Contact: guhhammer@protonmail.com
