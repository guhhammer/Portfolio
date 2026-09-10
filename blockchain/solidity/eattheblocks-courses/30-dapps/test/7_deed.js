const Deed = artifacts.require("Deed");
const truffleAssert = require("truffle-assertions");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("Deed", (accounts) => {

    const [account1, account2] = accounts;

    const _10secondsFromNow = 10;
    const _1hourFromNow = 3600;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
    
    });

    it("should revert when non-lawyer tries to withdraw", async () => {
        
        const DeedInstance = await Deed.new(account1, account2, _10secondsFromNow, 
        {from: account1, value: web3.utils.toWei("2", "ether")});
    
        // Try to withdraw from a non-lawyer account (account2)
        await truffleAssert.reverts(DeedInstance.withdraw({ from: account2 }), "Only lawyer can withdraw");

    });
    
    it("should revert if lawyer tries to send it to early", async () => {

        const DeedInstance = await Deed.new(account1, account2, _1hourFromNow, 
        {from: account1, value: web3.utils.toWei("2", "ether")});
    
        // Try to withdraw from a non-lawyer account (account2)
        await truffleAssert.reverts(DeedInstance.withdraw({ from: account1 }), "Too early to withdraw");

    });
    
    function wait(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }
    
    it("should increase beneficiary balance after 0 seconds and withdrawal", async () => {
        
        const DeedInstance = await Deed.new(account1, account2, 0, {
            from: account1,
            value: web3.utils.toWei("1", "ether"),
        });
    
        const initialBalance = web3.utils.toBN(await web3.eth.getBalance(account2));
    
        await DeedInstance.withdraw({ from: account1 });
    
        const finalBalance = web3.utils.toBN(await web3.eth.getBalance(account2));
        const difference = finalBalance.sub(initialBalance);
    
        assert.equal(difference.toString(), web3.utils.toWei("1", "ether"), "Balance did not increase by 1 ether");

    }); // my project is having conflicts with time imports.
    
});