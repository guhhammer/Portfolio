# Shared-memory IPC (C++)

Two exercises on inter-process communication with POSIX shared memory (`shm_open` + `mmap`) and named semaphores, from the Computer Systems Programming course at PUCPR (2020). The two helper headers in [`include/`](include/) (`SharedMemory<T>` and `NamedSemaphore`) were provided in class by Prof. Luiz A. de P. Lima Jr. and are kept with their comments translated.

| Project | What it is |
| --- | --- |
| [`date-broadcast/`](date-broadcast/) | A writer publishes a date to a reader through shared memory in three ways: **model 1** writes day, month and year into three shared integers as they are typed (the reader can see a torn date); **model 2** reads the whole date first and then copies the three values; **model 3** packs the date into a single integer so one store publishes it atomically. |
| [`battery-and-device/`](battery-and-device/) | Two processes share a `battery` struct guarded by a named semaphore: the battery process recharges it three times, the device process counts and drains one level every ten counts, "plugging it in" when it reaches zero (`screenshot.png` shows a run). Pair assignment with João Vitor Andrioli de Souza. |

Build and run (Linux; each pair needs two terminals):

```sh
cd date-broadcast && make && ./model-3/reader &   # then, in another terminal:
./model-3/writer
cd battery-and-device && make && ./battery &      # then: ./device
```
