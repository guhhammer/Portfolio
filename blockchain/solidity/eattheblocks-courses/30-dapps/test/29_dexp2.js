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

contract("DEX - TEST 1", (accounts) => {

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

    });

    it("should allow to deposit tokens", async () => {

        await DEXInstance.addToken(bytes32Tickers[0], AAVEInstance.address, {from: admin1});

        await AAVEInstance.transfer(account1, ether500, {from: admin2});
        
        await AAVEInstance.approve(DEXInstance.address, ether500, {from: account1});

        await DEXInstance.deposit(bytes32Tickers[0], ether500, {from: account1});

        const d = await DEXInstance.getDeposits(bytes32Tickers[0], {from: account1});

        assert.equal(d, ether500, "deposit was not made properly");

    });

    it("should not deposit tolens if token does not exist", async () => {

        await truffleAssert.reverts(
            DEXInstance.deposit(bytes32Tickers[9], ether500, {from: account1}), // up to now, not defined.
            "token does not exist"
        );

    });

    it("should withdraw tokens", async () => {

        await DEXInstance.addToken(bytes32Tickers[0], AAVEInstance.address, {from: admin1});

        await AAVEInstance.transfer(account1, ether500, {from: admin2});

        await AAVEInstance.approve(DEXInstance.address, ether500, {from: account1});

        await DEXInstance.deposit(bytes32Tickers[0], ether500, {from: account1});

        await DEXInstance.withdraw(bytes32Tickers[0], ether500, {from: account1});

        const dUser = await DEXInstance.getDeposits(bytes32Tickers[0], {from: account1});
        const dDex = await DEXInstance.getDeposits(bytes32Tickers[0], {from: admin1});
        
        assert.equal(dDex.toString(), "2000000000000000000", "fees are wrong");
        assert.equal(dUser.toString(), "0", "user has balance in contract left");      

    });

    it("should not withdraw if not enough tokens", async () => {

        await DEXInstance.addToken(bytes32Tickers[0], AAVEInstance.address, {from: admin1});

        await AAVEInstance.transfer(account1, ether500, {from: admin2});

        await AAVEInstance.approve(DEXInstance.address, ether500, {from: account1});

        await DEXInstance.deposit(bytes32Tickers[0], ether500, {from: account1});

        await truffleAssert.reverts(
            DEXInstance.withdraw(bytes32Tickers[0], ether1000, {from: account1}),
            "not enough funds"
        );

    });

});
