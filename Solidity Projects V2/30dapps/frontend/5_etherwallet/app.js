const express = require("express");
const Web3 = require("web3").default;
const fs = require("fs");

const app = express();
app.set("view engine", "ejs"); // Set EJS as the view engine
app.use(express.json());
app.use(express.urlencoded({ extended: true })); // To parse form data

// --- Setup Web3 and Contract ---
const RPC_URL = "http://127.0.0.1:8545"; // Ganache
const web3 = new Web3(RPC_URL);

// Replace with your test private key (from Ganache)
const PRIVATE_KEY = "0x29e6b3e29af63bb68782f0864c3dcf1dc4de01feaf360cc86ca514ee53234623";
const account = web3.eth.accounts.privateKeyToAccount(PRIVATE_KEY);
web3.eth.accounts.wallet.add(account);

// Load ABI and contract
const ABI = JSON.parse(fs.readFileSync("../../build/contracts/EtherWallet.json")).abi;
const CONTRACT_ADDRESS = "0x729dbFC762bfda8C1bEDcc9D139B83Ec4138E843"; // replace this

const contract = new web3.eth.Contract(ABI, CONTRACT_ADDRESS);

// --- Routes ---

// Render home page with balance info
app.get("/", async (req, res) => {
    const balance = await contract.methods.getBalance().call();
    res.render("index", {
        balance: web3.utils.fromWei(balance, "ether"),
        account: account.address,
    });
});

// Deposit ether
app.post("/deposit", async (req, res) => {
    const { amount } = req.body;

    try {
        const tx = await contract.methods.deposit().send({
            from: account.address,
            value: web3.utils.toWei(amount.toString(), "ether"),
            gas: 100000
        });

        res.render("success", {
            message: "Deposit Successful",
            txHash: tx.transactionHash,
            action: "Deposit",
        });
    } catch (err) {
        res.render("error", {
            message: err.message,
        });
    }
});

// Withdraw ether (owner only)
app.post("/withdraw", async (req, res) => {
    const { amount } = req.body;

    try {
        const tx = await contract.methods.withdraw(web3.utils.toWei(amount.toString(), "ether")).send({
            from: account.address,
            gas: 100000
        });

        res.render("success", {
            message: "Withdrawal Successful",
            txHash: tx.transactionHash,
            action: "Withdraw",
        });
    } catch (err) {
        res.render("error", {
            message: err.message,
        });
    }
});

// View transaction event history
app.get("/transactions", async (req, res) => {
    const events = await contract.getPastEvents("transaction", {
        fromBlock: 0,
        toBlock: "latest"
    });

    const parsed = events.map(e => ({
        owner: e.returnValues._owner,
        direction: e.returnValues._direction,
        amount: web3.utils.fromWei(e.returnValues._amount, "ether"),
        timestamp: new Date(e.returnValues._timestamp * 1000).toLocaleString(),
    }));

    res.render("transactions", { transactions: parsed });
});

// Start server
const PORT = 3000;
app.listen(PORT, () => {
    console.log(`EtherWallet server running at http://localhost:${PORT}`);
});
