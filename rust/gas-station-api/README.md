# Gas Station API

A REST API, written in Rust with the Rocket framework, that runs a fuel station: it keeps the layout of pumps ("the grid"), updates fuel prices and supply over time, simulates cars arriving and fuelling, and persists everything in MongoDB. It is the backend of a three-part system: this API, the [desktop client](../gas-station-desktop/) and the [global server](../gas-station-global-server/) that many stations report to.

For a non-technical reader: this is the kind of service that would sit behind a company's operations dashboard. Staff log in, see the state of every pump, change prices, and watch sales happen in real time.

## What it demonstrates

- **HTTP API design with Rocket**: routes for login, grid CRUD (`GET/POST/PATCH/DELETE /api/grid`), simulations and health checks, mounted under `/api`, `/simulation` and `/test`.
- **Authentication and authorization**: passwords hashed with Argon2, sessions carried as JSON Web Tokens, and request guards (`guards/auth_user.rs`, `guards/auth_role.rs`) that reject requests without a valid token or the right role.
- **Background simulation**: helpers such as `price_maker`, `supply_maker`, `price_tracker` and `incoming_simulation` run on timers and mutate shared state (`Grids`) behind locks.
- **Persistence layer**: a small adapter (`database_local_adapter/`) that isolates all MongoDB access (connection, get, post, patch, delete of grids) from the rest of the code.
- **Configuration**: `src/etc/config.json` toggles every subsystem (run or not, print or not, timers), so the same binary can run as a quiet server or a verbose simulator.
- **Operational details**: TLS via `Rocket.toml`, file + console logging (`fern`), a multi-stage `Dockerfile` that produces a small Debian image, and shell scripts to start a MongoDB container and post example grids.

## Layout

```
src/
├── main.rs                   startup: logger, config, MongoDB connection, Rocket launch
├── routes/                   HTTP handlers (login, grid CRUD, simulations, hello/health, tests)
├── guards/                   JWT user guard and role guard
├── fairings/                 Rocket middleware (environment logging)
├── datastructures/           Grid, GasPump, Pump, PumpStatus, Fuel, User, Role, Message, AppState, Config
├── database_local_adapter/   MongoDB connection and grid CRUD
├── helpers/                  builders, hasher, price/supply makers, price tracker, orchestrator, logger setup
├── simulations/              flow simulation and the price/supply rules it uses
└── etc/                      config.json and an example grid.json
certs/                        make-certs memo (generate your own cert.pem/key.pem, see below)
Dockerfile, Rocket.toml, rustfmt.toml
mongodb-docker-controller.sh  start/stop a local MongoDB container
post-grid-a.sh / -b / -c      example authenticated requests that create grids
```

## Running it

Requirements: Rust (stable), a MongoDB instance on `localhost:27017`, OpenSSL for the TLS certificate.

```bash
# 1. start MongoDB (Docker)
./mongodb-docker-controller.sh          # choose option 1

# 2. create a self-signed certificate for local HTTPS
openssl req -x509 -newkey rsa:4096 -keyout certs/key.pem -out certs/cert.pem -days 365 -nodes

# 3. configure secrets (never commit them)
export JWT_SECRET="change-me"
export EXPIRATION_TIME=3600            # JWT lifetime in seconds

# 4. run
cargo run
# API on https://localhost:8080/api  (accept the self-signed certificate, e.g. curl -k)
```

With Docker instead of a local toolchain:

```bash
docker build -t gas-station-api .
docker run -p 8080:8080 -e JWT_SECRET=change-me gas-station-api
```

Then log in (`POST /api/login`) to get a token and use `post-grid-a.sh <token>` to create a station grid.

## Notes

- `gas-station-concept.jpeg` is the concept image used while designing the pump-grid model.
- Release builds are tuned for small binaries (see `[profile.release]` in `Cargo.toml`); an earlier experiment compared the default, `opt-level=3` and fat-LTO builds of this crate.
