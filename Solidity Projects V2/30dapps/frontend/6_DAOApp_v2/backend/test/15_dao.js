const DAO = artifacts.require("DAO");
const truffleAssert = require("truffle-assertions");

const {increaseTime, ZERO_ADDRESS, takeSnapshot, revertToSnapshot} = require("./helpers");

contract("DAO", (accounts) => {

    let DAOInstance;

    const [account1, account2, account3, account4, account5, account6, account7, account8] = accounts;
    
    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        DAOInstance = await DAO.new(1000, {from: account1});

    });

    it("should check if contract is deployed", async () => {
       
        assert(DAOInstance.address !== "", "Contract not deployed");
    
    });

    it("should check if contract admin is deployer", async () => {
       
        assert(await DAOInstance.admin() == account1, "Deployer is not admin");
    
    });

    it("should have quorum set to 50", async () => {
    
        const value = await DAOInstance.quorum();
    
        assert.equal(value.toString(), "50", "Quorum constant should be 50");
    
    });

    it("should accept ETH via receive() and update availableFunds", async () => {

        // Send finney directly to contract (triggers receive())
        const tx = await web3.eth.sendTransaction({
          from: accounts[7],
          to: DAOInstance.address,
          value: web3.utils.toWei("1", "finney"),
        });
    
        // Check availableFunds
        const funds = await DAOInstance.availableFunds();
        assert.equal(funds.toString(), web3.utils.toWei("1", "finney"), "availableFunds should match sent value");
    
        // Check that the event was emitted
        const receipt = await web3.eth.getTransactionReceipt(tx.transactionHash);
        const logs = await DAOInstance.getPastEvents("FallbackTriggered", {
          fromBlock: receipt.blockNumber,
          toBlock: receipt.blockNumber,
        });
    
        assert.equal(logs.length, 1, "FallbackTriggered event should be emitted");
        assert.equal(logs[0].returnValues._sender, accounts[7], "Sender address mismatch");
        assert.equal(logs[0].returnValues._amount, web3.utils.toWei("1", "finney"), "Event amount mismatch");

    });

    it("should check if contribution deadline has not expired", async () => {

        await increaseTime(5000);

        // This should revert in checkContributionDeadline().
        // const tx = await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});
        // console.log(tx);

        await truffleAssert.reverts(
            DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if call to contribute has value funds to it", async () => {

        // This should revert in checkMustFund().
        // const tx = await DAOInstance.contribute({from: account2});
        // console.log(tx);

        await truffleAssert.reverts(
            DAOInstance.contribute({from: account2 }),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if event contributor was emitted properly", async () => {
        // Call the contribute function
        const tx = await DAOInstance.contribute({ from: account2, value: web3.utils.toWei("3", "finney") });
    
        // Access the transaction logs directly
        const logs = tx.logs;
    
        // Filter logs for the Contributor event
        const event = logs.find(log => log.event === "Contributor");
    
        assert.equal(event.args._contributor, account2, "Contributor address is incorrect");
        assert.equal(event.args._amount, web3.utils.toWei("3", "finney"), "Amount is incorrect");
        assert.equal(event.args._action, web3.utils.keccak256("contribute"), "Action is incorrect");
        
    });
    
    it("should check if investor account is true after fund", async () => {

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        assert.equal(await DAOInstance.investors(account2), true, "Investor didn't fund");

    });

    it("should check if shares, totalShares and availableFunds were increased properly", async () => {

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        let _s = await DAOInstance.shares(account2);
        let _ts = await DAOInstance.totalShares();
        let _af = await DAOInstance.availableFunds();
        
        assert.equal(_s, web3.utils.toWei("3", "finney"), "shares should be equal to 3 finney");
        assert.equal(_ts, web3.utils.toWei("3", "finney"), "totalShares should be equal to 3 finney");
        assert.equal(_af, web3.utils.toWei("3", "finney"), "availableFunds should be equal to 3 finney");

    });
    
    it("should check if only investor can redeem", async () => {

        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
      
        await truffleAssert.reverts(
            DAOInstance.redeemShares(web3.utils.toWei("3", "finney"), {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if investor can redeem enough shares", async () => {

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        // Redeeming more that it has.
        await truffleAssert.reverts(
            DAOInstance.redeemShares(web3.utils.toWei("4", "finney"), {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

        const tx = await DAOInstance.redeemShares(web3.utils.toWei("1", "finney"), {from: account2});

        const event = tx.logs.find(log => log.event === "Contributor");
        
        assert(event.args._amount, web3.utils.toWei("1", "finney"), "Expected event not found");

    });

    it("should check if event contributor was emitted properly", async () => {

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        const tx = await DAOInstance.redeemShares(web3.utils.toWei("1", "finney"), {from: account2});

        const event = tx.logs.find(log => log.event === "Contributor");
        
        assert.equal(event.args._contributor, account2, "Contributor address is incorrect");
        assert.equal(web3.utils.toWei("3", "finney") - event.args._amount, web3.utils.toWei("1", "finney"), "Amount is incorrect");
        assert.equal(event.args._action, web3.utils.keccak256("redeem"), "Action is incorrect");
    
    });

    it("should check if shares, totalShares and availableFunds were decreased properly", async () => {

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        await DAOInstance.redeemShares(web3.utils.toWei("1", "finney"), {from: account2});

        let _s = await DAOInstance.shares(account2);
        let _ts = await DAOInstance.totalShares();
        let _af = await DAOInstance.availableFunds();
        
        assert.equal(_s, web3.utils.toWei("2", "finney"), "shares should be equal to 2 finney");
        assert.equal(_ts, web3.utils.toWei("2", "finney"), "totalShares should be equal to 2 finney");
        assert.equal(_af, web3.utils.toWei("2", "finney"), "availableFunds should be equal to 2 finney");

    });

    it("should check if investor redeemed its shares", async () => {

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});
        
        // Get balance before redeeming
        const balanceBefore = new web3.utils.BN(await web3.eth.getBalance(account2));

        // Send transaction and capture receipt
        const tx = await DAOInstance.redeemShares(web3.utils.toWei("1", "finney"), { from: account2 });
        const txReceipt = await web3.eth.getTransaction(tx.tx);
        const gasUsed = new web3.utils.BN(tx.receipt.gasUsed);
        const gasPrice = new web3.utils.BN(txReceipt.gasPrice);
        const gasCost = gasUsed.mul(gasPrice);

        // Get balance after redeeming
        const balanceAfter = new web3.utils.BN(await web3.eth.getBalance(account2));

        // Calculate balance difference
        const balanceDiff = balanceAfter.sub(balanceBefore).add(gasCost); // Add gas cost back

        // Expected refund is 1 finney
        const expectedRefund = new web3.utils.BN(web3.utils.toWei("1", "finney"));

        // Assert difference is close (tolerate minor variations if needed)
        assert(
            balanceDiff.eq(expectedRefund),
            `Expected refund of 1 finney, but got ${web3.utils.fromWei(balanceDiff)}`
        );

    });

    it("should check if only investor can transfer", async () => {

        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
      
        await truffleAssert.reverts(
            DAOInstance.transferShares(account3, web3.utils.toWei("3", "finney"), {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if doesn't send to invalid recipient", async () => {

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});
        
        await truffleAssert.reverts(
            DAOInstance.transferShares(ZERO_ADDRESS, web3.utils.toWei("2", "finney"), {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if doesn't send more shares that it has", async () => {

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});
        
        await truffleAssert.reverts(
            DAOInstance.transferShares(account4, web3.utils.toWei("4", "finney"), {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if event contributor was emitted properly", async () => {

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        const tx = await DAOInstance.transferShares(account5, web3.utils.toWei("1", "finney"), {from: account2});

        const event = tx.logs.find(log => log.event === "Contributor");
        
        assert.equal(event.args._contributor, account2, "Contributor address is incorrect");
        assert.equal(web3.utils.toWei("3", "finney") - event.args._amount, web3.utils.toWei("1", "finney"), "Amount is incorrect");
        assert.equal(event.args._action, web3.utils.keccak256("transfer"), "Action is incorrect");
    
    });

    it("should check if event transfer was emitted properly", async () => {

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        const tx = await DAOInstance.transferShares(account5, web3.utils.toWei("1", "finney"), {from: account2});

        const event = tx.logs.find(log => log.event === "Transfer");
        
        assert.equal(event.args._sender, account2, "Sender address is incorrect");
        assert.equal(event.args._receiver, account5, "Receiver Address is incorrect");
        assert.equal(event.args._amount, web3.utils.toWei("1", "finney"), "Amount is incorrect");
    
    });

    it("should check if shares were swapped properly", async () => {

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        await DAOInstance.transferShares(account5, web3.utils.toWei("1", "finney"), {from: account2});

        let _s = await DAOInstance.shares(account2);
        let _t = await DAOInstance.shares(account5)
        
        assert.equal(_s, web3.utils.toWei("2", "finney"), "shares should be equal to 2 finney");
        assert.equal(_t, web3.utils.toWei("1", "finney"), "totalShares should be equal to 1 finney");

    });

    it("should check if only investor can create proposal", async () => {

        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
      
        await truffleAssert.reverts(
            DAOInstance.createProposal("proposal 1", account7, web3.utils.toWei("3", "finney"), {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if contract has enough funds ", async () => {

        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("10", "finney"),
        });
      
        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});
        
        await truffleAssert.reverts(
            DAOInstance.createProposal("proposal 1", account7, web3.utils.toWei("15", "finney"), {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if doesn't send to invalid recipient", async () => {

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});
        
        await truffleAssert.reverts(
            DAOInstance.createProposal("proposal 1", ZERO_ADDRESS, web3.utils.toWei("1", "finney"), {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );
    });

    it("should check if event 'proposal created' was emitted properly", async () => {

        DAOInstance = await DAO.new(1000, {from: account1});

        await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("10", "finney"),
        });

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});
        
        const tx = await DAOInstance.createProposal("proposal 1", account7, web3.utils.toWei("5", "finney"), {from: account2});
        
        const event = tx.logs.find(log => log.event === "ProposalCreated");
        
        assert.equal(event.args._id.toString(), "0", "Proposal Id is incorrect");
        assert.equal(event.args._name, web3.utils.keccak256("proposal 1"), "Proposal name is incorrect");
        assert.equal(event.args._recipient, account7, "Proposal recipient account is incorrect");
        assert.equal(event.args._amount, web3.utils.toWei("5", "finney"), "Proposal amount is incorrect");
    
    });

    it("should check if proposal was set right", async () => {

        await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("10", "finney"),
        });

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});
        
        const avail = await DAOInstance.availableFunds();

        await DAOInstance.createProposal("proposal 1", account7, web3.utils.toWei("5", "finney"), {from: account2});

        let p = await DAOInstance.getProposal(0);

        let n = await DAOInstance.nextProposalId();

        const now_avail = await DAOInstance.availableFunds();

        assert.equal(p[0], "0", "Proposal Id is incorrect");
        assert.equal(p[4], "proposal 1", "Proposal name is incorrect");
        assert.equal(p[5], account7, "Proposal recipient account is incorrect");
        assert.equal(p[1], web3.utils.toWei("5", "finney"), "Proposal amount is incorrect");
        assert.equal(p[2], 0, "Proposal votes is incorrect");
        assert.equal(p[6], false, "Proposal executed shoulb de false");
        //ignore deadline, it's always 30 days ahead of blocktimestamp transaction was mined.
        assert.equal(avail.sub(now_avail), web3.utils.toWei("5", "finney"), "available funds did not decrement properly");
        assert.equal(n, 1, "next proposal id did not increment properly");

    });

    it("should check if investor can vote on invalid proposal", async () => {

        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
      
        await truffleAssert.reverts(
            DAOInstance.vote(0, {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if only investor can vote", async () => {

        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
      
        await DAOInstance.contribute({from: account6, value: web3.utils.toWei("3", "finney")});
        
        await DAOInstance.createProposal("proposal 1", account7, web3.utils.toWei("5", "finney"), {from: account6});

        await truffleAssert.reverts(
            DAOInstance.vote(0, {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if investor can vote twice", async () => {

        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
      
        await DAOInstance.contribute({from: account6, value: web3.utils.toWei("3", "finney")});
        
        await DAOInstance.createProposal("proposal 1", account7, web3.utils.toWei("5", "finney"), {from: account6});

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        await DAOInstance.vote(0, {from: account2});

        await truffleAssert.reverts(
            DAOInstance.vote(0, {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if investor can vote after deadline expires", async () => { 
        
        const _30days = 2592000; 

        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
      
        await DAOInstance.contribute({from: account6, value: web3.utils.toWei("3", "finney")});
        
        await DAOInstance.createProposal("proposal 1", account7, web3.utils.toWei("5", "finney"), {from: account6});

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        increaseTime(_30days + 1000);

        await truffleAssert.reverts(
            DAOInstance.vote(0, {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if event voted was emitted properly", async () => {

        await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
      
        await DAOInstance.contribute({from: account6, value: web3.utils.toWei("3", "finney")});
        
        await DAOInstance.createProposal("proposal 1", account7, web3.utils.toWei("5", "finney"), {from: account6});

        const votes_before = (await DAOInstance.getProposal(0))[2];
        
        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        const tx = await DAOInstance.vote(0, {from: account2});

        const event = tx.logs.find(log => log.event === "Voted");

        assert.equal(event.args._voter, account2, "Sender address is incorrect");
        assert.equal(event.args._proposalId, 0, "Proposal Id is incorrect");
        assert.equal(votes_before, 0, "Initial votes counter is incorrect");
        assert.equal(event.args._votes, web3.utils.toWei("3", "finney"), "Votes is incorrect");

        const hv = await DAOInstance.checkVoted(0, {from: account2});

        assert.equal(hv, true, "Investor vote is not true");
    
    });

    it("should check if only investor can execute proposal", async () => {

        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
      
        await DAOInstance.contribute({from: account6, value: web3.utils.toWei("3", "finney")});
        
        await DAOInstance.createProposal("proposal 1", account7, web3.utils.toWei("5", "finney"), {from: account6});

        await truffleAssert.reverts(
            DAOInstance.executeProposal(0, {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if investor can vote before deadline expires", async () => { 
        
        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
      
        await DAOInstance.contribute({from: account6, value: web3.utils.toWei("3", "finney")});
        
        await DAOInstance.createProposal("proposal 1", account7, web3.utils.toWei("5", "finney"), {from: account6});

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        await DAOInstance.vote(0, {from: account2});

        await truffleAssert.reverts(
            DAOInstance.executeProposal(0, {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if investor can't execute proposal with inferior quorum", async () => { 
        
        await DAOInstance.contribute({from: account6, value: web3.utils.toWei("5", "finney")});
        
        await DAOInstance.createProposal("proposal 1", account7, web3.utils.toWei("5", "micro"), {from: account6});

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("3", "finney")});

        await DAOInstance.vote(0, {from: account2});

        await truffleAssert.reverts(
            DAOInstance.executeProposal(0, {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if only investor can execute proposal that is already executed", async () => {


        const _30days = 2592000; 

        await DAOInstance.contribute({from: account6, value: web3.utils.toWei("1", "finney")});
        
        await DAOInstance.createProposal("proposal 1", account8, web3.utils.toWei("5", "micro"), {from: account6});

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("5", "finney")});

        await DAOInstance.vote(0, {from: account2});

        increaseTime(_30days + 1000);

        await DAOInstance.executeProposal(0, {from: account2});

        await truffleAssert.reverts(
            DAOInstance.executeProposal(0, {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if investor can execute proposal with enough quorum", async () => { 
        
        const _30days = 2592000; 

        await DAOInstance.contribute({from: account6, value: web3.utils.toWei("1", "finney")});
        
        await DAOInstance.createProposal("proposal 1", account8, web3.utils.toWei("5", "micro"), {from: account6});

        await DAOInstance.contribute({from: account2, value: web3.utils.toWei("5", "finney")});

        await DAOInstance.vote(0, {from: account2});

        increaseTime(_30days + 1000);

        const tx = await DAOInstance.executeProposal(0, {from: account2});

        const event = tx.logs.find(log => log.event === "ExecutedProposal");

        assert.equal(event.args._caller, account2, "Caller address is incorrect");
        assert.equal(event.args._proposalId, 0, "Proposal Id is incorrect");
        assert.equal(event.args._percentage, 83, "Percentage is incorrect");
        assert.equal(event.args._amount, web3.utils.toWei("5", "micro"), "Amount is incorrect");
        assert.equal(event.args._recipient, account8, "Recipient is incorrect");
        // ignore timestamp.

    });

    it("should check if only admin can withdraw ether", async () => {

        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
    
        await truffleAssert.reverts(
            DAOInstance.withdrawEther(web3.utils.toWei("3", "finney"), account4, {from: account2}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if admin can withdraw ether more ether than available", async () => {

        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
        
        await truffleAssert.reverts(
            DAOInstance.withdrawEther(web3.utils.toWei("8", "finney"), account4, {from: account1}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

    it("should check if admin can withdraw ether more ether than available", async () => {

        const tx = await web3.eth.sendTransaction({
            from: accounts[7],
            to: DAOInstance.address,
            value: web3.utils.toWei("5", "finney"),
        });
    
        await truffleAssert.reverts(
            DAOInstance.withdrawEther(web3.utils.toWei("3", "finney"), account4, {from: account1}),
            null  // We can't decode custom error messages, so we check just for revert
        );

    });

});
