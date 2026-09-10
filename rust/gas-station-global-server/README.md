# Gas Station Global Server

The central service of the [gas-station system](../gas-station-api/): every local station API reports to it. Today it is a minimal Rocket server that answers `GET /ping` and logs the station that called; the plan is for it to become the company-wide registry of stations, their grids and enabled features.

It exists in the portfolio to show the intended multi-service shape of the system: local API per station, one global aggregator, and a desktop client.

## Run

```bash
cargo run          # listens on http://127.0.0.1:8000
curl http://127.0.0.1:8000/ping
```
