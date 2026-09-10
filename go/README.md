# Go

Backend-style tools and learning modules written in Go. Every module builds with `go build ./...` and passes `go vet` (checked with Go 1.25 in September 2026).

For a non-technical reader: Go is the language behind much of today's cloud tooling. These projects show concurrency (many things happening at once), file and image handling, and command-line tools.

## Projects

| Folder | What it is | Shows |
| --- | --- | --- |
| [`gas-station-simulator/`](gas-station-simulator/) | A fuel station simulated in the terminal: a grid of pumps, a background price updater writing to a JSON store, dashboards and randomized vehicle traffic | goroutines per pump, channels, mutex-protected shared state, JSON persistence, package layout |
| [`steganography-cli/`](steganography-cli/) | Command-line tool that hides a text message inside a PNG image and reads it back | bit manipulation, the `image` package, error handling, CLI design |
| [`parallel-mergesort/`](parallel-mergesort/) | Merge sort split across N goroutines with a timing harness | `sync.WaitGroup`, slicing, measuring speed-ups |
| [`sorting-benchmarks/`](sorting-benchmarks/) | Bubble, insertion, merge, quick and heap sort benchmarked across array sizes with per-algorithm limits | algorithm implementation, benchmarking |
| [`learning/`](learning/) | Nine small modules, one per language feature | arrays and slices, channels, generics, goroutines, pointers, strings, structs and interfaces |
| [`experiments/`](experiments/) | A terminal dashboard with `tview` and a Wails desktop app | third-party UI libraries |

## Run

```bash
cd go/<project>
go run .            # or: go build ./... && go vet ./...
```

The Go version of the gas-station idea is a sibling of the Rust system in [`../rust/gas-station-api/`](../rust/gas-station-api/); both were written to compare the two languages on the same problem.
