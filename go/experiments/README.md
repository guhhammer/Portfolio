# Experiments

Two throw-away projects that test user-interface libraries for the gas-station idea.

| Folder | What it tries |
| --- | --- |
| [`terminal-dashboard/`](terminal-dashboard/) | A `tview`/`tcell` terminal UI that renders the nine-pump grid as a table and updates random prices live. |
| [`wails-desktop-app/`](wails-desktop-app/) | The Wails starter: a Go backend (`app.go` exposes `Greet`) bound to a React front end (`frontend/`), packaged as a native desktop window. `frontend/dist/` is produced by `wails build`; an empty placeholder is committed so `go build` succeeds. |

```bash
cd terminal-dashboard && go run .
cd wails-desktop-app && wails dev     # needs the Wails CLI and Node
```
