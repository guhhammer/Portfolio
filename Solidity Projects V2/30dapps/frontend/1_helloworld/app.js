const express = require("express");
const Web3 = require("web3").default;
const contractData = require("../../build/contracts/HelloWorld.json");

const app = express();
const PORT = 3000;

app.set("view engine", "ejs");
app.use(express.urlencoded({ extended: true }));

const web3 = new Web3("http://127.0.0.1:8545"); // simple HTTP is enough

let contract;
let account;
let lastHelloMessage = "No message yet.";

async function init() {
  try {
    const networkId = await web3.eth.net.getId();
    console.log("Connected to network:", networkId);

    const deployedNetwork = contractData.networks[networkId];
    if (!deployedNetwork) {
      throw new Error("Contract not deployed on this network.");
    }

    contract = new web3.eth.Contract(contractData.abi, deployedNetwork.address);
    console.log("Contract loaded at:", deployedNetwork.address);

    const accounts = await web3.eth.getAccounts();
    account = accounts[0];
    console.log("Using account:", account);

    app.listen(PORT, () => {
      console.log(`Server running at http://localhost:${PORT}`);
    });

  } catch (err) {
    console.error("Failed to initialize:", err);
  }
}

init();

// 🏠 Home Page
app.get("/", (req, res) => {
  res.render("index", { message: lastHelloMessage });
});

// 📤 POST to call hello() and get the return value
app.post("/sayhello", async (req, res) => {
  try {
    const message = await contract.methods.hello().call();
    lastHelloMessage = message;
    console.log("Received message:", message);
    res.redirect("/");
  } catch (err) {
    console.error("Failed to call hello():", err);
    res.redirect("/");
  }
});
