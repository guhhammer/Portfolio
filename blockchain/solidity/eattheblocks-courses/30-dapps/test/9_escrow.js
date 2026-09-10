const Escrow = artifacts.require("Escrow");
const truffleAssert = require("truffle-assertions");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("Escrow", (accounts) => {

    let EscrowInstance;

    const [account1, account2, account3, account4] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
    
        
        EscrowInstance = await Escrow.new(account2, account3, web3.utils.toWei("5", "ether"), {
            from: account1,
        });

    });

    it("should check if balance is 0 at deploy", async () => {

        assert.equal(await EscrowInstance.balanceOf(), 0, "Balance should be 0 at deploy");
        
    });

    it("should check if payer can deposit whatever value", async () => {

        await EscrowInstance.deposit({from: account2, value: web3.utils.toWei("2", "ether")});

        assert.equal(await EscrowInstance.balanceOf(), web3.utils.toWei("2", "ether"), "Balance should be 2 ether after deposit");
        
    });

    it("should check if non-payer account can deposit whatever value", async () => {

        await truffleAssert.reverts(EscrowInstance.deposit({from: account4, value: web3.utils.toWei("2", "ether")}), "Only payer can deposit");
        
    });

    it("should check if lawyer can refund payer whatever value below defined amount", async () => {

        await EscrowInstance.deposit({from: account2, value: web3.utils.toWei("2", "ether")});

        const tx = await EscrowInstance.refund({from: account1});

       // assert.equal(tx, true, "Refund should be successful");
        assert.equal(await EscrowInstance.balanceOf(), 0, "Balance should be 0 after refund");

    });

    it("should check if other account can refund payer", async () => {

        await EscrowInstance.deposit({from: account2, value: web3.utils.toWei("2", "ether")});

        await truffleAssert.reverts(EscrowInstance.refund({from: account3}), "Only lawyer can refund");

    });

    it("should check if lawyer can refund payer when balance is greather than the amount", async () => {

        await EscrowInstance.deposit({from: account2, value: web3.utils.toWei("6", "ether")});

        await truffleAssert.reverts(EscrowInstance.refund({from: account1}), "Cannot refund if balance is sufficient");

    });

    it("should check if non-lawyer account can release amount", async () => {

        await EscrowInstance.deposit({from: account2, value: web3.utils.toWei("3", "ether")});

        await truffleAssert.reverts(EscrowInstance.release({from: account4}), "Only lawyer can release funds");

    });

    it("should check if lawyer cannot release insufficient balance", async () => {

        await EscrowInstance.deposit({from: account2, value: web3.utils.toWei("3", "ether")});

        await truffleAssert.reverts(EscrowInstance.release({from: account1}), "Insufficient balance");

    });

    it("should check if lawyer can release balance that is equal to amount and balance of payee", async () => {

        await EscrowInstance.deposit({from: account2, value: web3.utils.toWei("5", "ether")});

        let before = web3.utils.toBN(await web3.eth.getBalance(account3));

        await EscrowInstance.release({from: account1});

        let after = web3.utils.toBN(await web3.eth.getBalance(account3));

        assert.equal(after.sub(before).toString(), web3.utils.toWei("5", "ether"), "Balance of payee should be equal to amount");

    });

    it("should check if release splits funds correctly", async () => {
    
        const depositAmount = web3.utils.toWei("8", "ether");
        const refundAmount = web3.utils.toWei("3", "ether");
        const payeeAmount = web3.utils.toWei("5", "ether");
    
        // Deposit
        await EscrowInstance.deposit({ from: account2, value: depositAmount });
    
        const beforePayee = web3.utils.toBN(await web3.eth.getBalance(account3));
        const beforeContract = web3.utils.toBN(await web3.eth.getBalance(EscrowInstance.address));
    
        // Release
        await EscrowInstance.release({ from: account1 });
    
        const afterPayee = web3.utils.toBN(await web3.eth.getBalance(account3));
        const afterContract = web3.utils.toBN(await web3.eth.getBalance(EscrowInstance.address));
    
        const actualPayeeGain = afterPayee.sub(beforePayee);
        const actualContractBalance = afterContract;
    
        assert.equal(
            actualPayeeGain.toString(),
            payeeAmount,
            "Payee should receive exactly 5 ether"
        );
    
        assert.equal(
            actualContractBalance.toString(),
            "0",
            "Contract should have zero balance after release"
        );
    
    });
     
});
