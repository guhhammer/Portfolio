const RockPaperScissors = artifacts.require("RockPaperScissors");
const truffleAssert = require("truffle-assertions");

const { takeSnapshot, revertToSnapshot } = require("./helpers");

contract("RockPaperScissors", (accounts) => {

    let RockPaperScissorsInstance;

    const [account1, account2, account3, account4] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        RockPaperScissorsInstance = await RockPaperScissors.new({from: account1});

    });

    it("should check if contract is deployed", async () => {
       
        assert(RockPaperScissorsInstance.address !== "", "Contract not deployed");
    
    });
    
    it("should check if creates game with no value sent", async () => {

        await truffleAssert.reverts(
            RockPaperScissorsInstance.createGame(account3, {from: account2, value: 0}),
            "need to send some ether"
        );
        
    });

    it("should check if creates a game", async () => {

        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});
       
        const g = await RockPaperScissorsInstance.games(0);

        assert(g.player0 == account2 && g.player1 == account3, "players are incorrect");

    });

    it("should check if it joins a game that is not created", async () => {
       
        await truffleAssert.reverts(
            RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("2", "finney")}),
            "must be in CREATED state"
        );

    });

    it("should check if another player can join an already created game", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});

        await truffleAssert.reverts(
            RockPaperScissorsInstance.joinGame(0, {from: account4, value: web3.utils.toWei("2", "finney")}),
            "sender must be second player"
        );

    });

    it("should check if a player can bet a different amount", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});

        await truffleAssert.reverts(
            RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("1", "finney")}),
            "not enough ether sent"
        );

    });

    it("should check if a player can send more than amount defined", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});

        const balanceBefore = await web3.eth.getBalance(account3) - web3.utils.toWei("3", "finney");

        await RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("3", "finney")});

        const balanceAfter = await web3.eth.getBalance(account3);

        assert(balanceAfter > balanceBefore, "Balance was not refunded the extra amount");

    });

    it("should check if a player joined game correctly", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});

        await RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("2", "finney")});

        const g = await RockPaperScissorsInstance.games(0);

        assert.equal(g.state, 2, "state is not joined");

    });

    it("should check if a player can commit move if not in state joined", async () => {
        
        await truffleAssert.reverts(
            RockPaperScissorsInstance.commitMove(0, 1, 5, {from: account2}),
            "game must be in JOINED state"
        );
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});

        await truffleAssert.reverts(
            RockPaperScissorsInstance.commitMove(0, 1, 5, {from: account3}),
            "game must be in JOINED state"
        );
    
    });

    it("should check if a player can commit move other than 1, 2 or 3", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});

        await RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("2", "finney")});

        await truffleAssert.reverts(              //x
            RockPaperScissorsInstance.commitMove(0, 0, 5, {from: account3}),
            "move must be either 1,2,3"
        );

        await truffleAssert.reverts(              //x
            RockPaperScissorsInstance.commitMove(0, 4, 5, {from: account3}),
            "move must be either 1,2,3"
        );
    
    });

    it("should check if a non-player can commit move", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});

        await RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("2", "finney")});

        await truffleAssert.reverts(              //x
            RockPaperScissorsInstance.commitMove(0, 2, 5, {from: account4}),
            "can only be called by one of the players"
        );

    });

    it("should check if a player can commit move more than once", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});

        await RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("2", "finney")});

        await RockPaperScissorsInstance.commitMove(0, 2, 5, {from: account2}),

        await truffleAssert.reverts(              //x
            RockPaperScissorsInstance.commitMove(0, 3, 5, {from: account2}),
            "move already made"
        );
        
    });

    it("should check if only a player commit keeps state in joined and has hash set", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});

        await RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("2", "finney")});

        await RockPaperScissorsInstance.commitMove(0, 2, 5, {from: account2});

        const g = await RockPaperScissorsInstance.games(0);

        assert.equal(g.state, 2, "state is not joined");
        
        const move = await RockPaperScissorsInstance.moves(0, account2);
        assert.notEqual(move.hash_, 0, "move.hash_ is 0 for account2");
        
    });

    it("should check if state is commited", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});

        await RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("2", "finney")});

        await RockPaperScissorsInstance.commitMove(0, 2, 5, {from: account2});

        await RockPaperScissorsInstance.commitMove(0, 3, 5, {from: account3});

        const g = await RockPaperScissorsInstance.games(0);

        assert.equal(g.state, 3, "state is not commited");
        
    });

    it("should check if a player can commit move if not in state joined", async () => {
        
        await truffleAssert.reverts(
            RockPaperScissorsInstance.revealMove(0, 1, 5, {from: account2}),
            "game must be in commited state"
        );
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});
        
        await RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("2", "finney")}),
        
        await truffleAssert.reverts(
            RockPaperScissorsInstance.revealMove(0, 1, 5, {from: account2}),
            "game must be in commited state"
        );

        await RockPaperScissorsInstance.commitMove(0, 2, 5, {from: account2});
    
        await truffleAssert.reverts(
            RockPaperScissorsInstance.revealMove(0, 1, 5, {from: account2}),
            "game must be in commited state"
        );

    });

    it("should check if a non-player can reveal move", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});
        
        await RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("2", "finney")}),
        
        await RockPaperScissorsInstance.commitMove(0, 2, 5, {from: account2});
        
        await RockPaperScissorsInstance.commitMove(0, 3, 5, {from: account3});
        
        await truffleAssert.reverts(
            RockPaperScissorsInstance.revealMove(0, 1, 5, {from: account4}),
            "can only be called by one of the players"
        );

    });

    it("should check if a player can fake its move when reveal move is called", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});
        
        await RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("2", "finney")}),
        
        await RockPaperScissorsInstance.commitMove(0, 2, 5, {from: account2});
        
        await RockPaperScissorsInstance.commitMove(0, 3, 5, {from: account3});
        
        await truffleAssert.reverts(
            RockPaperScissorsInstance.revealMove(0, 2, 8, {from: account2}),
            "moveId does not match commitment"
        );

    });

    it("should check if a both balances are send half-bet in case of draw", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});
        
        await RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("2", "finney")}),
        
        await RockPaperScissorsInstance.commitMove(0, 3, 5, {from: account2});
        
        await RockPaperScissorsInstance.commitMove(0, 3, 4, {from: account3});

        const balanceBefore1 = await web3.eth.getBalance(account2);
        const balanceBefore2 = await web3.eth.getBalance(account3);
        
        await RockPaperScissorsInstance.revealMove(0, 3, 5, {from: account2});
        await RockPaperScissorsInstance.revealMove(0, 3, 4, {from: account3});

        const balanceAfter1 = await web3.eth.getBalance(account2);
        const balanceAfter2 = await web3.eth.getBalance(account3);

        assert(balanceAfter1 > balanceBefore1, "Account2 was not refunded");
        assert(balanceAfter2 - balanceBefore2, "Account3 was not refunded");

        const g = await RockPaperScissorsInstance.games(0);

        assert.equal(g.state, 4, "state is not revealed");

    });

    it("should check if a both balances are send half-bet in case of draw", async () => {
        
        await RockPaperScissorsInstance.createGame(account3, {from: account2, value: web3.utils.toWei("2", "finney")});
        
        await RockPaperScissorsInstance.joinGame(0, {from: account3, value: web3.utils.toWei("2", "finney")}),
        
        await RockPaperScissorsInstance.commitMove(0, 1, 5, {from: account2});
        
        await RockPaperScissorsInstance.commitMove(0, 3, 4, {from: account3});

        const balanceBefore1 = BigInt(await web3.eth.getBalance(account2));
        
        await RockPaperScissorsInstance.revealMove(0, 1, 5, {from: account2});
        await RockPaperScissorsInstance.revealMove(0, 3, 4, {from: account3});

        const balanceAfter1 = BigInt(await web3.eth.getBalance(account2));

        assert(balanceAfter1 > balanceBefore1, "Account2 balance did not increase correctly");
    
        const g = await RockPaperScissorsInstance.games(0);

        assert.equal(g.state, 4, "state is not revealed");

    });

});
