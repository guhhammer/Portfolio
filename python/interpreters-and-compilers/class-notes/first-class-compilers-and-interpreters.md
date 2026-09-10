# First class: compilers and interpreters (my notes, translated)

An **artificial language** is planned and designed consciously: its grammar is usually simplified to make it easier to learn. A **natural language** evolves on its own; its grammars are imperfect models of linguistic phenomena that are more complex, non-deterministic and ambiguous (spoken languages, for example).

An **interpreter** is a tool that analyses and processes an artificial language; the code is executed as it is analysed.

## Interpreter vs compiler

- Interpreter: executes a source program immediately. Process: translation (compilation) -> intermediate code (bytecode, for example) -> virtual machine (modules/libraries) -> running code.
- Compiler: generates object code (machine/binary) that is executed after the translation finishes.
- The professor's remark: strictly speaking, only tools that turn code into binary are called compilers.

## Interpreters

Main responsibilities: provide an abstraction for programming, check certain kinds of errors in the code, allow the code to run.

Main phases of interpretation: lexical analysis, syntactic analysis, semantic analysis (optional). Additional phases that characterize compilation: code generation, code optimization.

Interpretation process, from source code (language A) to target code (language B):

1. Lexical analysis -> syntactic analysis: tokens.
2. Syntactic analysis -> semantic analysis: syntax tree.
3. Semantic analysis -> code optimization: annotated tree.
4. Code optimization -> code generation: intermediate code.

Steps 1 and 2 are interpretation; step 4 is compilation.

## Alphabets and words

An alphabet Σ is a finite set of indivisible symbols, for example Σ = {a, b, c} or Σ = {0, 1}. The length of a word w is |w| (W = abab, |W| = 4). The empty word is ε, |ε| = 0.

- Concatenation: W = "açú", U = "car": WU = "açúcar", UW = "caraçú".
- Successive concatenation: W^n is W repeated n times (oi² = oioi).
- Subword: any sequence of contiguous symbols of the word. Prefix: any initial sequence. Suffix: any final sequence. Every prefix or suffix is a subword; ε is also a subword.
- Reverse: W^r is W with its symbols in reverse order.
- Σ* is the set of all words over Σ (Kleene closure, "zero or more repetitions"): for Σ = {a, b}, Σ* = {ε, a, b, aa, ab, ba, bb, aaa, ...}. Σ+ = Σ* - {ε}.
- A formal language L over Σ is a subset of Σ*.

## Lexical analysis

- Tokens: classes of symbols such as reserved words, delimiters, identifiers. The values recognized in the source code must be stored with the token.
- Pattern: a rule describing the set of input strings recognized as a given token. Lexeme (value): the characters recognized in the source by the pattern of a token; an attribute of the token.

### Regular languages

A regular language is a formal language that can be expressed by a regular expression. Regular languages fit the minimum needs of lexical analysis (specification and recognition of tokens) but have limited expressiveness: a language with balanced nesting is not regular (languages with balanced parentheses such as C, C++, Pascal, LISP, Java).

### Regular expressions

Every regular language can be described by a regular expression. A regular expression is a generative formalism (it tells how to generate the words of a language), represents a pattern of strings, and is useful to describe the tokens of a language formally.

Definition, from the basic sets: ∅ is a regular expression for the empty language; ε is a regular expression for the language {ε}; for any symbol x of Σ, x is a regular expression for {x}; plus the operations concatenation (.), union (+, also written |) and Kleene closure (*). Precedence from highest to lowest: * then . then +.

Simplified notation: `[0-9]` digits, `[a-z]` lowercase, `[A-Z]` uppercase, `[a-zA-Z]` both, `+` one or more, `*` zero or more, `?` zero or one.

## Chomsky hierarchy

Regular languages (type 3) ⊂ context-free languages (type 2) ⊂ context-sensitive languages (type 1) ⊂ recursively enumerable languages (type 0); complexity grows from type 3 to type 0.
