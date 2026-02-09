const AAVE = artifacts.require("AAVE");
const DAI = artifacts.require("DAI");
const LDO = artifacts.require("LDO");
const LINK = artifacts.require("LINK");

const DEX = artifacts.require("DEX");

const truffleAssert = require("truffle-assertions");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

const tickers = ["AAVE", "DAI", "LDO", "LINK", "MKR", "SHIB", "UNI", "USDC", "USDT", "WETH"];

const bytes32Tickers = tickers.map(t =>
  web3.utils.asciiToHex(t).padEnd(66, '0') // 0x + 64 hex chars = bytes32
);

const ether1000 = web3.utils.toWei("1000", "ether");
const ether50 = web3.utils.toWei("50", "ether");

contract("DEX - TEST 3", (accounts) => {

    let DEXInstance;
        
    const [admin1, admin2, admin3, account1, account2, account3, account4, accountME] = accounts;
    
    let AAVEInstance, DAIInstance, LDOInstance, LINKInstance;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    before(async () => {
    
        
        // Accounts 1, 2 and 3 are reserved for control and admin related logic.
        
        AAVEInstance = await AAVE.new({ from: admin2 });
        DAIInstance = await DAI.new({ from: admin2 });
        LDOInstance = await LDO.new({ from: admin2 });
        LINKInstance = await LINK.new({ from: admin2 });
        
        DEXInstance = await DEX.new({ from: admin1 });
        
        DEXInstance.addToken(bytes32Tickers[0], AAVEInstance.address, {from: admin1});
        DEXInstance.addToken(bytes32Tickers[1], DAIInstance.address, {from: admin1});
        DEXInstance.addToken(bytes32Tickers[2], LDOInstance.address, {from: admin1});
        DEXInstance.addToken(bytes32Tickers[3], LINKInstance.address, {from: admin1});
        
        await DAIInstance.transfer(accountME, ether1000, {from: admin2});
        await DAIInstance.approve(DEXInstance.address, ether1000, {from: accountME});
        await DEXInstance.deposit(bytes32Tickers[1], ether1000, {from: accountME});
        
        snapshotId = await takeSnapshot();

    });

    it("should NOT create market order if not enough funds", async () => {
        
        await truffleAssert.reverts( 
            DEXInstance.createMarketSellOrder(bytes32Tickers[0], ether1000, 1, {from: accountME}),
            "not enough funds"
        );

    });

    it("should not create marker order if token is DAI", async () => {
        
        await truffleAssert.reverts( 
            DEXInstance.createMarketBuyOrder(bytes32Tickers[1], ether50, 0, {from: accountME}),
            "cannot trade DAI"
        );

    });

    it("should not create market order if token does not exist", async () => {
       
        await truffleAssert.reverts( 
            DEXInstance.createMarketBuyOrder(bytes32Tickers[9], ether50, 0, {from: accountME}),
            "token does not exist"
        );

    });

    it("should create market order & match", async () => {

        let v = web3.utils.toWei('500000', 'ether');

        await DAIInstance.transfer(account1, v, { from: admin2 });
        await DAIInstance.transfer(account2, v, { from: admin2 });
        await DAIInstance.approve(DEXInstance.address, v, { from: account1 });;
        await DAIInstance.approve(DEXInstance.address, v, { from: account2 });
        await DEXInstance.deposit(bytes32Tickers[1], v, { from: account1 });
        await DEXInstance.deposit(bytes32Tickers[1], v, { from: account2 });

        await AAVEInstance.transfer(account1, v, { from: admin2 });
        await AAVEInstance.transfer(account2, v, { from: admin2 });
        await AAVEInstance.approve(DEXInstance.address, v, { from: account1 });
        await AAVEInstance.approve(DEXInstance.address, v, { from: account2 });
        await DEXInstance.deposit(bytes32Tickers[0], v, { from: account1 });
        await DEXInstance.deposit(bytes32Tickers[0], v, { from: account2 });
        
        await AAVEInstance.transfer(accountME, v, { from: admin2 });
        await AAVEInstance.approve(DEXInstance.address, v, { from: accountME });
        await DEXInstance.deposit(bytes32Tickers[0], v, { from: accountME });
        
        let w = (i) => web3.utils.toWei(i.toString(), "ether");

        // 1) Seed the book with a few SELLs (bestAsk will start at 5)
        await DEXInstance.createLimitOrder(bytes32Tickers[0], w(90),  w(5), 1, { from: account2 });  // call 2
        await DEXInstance.createLimitOrder(bytes32Tickers[0], w(70),  w(5), 1, { from: account2 });  // call 4
        await DEXInstance.createLimitOrder(bytes32Tickers[0], w(50),  w(7), 1, { from: account2 });  // call 6
        await DEXInstance.createLimitOrder(bytes32Tickers[0], w(30),  w(7), 1, { from: account2 });  // call 8
        await DEXInstance.createLimitOrder(bytes32Tickers[0], w(10),  w(6), 1, { from: account2 });  // call 10
        
        // 2) Now all your SELLs live at prices {5,5,7,7,6}, so bestAsk = 5.
        //    Any BUY < 5 will pass the “< bestAsk” check:
        await DEXInstance.createLimitOrder(bytes32Tickers[0], w(100), w(2), 0, { from: account1 });  // call 1 (2 ≤ 5)
        await DEXInstance.createLimitOrder(bytes32Tickers[0], w(80),  w(3), 0, { from: account1 });  // call 3 (3 ≤ 5)
        await DEXInstance.createLimitOrder(bytes32Tickers[0], w(60),  w(4), 0, { from: account1 });  // call 5 (4 ≤ 5)
        await DEXInstance.createLimitOrder(bytes32Tickers[0], w(20),  w(4), 0, { from: account1 });  // call 9 (5 ≤ 5)
        await DEXInstance.createLimitOrder(bytes32Tickers[0], w(120), w(2), 0, { from: account1 });  // call 11 (2 ≤ 5)
        


        await DEXInstance.createMarketSellOrder(bytes32Tickers[0], w(8), 1, {from: accountME});

        const sell = await DEXInstance.getDeposits(bytes32Tickers[0], {from: accountME});
        
        assert.equal(sell, "499992000000000000000000", "did not sell 8 AAVE");


        await DEXInstance.createMarketBuyOrder(bytes32Tickers[0], w(5), 0, {from: accountME});

        const buy = await DEXInstance.getDeposits(bytes32Tickers[0], {from: accountME});
        
        assert.equal(buy, "499997000000000000000000", "did not buy 5 AAVE");

    });

});
