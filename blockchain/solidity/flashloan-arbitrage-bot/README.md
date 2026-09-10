# Flash-loan arbitrage bot

A Node.js bot plus a Solidity contract that together look for price differences between two Ethereum exchanges and capture them with a flash loan.

How it works, in plain terms: on every new block the bot asks Kyber and Uniswap what they would pay for a large amount of ETH in DAI. If one exchange sells cheaper than the other buys, the bot calls the `Flashloan` contract, which borrows the money from dYdX, buys on the cheap exchange, sells on the expensive one, repays the loan and keeps the difference, all inside one transaction (so if anything fails nothing is lost but gas).

## Pieces

- `run-arbitrage.js`: connects to an Ethereum node over WebSockets (`INFURA_WSS`), subscribes to `newBlockHeaders`, quotes ETH/DAI on Kyber (`getExpectedRate`) and Uniswap (`@uniswap/sdk` pair reserves), compares the two directions and, when profitable, sends the transaction from the configured account.
- `contracts/Flashloan.sol`: extends `DydxFlashloanBase` from `@studydefi/money-legos`; `callFunction` receives the borrowed DAI, trades Kyber to Uniswap or Uniswap to Kyber (`Direction` enum), repays and emits `NewArbitrage`.
- `contracts/IUniswapV2Router02.sol`, `IWeth.sol`: interfaces used by the contract.
- `abis/`, `addresses/`: Kyber ABI and mainnet addresses of tokens, Kyber, Uniswap and dYdX.
- `truffle-config.js`, `migrations/`, `test/`: Truffle project scaffolding.

## Configuration

Copy `.env.example` to `.env` and fill in your own Infura WebSocket endpoint and the private key of a funded account. The `.env` file must never be committed.

```bash
npm install
node run-arbitrage.js
```

The bot was built during the EatTheBlocks "Profitable Flashloans" course and then adapted; the course's step-by-step versions are in [`../eattheblocks-courses/profitable-flashloans/`](../eattheblocks-courses/profitable-flashloans/).
