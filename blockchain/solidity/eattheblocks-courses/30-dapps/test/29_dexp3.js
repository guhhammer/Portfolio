const AAVE = artifacts.require("AAVE");
const DAI = artifacts.require("DAI");
const LDO = artifacts.require("LDO");
const LINK = artifacts.require("LINK");
const MKR = artifacts.require("MKR");
const SHIB = artifacts.require("SHIB");
const UNI = artifacts.require("UNI");
const USDC = artifacts.require("USDC");
const USDT = artifacts.require("USDT");
const WETH = artifacts.require("WETH");

const DEX = artifacts.require("DEX");

const truffleAssert = require("truffle-assertions");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

const tickers = ["AAVE", "DAI", "LDO", "LINK", "MKR", "SHIB", "UNI", "USDC", "USDT", "WETH"];

const bytes32Tickers = tickers.map(t =>
  web3.utils.asciiToHex(t).padEnd(66, '0') // 0x + 64 hex chars = bytes32
);

const ether1000 = web3.utils.toWei("1000", "ether");
const ether500 = web3.utils.toWei("500", "ether");
const ether300 = web3.utils.toWei("300", "ether");
const ether100 = web3.utils.toWei("100", "ether");
const ether3 = web3.utils.toWei("3", "ether");
const ether2 = web3.utils.toWei("2", "ether");

contract("DEX - TEST 2", (accounts) => {

    let DEXInstance;

    const [admin1, admin2, admin3, account1, account2] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        // Accounts 1, 2 and 3 are reserved for control and admin related logic.
        
        AAVEInstance = await AAVE.new({ from: admin2 });
        DAIInstance = await DAI.new({ from: admin2 });
        LDOInstance = await LDO.new({ from: admin2 });
        LINKInstance = await LINK.new({ from: admin2 });
        MKRInstance = await MKR.new({ from: admin2 });
        SHIBInstance = await SHIB.new({ from: admin3 });
        UNIInstance = await UNI.new({ from: admin3 });
        USDCInstance = await USDC.new({ from: admin3 });
        USDTInstance = await USDT.new({ from: admin3 });
        WETHInstance = await WETH.new({ from: admin3 });

        DEXInstance = await DEX.new({ from: admin1 });

        await DEXInstance.addToken(bytes32Tickers[0], AAVEInstance.address, {from: admin1});
        await DEXInstance.addToken(bytes32Tickers[1], DAIInstance.address, {from: admin1});
        await DEXInstance.addToken(bytes32Tickers[2], LDOInstance.address, {from: admin1});
        await DEXInstance.addToken(bytes32Tickers[3], LINKInstance.address, {from: admin1});
        await DEXInstance.addToken(bytes32Tickers[4], MKRInstance.address, {from: admin1});

    });

    it("should create limit order", async () => {

        await LINKInstance.transfer(account1, ether1000, {from: admin2});

        await DAIInstance.transfer(account2, ether1000, {from: admin2});


        await LINKInstance.approve(DEXInstance.address, ether500, {from: account1});

        await DEXInstance.deposit(bytes32Tickers[3], ether500, {from: account1});


        await DAIInstance.approve(DEXInstance.address, ether300, {from: account2});

        await DEXInstance.deposit(bytes32Tickers[1], ether300, {from: account2});


        await DEXInstance.createLimitOrder(bytes32Tickers[3], ether100, ether2, 0, {from: account2});
        
        await DEXInstance.createLimitOrder(bytes32Tickers[3], ether500, ether3, 1, {from: account1});

        
    });

    it("should not create limit order if balance is too low", async () => {
        
        await LINKInstance.transfer(account1, ether500, {from: admin2});

        await DAIInstance.transfer(account2, ether100, {from: admin2});


        await LINKInstance.approve(DEXInstance.address, ether500, {from: account1});

        await DEXInstance.deposit(bytes32Tickers[3], ether500, {from: account1});


        await DAIInstance.approve(DEXInstance.address, ether100, {from: account2});

        await DEXInstance.deposit(bytes32Tickers[1], ether100, {from: account2});


        await truffleAssert.reverts(
            DEXInstance.createLimitOrder(bytes32Tickers[3], ether1000, ether2, 1, {from: account1}),
            "not enough funds"
        );
        

        await truffleAssert.reverts(
            DEXInstance.createLimitOrder(bytes32Tickers[3], ether500, ether2, 0, {from: account2}),
            "not enough DAI"
        );

    });

    it("should not create limit order if token is DAI", async () => {
        
        await DAIInstance.transfer(account2, ether1000, {from: admin2});

        await DAIInstance.approve(DEXInstance.address, ether1000, {from: account2});

        await DEXInstance.deposit(bytes32Tickers[1], ether1000, {from: account2});

        await truffleAssert.reverts(
            DEXInstance.createLimitOrder(bytes32Tickers[1], ether500, ether2, 0, {from: account2}),
            "cannot trade DAI"
        );

    });

    it("should not create limit order if token does not exist", async () => {
        
        await UNIInstance.transfer(account2, ether1000, {from: admin3});

        await UNIInstance.approve(DEXInstance.address, ether1000, {from: account2});

        await truffleAssert.reverts(
            DEXInstance.createLimitOrder(bytes32Tickers[7], ether500, ether2, 1, {from: account2}),
            "token does not exist"
        );

    });

});
