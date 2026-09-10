# Blockchain

Smart contracts and decentralized applications on Ethereum (Solidity) and Solana (Rust with Anchor). This folder mixes shipped work (a capstone NFT marketplace, a flash-loan arbitrage bot), my own learning contracts, and the projects completed in a long self-taught curriculum with EatTheBlocks (certificates in [`../certificates/`](../certificates/)).

For a non-technical reader: a smart contract is a small program that lives on a public blockchain and moves money or digital assets by rules nobody can change afterwards. The projects below are such programs plus the web apps people use to interact with them.

## Start here

| Project | What it is |
| --- | --- |
| [`solidity/diskify-music-nft-marketplace/`](solidity/diskify-music-nft-marketplace/) | Capstone: music albums sold as NFTs with automatic artist royalties. ERC-1155 contract + React/Web3 app + IPFS. |
| [`solidity/flashloan-arbitrage-bot/`](solidity/flashloan-arbitrage-bot/) | A bot that watches ETH/DAI prices on Kyber and Uniswap every block and executes a dYdX flash loan when a profitable spread appears. |
| [`solana/`](solana/) | Three Anchor programs: on-chain computation, per-user storage with program-derived addresses, and the canonical hello world, each with TypeScript tests. |
| [`solidity/eattheblocks-courses/30-dapps/`](solidity/eattheblocks-courses/30-dapps/) | Thirty contracts of increasing difficulty (multisig wallet, DAO, lottery, ICO, ERC-20/721, DEX) with Truffle tests and small Express front ends. |
| [`solidity/learning-contracts/`](solidity/learning-contracts/) | Twelve short contracts that trace how I learned Solidity: storage, modifiers, payable functions, tokens, libraries, CRUD, ERC-721 with Chainlink VRF. |

## Folder map

```
blockchain/
├── solidity/
│   ├── diskify-music-nft-marketplace/   capstone dapp (contract, React app, contract history)
│   ├── flashloan-arbitrage-bot/         Web3.js bot + dYdX flash-loan contract
│   ├── nft-collectible-truffle/         minimal ERC-721 collectible with Truffle
│   ├── learning-contracts/              numbered learning contracts (0.4 to 0.8)
│   ├── eattheblocks-courses/            course projects: 30 dapps, Web3 101, NFT 101, DeFi, security 101/201, bootcamp, GameFi
│   └── notes/                           reference links
└── solana/
    ├── hello-world/, armstrong-number/, advanced-storage/   Anchor programs with tests
    └── notes/anchor-setup.md            toolchain install and CLI cheat sheet
```

Related: the Rust donation website in [`../rust/rocket-mongodb-web-app/`](../rust/rocket-mongodb-web-app/) ships its own ERC-20 and Donation contracts (Hardhat + Truffle).
