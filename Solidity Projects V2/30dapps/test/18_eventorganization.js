const EventOrganizer = artifacts.require("EventOrganizer");
const truffleAssert = require("truffle-assertions");

const {increaseTime, takeSnapshot, revertToSnapshot} = require("./helpers");

contract("EventOrganizer", (accounts) => {

    let EventOrganizerInstance;

    const [account1, account2, account3, account4] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        EventOrganizerInstance = await EventOrganizer.new();

    });

    it("should check if contract is deployed", async () => {
       
        assert(EventOrganizerInstance.address !== "", "Contract not deployed");
    
    });

    it("should try to create an event now and fail", async() => {

        await truffleAssert.reverts(
            EventOrganizerInstance.createEvent("event 1", 0, web3.utils.toWei("5", "finney"), 100000, {from: account1}),
            "event can only be organized in the future"
        )

    });

    it("should try to create an event with 0 ticket and fail", async() => {

        await truffleAssert.reverts(
            EventOrganizerInstance.createEvent("event 1", 3600, web3.utils.toWei("5", "finney"), 0, {from: account1}),
            "can only create event with at least 1 ticket available"
        )

    });
    
    it("should make an event and check it's info", async() => {

        const tx = await EventOrganizerInstance.createEvent("event 1", 3600, web3.utils.toWei("5", "finney"), 100000, {from: account1});
        
        const block = await web3.eth.getBlock(tx.receipt.blockNumber);
        
        const e = await EventOrganizerInstance.events(0);

        assert.equal(e.name, "event 1", "Event name is incorrect");
        assert.equal(e.date.toNumber(), 3600 + block.timestamp , "Event date is incorrect");
        assert.equal(e.price.toString(), web3.utils.toWei("5", "finney"), "Event price is incorrect");
        assert.equal(e.ticketCount.toNumber(), 100000, "Event ticket count is incorrect");
        assert.equal(e.ticketRemaining.toNumber(), 100000, "Event ticket remaining is incorrect");
        assert.equal(e.admin, account1, "Event admin is incorrect");

    });

    it("should check if you can buy tickets of an invalid event id", async() => {

        const tx = await EventOrganizerInstance.createEvent("event 1", 3600, web3.utils.toWei("5", "finney"), 100000, {from: account1});

        await truffleAssert.reverts(
            EventOrganizerInstance.buyTicket(42, 1000, {from: account2, value: web3.utils.toWei("5", "ether")}),
            "this event doesn't exist"
        )

    });

    it("should check if you can buy tickets of an event that expired", async() => {

        const tx = await EventOrganizerInstance.createEvent("event 1", 3600, web3.utils.toWei("5", "finney"), 100000, {from: account1});

        await increaseTime(3700);

        await truffleAssert.reverts(
            EventOrganizerInstance.buyTicket(0, 1000, {from: account2, value: web3.utils.toWei("5", "ether")}),
            "this event is not active anymore"
        )

    });

    it("should try to buy more tickets than ether sent", async() => {
        
        const tx = await EventOrganizerInstance.createEvent("event 1", 3600, web3.utils.toWei("5", "finney"), 100000, {from: account1});

        await truffleAssert.reverts(
            EventOrganizerInstance.buyTicket(0, 1000, {from: account2, value: web3.utils.toWei("1", "ether")}),
            "not enough ether sent"
        )

    });

    it("should try to buy more tickets than avialable", async() => {

        const tx = await EventOrganizerInstance.createEvent("event 1", 3600, web3.utils.toWei("5", "finney"), 100000, {from: account1});

        const million = 1_000_000;
        const price = million * web3.utils.toWei("5", "wei");

        await truffleAssert.reverts(
            EventOrganizerInstance.buyTicket(0, million, {from: account2, value: price}),
            "not enough ticket left"
        )

    });

    it("should buy 1000 tickets and check the ticket contract and buyer balances", async() => {

        const tx = await EventOrganizerInstance.createEvent("event 1", 3600, web3.utils.toWei("5", "finney"), 100000, {from: account1});

        await EventOrganizerInstance.buyTicket(0, 1000, {from: account2, value: web3.utils.toWei("5", "ether")});

        const event = await EventOrganizerInstance.events(0);
        assert.equal(event.ticketRemaining, 100000 - 1000, "available tickets are incorrect");

        const buyer = await EventOrganizerInstance.has(account2, 0);
        assert.equal(buyer, 1000, "buyer has incorrect number of tickets");
        
    });

    it("should check if you can transfer tickets of an invalid event id", async() => {

        const tx = await EventOrganizerInstance.createEvent("event 1", 3600, web3.utils.toWei("5", "finney"), 100000);

        await EventOrganizerInstance.buyTicket(0, 1000, {from: account2, value: web3.utils.toWei("5", "ether")});

        await truffleAssert.reverts(
            EventOrganizerInstance.transferTicket(42, 500, account3, {from: account2}),
            "this event doesn't exist"
        )

    });

    it("should check if you can transfer tickets of an event that expired", async() => {

        const tx = await EventOrganizerInstance.createEvent("event 1", 3600, web3.utils.toWei("5", "finney"), 100000);

        await EventOrganizerInstance.buyTicket(0, 1000, {from: account2, value: web3.utils.toWei("5", "ether")});

        await increaseTime(3700);

        await truffleAssert.reverts(
            EventOrganizerInstance.transferTicket(0, 500, account3, {from: account2}),
            "this event is not active anymore"
        )

    });

    it("should try to transfer more ticket than available", async() => {

        const tx = await EventOrganizerInstance.createEvent("event 1", 3600, web3.utils.toWei("5", "finney"), 100000);

        await EventOrganizerInstance.buyTicket(0, 1000, {from: account2, value: web3.utils.toWei("5", "ether")});

        await truffleAssert.reverts(
            EventOrganizerInstance.transferTicket(0, 1500, account3, {from: account2}),
            "not enough tickets"
        )

    });

    it("should buy 1000 tickets and check the ticket contract and buyer balances", async() => {

        const tx = await EventOrganizerInstance.createEvent("event 1", 3600, web3.utils.toWei("5", "finney"), 100000, {from: account1});

        await EventOrganizerInstance.buyTicket(0, 1000, {from: account2, value: web3.utils.toWei("5", "ether")});

        await EventOrganizerInstance.transferTicket(0, 500, account3, {from: account2});

        const sender = await EventOrganizerInstance.has(account2, 0);
        const receiver = await EventOrganizerInstance.has(account3, 0);

        assert.equal(sender, 500, "sender balance is incorrect");
        assert.equal(receiver, 500, "receiver balance is incorrect");
        
    });

});
