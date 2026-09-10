
const Lottery = artifacts.require("Lottery");
const truffleAssert = require("truffle-assertions");

const { takeSnapshot, revertToSnapshot } = require("./helpers");

contract("Lottery", (accounts) => {

    let LotteryInstance;

    const [account1, account2, account3, account4, account5, account6, account7] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();

        LotteryInstance = await Lottery.new(5, {from: account1});

    });

    it("should check if contract is deployed", async () => {
       
        assert(LotteryInstance.address !== "", "Contract not deployed");
    
    });

    it("should check if contract admin is deployer", async () => {
       
        assert(await LotteryInstance.admin() == account1, "Deployer is not admin");
    
    });

    it("should check if only admin can create bet", async () => { 

        await truffleAssert.reverts(
            LotteryInstance.createBet(5, web3.utils.toWei("10", "finney"), {from: account7}),
            "only admin can execute"
        )

    });

    it("should create bet if admin", async () => { 

        const tx = await LotteryInstance.createBet(5, web3.utils.toWei("10", "finney"), {from: account1});

        const count = await LotteryInstance.betCount();
        const betSize = await LotteryInstance.betSize();
        const state = await LotteryInstance.state();

        assert.equal(count.toString(), "5", "Bet count is incorrect");
        assert.equal(betSize.toString(), web3.utils.toWei("10", "finney"), "Bet size is incorrect");
        assert.equal(state.toString(), "1", "State is incorrect");

    });
    
    it("should try to overwrite bet and fail", async () => { 

        await LotteryInstance.createBet(5, web3.utils.toWei("10", "finney"), {from: account1});

        await truffleAssert.reverts(
            LotteryInstance.createBet(5, web3.utils.toWei("10", "finney"), {from: account1}),
            "current state does not allow this"
        )
        
    });

    it("should try to bet when there is not a bet placed", async () => { 

        await truffleAssert.reverts(
            LotteryInstance.bet({from: account2, value: web3.utils.toWei("10", "finney")}),
            "current state does not allow this"
        )
        
    });
    
    it("should try to bet with improper value", async () => { 

        await LotteryInstance.createBet(5, web3.utils.toWei("10", "finney"), {from: account1});

        await truffleAssert.reverts(
            LotteryInstance.bet({from: account2, value: web3.utils.toWei("1", "finney")}),
            "can only bet exactly the bet size"
        )
        
    });
    
    it("should place a bet and see only a new player pushed to players", async () => { 

        await LotteryInstance.createBet(5, web3.utils.toWei("10", "finney"), {from: account1});

        await LotteryInstance.bet({from: account2, value: web3.utils.toWei("10", "finney")});

        const sender = await LotteryInstance.players(0);

        assert.equal(sender, account2, "wrong player pushed");
        
    });

    it("should reveal winner after enough bets and reset state for next bet creation", async () => { 

        await LotteryInstance.createBet(5, web3.utils.toWei("10", "finney"), {from: account1});

        const matcher = [
            [account2, await web3.eth.getBalance(account2)],
            [account3, await web3.eth.getBalance(account3)],
            [account4, await web3.eth.getBalance(account4)],
            [account5, await web3.eth.getBalance(account5)],
            [account6, await web3.eth.getBalance(account6)],
        ];
        
        await LotteryInstance.bet({ from: account2, value: web3.utils.toWei("10", "finney") });
        await LotteryInstance.bet({ from: account3, value: web3.utils.toWei("10", "finney") });
        await LotteryInstance.bet({ from: account4, value: web3.utils.toWei("10", "finney") });
        await LotteryInstance.bet({ from: account5, value: web3.utils.toWei("10", "finney") });
        await LotteryInstance.bet({ from: account6, value: web3.utils.toWei("10", "finney") });

        const winner = await LotteryInstance.lastWinner();
        const winnerBalance = await web3.eth.getBalance(winner);

        const before = matcher.filter( m => m[0] == winner)[0][1];

        const actualIncrease = web3.utils.toBN(winnerBalance).sub(web3.utils.toBN(before));

        const tolerance = web3.utils.toBN("10000000000000"); // 0.00001 ETH (adjust as needed)
        const lowerBound = winnerBalance.sub(tolerance);

        assert(
            actualIncrease.gte(lowerBound),
            `Winner's balance increased by ${actualIncrease.toString()} which is below expected minimum ${lowerBound.toString()}`
        );

        const betSize = await LotteryInstance.betSize();
        const betCount = await LotteryInstance.betCount();
        const houseFee = await LotteryInstance.houseFee();

        const prize = (betSize * betCount * (100 - houseFee)) / 100;

        const state = await LotteryInstance.state();

        const emptied = await LotteryInstance.length();

        assert.equal(betSize.toString(), web3.utils.toWei("10", "finney"), "Bet size is incorrect");
        assert.equal(betCount.toString(), "5", "Bet count is incorrect");
        assert.equal(houseFee.toString(), "5", "House fee is incorrect");
        assert.equal(prize.toString(), web3.utils.toBN("47500000000000000"), "Prize calculation is incorrect");
        assert.equal(state.toString(), "0", "State is not reset to IDLE");
        assert.equal(emptied, 0, "Players array is not emptied");

    });

    it("should fail to cancel if not admin", async () => {
       
        await LotteryInstance.createBet(5, web3.utils.toWei("10", "finney"), {from: account1});

        await truffleAssert.reverts(
            LotteryInstance.cancel({from: account2}),
            "only admin can execute"
        );
    
    });

    it("should fail to cancel if admin but state is idle", async () => {
      
        await truffleAssert.reverts(
            LotteryInstance.cancel({from: account1}),
            "current state does not allow this"
        );
   
    });

    it("should allow admin to cancel bet that is not finished", async () => {

        await LotteryInstance.createBet(5, web3.utils.toWei("10", "finney"), {from: account1});        

        await LotteryInstance.bet({ from: account2, value: web3.utils.toWei("10", "finney") });
        await LotteryInstance.bet({ from: account3, value: web3.utils.toWei("10", "finney") });
        await LotteryInstance.bet({ from: account4, value: web3.utils.toWei("10", "finney") });
        
        await LotteryInstance.cancel({from: account1});

        const state = await LotteryInstance.state();
        assert.equal(state.toString(), "0", "State is not reset to IDLE");

        const l = await LotteryInstance.length();

        assert.equal(l, 0, "Players array is not emptied");

    });

});

