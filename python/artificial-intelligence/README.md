# Artificial intelligence (Python)

Coursework from the "Artificial Intelligence" course at PUCPR (2021): search methods, game theory and adversarial search, expert systems and planning. The two coding projects are a checkers opponent and a tic-tac-toe player; the search-algorithm implementations of the same course were written in Java and live in [`../../java/ai-search-algorithms/`](../../java/ai-search-algorithms/).

For a non-technical reader: this is where a program plays a board game against you, or answers questions from a set of rules, by exploring the consequences of each possible move.

## Projects

| Folder | What it is | Run |
| --- | --- | --- |
| `checkers-ai/` | Console checkers (8x8 board, kings, chained captures, coloured output) and a rule-based AI opponent that ranks moves by fixed priorities: capture an exposed king, block an enemy capture, save its own king, capture (safely if possible), promote a man, and only then move at random. Team project with André Wlodkovski and Isa Stohler | `pip install colorama && python3 checkers_ai.py` (the last line picks the AI's colour: `CheckersAI(1)` red, `CheckersAI(2)` blue); `python3 checkers.py` for two humans |
| `tic-tac-toe-minimax/` | Human vs. computer tic-tac-toe: the computer expands the whole game tree, scores the leaves and backs the values up with max/min (minimax), then takes an immediate win or block, otherwise a random best move. With the game-theory questionnaire (`game-theory-answers.md`) and the hand-drawn game tree | `python3 tic_tac_toe.py` |
| `expert-system-personalities/` | Expert system that suggests a Myers-Briggs personality type from yes/no questions, built as a rule base for the Expert SINTA shell (`personalities.bcm`), with the concepts questionnaire (07a) and the project report (07b) | open `personalities.bcm` in Expert SINTA |

## Written assignments (`written-assignments/`)

Filled-in assignment sheets, in Portuguese unless noted:

- `01-natural-intelligence/` – discussion questions on what intelligence is; translated in `answers.md`.
- `02-multi-agent-systems/` – intelligent agents and multi-agent systems (chapter 2 of Russell & Norvig): theory exercise.
- `03-uninformed-search/`, `04-heuristic-search/`, `05-general-search/` – breadth-first, depth-first, greedy and A* search worked on graphs, with screenshots of the search implementations running.
- `08-planning-systems/` – states, actions, goals and plans in classical planning.
- `tde-01-search-methods/`, `tde-02-game-theory/` – the two graded take-home assignments (answers as DOCX and PDF).
- `assessments/` – the two individual assessments (the second one on game theory).

Third-party material (the Russell & Norvig textbook, the Expert SINTA shell files and manual, the professor's slides) was removed from the repository.
