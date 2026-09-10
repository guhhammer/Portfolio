# Wails desktop app

The Wails React starter, kept as a reference for packaging a Go backend as a native desktop app. `app.go` exposes a `Greet(name)` method that the React front end (`frontend/src/App.jsx`) calls through the generated bindings in `frontend/wailsjs/`.

```bash
wails dev      # live development (needs the Wails CLI, Go and Node)
wails build    # produces the desktop binary; fills frontend/dist/
```

`frontend/dist/` holds only a placeholder in git so that `go build` and `go vet` succeed without a front-end build.
