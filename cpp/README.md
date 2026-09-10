# C++

Systems-programming coursework in C and C++: an operating-systems course taken in the USA (shell, scheduler simulator, pthreads producer/consumer), POSIX shared-memory IPC, OpenGL/freeglut graphics and a coding challenge. Everything is in English; the console projects build with `g++`/`make` (verified on Linux), the OpenGL ones need `freeglut3-dev`.

| Folder | What it is |
| --- | --- |
| [`operating-systems/`](operating-systems/) | CS433 (CSUSM, 2020): process ready queue, a Unix shell with `fork`/`execvp`, a CPU-scheduling simulator (FCFS, SJF, priority, RR, priority + RR), a pthreads bounded buffer, plus the written homework. |
| [`shared-memory-ipc/`](shared-memory-ipc/) | POSIX shared memory and named semaphores: three ways of publishing a date between processes, and a battery/device pair. |
| [`opengl-3d-graphics/`](opengl-3d-graphics/) | A freeglut scene editor (solids from a scene file, lighting, keyboard/mouse control) and a bouncing cube driven by a small vector class. |
| [`challenges/`](challenges/) | Armstrong-number check, one of the cross-language coding challenges. |
