# Solana (Rust + Anchor)

Three on-chain programs for the Solana blockchain written in Rust with the Anchor framework, each scaffolded as a full Anchor workspace with a TypeScript test suite (Mocha + Chai), migrations and deploy configuration.

For a non-technical reader: on Solana, a "program" is the equivalent of an Ethereum smart contract, and Anchor is the framework that removes most of the boilerplate around accounts and security checks.

| Program | What it does | Concepts |
| --- | --- | --- |
| [`hello-world/`](hello-world/) | The canonical first program: `initialize` logs a greeting with the program id. | Anchor project layout, `declare_id!`, `#[program]`, tests against a local validator |
| [`armstrong-number/`](armstrong-number/) | `is_armstrong_number(n)` computes on-chain whether `n` equals the sum of its digits raised to the number of digits, and stores the boolean in a per-user account. | Program-derived addresses (PDA) with `seeds` and `bump`, `init_if_needed`, explicit account `space` budgeting |
| [`advanced-storage/`](advanced-storage/) | `set(data, convert)` writes two strings into a storage account owned by the caller and derived from their public key. | PDA per user, `init` with payer, sized string fields |

## Run the tests

Requires the Solana CLI, Anchor CLI and Node (setup notes in [`notes/anchor-setup.md`](notes/anchor-setup.md)).

```bash
cd armstrong-number
anchor build          # compiles the Rust program and generates the TypeScript types
anchor test           # starts a local validator, deploys, runs tests/armstrong_number.ts
```

Generated folders (`target/`, `.anchor/`, `node_modules/`) are not committed.
