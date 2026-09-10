const Donation = artifacts.require("Donation");
const truffleAssert = require("truffle-assertions");

const {increaseTime, ZERO_ADDRESS, takeSnapshot, revertToSnapshot} = require("./helpers");

contract("Donation", (accounts) => {

    let DonationInstance;
    let snapshotId;

    const [account1, account2, account3, account4, user1, user2, user3] = accounts;

    afterEach(async () => { await revertToSnapshot(); });

    beforeEach(async () => {

        DonationInstance = await Donation.new({from: account1});
        
        snapshotId = await takeSnapshot();

    });

    it("should check if contract is deployed", async () => {
       
        assert(DonationInstance.address !== "", "Contract not deployed");
    
    });

    it("should check if contract admin is deployer", async () => {
       
        assert(await DonationInstance.admin() == account1, "Deployer is not admin");
    
    });

    it("should check if contract fee is 10", async () => {
        
        const fee = await DonationInstance.feePerThousand();

        assert(fee.toString() == "10", "Fee is not right");
    
    });

    it("should allow user to call makeMyselfUser and create a user", async () => {
        const profileName = "Alice";
        const categories = "charity&&education";
        const tx = await DonationInstance.makeMyselfUser(profileName, categories, { from: account3 });

        const userStruct = await DonationInstance.donationAddresses(account3);
        assert.equal(userStruct.donate_address, account3, "User address mismatch");
        assert.equal(userStruct.profile_name, profileName, "Profile name mismatch");
        assert.equal(userStruct.tier, "USER", "Tier should be USER");
        assert.equal(userStruct.categories, categories, "Categories mismatch");
        assert.equal(userStruct.exist, true, "User should exist");
        assert.equal(userStruct.user_mode, true, "User mode should be true");

        truffleAssert.eventEmitted(tx, "NewUser", (ev) => ev._recipient === account3);
    });

    it("should increment nextUser and set allDonationAddresses", async () => {
        const profileName = "Alice";
        const categories = "charity&&education";
        await DonationInstance.makeMyselfUser(profileName, categories, { from: account3 });

        const nextUser = await DonationInstance.nextUser();
        assert.equal(nextUser.toString(), "1", "nextUser should increment");

        const addr = await DonationInstance.allDonationAddresses(0);
        assert.equal(addr, account3, "allDonationAddresses not set correctly");
    });

    it("should emit NewUser event with correct parameters", async () => {
        const profileName = "Alice";
        const categories = "charity&&education";
        const tx = await DonationInstance.makeMyselfUser(profileName, categories, { from: account3 });

        truffleAssert.eventEmitted(tx, "NewUser", (ev) => {
            return ev._recipient === account3 && typeof ev._timestamp !== "undefined";
        });
    });

     it("should allow admin to create a new user with makeNewUser", async () => {
        const admin = account1;
        const user1 = accounts[4];
        const profileName = "Bob";
        const tier = "USER";
        const categories = "health&&education";
        const tx = await DonationInstance.makeNewUser(user1, profileName, tier, categories, { from: admin });

        const userStruct = await DonationInstance.donationAddresses(user1);
        assert.equal(userStruct.donate_address, user1, "User address mismatch");
        assert.equal(userStruct.profile_name, profileName, "Profile name mismatch");
        assert.equal(userStruct.tier, tier, "Tier mismatch");
        assert.equal(userStruct.categories, categories, "Categories mismatch");
        assert.equal(userStruct.exist, true, "User should exist");
        assert.equal(userStruct.user_mode, false, "User mode should be false for admin-created user");

        truffleAssert.eventEmitted(tx, "NewUser", (ev) => ev._recipient === user1);
    });

    it("should increment nextUser and set allDonationAddresses", async () => {
        const admin = account1;
        const user2 = account4;
        const profileName = "Carol";
        const tier = "USER";
        const categories = "charity";
        await DonationInstance.makeNewUser(user2, profileName, tier, categories, { from: admin });

        const nextUser = await DonationInstance.nextUser();
        assert.equal(nextUser.toString(), "1", "nextUser should increment");

        const addr = await DonationInstance.allDonationAddresses(0);
        assert.equal(addr, user2, "allDonationAddresses not set correctly");
    });

    it("should only allow admin or approved addresses to call makeNewUser", async () => {
        const admin = account1;
        const user1 = account4;
        const approved1 = account2;

        // Try from non-admin, non-approved
        await truffleAssert.reverts(
            DonationInstance.makeNewUser(user1, "Eve", "USER", "misc", { from: user1 }),
            null
        );

        // Approve an address and try
        await DonationInstance.approveAddress(0, approved1, { from: admin });
        const tx = await DonationInstance.makeNewUser(user1, "Eve", "USER", "misc", { from: approved1 });
        truffleAssert.eventEmitted(tx, "NewUser", (ev) => ev._recipient === user1);
    });

    it("should emit NewUser event with correct parameters", async () => {
        const admin = account1;
        const user2 = account4;
        const profileName = "Dave";
        const tier = "USER";
        const categories = "science";
        const tx = await DonationInstance.makeNewUser(user2, profileName, tier, categories, { from: admin });

        truffleAssert.eventEmitted(tx, "NewUser", (ev) => {
            return ev._recipient === user2 && typeof ev._timestamp !== "undefined";
        });
    });

    it("should not allow creating user with zero address", async () => {
        await truffleAssert.reverts(
            DonationInstance.makeNewUser(ZERO_ADDRESS, "Zero", "USER", "none", { from: account1 }),
            null
        );
    });

    it("should allow admin (account1) to change user tier", async () => {
        await DonationInstance.changeUserTier(account4, "PREMIUM", { from: account1 });
        const userStruct = await DonationInstance.donationAddresses(account4);
        assert.equal(userStruct.tier, "PREMIUM", "Tier should be updated to PREMIUM");
        assert.equal(userStruct.user_mode, false, "user_mode should be false for non-USER tier");
    });

    it("should allow approved address (account2) to change user tier", async () => {
        await DonationInstance.approveAddress(0, account2, { from: account1 });
        await DonationInstance.changeUserTier(account4, "PREMIUM", { from: account2 });
        const userStruct = await DonationInstance.donationAddresses(account4);
        assert.equal(userStruct.tier, "PREMIUM", "Tier should be updated to PREMIUM");
    });

    it("should set user_mode to true if tier is USER", async () => {
        await DonationInstance.changeUserTier(account4, "USER", { from: account1 });
        const userStruct = await DonationInstance.donationAddresses(account4);
        assert.equal(userStruct.tier, "USER", "Tier should be USER");
        assert.equal(userStruct.user_mode, true, "user_mode should be true for USER tier");
    });

    it("should revert if non-admin and non-approved tries to change user tier", async () => {
        await truffleAssert.reverts(
            DonationInstance.changeUserTier(account4, "PREMIUM", { from: account4 }),
            null
        );
    });

    it("should revert if trying to change tier for non-existent user", async () => {
        await truffleAssert.reverts(
            DonationInstance.changeUserTier(accounts[5], "PREMIUM", { from: account4 }),
           "address is not approved"
        );
    });

    it("should allow only account1 to change the fee", async () => {
        // account1 can change fee
        await DonationInstance.changeFee(20, { from: account1 });
        const fee = await DonationInstance.feePerThousand();
        assert.equal(fee.toString(), "20", "Fee should be updated by account1");

        // account4 cannot change fee
        await truffleAssert.reverts(
            DonationInstance.changeFee(30, { from: account4 }),
            null
        );
    });

    it("should revert if new fee is equal to current fee", async () => {
        const currentFee = await DonationInstance.feePerThousand();
        await truffleAssert.reverts(
            DonationInstance.changeFee(currentFee, { from: account1 }),
            null
        );
    });

    it("should revert if new fee is greater than 1000", async () => {
        await truffleAssert.reverts(
            DonationInstance.changeFee(1001, { from: account1 }),
            null
        );
        await truffleAssert.reverts(
            DonationInstance.changeFee(2000, { from: account1 }),
            null
        );
    });

    it("should allow changing fee to 0", async () => {
        await DonationInstance.changeFee(0, { from: account1 });
        const fee = await DonationInstance.feePerThousand();
        assert.equal(fee.toString(), "0", "Fee should be set to 0");
    });

    it("should allow changing fee multiple times as long as valid", async () => {
        await DonationInstance.changeFee(20, { from: account1 });
        let fee = await DonationInstance.feePerThousand();
        assert.equal(fee.toString(), "20", "Fee should be 20");

        await DonationInstance.changeFee(5, { from: account1 });
        fee = await DonationInstance.feePerThousand();
        assert.equal(fee.toString(), "5", "Fee should be 5");
    });

    it("should allow makeDonate to a valid donation address", async () => {
        // Register user1 as a donation address
        await DonationInstance.makeMyselfUser("Alice", "charity", { from: user1 });
        // user2 donates to user1
        const tx = await DonationInstance.makeDonate(user1, { from: user2, value: web3.utils.toWei("1", "ether") });
        truffleAssert.eventEmitted(tx, "NewTransaction", (ev) => ev._to === user1);
    });

    it("should revert makeDonate to a non-existent donation address", async () => {
        // user2 tries to donate to user1 who is not registered
        await truffleAssert.reverts(
            DonationInstance.makeDonate(user1, { from: user2, value: web3.utils.toWei("1", "ether") }),
            "address cannot receive donations"
        );
    });

    it("should revert makeDonate to the zero address", async () => {
        await truffleAssert.reverts(
            DonationInstance.makeDonate(ZERO_ADDRESS, { from: user2, value: web3.utils.toWei("1", "ether") }),
            "invalid address"
        );
    });

    it("should allow account1 to call onlyAdminOrApproved functions", async () => {
        // approveAddress is protected by onlyAdminOrApproved
        const tx = await DonationInstance.approveAddress(1, account2, { from: account1 });
        truffleAssert.eventEmitted(tx, "ApproveAddress", (ev) => ev._current === account2);
    });

    it("should allow account2 to call onlyAdminOrApproved functions", async () => {
        // account1 approves account2
        await DonationInstance.approveAddress(0, account2, { from: account1 });
        // Now account2 can approve another address
        const tx = await DonationInstance.approveAddress(1, account3, { from: account2 });
        truffleAssert.eventEmitted(tx, "ApproveAddress", (ev) => ev._current === account3);
    });

    it("should not allow account4 to call onlyAdminOrApproved functions", async () => {
        await truffleAssert.reverts(
            DonationInstance.approveAddress(2, user1, { from: account4 }),
            "address is not approved"
        );
    });

    it("should allow account1 to unapprove an address", async () => {
        await DonationInstance.approveAddress(0, account2, { from: account1 });
        const tx = await DonationInstance.unapproveAddress(0, { from: account1 });
        truffleAssert.eventEmitted(tx, "ApproveAddress", (ev) => ev._current === ZERO_ADDRESS);
    });

    it("should allow account2 to unapprove an address", async () => {
        await DonationInstance.approveAddress(0, account2, { from: account1 });
        const tx = await DonationInstance.unapproveAddress(0, { from: account2 });
        truffleAssert.eventEmitted(tx, "ApproveAddress", (ev) => ev._current === ZERO_ADDRESS);
    });

    it("should not allow account4 to unapprove an address", async () => {
        await DonationInstance.approveAddress(0, account2, { from: account1 });
        await truffleAssert.reverts(
            DonationInstance.unapproveAddress(0, { from: account4 }),
            "address is not approved"
        );
    });

    it("should not allow zero address to be approved", async () => {
        await truffleAssert.reverts(
            DonationInstance.approveAddress(0, ZERO_ADDRESS, { from: account1 }),
            "invalid address"
        );
    });

    it("should allow account1 to approve multiple slots", async () => {
        for (let i = 0; i < 5; i++) {
            const tx = await DonationInstance.approveAddress(i, accounts[i + 1], { from: account1 });
            truffleAssert.eventEmitted(tx, "ApproveAddress", (ev) => ev._current === accounts[i + 1]);
        }
    });

    it("should allow only account3 in correct slot", async () => {
        await DonationInstance.approveAddress(2, account3, { from: account1 });
        // account3 can call onlyAdminOrApproved
        const tx = await DonationInstance.unapproveAddress(2, { from: account3 });
        truffleAssert.eventEmitted(tx, "ApproveAddress", (ev) => ev._current === ZERO_ADDRESS);
    });

    it("should not allow previously approved account2 after unapproval", async () => {
        await DonationInstance.approveAddress(0, account2, { from: account1 });
        await DonationInstance.unapproveAddress(0, { from: account1 });
        await truffleAssert.reverts(
            DonationInstance.approveAddress(1, user1, { from: account2 }),
            "address is not approved"
        );
    });

    it("should revert if non-admin tries to withdraw", async () => {

        await web3.eth.sendTransaction({
            from: user1,
            to: DonationInstance.address,
            value: web3.utils.toWei("5", "ether")
        });

        const withdrawAmount = web3.utils.toWei("1", "ether");
        await truffleAssert.reverts(
            DonationInstance.withdraw(account3, withdrawAmount, { from: account3 }),
            "only admin"
        );
    });

    it("should revert if trying to withdraw more than contractFunds", async () => {
        
        await web3.eth.sendTransaction({
            from: user1,
            to: DonationInstance.address,
            value: web3.utils.toWei("5", "ether")
        });

        const tooMuch = web3.utils.toWei("10", "ether");
        await truffleAssert.reverts(
            DonationInstance.withdraw(user1, tooMuch, { from: account1 }),
            "not enough funds"
        );
    });

    it("should allow admin to withdraw contract funds", async () => {

        await web3.eth.sendTransaction({
            from: account2,
            to: DonationInstance.address,
            value: web3.utils.toWei("5", "ether")
        });

        const contractFunds = await DonationInstance.contractFunds();

        assert.equal(contractFunds.toString(), web3.utils.toWei("5", "ether"), "contractFunds not updated correctly");

        const initialBalance = web3.utils.toBN(await web3.eth.getBalance(user1));

        const tx = await DonationInstance.withdraw(user1, web3.utils.toWei("3", "ether"), { from: account1 });

        const contractFundsAfter = await DonationInstance.contractFunds();

        const finalBalance = web3.utils.toBN(await web3.eth.getBalance(user1));

        assert(finalBalance.gt(initialBalance), "user1 did not receive withdrawn funds");

        // The contract should now have 2 ether left
        assert.equal(contractFundsAfter.toString(), web3.utils.toWei("2", "ether"), "contractFunds not reduced correctly");

    });

    it("should allow withdrawing all contract funds", async () => {

        await web3.eth.sendTransaction({
            from: account2,
            to: DonationInstance.address,
            value: web3.utils.toWei("5", "ether")
        });
        
        const contractFunds = await DonationInstance.contractFunds();
        const initialBalance = web3.utils.toBN(await web3.eth.getBalance(user1));

        await DonationInstance.withdraw(user1, contractFunds, { from: account1 });

        const finalBalance = web3.utils.toBN(await web3.eth.getBalance(user1));
        assert(finalBalance.sub(initialBalance).eq(web3.utils.toBN(contractFunds)), "user1 did not receive all contract funds");

        const remainingFunds = await DonationInstance.contractFunds();
        assert.equal(remainingFunds.toString(), "0", "contractFunds should be zero after full withdrawal");
    });

    it("should revert if withdrawing to zero address", async () => {
        const contractFunds = await DonationInstance.contractFunds();
        await truffleAssert.fails(
            DonationInstance.withdraw(ZERO_ADDRESS, contractFunds, { from: account1 }),
            "invalid address"
        );
    });

    it("should allow user1 to receive donations after time passes, and not before", async () => {
        // Register user1 as donation address
        await DonationInstance.makeMyselfUser("Alice", "charity", { from: user1 });

        // user2 donates 1 ether to user1
        await DonationInstance.makeDonate(user1, { from: user2, value: web3.utils.toWei("1", "ether") });

        // user3 donates 2 ether to user1
        await DonationInstance.makeDonate(user1, { from: user3, value: web3.utils.toWei("2", "ether") });

        // Try to receive donations before time expires (should revert or do nothing)
        await truffleAssert.reverts(
            DonationInstance.receiveDonation({ from: user1 }),
            "nothing to retrieve now"
        );

        // Increase time by 30 days + 100 seconds
        await increaseTime(30 * 24 * 60 * 60 + 100);

        // Now user1 should be able to receive donations
        const receipt = await DonationInstance.receiveDonation({ from: user1 });
        truffleAssert.eventEmitted(receipt, "DonationRetrieved");

        // Make more donations
        await DonationInstance.makeDonate(user1, { from: account4, value: web3.utils.toWei("3", "ether") });
        await DonationInstance.makeDonate(user1, { from: account3, value: web3.utils.toWei("4", "ether") });

        // Try to retrieve before time expires (should revert or do nothing)
        await truffleAssert.reverts(
            DonationInstance.receiveDonation({ from: user1 }),
            "nothing to retrieve now"
        );

        // Increase time again
        await increaseTime(30 * 24 * 60 * 60 + 100);

        // Now user1 can retrieve again
        const receipt2 = await DonationInstance.receiveDonation({ from: user1 });
        truffleAssert.eventEmitted(receipt2, "DonationRetrieved");
    });

});