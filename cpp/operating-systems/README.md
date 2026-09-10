# Operating systems (C++)

Programming and written assignments from CS433 Operating Systems at California State University San Marcos (exchange semester, spring 2020). All code was written in English at the time; it builds with `g++` and each project has a `Makefile` (verified on Linux) and the report that was handed in.

| Project | What it is | Build and run |
| --- | --- | --- |
| [`process-ready-queue/`](process-ready-queue/) | Process control blocks (`PCB`) in a linked list and a priority-ordered ready queue (`ReadyQueue`), with a test driver that inserts and removes a million processes and reports timings. | `make && ./ready_queue` |
| [`unix-shell/`](unix-shell/) | `osh`, a small Unix shell: parses the command line, runs each command in a child process with `fork`/`execvp`, supports `!!` (repeat last command) and `>` / `<` redirection. | `make && ./osh` |
| [`cpu-scheduling-simulator/`](cpu-scheduling-simulator/) | Simulates five CPU scheduling algorithms on the processes of `input.txt` (id, burst time, priority): FCFS, SJF, priority, round-robin and priority with round-robin; prints per-process turnaround and waiting times and the averages. | `make && ./fcfs input.txt` (also `./sjf`, `./priority`, `./rr`, `./priorityWithRR`) |
| [`producer-consumer-pthreads/`](producer-consumer-pthreads/) | Bounded-buffer producer/consumer with POSIX threads, a mutex and two counting semaphores; the run time, thread counts, buffer size and delays come from the command line. | `make && ./producer_consumer 10 3 2` |

[`written-homework/`](written-homework/) holds the five written homework sets (answers to Silberschatz, *Operating System Concepts*, 10th edition exercises) and the chapter 1 exercises.
