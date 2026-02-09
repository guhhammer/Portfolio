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

contract("AAVE", (accounts) => {
    let AAVEInstance;
    const [account1] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        AAVEInstance = await AAVE.new({ from: account1 });
    
    });

    it("should check if contract is deployed", async () => {
        assert(AAVEInstance.address !== "", "Contract not deployed");
    });

    it("AAVE constructor set right", async () => {
        const name = await AAVEInstance.name();
        const symbol = await AAVEInstance.symbol();
        const decimals = await AAVEInstance.decimals();
        const totalSupply = await AAVEInstance.totalSupply();

        assert.equal(name, "Aave Token", "Name is not set correctly");
        assert.equal(symbol, "AAVE", "Symbol is not set correctly");
        assert.equal(decimals.toString(), "18", "Decimals are not set correctly");
        assert.equal(totalSupply.toString(), "16000000000000000000000000", "Total supply is not set correctly");
    });
});

contract("DAI", (accounts) => {
    let DAIInstance;
    const [account1] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        DAIInstance = await DAI.new({ from: account1 });
    });

    it("should check if contract is deployed", async () => {
        assert(DAIInstance.address !== "", "Contract not deployed");
    });

    it("DAI constructor set right", async () => {
        const name = await DAIInstance.name();
        const symbol = await DAIInstance.symbol();
        const decimals = await DAIInstance.decimals();
        const totalSupply = await DAIInstance.totalSupply();

        assert.equal(name, "Dai Stablecoin", "Name is not set correctly");
        assert.equal(symbol, "DAI", "Symbol is not set correctly");
        assert.equal(decimals.toString(), "18", "Decimals are not set correctly");
        assert.equal(totalSupply.toString(), "1000000000000000000000000000", "Total supply is not set correctly");
    });
});

contract("LDO", (accounts) => {
    let LDOInstance;
    const [account1] = accounts;

    let snapshotId;
 
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
   
        LDOInstance = await LDO.new({ from: account1 });
   
    });

    it("should check if contract is deployed", async () => {
        assert(LDOInstance.address !== "", "Contract not deployed");
    });

    it("LDO constructor set right", async () => {
        const name = await LDOInstance.name();
        const symbol = await LDOInstance.symbol();
        const decimals = await LDOInstance.decimals();
        const totalSupply = await LDOInstance.totalSupply();

        assert.equal(name, "Lido DAO Token", "Name is not set correctly");
        assert.equal(symbol, "LDO", "Symbol is not set correctly");
        assert.equal(decimals.toString(), "18", "Decimals are not set correctly");
        assert.equal(totalSupply.toString(), "1000000000000000000000000000", "Total supply is not set correctly");
    });
});

contract("LINK", (accounts) => {
    let LINKInstance;
    const [account1] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        LINKInstance = await LINK.new({ from: account1 });
    
    });

    it("should check if contract is deployed", async () => {
        assert(LINKInstance.address !== "", "Contract not deployed");
    });

    it("LINK constructor set right", async () => {
        const name = await LINKInstance.name();
        const symbol = await LINKInstance.symbol();
        const decimals = await LINKInstance.decimals();
        const totalSupply = await LINKInstance.totalSupply();

        assert.equal(name, "ChainLink Token", "Name is not set correctly");
        assert.equal(symbol, "LINK", "Symbol is not set correctly");
        assert.equal(decimals.toString(), "18", "Decimals are not set correctly");
        assert.equal(totalSupply.toString(), "1000000000000000000000000000", "Total supply is not set correctly");
    });
});

contract("MKR", (accounts) => {
    let MKRInstance;
    const [account1] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        MKRInstance = await MKR.new({ from: account1 });
    
    });

    it("should check if contract is deployed", async () => {
        assert(MKRInstance.address !== "", "Contract not deployed");
    });

    it("MKR constructor set right", async () => {
        const name = await MKRInstance.name();
        const symbol = await MKRInstance.symbol();
        const decimals = await MKRInstance.decimals();
        const totalSupply = await MKRInstance.totalSupply();

        assert.equal(name, "Maker", "Name is not set correctly");
        assert.equal(symbol, "MKR", "Symbol is not set correctly");
        assert.equal(decimals.toString(), "18", "Decimals are not set correctly");
        assert.equal(totalSupply.toString(), "1000000000000000000000000", "Total supply is not set correctly");
    });
});

