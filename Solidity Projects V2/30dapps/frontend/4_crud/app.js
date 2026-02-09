const express = require("express");
const Web3 = require("web3").default;
const path = require("path");
const contractJson = require("../../build/contracts/Crud.json");

const app = express();
const port = 3000;

// Setup web3
const web3 = new Web3("http://127.0.0.1:8545"); // Ganache or Hardhat

// Setup contract
async function setupContract() {
    const networkId = await web3.eth.net.getId();
    const deployedNetwork = contractJson.networks[networkId];
    return new web3.eth.Contract(contractJson.abi, deployedNetwork.address);
}

app.set("view engine", "ejs");
app.set("views", path.join(__dirname, "views"));
app.use(express.static(path.join(__dirname, "public")));
app.use(express.urlencoded({ extended: true }));

let contract;
let accounts;

// Load contract and accounts once
(async () => {
    contract = await setupContract();
    accounts = await web3.eth.getAccounts();
})();

app.get("/", (req, res) => {
    res.render("index", { users: [], accounts });
});

app.post("/create", async (req, res) => {
    const { name } = req.body;
    await contract.methods.create(name).call();
    res.redirect("/");
});

app.post("/read", async (req, res) => {
    const { id } = req.body;
    try {
        const name = await contract.methods.read(id).call();
        res.render("index", { users: [{ id, name }], accounts });
    } catch (err) {
        res.render("index", { users: [], accounts });
    }
});

app.post("/update", async (req, res) => {
    const { id, name } = req.body;
    await contract.methods.update(id, name).send({ from: accounts[0] });
    res.redirect("/");
});

app.post("/destroy", async (req, res) => {
    const { id } = req.body;
    await contract.methods.destroy(id).send({ from: accounts[0] });
    res.redirect("/");
});

app.listen(port, () => {
    console.log(`App running at http://localhost:${port}`);
});
