const EtherWallet = artifacts.require("EtherWallet");
const truffleAssert = require('truffle-assertions');

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("EtherWallet", (accounts) => {

    let EtherWalletInstance;
    const [account1, account2, account3, account4] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        EtherWalletInstance = await EtherWallet.new(account1);
    
    });

    it("should get initial balance as 0", async () => {
       
        const balance = await EtherWalletInstance.getBalance({from: account1});
        
        //await AdvancedStorageInstance.setData("Data from Account 2", { from: account2 });

        assert.equal(balance, 0, "Initial balance mismatch");
        
    });

    it("should deposit 2 ethers to contract", async () => {
   
        const tx = await EtherWalletInstance.deposit({from: account2, value: web3.utils.toWei("2", "ether")});
    
        assert.equal(tx.receipt.status, true, "Transaction failed");
    
    });

    it("should check if balance is 2 ethers", async () => {
   
        await EtherWalletInstance.deposit({from: account2, value: web3.utils.toWei("2", "ether")});

        const balance = await EtherWalletInstance.getBalance({from: account1});

        assert.equal(balance, web3.utils.toWei("2", "ether"), "Deposit failed or balance mismatch");
   
    });
 
    it("should withdraw 1 ether", async () => {
        
        await EtherWalletInstance.deposit({from: account2, value: web3.utils.toWei("2", "ether")});

        await EtherWalletInstance.withdraw(web3.utils.toWei("1", "ether"), {from: account1});
        
        const balance = await EtherWalletInstance.getBalance({from: account1});

        assert.equal(balance, web3.utils.toWei("1", "ether"), "Deposit is 1 ether and withdrawed 1 ether");
   
    });
    
    it("should try to withdraw 1 ether from account that is not the owner", async () => {
        
        await EtherWalletInstance.deposit({from: account2, value: web3.utils.toWei("2", "ether")});

        await truffleAssert.reverts(
            EtherWalletInstance.withdraw(web3.utils.toWei("1", "ether"), {
                from: account2
            }),
            "Not the contract owner"
        );
      
        const balance = await EtherWalletInstance.getBalance({from: account2});

        assert.equal(balance, web3.utils.toWei("2", "ether"), "withdrawed 0 ether");
   
    });

    it("should send 1 ether", async () => {
        
        await EtherWalletInstance.deposit({from: account3, value: web3.utils.toWei("2", "ether")});

        await EtherWalletInstance.send(account4, web3.utils.toWei("1", "ether"), {from: account1});
        
        const balance = await EtherWalletInstance.getBalance({from: account1});

        assert.equal(balance, web3.utils.toWei("1", "ether"), "Sent 0 ether");
   
    });
    
    it("should try to send 1 ether from account that is not the owner", async () => {
        
        await EtherWalletInstance.deposit({from: account2, value: web3.utils.toWei("2", "ether")});

        await truffleAssert.reverts(
            EtherWalletInstance.send(account4, web3.utils.toWei("1", "ether"), {
                from: account2
            }),
            "Not the contract owner"
        );
      
        const balance = await EtherWalletInstance.getBalance({from: account2});

        assert.equal(balance, web3.utils.toWei("2", "ether"), "withdrawed 0 ether");
   
    });
    
});