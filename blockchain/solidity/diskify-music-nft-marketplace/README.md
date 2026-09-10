# Diskify: music NFT marketplace (capstone project, 2021)

Diskify lets musicians sell digital albums as scarce, verifiable collectibles (NFTs) and earn a royalty every time a copy is resold. Fans buy, trade and play albums from a web app; ownership and royalties are enforced by an Ethereum smart contract; album art and audio live on IPFS.

This was my Computer Science capstone at PUCPR (Pontifícia Universidade Católica do Paraná). The written thesis, presentations and mockups are in [`../../../academic-writing/final-paper-diskify/`](../../../academic-writing/final-paper-diskify/).

For a non-technical reader: a record shop for digital albums where every copy is unique, like numbered vinyl, and the artist is paid automatically on every resale.

## What it demonstrates

- **Smart contract design** (`src/contracts/Diskify.sol`, 438 lines): an ERC-1155 multi-token contract in which approved artists mint an album with a chosen number of copies and a price, owners re-list copies, and `tradeNFT` splits every payment three ways (artist royalty, platform fee, seller) before transferring the copy. It also keeps "top 100 new releases" and "last 100 sold" lists and lets the platform owner block an album.
- **Contract evolution** (`contract-versions/`): four dated versions showing the design move from ERC-721 (one token per copy) to ERC-1155 (one token id per album, many copies), and the final trimmed version that shipped.
- **Full-stack dapp**: a React front end (`src/components/`: navigation, mint, search, view, "me" pages) talking to the contract through Web3.js and the ABI that `truffle compile` generates into `src/abis/`, with MetaMask as the wallet and `ipfs-http-client` for uploads.
- **Truffle workflow**: migrations, local Ganache blockchain, compiled artifacts generated into `src/abis/` (not committed).

## Layout

```
src/contracts/Diskify.sol       the marketplace contract (ERC-1155)
src/contracts/Migrations.sol    Truffle bookkeeping
contract-versions/              v1 ERC-721 draft, v2/v3 stand-alone ERC-1155 drafts, v4 final
src/components/                 React pages: App, NavBar, Diskify (home), Mint, Search, View, Me
src/abis/                       compiled contract ABIs used by the front end (created by `truffle compile`)
migrations/                     Truffle deployment scripts
truffle-config.js, .babelrc, package.json
```

## Running it locally

1. Install the [MetaMask](https://metamask.io/) browser extension and [Ganache](https://trufflesuite.com/ganache/) (a local Ethereum blockchain).
2. In this folder run `npm install` and then `npm install --save ipfs-http-client`.
3. Open Ganache and click **Quickstart**. Confirm the RPC server is `127.0.0.1:7545` (network id 5777; MetaMask may show 1337).
4. In MetaMask add a network with RPC URL `http://127.0.0.1:7545` and chain id 5777 (or 1337 if MetaMask asks), then import one Ganache account by pasting its private key (key icon next to the account in Ganache).
5. Compile and deploy the contract: `truffle compile` then `truffle migrate`.
6. Start the app with `npm run start`, open `http://127.0.0.1:3000` and approve the MetaMask connection.

A fresh local chain has no albums, so mint a few from the **Mint** page first (any cover image and audio files will do).

Notes: `npm install` and the first start take a while; `node_modules` is not committed. The original repository is [github.com/GusHammer/tccProjeto](https://github.com/GusHammer/tccProjeto).
