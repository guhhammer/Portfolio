const Dex = artifacts.require("Dex");
const Oracle = artifacts.require("Oracle");
const ArbitrageTrader = artifacts.require("ArbitrageTrader");

const truffleAssert = require("truffle-assertions");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("ArbitrageTrader", (accounts) => {

    let DexInstance; let OracleInstance; let ArbitrageTraderInstance;
        
    const [account1, account2, account3] = accounts;
    
    let snapshotId;
    
    afterEach(async () => { await revertToSnapshot(); });
    
    before(async () => {
            
        DexInstance = await Dex.new({from: account1});

        OracleInstance = await Oracle.new([account1], {from: account1});

        ArbitrageTraderInstance = await ArbitrageTrader.new({from: account1});

        snapshotId = await takeSnapshot();

    });

    it.skip("ignore all tests for this contract", async () => {

    });

});