contract("SHIB", (accounts) => {
    let SHIBInstance;
    const [account1] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        SHIBInstance = await SHIB.new({ from: account1 });
    
    });

    it("should check if contract is deployed", async () => {
        assert(SHIBInstance.address !== "", "Contract not deployed");
    });

    it("SHIB constructor set right", async () => {
        const name = await SHIBInstance.name();
        const symbol = await SHIBInstance.symbol();
        const decimals = await SHIBInstance.decimals();
        const totalSupply = await SHIBInstance.totalSupply();

        assert.equal(name, "Shiba Inu", "Name is not set correctly");
        assert.equal(symbol, "SHIB", "Symbol is not set correctly");
        assert.equal(decimals.toString(), "18", "Decimals are not set correctly");
        assert.equal(totalSupply.toString(), "589000000000000000000000000000000", "Total supply is not set correctly");
    });
});

contract("UNI", (accounts) => {
    let UNIInstance;
    const [account1] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        UNIInstance = await UNI.new({ from: account1 });
    
    });

    it("should check if contract is deployed", async () => {
        assert(UNIInstance.address !== "", "Contract not deployed");
    });

    it("UNI constructor set right", async () => {
        const name = await UNIInstance.name();
        const symbol = await UNIInstance.symbol();
        const decimals = await UNIInstance.decimals();
        const totalSupply = await UNIInstance.totalSupply();

        assert.equal(name, "Uniswap", "Name is not set correctly");
        assert.equal(symbol, "UNI", "Symbol is not set correctly");
        assert.equal(decimals.toString(), "18", "Decimals are not set correctly");
        assert.equal(totalSupply.toString(), "1000000000000000000000000000", "Total supply is not set correctly");
    });
});

contract("USDC", (accounts) => {
    let USDCInstance;
    const [account1] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        USDCInstance = await USDC.new({ from: account1 });
    
    });

    it("should check if contract is deployed", async () => {
        assert(USDCInstance.address !== "", "Contract not deployed");
    });

    it("USDC constructor set right", async () => {
        const name = await USDCInstance.name();
        const symbol = await USDCInstance.symbol();
        const decimals = await USDCInstance.decimals();
        const totalSupply = await USDCInstance.totalSupply();

        assert.equal(name, "USD Coin", "Name is not set correctly");
        assert.equal(symbol, "USDC", "Symbol is not set correctly");
        assert.equal(decimals.toString(), "6", "Decimals are not set correctly");
        assert.equal(totalSupply.toString(), "10000000000000000", "Total supply is not set correctly");
    });
});

contract("USDT", (accounts) => {
    let USDTInstance;
    const [account1] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        USDTInstance = await USDT.new({ from: account1 });
    
    });

    it("should check if contract is deployed", async () => {
        assert(USDTInstance.address !== "", "Contract not deployed");
    });

    it("USDT constructor set right", async () => {
        const name = await USDTInstance.name();
        const symbol = await USDTInstance.symbol();
        const decimals = await USDTInstance.decimals();
        const totalSupply = await USDTInstance.totalSupply();

        assert.equal(name, "Tether USD", "Name is not set correctly");
        assert.equal(symbol, "USDT", "Symbol is not set correctly");
        assert.equal(decimals.toString(), "6", "Decimals are not set correctly");
        assert.equal(totalSupply.toString(), "10000000000000000", "Total supply is not set correctly");
    });
});

contract("WETH", (accounts) => {
    let WETHInstance;
    const [account1] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        WETHInstance = await WETH.new({ from: account1 });
    
    });

    it("should check if contract is deployed", async () => {
        assert(WETHInstance.address !== "", "Contract not deployed");
    });

    it("WETH constructor set right", async () => {
        const name = await WETHInstance.name();
        const symbol = await WETHInstance.symbol();
        const decimals = await WETHInstance.decimals();
        const totalSupply = await WETHInstance.totalSupply();

        assert.equal(name, "Wrapped Ether", "Name is not set correctly");
        assert.equal(symbol, "WETH", "Symbol is not set correctly");
        assert.equal(decimals.toString(), "18", "Decimals are not set correctly");
        assert.equal(totalSupply.toString(), "5000000000000000000000000", "Total supply is not set correctly");
    });
});
