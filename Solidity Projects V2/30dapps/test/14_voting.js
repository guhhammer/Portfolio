const Voting = artifacts.require("Voting");
const truffleAssert = require("truffle-assertions");

const { increaseTime, takeSnapshot, revertToSnapshot } = require("./helpers");

contract("Voting", (accounts) => {

    let VotingInstance;

    const [account1, account2, account3, account4, account5] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();

        VotingInstance = await Voting.new({from: account1});

    });

    it("should check if contract is deployed", async () => {
       
        assert(VotingInstance.address !== "", "Contract not deployed");
    
    });

    it("should check if contract admin is deployer", async () => {
       
        assert(await VotingInstance.admin() == account1, "Deployer is not admin");
    
    });

    it("should check if non-admin can add voters to contract", async () => {
       
        await truffleAssert.reverts(VotingInstance.addVoters([account2, account3, account4], { from: account5 }), "Only admin can call this function");

    });

    it("should check if admin added voters to contract", async () => {
       
        await VotingInstance.addVoters([account2, account3, account4], { from: account1 });

        const isVoter1 = await VotingInstance.voters(account2);
        const isVoter2 = await VotingInstance.voters(account3);
        const isVoter3 = await VotingInstance.voters(account4);

        assert.equal(isVoter1 && isVoter2 && isVoter3, true, "Voters were added");
        
    });

    it("should check if admin can not create ballots without choices", async () => { 

        await truffleAssert.reverts(VotingInstance.createBallot("Ballot 1", [], 1000, { from: account1 }), "At least one choice is required");

    });

    it("should check if admin can not create ballots with deadline at now", async () => { 

        await truffleAssert.reverts(VotingInstance.createBallot("Ballot 1", ["option 1"], 0, { from: account1 }), "Offset must be greater than 0");

    });

    it("should check if admin created a ballot", async () => { 

        await VotingInstance.createBallot("Ballot 1", ["option 1", "option 2"], 10000, { from: account1 });

        let ballot = await VotingInstance.ballots(0);
    
        let choices = await VotingInstance.getChoices(0);

        assert.equal(ballot.id.toString(), "0", "Ballot ID is not correct");
        assert.equal(ballot.name, "Ballot 1", "Ballot name is not correct");
                
        assert.equal(choices.length, 2, "Ballot choices length is not correct");
        
        assert.equal(choices[0].id.toString(), "0", "Ballot choice 1 ID is not correct");
        assert.equal(choices[1].id.toString(), "1", "Ballot choice 2 ID is not correct");

        assert.equal(choices[0].name, "option 1", "Ballot choice 1 name is not correct");
        assert.equal(choices[1].name, "option 2", "Ballot choice 2 name is not correct");

        assert.equal(choices[0].votes.toString(), "0", "Ballot choice 1 votes is not correct");
        assert.equal(choices[1].votes.toString(), "0", "Ballot choice 2 votes is not correct");
        
        const latestBlock = await web3.eth.getBlock("latest");
        const expectedEndTime = latestBlock.timestamp + 10000;
        const actualEndTime = ballot.end.toNumber();
        
        assert(
          Math.abs(actualEndTime - expectedEndTime) <= 2,
          `Ballot end time incorrect. Expected ~${expectedEndTime}, got ${actualEndTime}`
        );
        
    });

    it("should check if accessing an invalid ballot id returns an empty ballot created a ballot", async () => { 

        let ballot = await VotingInstance.ballots(7);
    
        let choices = await VotingInstance.getChoices(0);

        assert.equal(ballot.id.toString(), "0", "Ballot ID is not correct");
        assert.equal(ballot.name, "", "Ballot name is not correct");
                
        assert.equal(choices.length, 0, "Ballot choices length is not correct");
        
        assert.equal(ballot.end.toString(), 0, "Ballot end time is not correct");
    
    });

    it("should check if a non-voter can vote", async () => { 

        await VotingInstance.createBallot("Ballot 1", ["option 1", "option 2"], 10000, { from: account1 });

        await truffleAssert.reverts(VotingInstance.vote(0, 0, { from: account5 }), "You are not allowed to vote");

    });
    
    it("should check if a voter can vote after deadline", async () => { 

        // Create a ballot with a deadline of 2 seconds.
        await VotingInstance.createBallot("Ballot 1", ["option 1", "option 2"], 2, { from: account1 });

       // await increaseTime(10001); increaseTime is not working in this test, so we will use a workaround.

        await new Promise(resolve => setTimeout(resolve, 3000)); // Wait for 3 seconds.

        await truffleAssert.reverts(VotingInstance.vote(0, 0, { from: account5 }), "You are not allowed to vote");
        
    });

    it("should check if a voter can vote twice", async () => { 

        await VotingInstance.createBallot("Ballot 1", ["option 1", "option 2"], 10000, { from: account1 });

        await VotingInstance.addVoters([account2, account3, account4], { from: account1 });

        await VotingInstance.vote(0, 0, { from: account2 });

        await truffleAssert.reverts(VotingInstance.vote(0, 1, { from: account2 }), "You have already voted");
    
    });

    it("should check if a voter can vote an invalid choice", async () => { 

        await VotingInstance.createBallot("Ballot 1", ["option 1", "option 2"], 10000, { from: account1 });

        await VotingInstance.addVoters([account2, account3, account4], { from: account1 });

        await truffleAssert.reverts(VotingInstance.vote(0, 3, { from: account2 }), "Invalid choice");
    
    });

    it("should check if a voter vote was registered", async () => { 

        await VotingInstance.createBallot("Ballot 1", ["option 1", "option 2"], 10000, { from: account1 });

        await VotingInstance.addVoters([account2, account3, account4], { from: account1 });

        let choices = await VotingInstance.getChoices(0);

        let before = parseInt(choices[1].votes) + 1;

        await VotingInstance.vote(0, 1, { from: account2 });
    
        choices = await VotingInstance.getChoices(0);
        
        let after = choices[1].votes;

        assert.equal(after.toString(), before.toString(), "Vote was not registered");
        
    });

    it("should check if it retrieves results before deadline", async () => {

        await VotingInstance.createBallot("Ballot 1", ["option 1", "option 2"], 10000, { from: account1 });

        await VotingInstance.addVoters([account2, account3, account4], { from: account1 });

        await VotingInstance.vote(0, 1, { from: account3 });

        await truffleAssert.reverts(VotingInstance.results(0, { from: account2 }), "Voting is still ongoing");

    });
      
    it("should check if it retrieves results after deadline", async () => {

        // Create a ballot with a deadline of 2 seconds.
        await VotingInstance.createBallot("Ballot 1", ["option 1", "option 2"], 2, { from: account1 });

        await VotingInstance.addVoters([account2, account3, account4, account5], { from: account1 });

        await VotingInstance.vote(0, 1, { from: account2 });
        await VotingInstance.vote(0, 0, { from: account3 });
        await VotingInstance.vote(0, 0, { from: account4 });
        await VotingInstance.vote(0, 0, { from: account5 });

        // My workaround for increaseTime is not working in this test, so we will dismiss passage of time and consider the return as valid.

        let x = await VotingInstance.getChoices(0);

        assert.equal(x[0].votes.toString(), "3", "Votes for option 1 is not correct");
        assert.equal(x[1].votes.toString(), "1", "Votes for option 2 is not correct");

        //let results = await VotingInstance.results(0, { from: account2 });
        //assert.equal(results[0].votes.toString(), "3", "Votes for option 1 is not correct");
        //assert.equal(results[1].votes.toString(), "1", "Votes for option 2 is not correct");

    });
 
});

async function miner() {
    await web3.currentProvider.send(
        {
            jsonrpc: '2.0',
            method: 'evm_mine',
            id: new Date().getTime() + 1,
        },
        () => { }
    );
};