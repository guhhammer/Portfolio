# EatTheBlocks courses

Projects completed while working through the EatTheBlocks blockchain curriculum between 2021 and 2023 (certificates in [`../../../certificates/courses/blockchain/`](../../../certificates/courses/blockchain/)). Most folders follow the course's numbered lecture structure; the code was written along with the lectures and extended where the course left exercises open.

| Folder | Course | What was built |
| --- | --- | --- |
| [`30-dapps/`](30-dapps/) | DApp 30 | Thirty contracts in `contracts/` (hello world, storage, CRUD, ether wallet, split payment, deed, escrow, string manipulation, Fibonacci, Armstrong number, multisig wallet, voting, DAO, loan state machine, event organization, lottery, ERC-20, rock-paper-scissors, ICO, ERC-721, CryptoKitties-style breeding, Twitter, eBay, Tinder, assembly, DEX, arbitrage trader, DeFi) with a Truffle test per contract and small Express/EJS front ends for the first ones and a React DAO front end. My working notes are in `memo.txt`, `exec.txt`, `todo.txt` and `contract-structure-order.txt`. |
| [`web3-101/`](web3-101/) | Web3 101 | Sections 2 to 10: Solidity fundamentals, Hardhat, events, an ether wallet dapp with RainbowKit, a multisig wallet dapp, ERC-721, and a staking wallet. |
| [`nft-101/`](nft-101/) | NFT 101 | 46 numbered steps building an NFT collection and marketplace, plus `final/`. |
| [`defi-development-mastery/`](defi-development-mastery/) | DeFi Development Mastery | DeFi building blocks, integrating protocols, a Compound dashboard, yield farming, a DeFi bond, a DeFi wallet, and a Uniswap fork. |
| [`smart-contract-security-101/`](smart-contract-security-101/) | Security 101 | Hardhat project with vulnerable contracts, tests, and fill-in-the-blank exercises. |
| [`smart-contract-security-201/`](smart-contract-security-201/) | Security 201 | Audit methodology, manual audit, automated analysis tools, gas optimization and a final audit project. |
| [`bootcamp/`](bootcamp/) | Bootcamp (and Masterclass) | A decentralized exchange and a multisig wallet, each taken from contract to tests to React front end to deployment, plus the weekly exercises. The Blockchain Masterclass covered the same DEX and wallet projects with identical code, so only the bootcamp copy is kept. |
| [`profitable-flashloans/`](profitable-flashloans/) | Profitable Flashloans | Steps 9 to 30 of the flash-loan arbitrage bot: Web3 connection, secrets management, WebSocket block listening, Kyber and Uniswap price polling, the flash-loan contract, deployment and a 24/7 runner. The adapted result is [`../flashloan-arbitrage-bot/`](../flashloan-arbitrage-bot/). |
| [`gamefi-101/`](gamefi-101/) | GameFi 101 | A browser game (`game/`), an Express API with SQLite (`api/`) and a Hardhat smart contract (`smartcontract/`) that rewards players. |

Course scaffolds use Truffle, Hardhat, Ganache, Web3.js, ethers.js, OpenZeppelin and React. Each project keeps its own `package.json`; run `npm install` inside a project before `truffle test` or `npx hardhat test`. Compiled contract artifacts (`build/contracts/`, `client/src/contracts/*.json`) are not committed; `truffle compile` regenerates them.
