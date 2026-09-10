const MultiSigWallet = artifacts.require("MultiSigWallet");
const truffleAssert = require("truffle-assertions");

const { ZERO_ADDRESS, takeSnapshot, revertToSnapshot } = require("./helpers");

contract("MultiSigWallet", (accounts) => {

    let MultiSigWalletInstance;

    const [account1, account2, account3, account4, account5] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        MultiSigWalletInstance = await MultiSigWallet.new( [account1, account2, account3], 2, {
            from: account1,
            value: web3.utils.toWei("10", "ether"),
        });

    });

    it("should check if contract is deployed", async () => {
       
        assert(MultiSigWalletInstance.address !== "", "Contract not deployed");
    
    });


    it("should check if a non-approver can create a transfer", async () => {

        await truffleAssert.reverts(MultiSigWalletInstance.createTransfer(web3.utils.toWei("1", "ether"), account4, { from: account5 }), "Only approvers can call this function");

    });

    it("should check if an approver can create a transfer", async () => {

        await MultiSigWalletInstance.createTransfer(web3.utils.toWei("2", "ether"), account4, { from: account1 });
        
        await MultiSigWalletInstance.transfers(0).then((transfer) => {
            
            assert.equal(transfer.id.toString(), "0", "Transfer ID should be 0");
            assert.equal(transfer.amount.toString(), web3.utils.toWei("2", "ether"), "Transfer amount should be 2 ether");
            assert.equal(transfer.to, account4, "Transfer recipient should be account4");
            assert.equal(transfer.approvals.toString(), "0", "Approvals counter should be 0");
            assert.equal(transfer.sent, false, "Transfer should not be sent yet");

        });

    });

    it("should check if approvers and quorum are defined properly", async () => {

        await MultiSigWalletInstance.getApprovers().then((approvers) => {
            
            assert.equal(approvers.length, 3, "There should be 3 approvers");
            assert.equal(approvers[0], account1, "First approver should be account1");
            assert.equal(approvers[1], account2, "Second approver should be account2");
            assert.equal(approvers[2], account3, "Third approver should be account3");
      
        });

        await MultiSigWalletInstance.quorum().then((quorum) => {
      
            assert.equal(quorum.toString(), "2", "Quorum should be 2");
      
        });

    });

    it("should retrieve nextId before and after transfer are created", async () => {

        await MultiSigWalletInstance.nextId().then((nextId) => {
    
            assert.equal(nextId.toString(), "0", "Next ID should be 0 before any transfer is created");
    
        });

        await MultiSigWalletInstance.createTransfer(web3.utils.toWei("2", "ether"), account4, { from: account1 });
        
        await MultiSigWalletInstance.nextId().then((nextId) => {
    
            assert.equal(nextId.toString(), "1", "Next ID should be 1 after one transfer is created");
    
        });
        
        await MultiSigWalletInstance.createTransfer(web3.utils.toWei("4", "ether"), account4, { from: account2 });
        
        await MultiSigWalletInstance.nextId().then((nextId) => {
    
            assert.equal(nextId.toString(), "2", "Next ID should be 2 after two transfers are created");
    
        });

        let unused_id = await MultiSigWalletInstance.nextId();

        
        
        await MultiSigWalletInstance.transfers(unused_id).then((transfer) => { 
       
            assert.equal(transfer.id.toString(), "0", "Transfer ID should default to 0");
            assert.equal(transfer.amount.toString(), 0, "Transfer amount should default to 0");
            assert.equal(transfer.to, ZERO_ADDRESS, "Transfer recipient should be address 0");
            assert.equal(transfer.approvals.toString(), "0", "Approvals counter should default to 0");
            assert.equal(transfer.sent, false, "Transfer should default to false");
       
        });

    });

    it("should check if all approvers have sent as false after transfer is created", async () => {

        await MultiSigWalletInstance.createTransfer(web3.utils.toWei("2", "ether"), account4, { from: account1 });
        
        await MultiSigWalletInstance.approvals(account1, 0).then((approval) => {

            assert.equal(approval, false, "Approver 1 should not have approved the transfer");

        });

        await MultiSigWalletInstance.approvals(account2, 0).then((approval) => {

            assert.equal(approval, false, "Approver 2 should not have approved the transfer");

        });

        await MultiSigWalletInstance.approvals(account3, 0).then((approval) => {

            assert.equal(approval, false, "Approver 3 should not have approved the transfer");

        });

    });

    it("should check if an approver can send a transfer", async () => { 

        await MultiSigWalletInstance.createTransfer(web3.utils.toWei("2", "ether"), account4, { from: account1 });

        await MultiSigWalletInstance.sendTransfer(0, { from: account1 });

        await MultiSigWalletInstance.approvals(account1, 0).then((approval) => {

            assert.equal(approval, true, "Approver 1 should have approved the transfer");

        });
        
    });

    it("should check if an quorum submit a transfer, if i can be sent twice and if balance of to changed", async () => { 

        await MultiSigWalletInstance.createTransfer(web3.utils.toWei("2", "ether"), account4, { from: account1 });

        let before = await web3.eth.getBalance(account4);

        await MultiSigWalletInstance.sendTransfer(0, { from: account1 });

        await MultiSigWalletInstance.sendTransfer(0, { from: account2 });
        
        await MultiSigWalletInstance.transfers(0).then((transfer) => {

            assert.equal(transfer.sent, true, "Transfer should be sent");
            assert.equal(transfer.approvals.toString(), "2", "Approvals counter should be 2");
        
        });

        let after = await web3.eth.getBalance(account4);

        assert.equal((after - before).toString(), web3.utils.toWei("2", "ether").toString(), "Balance of to should be equal to amount");

    });
     
});
