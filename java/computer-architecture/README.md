# Computer architecture (Java)

Coursework from the Computer Architecture course at PUCPR (2020): simulators of the CPU / cache / memory hierarchy written from scratch, plus exercises on address mapping, processes and cache coherence. Everything is in English and compiles with `javac` (verified).

| Project | What it is |
| --- | --- |
| [`cache-simulator/`](cache-simulator/) | **The graded assignment.** A direct-mapped, write-back cache (128 lines of 64 words) in front of a 16,000-word RAM, with the CPU walking a memory range through it; the address is split into tag / line / word fields and dirty lines are written back on eviction. |
| [`cache-memory-v1/`](cache-memory-v1/) | First version of the same idea: a single-block write-through cache. |
| [`boot-simulator/`](boot-simulator/) | A CPU that boots by walking the address range stored in the first two words of RAM. |
| [`cache-address-mapping/`](cache-address-mapping/) | Computes the tag, line and word bit fields of an address for an 8K-word cache over a 16M-word memory. |
| [`process-spawning/`](process-spawning/) | Spawns child processes with `ProcessBuilder` and answers two questions about parent/child lifetimes (`questions.md`). |
| [`pi-benchmark/`](pi-benchmark/) | Times the Nilakantha and Bellard series for pi (one billion terms) and console output throughput. |
| [`mesi-coherence-trace/`](mesi-coherence-trace/) | A written trace of the MESI cache-coherence protocol for two processors sharing one variable. |
| [`notes/`](notes/) | Memory-placement (first/best/worst/next fit) and program-relocation exercises; example commands for the virtual-memory simulator. |

Build any project with, for example:

```sh
javac -d out $(find cache-simulator/src -name '*.java') && (cd out && java main.Main)
```
