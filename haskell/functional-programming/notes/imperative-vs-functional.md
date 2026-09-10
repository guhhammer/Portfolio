# Imperative vs functional

*Study notes from the Functional Programming course at PUCPR (2019); translated from Portuguese.*

- In the **imperative paradigm** we describe *how* the problem is solved: an algorithm made of a sequence of instructions, focused on changing the state of variables.
- In the **functional paradigm** there are no variables (or they are immutable), because functions are only interested in processing data. In principle nothing keeps state: each function operates on its data and does not depend on another function, so there is no sequence of execution.
- A paradigm is a way of turning a problem into a computable form. The functional one focuses on the evaluation of functions, with immutability and referential transparency.
