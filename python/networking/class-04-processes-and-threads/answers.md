# Class 4 answers: processes and threads

**A.1** In the first program the time measured is the launch of the threads; in the second, their completion.

**A.2** The threads run serialized: a thread only starts when the previous one finishes.

**B.1** Each process creates its own variable, which is not shared with other processes or with the main program. Because the variable was never initialized in the child, `+= 1` raises an error.

**B.2** The Python interpreter reports that the global variable was not found, since it only exists in the main process.

**C.1** All threads read the value before sleeping, so each one stores 0 as the thread count and the final value is just 0 + 1.

**C.2** Every time a thread tries to access `numthreads` it is put to sleep until the resource is released by `lock.release()`, which serializes the execution of the threads.
