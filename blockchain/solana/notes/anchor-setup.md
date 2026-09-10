# Anchor and Solana CLI setup

Notes taken while setting up the toolchain (Debian). Official reference: https://www.anchor-lang.com/docs/installation

## Install

```bash
# Rust
sudo apt update && sudo apt install rustup -y
rustc --version

# Anchor CLI (pin the version the projects were built with)
cargo install --git https://github.com/coral-xyz/anchor --tag v0.31.0 anchor-cli --force
anchor --version
```

## Solana CLI configuration

```bash
solana config get                 # show current cluster and wallet

solana config set --url mainnet-beta
solana config set --url devnet
solana config set --url localhost
solana config set --url testnet
# short forms: -um  -ud  -ul  -ut

solana-keygen new                 # creates ~/.config/solana/id.json (keep the seed phrase offline)
solana address                    # public key of that wallet
solana airdrop 2                  # devnet only: request test SOL
solana balance
```

`solana-test-validator` runs a local validator; point the CLI at it with `solana config set -ul`.

## Project workflow

```bash
anchor init <project-name>
cd <project-name>
anchor build
anchor deploy
anchor test          # starts its own local validator; no need to run solana-test-validator
```

When a test run gets into a bad state:

```bash
anchor clean
anchor build
solana-test-validator --reset && anchor deploy
# stop the validator, then
anchor test
```

Copying the latest working project is often the fastest way around `anchor init` version mismatches.
