const ICO = artifacts.require("ICO");
const truffleAssert = require("truffle-assertions");

const {increaseTime, ZERO_ADDRESS, takeSnapshot, revertToSnapshot} = require("./helpers");

contract("ICO", (accounts) => {

    let ICOInstance;

    const [account1, account2, account3, account4] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        ICOInstance = await ICO.new("Artemis", "ATS", 18, 100_000_000_000, {from: accounts[0]});

    });

    it("should check if contract is deployed", async () => {
       
        assert(ICOInstance.address !== "", "Contract not deployed");
    
    });

    it("should check if all variables are set right", async () => {
 
        const n = await ICOInstance.name();
        const s = await ICOInstance.symbol();
        const d = await ICOInstance.decimals();
        const t = await ICOInstance.totalSupply();

        const a = await ICOInstance.admin();

        assert.equal(n, "Artemis", "Name is not set correctly");
        assert.equal(s, "ATS", "Symbol is not set correctly");
        assert.equal(d.toNumber(), 18, "Decimals are not set correctly");
        assert.equal(t.toString(), "100000000000000000000000000000", "Total supply is not set correctly");

        assert.equal(a, account1, "Admin is not set to account1");
        
        assert(ICOInstance.token() !== ZERO_ADDRESS, "Token address is not set correctly");

    });

    it("should start the ICO with valid parameters", async () => {
       
        await ICOInstance.start(
            1000, 
            web3.utils.toWei("1", "wei"), 
            80_000_000_000, 
            50_000, 
            2_000_000, 
            { from: account1 }
        );

        const end = await ICOInstance.end();
        const price = await ICOInstance.price();
        const availableTokens = await ICOInstance.availableTokens();
        const minPurchase = await ICOInstance.minPurchase();
        const maxPurchase = await ICOInstance.maxPurchase();

        assert(end > 0, "End time not set correctly");
        assert.equal(price.toString(), web3.utils.toWei("1", "wei"), "Price not set correctly");
        assert.equal(availableTokens.toString(), "80000000000", "Available tokens not set correctly");
        assert.equal(minPurchase.toString(), "50000", "Min purchase not set correctly");
        assert.equal(maxPurchase.toString(), "2000000", "Max purchase not set correctly");
    
    });

    it("should not allow account2 to start the ICO", async () => {
      
        await truffleAssert.reverts(
            ICOInstance.start(
            1000, 
            web3.utils.toWei("1", "wei"), 
            80_000_000_000, 
            50_000, 
            2_000_000, 
            { from: account2 }
            ),
            "Only admin"
        );
  
    });
  
    it("should not allow starting the ICO with invalid duration", async () => {
    
        await truffleAssert.reverts(
            ICOInstance.start(
                0, // Invalid duration
                web3.utils.toWei("1", "wei"),
                80_000_000_000,
                50_000,
                2_000_000,
                { from: account1 }
            ),
            "Duration is not acceptable"
        );
    
    });

    it("should check if available amount is valid", async () => {
   
        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            2_000_000,
            { from: account1 }
        );

        const availableTokens = await ICOInstance.availableTokens();
        assert(availableTokens > 0, "Available tokens should be greater than 0");
        assert.equal(availableTokens.toString(), "80000000000", "Available tokens not set correctly");
   
    });

    it("should not allow starting the ICO with invalid min and max purchase", async () => {
    
        await truffleAssert.reverts(
            ICOInstance.start(
                1000,
                web3.utils.toWei("1", "wei"),
                80_000_000_000,
                0, // Invalid min purchase
                2_000_000,
                { from: account1 }
            ),
            "_minPurchase should be > 0"
        );

        await truffleAssert.reverts(
            ICOInstance.start(
                1000,
                web3.utils.toWei("1", "wei"),
                80_000_000_000,
                50_000,
                90_000_000_000, // Invalid max purchase
                { from: account1 }
            ),
            "_maxPurchase should be 0 < X <= _availableTokens"
        );
    
    });
    
    it("should allow admin to whitelist an investor", async () => {

        await ICOInstance.whitelist(account2, { from: account1 });

        const isWhitelisted = await ICOInstance.investors(account2);
        assert(isWhitelisted, "Investor was not whitelisted by admin");

    });

    it("should not allow non-admin to whitelist an investor", async () => {
  
        await truffleAssert.reverts(
            ICOInstance.whitelist(account3, { from: account2 }),
            "Only admin"
        );

        const isWhitelisted = await ICOInstance.investors(account3);
        assert(!isWhitelisted, "Non-admin was able to whitelist an investor");
   
    });

    it("should allow only whitelisted investors to buy", async () => {
        
        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            2_000_000,
            { from: account1 }
        );
        
        await ICOInstance.whitelist(account2, { from: account1 });

        // Whitelisted investor should be able to buy
        await ICOInstance.buy({ from: account2, value: web3.utils.toWei("500000", "wei") });

        const sales = await ICOInstance.sales(0);
        assert.equal(sales.investor, account2, "Whitelisted investor was not able to buy");

        // Non-whitelisted investor should not be able to buy
        await truffleAssert.reverts(
            ICOInstance.buy({ from: account3, value: web3.utils.toWei("500000", "wei") }),
            "msg.sender is not investor"
        );
    
    });

    it("should check if ICO is active", async () => {
        
        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            2_000_000,
            { from: account1 }
        );

        const isActive = await ICOInstance.end() > Math.floor(Date.now() / 1000);
        assert(isActive, "ICO is not active when it should be");
    
    });

    it("should not allow buying if there are no tokens left", async () => {
    
        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            80_000_000_000,
            { from: account1 }
        );
    
        // Whitelist an investor
        await ICOInstance.whitelist(account2, { from: account1 });
        await ICOInstance.whitelist(account3, { from: account1 });
    
        // Simulate buying all available tokens
        await ICOInstance.buy({ from: account2, value: web3.utils.toWei("80000000000", "wei") });
    
        // Attempt to buy tokens when no tokens are left
        await truffleAssert.reverts(
            ICOInstance.buy({ from: account3, value: web3.utils.toWei("500000", "wei") }),
            "No available tokens"
        );
  
    });

    it("should not allow buying if value does not divide evenly by price", async () => {
  
        await ICOInstance.start(
            1000,
            web3.utils.toWei("5", "wei"),
            80_000_000_000,
            50_000,
            2_000_000,
            { from: account1 }
        );
    
        // Whitelist an investor
        await ICOInstance.whitelist(account2, { from: account1 });
    
        // Attempt to buy with a value that cannot be evenly divided by price
        await truffleAssert.reverts(
            ICOInstance.buy({ from: account2, value: web3.utils.toWei("7", "wei") }),
            "cannot rightly share by price"
        );

    });

    it("should check if bid is acceptable", async () => {

        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            2_000_000,
            { from: account1 }
        );

        // Whitelist an investor
        await ICOInstance.whitelist(account2, { from: account1 });

        // Attempt to buy with a valid bid
        await ICOInstance.buy({ from: account2, value: web3.utils.toWei("500000", "wei") });

        const sales = await ICOInstance.sales(0);
        assert.equal(sales.investor, account2, "Valid bid was not accepted");

        // Attempt to buy with a bid below the minimum purchase
        await truffleAssert.reverts(
            ICOInstance.buy({ from: account2, value: web3.utils.toWei("10", "wei") }),
            "max >= b > min"
        );

        // Attempt to buy with a bid above the maximum purchase
        await truffleAssert.reverts(
            ICOInstance.buy({ from: account2, value: web3.utils.toWei("500", "ether") }),
            "max >= b > min"
        );

    });

    it("should not allow buying if there are not enough tokens left for the purchase", async () => {

        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            80_000_000_000,
            { from: account1 }
        );

        // Whitelist an investor
        await ICOInstance.whitelist(account2, { from: account1 });
        
        await ICOInstance.buy({ from: account2, value: web3.utils.toWei("70000000000", "wei") });
        
        // Attempt to buy more tokens than available
        await truffleAssert.reverts(
            ICOInstance.buy({ from: account2, value: web3.utils.toWei("15000000000", "wei") }),
            "Not Enough tokens left for sale"
        );

    });

    it("should check if a sale is made", async () => {
       
        // Start the ICO
        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            2_000_000,
            { from: account1 }
        );

        // Whitelist an investor
        await ICOInstance.whitelist(account2, { from: account1 });

        // Make a purchase
        await ICOInstance.buy({ from: account2, value: web3.utils.toWei("500000", "wei") });

        // Check if the sale is recorded
        const sale = await ICOInstance.sales(0);
        assert.equal(sale.investor, account2, "Sale was not recorded correctly");
        assert.equal(sale.quantity.toString(), "500000", "Sale quantity is incorrect"); // 50 wei / 5 wei = 10
   
    });

    it("should not allow non-admin to release tokens", async () => {
        
        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            2_000_000,
            { from: account1 }
        );

        // Whitelist an investor
        await ICOInstance.whitelist(account2, { from: account1 });

        // Simulate a purchase
        await ICOInstance.buy({ from: account2, value: web3.utils.toWei("500000", "wei") });

        // Attempt to release tokens by a non-admin
        await truffleAssert.reverts(
            ICOInstance.release({ from: account2 }),
            "Only admin"
        );

    });

    it("should not allow releasing tokens before ICO has ended", async () => {
        
        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            2_000_000,
            { from: account1 }
        );

        // Attempt to release tokens before ICO has ended
        await truffleAssert.reverts(
            ICOInstance.release({ from: account1 }),
            "ICO must have ended"
        );

    });

    it("should allow admin to release tokens after ICO has ended", async () => {
        
        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            2_000_000,
            { from: account1 }
        );

        // Whitelist an investor
        await ICOInstance.whitelist(account2, { from: account1 });

        // Simulate a purchase
        await ICOInstance.buy({ from: account2, value: web3.utils.toWei("500000", "wei") });

        // Increase time to simulate ICO end
        await increaseTime(1100);

        // Release tokens
        await ICOInstance.release({ from: account1 });

        const released = await ICOInstance.released();
        assert(released, "Tokens were not released");

        const balance = await ICOInstance.balanceOf(account2);
        assert.equal(balance.toString(), "500000", "Account 2 did not receive the correct amount of tokens");

    });

    it("should not allow non-admin to withdraw funds", async () => {

        // Start the ICO
        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            2_000_000,
            { from: account1 }
        );

        // Whitelist an investor
        await ICOInstance.whitelist(account2, { from: account1 });

        // Simulate a purchase
        await ICOInstance.buy({ from: account2, value: web3.utils.toWei("500000", "wei") });

        // Increase time to simulate ICO end
        await increaseTime(1100);

        // Release tokens
        await ICOInstance.release({ from: account1 });

        // Attempt to withdraw funds by a non-admin
        await truffleAssert.reverts(
            ICOInstance.withdraw(account3, { from: account2 }),
            "Only admin"
        );

    });

    it("should not allow admin to withdraw funds before ICO has ended", async () => {

        // Start the ICO
        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            2_000_000,
            { from: account1 }
        );

        // Whitelist an investor
        await ICOInstance.whitelist(account2, { from: account1 });

        // Simulate a purchase
        await ICOInstance.buy({ from: account2, value: web3.utils.toWei("500000", "wei") });

        // Attempt to withdraw funds before ICO has ended
        await truffleAssert.reverts(
            ICOInstance.withdraw(account3, { from: account1 }),
            "ICO must have ended"
        );

    });

    it("should not allow withdrawing funds before tokens are released", async () => {

        // Start the ICO
        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            2_000_000,
            { from: account1 }
        );

        // Whitelist an investor
        await ICOInstance.whitelist(account2, { from: account1 });

        // Simulate a purchase
        await ICOInstance.buy({ from: account2, value: web3.utils.toWei("500000", "wei") });

        // Increase time to simulate ICO end
        await increaseTime(1100);

        // Attempt to withdraw funds before tokens are released
        await truffleAssert.reverts(
            ICOInstance.withdraw(account4, { from: account1 }),
            "tokens not released"
        );

    });

    it("should allow admin to withdraw funds to account4 after ICO has ended and tokens are released", async () => {

        // Start the ICO
        await ICOInstance.start(
            1000,
            web3.utils.toWei("1", "wei"),
            80_000_000_000,
            50_000,
            200_000_000,
            { from: account1 }
        );

        // Whitelist an investor
        await ICOInstance.whitelist(account2, { from: account1 });

        // Simulate a purchase
        await ICOInstance.buy({ from: account2, value: web3.utils.toWei("200000000", "wei") });

        // Increase time to simulate ICO end
        await increaseTime(1100);

        // Release tokens
        await ICOInstance.release({ from: account1 });

        // Check initial balance of account4
        const initialBalance = BigInt(await web3.eth.getBalance(account4));

        // Withdraw funds to account4
        await ICOInstance.withdraw(account4, { from: account1 });

        // Check final balance of account4
        const finalBalance = BigInt(await web3.eth.getBalance(account4));
        assert(finalBalance > initialBalance, "Funds were not withdrawn to account4");

    });

});
