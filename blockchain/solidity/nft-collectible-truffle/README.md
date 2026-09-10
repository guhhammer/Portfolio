# NFT collectible (Truffle)

The smallest complete NFT project: `SimpleCollectible` extends OpenZeppelin's `ERC721` (token "Dogie", symbol "DOG") and `createCollectible(tokenURI)` mints a new token to the caller with a metadata URI. Truffle handles compilation and deployment (`migrations/`).

```bash
npm install @openzeppelin/contracts@3   # ERC721 for Solidity 0.6.6
truffle compile
truffle migrate --network development   # against a local Ganache
```

Its bigger sibling in `learning-contracts/12_erc721_collectible_chainlink_vrf.sol` adds Chainlink VRF so each collectible gets random traits.
