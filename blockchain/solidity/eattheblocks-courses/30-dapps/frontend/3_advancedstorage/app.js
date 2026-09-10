const express = require("express");
const Web3 = require("web3").default;
const path = require("path");
const contractJson = require("../../build/contracts/AdvancedStorage.json");

const app = express();
const port = 3000;

// Setup web3
const web3 = new Web3("http://127.0.0.1:8545"); // Ganache or Hardhat node

// Setup contract
async function setupContract() {
    const networkId = await web3.eth.net.getId();
    const deployedNetwork = contractJson.networks[networkId];
    return new web3.eth.Contract(contractJson.abi, deployedNetwork.address);
}

app.set("view engine", "ejs");
app.set("views", path.join(__dirname, "views"));

app.use(express.urlencoded({ extended: true }));

let contract;
let accounts;

// Load contract and accounts once
(async () => {
    contract = await setupContract();
    accounts = await web3.eth.getAccounts();
})();

app.get("/", async (req, res) => {
    res.render("index", { accounts, storedData: null, selectedAccount: null });
});

app.post("/set", async (req, res) => {
    const { account, newData } = req.body;
    await contract.methods.setData(newData).send({ from: account });
    res.redirect("/");
});

app.post("/get", async (req, res) => {
    const { account } = req.body;
    const data = await contract.methods.getData().call({ from: account });
    res.render("index", { accounts, storedData: data, selectedAccount: account });
});

app.listen(port, () => {
    console.log(`App listening at http://localhost:${port}`);
});
