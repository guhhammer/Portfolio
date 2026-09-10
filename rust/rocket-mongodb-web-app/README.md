# Rocket + MongoDB Web App (creator donation platform)

A complete server-rendered website written in Rust: creators and non-profits publish a profile, visitors search them by category and tier, and donations are recorded and turned into a "trending" ranking. HTML is rendered on the server with Tera templates; data lives in MongoDB; an Ethereum sub-project holds the donation smart contract the site was designed to plug into.

For a non-technical reader: think of a small "support a creator" site (profiles, search, donate button, trending list, admin page) where the whole server is written in Rust.

## What it demonstrates

- **Rocket web framework end to end**: routes for the home page, search, profile creation, donations, user dashboard (get/patch/delete), trends, about, terms and an admin page; a static file server; custom error pages.
- **Server-side rendering** with Tera templates (`templates/`), reusable components (header, search bar, trending, footer, profile) and a Tailwind-styled `static/styles.css`.
- **MongoDB persistence** through a small `db/` module: connection, CRUD, periodic snapshots to disk (`migrations/snapshots/`) and restore from the last snapshot.
- **Rocket fairings as background jobs**: `populater` seeds the database from JSON examples, `set_snapshot_tasks` schedules snapshots, `donation_redefiner` recomputes donation statistics, `snapshots_loader` restores state on boot.
- **Typed domain models** (`models/`): `User`, `Donation`, `Trending`, plus `Category` and `Tier` enums with over forty categories, validated at deserialization time.
- **Operations**: multi-stage `Dockerfile`, `docker-compose.yml` with a MongoDB service and health check, `docker.sh` helper menu, file + console logging with `fern`/`log4rs`.
- **Blockchain integration** (`blockchain/ethereum/`): an ERC-20 token and a `Donation` contract with Hardhat and Truffle configurations, tests and migrations, written to receive the donations the website records.

## Layout

```
src/
├── main.rs          logger, MongoDB connection, collections, fairings, route mounting
├── config.rs        database, snapshot and populate settings (paths, timers, flags)
├── routes/          about, admin, create, donate, index, search, terms, trends, user_dashboard
├── models/          User, Donation, Trending, Category, Tier
├── db/              connection, crud, snapshot, load_from_snapshot, simulate_transactions
├── fairings/        populater, snapshots_loader, set_snapshot_tasks, donation_redefiner
├── utils/           builders and helpers for users, trending and donations
├── guards/, error.rs, logger.rs, state.rs
templates/           Tera pages and components
static/              CSS, JS and images
migrations/          example seed data (fictional creators), MongoDB install notes
blockchain/ethereum/ Solidity contracts (MyERC20, Donation), Hardhat + Truffle setup, tests
```

## Run

Requirements: Rust, MongoDB on `localhost:27017` (or Docker).

```bash
# .env holds DATABASE_URL / DATABASE_NAME (local defaults only, no secrets)
cargo run                     # http://127.0.0.1:8000
```

With Docker Compose (starts MongoDB and the app together):

```bash
./docker.sh                   # option 1: docker-compose up --build -d
```

On first start the `populater` fairing loads the fictional creators from `migrations/examples/` so the site has something to show. Set `POPULATE_DB_EXAMPLE` to `false` in `src/config.rs` to disable that.

## Ethereum contracts

```bash
cd blockchain/ethereum
npm install
npx hardhat test              # runs the MyERC20 and Donation tests
```
