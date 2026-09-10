# Diskify: final paper (capstone, PUCPR 2021)

Diskify is a music NFT marketplace: musicians turn albums into unique, verifiable collectibles that fans buy and trade, and the artist earns a royalty on every resale. This folder is the academic side of the capstone; the working application (Solidity contract, React front end, IPFS storage) is in [`blockchain/solidity/diskify-music-nft-marketplace/`](../../blockchain/solidity/diskify-music-nft-marketplace/).

The thesis documents are in Portuguese.

| Folder | What it is |
| --- | --- |
| `proposal/` | Stage one: the 51-page innovation proposal (`thesis-proposal.pdf`, with the docx), the submission form, the proposal presentation and the logo. `architecture/` has the service-oriented architecture diagrams and Astah model; `research-notes/` the questionnaire, the interviews with musicians (CSV), reading notes on NFTs and smart contracts and the value-proposition canvases; `market-research/` a report on how musicians earn from their music. |
| `project/` | Stage two: the 54-page innovation project (`thesis-project.pdf`, with the docx), the final presentation, the MVP screenshot and the figures used in the presentation (hosting, migrations, contract, screens). |
| `sprint-planning/` | The sprint plan for the development semester and the contract as it stood at the end of sprint 1 (`sprint-1/Diskify.sol`). |
| `mockup/` | The first HTML/Tailwind mockup of the marketplace (search and album pages, served by a small Express app) and a Python script that generated the procedural album art. The generated `tailwind.css` is not committed; rebuild it with `npx postcss src/tailwind.css -o public/css/tailwind.css` after `npm install`. |
