# Distributed leader election with ZooKeeper (assignment 1)

Author: Gustavo Hammerschmidt.

**How the election works.** Suppose the candidate process is the first to start the ZooKeeper structure. It runs: (1) create a znode named `leader` (`create`) and set its content to "no leader" (`set data`); (2) create a znode named `candidates` (`create`); (3) create an ephemeral child znode of `candidates` named after its unique identifier (for example `candidateC` or just `C`).

A process that joins later only runs step 3. If the election ends once every process has run, the last process could shut the server down and delete the structure, but the intention is rather that the server and the tree stay in memory for other processes to join; in that case a leaving process only removes its own leaf znode (`delete`).

The tree:

```
/             [root]
|-leader      [znode holding the current leader]
|-candidates  [znode holding the processes]
| |-A         [candidate process]
| |-B         [candidate process]
| |-C         [candidate process]
```

Each process creates an ephemeral znode under `candidates` (`create`) and asks the server for the data of `leader` (`get data`). If it reads the unique name of a leader, the non-leader process waits until it reads "no leader" or until the leader disappears from `candidates` (because the child is ephemeral it is removed when the leader loses its connection, raises, or simply ends its execution and yields the leadership). If no leader is found, the process writes its own identifier into `leader` (`set data`); that operation is atomic and notifies every process of who the leader is, so the writer becomes the current leader.

**Duration of the leader status.** A process writes its name into `leader`, performs its work, and when done asks the server to write "no leader" again. While a leader exists the other processes suspend and do not ask to become leader; if the leader fails or disconnects, the waiting processes check the ephemeral children (`get children`) and, if the leader is no longer a leaf, they all ask to become leader; the atomicity of `set data` picks one of them and blocks the rest until that new leader finishes.

Thus every process knows who the leader is and when it may become the leader, under the conditions above.

Note: I had problems with the JRE and could not start a ZooKeeper server on my machine, so this assignment describes the election protocol and how the code would be written.
