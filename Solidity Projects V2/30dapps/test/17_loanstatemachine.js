const LoanStateMachine = artifacts.require("LoanStateMachine");
const truffleAssert = require("truffle-assertions");

const {increaseTime, takeSnapshot, revertToSnapshot} = require("./helpers");

contract("LoanStateMachine", (accounts) => {

    let LoanStateMachineInstance;

    const [account1, account2, account3] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        LoanStateMachineInstance = await LoanStateMachine.new(
            web3.utils.toWei("5", "ether"),
            web3.utils.toWei("800", "finney"),
            3600,
            account2,   // borrower.
            account1,   // lender.
            {from: account1}
        );

    });

    it("should check if contract is deployed", async () => {
       
        assert(LoanStateMachineInstance.address !== "", "Contract not deployed");
    
    });

    it("should check if only lender can fund the contract", async () => {
       
        await truffleAssert.reverts(LoanStateMachineInstance.fund({from: account3, value: web3.utils.toWei("5", "ether")}), "only lender can lend");
    
    });

    it("should check if lender can lend more than specified amount", async () => {
       
        await truffleAssert.reverts(LoanStateMachineInstance.fund({from: account1, value: web3.utils.toWei("6", "ether")}), "cannot lend more than amount");
    
    });

    it("should check if state is active and borrow was made by lender", async () => {
        
        const before = await web3.eth.getBalance(account2);

        await LoanStateMachineInstance.fund({from: account1, value: web3.utils.toWei("5", "ether")});

        const state = await LoanStateMachineInstance.state();

        const after = await web3.eth.getBalance(account2);

        assert.equal(state, 1, "state is incorrect");

        assert.equal(after - before, web3.utils.toWei("5", "ether"), "Amount borrowed is incorrect");
 
    });

    it("should check if only borrower can reimburse", async () => {
       
        await LoanStateMachineInstance.fund({from: account1, value: web3.utils.toWei("5", "ether")});

        await truffleAssert.reverts(LoanStateMachineInstance.reimburse({from: account3, value: web3.utils.toWei("5", "ether")}), "only borrower can reimburse");
    
    });

    it("should check if borrower can reimburse wrong amount", async () => {

        await LoanStateMachineInstance.fund({from: account1, value: web3.utils.toWei("5", "ether")});
       
        await truffleAssert.reverts(LoanStateMachineInstance.reimburse({from: account2, value: web3.utils.toWei("5", "ether")}), "borrower need to reimburse exactly amount + interest");
    
    });

    it("should check if borrower can reimburse lender the exact balance before loan maturing", async () => {
       
        await LoanStateMachineInstance.fund({from: account1, value: web3.utils.toWei("5", "ether")});

        await truffleAssert.reverts(
            LoanStateMachineInstance.reimburse({from: account2, value: web3.utils.toWei("5800", "finney")}),
            "loan hasn't matured yet"
        );

    });

    it("should check if borrower can reimburse lender the exact balance before loan maturing", async () => {
       
        await LoanStateMachineInstance.fund({from: account1, value: web3.utils.toWei("5", "ether")});

        const before = await web3.eth.getBalance(account1);

        await increaseTime(3800);

        await LoanStateMachineInstance.reimburse({from: account2, value: web3.utils.toWei("5800", "finney")});
        
        const after = await web3.eth.getBalance(account1);

        const state = await LoanStateMachineInstance.state();

        assert.equal(state, 2, "State is not CLOSED");
        assert.equal(after-before, web3.utils.toWei("5800", "finney"), "wrong balance reimbursed");

    });

});
