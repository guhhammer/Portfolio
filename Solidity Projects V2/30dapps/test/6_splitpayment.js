const SplitPayment = artifacts.require("SplitPayment");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("SplitPayment", (accounts) => {

    let SplitPaymentInstance;
    const [account1, account2, account3, account4] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        SplitPaymentInstance = await SplitPayment.new();
    
    });

    it("should send 6 ethers to 3 addresses in a single function call", async () => {
    
        const amount1 = web3.utils.toWei("1", "ether");
        const amount2 = web3.utils.toWei("3", "ether");
        const amount3 = web3.utils.toWei("2", "ether");
    
        const beforeBalance2 = web3.utils.toBN(await web3.eth.getBalance(account2));
        const beforeBalance3 = web3.utils.toBN(await web3.eth.getBalance(account3));
        const beforeBalance4 = web3.utils.toBN(await web3.eth.getBalance(account4));
    
        const tx = await SplitPaymentInstance.send(
            [account2, account3, account4],
            [amount1, amount2, amount3],
            {from: account1, value: web3.utils.toWei("6", "ether")}
        );
    
        assert.equal(tx.receipt.status, true, "Transaction failed");
    
        const afterBalance2 = web3.utils.toBN(await web3.eth.getBalance(account2));
        const afterBalance3 = web3.utils.toBN(await web3.eth.getBalance(account3));
        const afterBalance4 = web3.utils.toBN(await web3.eth.getBalance(account4));
    
        assert.equal(afterBalance2.sub(beforeBalance2).toString(), amount1, "Account2 did not receive 1 ETH");
        assert.equal(afterBalance3.sub(beforeBalance3).toString(), amount2, "Account3 did not receive 3 ETH");
        assert.equal(afterBalance4.sub(beforeBalance4).toString(), amount3, "Account4 did not receive 2 ETH");
    
    });
      
});