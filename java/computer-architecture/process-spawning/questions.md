# Process-spawning exercise

Two questions answered by running `Main`, which starts two `Counter` child processes:

1. **Two counters with different periods and counts.** Do they run in parallel?
   Yes: the processes execute concurrently, and the parent only ends after both children finish (it waits on them).
2. **If the parent finishes first, does the child keep running?**
   Yes: the children keep running even when the parent exits before them.

The two screenshots show the exercise statement and observations.
