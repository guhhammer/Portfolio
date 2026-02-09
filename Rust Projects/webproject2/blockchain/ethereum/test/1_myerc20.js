const MyERC20 = artifacts.require("MyERC20");
const truffleAssert = require("truffle-assertions");

const { takeSnapshot, revertToSnapshot } = require("./helpers");

contract("MyERC20", (accounts) => {

    let MyERC20Instance;

    const [account1, account2, account3] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        MyERC20Instance = await MyERC20.new("Artemis", "ATS", 18, 100_000_000_000, {from: accounts[0]});

    });

    it("should check if contract is deployed", async () => {
       
        assert(MyERC20Instance.address !== "", "Contract not deployed");
    
    });

    it("should check if all variables are set right", async () => {
 
        const name = await MyERC20Instance.name();
        const symbol = await MyERC20Instance.symbol();
        const decimals = await MyERC20Instance.decimals();
        const totalSupply = await MyERC20Instance.totalSupply();

        assert.equal(name, "Artemis", "Name is not set correctly");
        assert.equal(symbol, "ATS", "Symbol is not set correctly");
        assert.equal(decimals.toNumber(), 18, "Decimals are not set correctly");
        assert.equal(totalSupply.toString(), "100000000000000000000000000000", "Total supply is not set correctly");
 
    });

    it("should check balances and total supply", async () => {
 
        const balanceAccount1 = await MyERC20Instance.balanceOf(account1);
        const balanceAccount2 = await MyERC20Instance.balanceOf(account2);
        const totalSupply = await MyERC20Instance.totalSupply();

        assert.equal(balanceAccount1.toString(), "100000000000000000000000000000", "Initial balance of account1 is incorrect");
        assert.equal(balanceAccount2.toString(), "0", "Initial balance of account2 is incorrect");
        assert.equal(totalSupply.toString(), "100000000000000000000000000000", "Total supply is incorrect");
 
    });

    it("should transfer tokens from account1 to account2", async () => {
        const transferAmount = web3.utils.toWei("1000", "ether");

        const after = web3.utils.toWei("99999999000", "ether");
        
        // Perform the transfer
        await MyERC20Instance.transfer(account2, transferAmount, { from: account1 });

        // Check balances after transfer
        const balanceAccount1 = await MyERC20Instance.balanceOf(account1);
        const balanceAccount2 = await MyERC20Instance.balanceOf(account2);

        assert.equal(balanceAccount1.toString(), after, "Balance of account1 is incorrect after transfer");
        assert.equal(balanceAccount2.toString(), transferAmount, "Balance of account2 is incorrect after transfer");
    });

    it("should approve account3 to spend on behalf of account1", async () => {
        const approveAmount = 5000;

        // Approve account3 to spend on behalf of account1
        await MyERC20Instance.approve(account3, approveAmount, { from: account1 });

        // Check the allowance
        const allowance = await MyERC20Instance.allowance(account1, account3);

        assert.equal(allowance.toString(), approveAmount.toString(), "Allowance is not set correctly");
    });

    it("should check allowance function", async () => {
        const approveAmount = 3000;

        // Approve account3 to spend on behalf of account1
        await MyERC20Instance.approve(account3, approveAmount, { from: account1 });

        // Check the allowance using the allowance function
        const allowance = await MyERC20Instance.allowance(account1, account3);

        assert.equal(allowance.toString(), approveAmount.toString(), "Allowance function did not return the correct value");
    });

    it("should allow account3 to transfer tokens from account1 to account2", async () => {
        const approveAmount = web3.utils.toWei("5000", "ether");
        const transferAmount = web3.utils.toWei("3000", "ether");

        // Approve account3 to spend on behalf of account1
        await MyERC20Instance.approve(account3, approveAmount, { from: account1 });

        // Perform the transferFrom by account3
        await MyERC20Instance.transferFrom(account1, account2, transferAmount, { from: account3 });

        // Check balances after transfer
        const balanceAccount1 = await MyERC20Instance.balanceOf(account1);
        const balanceAccount2 = await MyERC20Instance.balanceOf(account2);
        const allowance = await MyERC20Instance.allowance(account1, account3);

        assert.equal(balanceAccount1.toString(), web3.utils.toWei("99999997000", "ether"), "Balance of account1 is incorrect after transferFrom");
        assert.equal(balanceAccount2.toString(), transferAmount, "Balance of account2 is incorrect after transferFrom");
        assert.equal(allowance.toString(), web3.utils.toWei("2000", "ether"), "Allowance is not updated correctly after transferFrom");
    });
    
});
