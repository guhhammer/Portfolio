# Learning contracts

Twelve small contracts, numbered in the order I wrote them while learning Solidity. Each one introduces a single idea.

| File | Compiler | Concept |
| --- | --- | --- |
| `01_string_storage_solidity_0.4.sol` | 0.4.24 | A stored string with `get`/`set` (old syntax) |
| `02_string_storage.sol` | 0.5.1 | Same contract in 0.5 syntax (`memory` data location) |
| `03_people_registry_arrays_and_mappings.sol` | 0.5.1 | Structs, arrays, mappings, a private "active" flag |
| `04_modifiers_owner_and_time_window.sol` | 0.5.1 | `onlyOwner` and `onlyWhileOpen` modifiers, internal counters |
| `05_payable_fallback_and_token_sale.sol` | 0.5.1 | Receiving ether: payable fallback and `buyToken` |
| `06_mintable_token_sale.sol` | 0.5.1 | A minimal `ERC20Token` with `mint`, sold by a second contract |
| `07_token_inheritance.sol` | 0.5.1 | `MyToken is ERC20Token`: inheritance and overriding `mint` |
| `08_math_library/` | 0.5.1 | Using a library (`Math.sol`) from a contract |
| `09_safemath_library/` | 0.5.1 | Overflow-safe arithmetic with `SafeMath` |
| `10_crud.sol` | 0.5.x | Create, read, update and delete records in storage |
| `11_erc20_style_token.sol` | 0.8.6 | Hand-written token with balances, transfer event and a `hasValue` modifier |
| `12_erc721_collectible_chainlink_vrf.sol` | 0.8.x | ERC-721 collectible whose traits come from Chainlink VRF randomness |

Compile any of them with the matching `solc` version, for example:

```bash
solc-select use 0.5.1 && solc --bin 03_people_registry_arrays_and_mappings.sol
```

Contract 12 imports OpenZeppelin and Chainlink packages, so it needs `npm install @openzeppelin/contracts @chainlink/contracts` and `solc --base-path . --include-path node_modules`.
