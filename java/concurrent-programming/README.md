# Concurrent, parallel and distributed programming (Java)

Coursework from the Concurrent Programming course at PUCPR (2020): the classic synchronization problems solved with semaphores and monitors, parallel speed-up studies, and small distributed systems over sockets and RMI. Everything is in English and compiles with `javac` (verified); each project has its own `src/` and a `Main` (or the class named in the table).

## Synchronization problems

| Project | What it is |
| --- | --- |
| [`barbershop-semaphores/`](barbershop-semaphores/) | Sleeping barber with semaphores: a waiting room of four, customers who give up when it is full. |
| [`barbershop-monitor/`](barbershop-monitor/) | The same problem as a monitor (`ReentrantLock` + conditions). |
| [`dining-savages-semaphores/`](dining-savages-semaphores/) | Dining savages: a shared pot, a cook woken when it is empty. |
| [`dining-savages-monitor/`](dining-savages-monitor/) | Dining savages as a monitor with three gates (scoreboard pattern). |
| [`pipeline-generator-filter-analyzer/`](pipeline-generator-filter-analyzer/) | Three threads passing a baton with semaphores: generate a string, upper-case it, count its characters. |
| [`payroll-barrier/`](payroll-barrier/) | Four workers each apply one payroll deduction to four slices of an employee list, meet at a barrier, then write one report per slice. |
| [`reusable-barrier-merge/`](reusable-barrier-merge/) | Reusable (two-turnstile) barrier: four workers write sorted files of a million numbers, a combiner merges them without duplicates, forever. |
| [`semaphore-basics/`](semaphore-basics/) | The first exercises: wait/signal on one thread, tasks running freely, ten tasks serialised by a mutex. |
| [`producer-consumer/`](producer-consumer/) | A bounded buffer three ways: counting semaphores, a monitor with explicit conditions, and `synchronized`/`wait`/`notify`. Run `semaphores.Main`, `monitor.Main` or `syncmonitor.Main`. |
| [`dining-philosophers/`](dining-philosophers/) | Dining philosophers with one semaphore per fork and a limiter that admits N-1 philosophers to the table, which removes the circular wait. |
| [`barbershop-fifo/`](barbershop-fifo/) | Sleeping barber where customers are served strictly in arrival order, each waiting on its own ticket semaphore held in a queue. |
| [`barbershop-hilzer/`](barbershop-hilzer/) | Hilzer's barbershop: three barbers, three chairs, a sofa for four and standing room, with a sofa queue, a chair queue and payment before leaving. |
| [`barbershop-monitor-colours/`](barbershop-monitor-colours/) | The barbershop as a monitor driven by a colour-coded state diagram (customer and barber states, four condition gates). |
| [`bridge-traffic-control/`](bridge-traffic-control/) | A one-lane bridge shared by cars from both ends: a monitor that admits at most five cars in the current direction and switches direction after a batch of ten when cars are waiting on the other side. |

## Parallel speed-up studies

| Project | What it is |
| --- | --- |
| [`parallel-fibonacci/`](parallel-fibonacci/) | Fibonacci(45) three ways: naive recursion, one thread per branch down to the processor count, and an iterative list. |
| [`matrix-multiplication-speedup/`](matrix-multiplication-speedup/) | Matrix product with one thread per processor vs sequential, on the four cases of the exercise table. |
| [`parallel-mergesort-speedup/`](parallel-mergesort-speedup/) | Mergesort that forks a thread per half down to the processor count, benchmarked against the sequential version for 2^15 to 2^26 elements; results charted into a spreadsheet by a generated Python script (`results/`). |
| [`task-parallelism-speedup/`](task-parallelism-speedup/) | A CPU-bound calculation split across tasks: time vs size, time vs processors, speed-up and efficiency, for one and two tasks per processor, with the written report and the result spreadsheet. |

## Distributed systems

| Project | What it is |
| --- | --- |
| [`tcp-chat-server/`](tcp-chat-server/) | Multi-threaded TCP messaging server: register, log in, list users, send and read messages; accounts persisted to `databank.txt`. Run `chat.Server`, then one `chat.User` per terminal. |
| [`rmi-multicast-product-search/`](rmi-multicast-product-search/) | A product-search broker: customers and an administrator talk to the server over Java RMI, the server multicasts each search to the stores over UDP and collects their unicast answers. Run `search.Server`, one or more `search.Store`, then `search.Client` / `search.Manager`. |
| [`udp-examples/`](udp-examples/) | Minimal UDP unicast (question/answer) and multicast (group of five messages) examples. |

[`written-assignments/`](written-assignments/) holds the essay on parallel computer architectures (in Portuguese).

Build any project with, for example:

```sh
javac -d out $(find barbershop-semaphores/src -name '*.java') && (cd out && java barbershop.Barbershop)
```

The benchmarks write their files to an `outputs/` folder (git-ignored); the chart scripts need `pip install xlsxwriter`.
