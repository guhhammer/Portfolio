# Class 06 – Game theory and the tic-tac-toe assignment

Artificial Intelligence course, PUCPR, 2021. Author: Gustavo Hammerschmidt.

**Step 1.** Reading: chapter 5 (adversarial search, sections 5.1 to 5.4) of *Artificial Intelligence: A Modern Approach* (Russell & Norvig).

**Step 2.** Questions on game theory concepts.

1. *What does a game tree represent?*
   The possible strategies the computer can use to solve a problem (the objective of the game).

2. *Can a game tree actually be a graph? Why?*
   Yes. Every tree is a graph with particular properties: every node has some number n of children, and each node is either a parent or a leaf. A game tree, specifically, has the properties that each node represents a state of the game and each relation between nodes is an action that leads from one state to another.

3. *What is a zero-sum game?*
   A game in which one player necessarily has to lose for the other to win; or, in which all the actions taken, when added up, must result in zero or in an equilibrium.

4. *In a game tree of depth 12, how many moves of each player are represented? Why?*
   With each player making one move, this game tree would have 12! = 479,001,600 plays. For each scenario, if an action X is taken there are 12 − 1 scenarios in which that action was taken, and from it new scenarios can arise or the game can proceed with 12 other scenarios; hence it is a factorial operation.

5. *In game theory, is the opponent assumed to have some expected behaviour, for example taking random decisions? Or the best decision from their point of view? Or the worst? Why?*
   Yes. It is always assumed that the game has a stated objective and, if the game is competitive between two or more players, that each player's objective is to complete the game's objective before the others, i.e. to win. One can take as a premise that the opponent will act as intelligently as possible from their perspective. After all, a player may be good, very good or excellent, but will not necessarily act like a machine evaluating state by state; sometimes they act intuitively. Depending on the complexity of the game, as in chess, one must assume the opponent has the same chances of winning and that the first moves matter less for a checkmate as a whole; otherwise the game tree of chess would be absurdly large.

6. *What is the purpose of an evaluation function in games?*
   It offers an insight into how promising a state is without searching the whole tree.

7. *Describe how tic-tac-toe works.*
   Two players compete to form a line of three marks (either "X" or "O") on a 3-by-3 grid. Whoever completes a row, column or diagonal with their marks wins. The X player starts and the game is divided into turns: after the X player it is the O player's turn, and vice versa.

**Step 3.** Watch the video <https://www.youtube.com/watch?v=cwzKjFkSyIE>.

**Step 4.** Implementation: `tic_tac_toe.py` (human vs. computer with full game-tree search); the hand-drawn game tree is in `game-tree-step-4.jpg`.

**Step 5.** *How many nodes are generated?* 9! = 362,880 nodes. (Note to self: check what the professor says about this answer in the next class.)
