# Solidity

Ethereum smart contracts and the apps around them, from my first `get`/`set` contract to a capstone marketplace and a flash-loan bot.

| Folder | Kind | Summary |
| --- | --- | --- |
| [`diskify-music-nft-marketplace/`](diskify-music-nft-marketplace/) | Shipped project (capstone) | ERC-1155 album marketplace with artist royalties, React + Web3.js front end, IPFS storage, Truffle. |
| [`flashloan-arbitrage-bot/`](flashloan-arbitrage-bot/) | Own project | Node.js bot that compares Kyber and Uniswap prices on every new block and triggers a dYdX flash-loan arbitrage contract. |
| [`nft-collectible-truffle/`](nft-collectible-truffle/) | Own project | Smallest complete ERC-721 collectible (OpenZeppelin) with Truffle migrations. |
| [`learning-contracts/`](learning-contracts/) | Learning | Twelve numbered contracts, Solidity 0.4 to 0.8, one concept each. |
| [`eattheblocks-courses/`](eattheblocks-courses/) | Courses | Projects completed in the EatTheBlocks curriculum (30 dapps, Web3 101, NFT 101, DeFi, security 101/201, bootcamp, GameFi, flash loans). |
| [`notes/`](notes/) | Reference | Links to OpenZeppelin ERC-721 sources and docs. |

## Compiling

Each project pins its own compiler in `pragma solidity`. For the stand-alone contracts a version manager is the easiest route:

```bash
pip install solc-select
solc-select install 0.5.1 && solc-select use 0.5.1
solc learning-contracts/02_string_storage.sol
```

Truffle and Hardhat projects compile with `truffle compile` or `npx hardhat compile` after `npm install`.
