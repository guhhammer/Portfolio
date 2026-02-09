// app.js
const express = require("express");
const Web3 = require("web3").default;

const contractData = require("../../build/contracts/SimpleStorage.json");

const app = express();
const PORT = 3000;

// Set EJS and form parser
app.set("view engine", "ejs");
app.use(express.urlencoded({ extended: true }));

// Connect to local Ganache or Truffle dev network
const web3 = new Web3("http://127.0.0.1:8545"); // Make sure Ganache or Truffle is running

let contract;
let account;

async function init() {
  const networkId = await web3.eth.net.getId();
  const deployedNetwork = contractData.networks[networkId];
  contract = new web3.eth.Contract(contractData.abi, deployedNetwork.address);

  const accounts = await web3.eth.getAccounts();
  account = accounts[0];
}

init();

// Homepage
app.get("/", async (req, res) => {
  const storedValue = await contract.methods.get().call();
  res.render("index", { storedValue });
});

// Set new value
app.post("/set", async (req, res) => {
  const { value } = req.body;
  await contract.methods.set(value).send({ from: account });
  res.redirect("/");
});

app.listen(PORT, () => {
  console.log(`Server running at http://localhost:${PORT}`);
});
