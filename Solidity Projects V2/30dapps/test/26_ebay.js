const Ebay = artifacts.require("Ebay");
const truffleAssert = require("truffle-assertions");

const {increaseTime, takeSnapshot, revertToSnapshot } = require("./helpers");

contract("Ebay", (accounts) => {

    let EbayInstance;

    const [account1, account2, account3, account4, account5, account6, account7, account8] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();

        EbayInstance = await Ebay.new({from: account1});

        await EbayInstance.createAuction(
            "Auction NFT 1",                        // name.
            "selling this Crypto Punks Rare NFT",   // description.
            web3.utils.toWei("5", "finney"),        // min. bid price (user-defined).
            7200,                                   // 2 hours minimum (contract-defined hard-coded).
            web3.utils.toWei("1", "finney"),          // one-thousandth of min. bid price (min. % is hard-coded).
            {from: account6}
        );

        await EbayInstance.createAuction(
            "Auction NFT 2",                        // name.
            "selling this Bored Ape Yacht Club NFT",// description.
            web3.utils.toWei("10", "finney"),       // min. bid price (user-defined).
            10800,                                  // 3 hours duration.
            web3.utils.toWei("1", "finney"),         // one-thousandth of min. bid price (min. % is hard-coded).
            {from: account5}
        );

        await EbayInstance.createAuction(
            "Auction NFT 3",                        // name.
            "selling this Azuki NFT",               // description.
            web3.utils.toWei("8", "finney"),        // min. bid price (user-defined).
            14400,                                  // 4 hours duration.
            web3.utils.toWei("1", "finney"),          // one-thousandth of min. bid price (min. % is hard-coded).
            {from: account4}
        );
            
    });

    it("should check if contract is deployed", async () => {
    
        assert(EbayInstance.address !== "", "Contract not deployed");
    
    });

    it("should check if contract admin is right", async () => {
    
        assert(await EbayInstance.admin() == account1, "Contract admin is wrong");
    
    });

    it("should next auction id is set right", async () => {

        assert(await EbayInstance.nextAuctionId() == 3, "Contract next auction id is wrong");

    });

    it("should try to create an auction with wrong min. bid, spread on each bid and duration and fail", async () => {

        await truffleAssert.reverts(
            EbayInstance.createAuction("name", "description", 0, 14400, web3.utils.toWei("8", "gwei"), {from: account4}),
            "invalid min bid"
        );

        await truffleAssert.reverts(
            EbayInstance.createAuction("name", "description", web3.utils.toWei("8", "finney"), 14400, 0, {from: account4}),
            "invalid spread on each bid, must be one-thousandth"
        );

        await truffleAssert.reverts(
            EbayInstance.createAuction("name", "description", web3.utils.toWei("8", "finney"), 0, web3.utils.toWei("1", "finney"), {from: account4}),
            "invalid auction 2-hour duration limit"
        );

    });

    it("should try to bid inferior to min. bid", async () => {

        await truffleAssert.reverts(
            EbayInstance.bidOn(0, {from: account2, value: web3.utils.toWei("3", "finney")}),
            "bid too low"
        );

    });

    it("should try to make the first bid with min value and succeed", async () => {

        await EbayInstance.bidOn(0, {from: account2, value: web3.utils.toWei("5", "finney")});
        
        const auction = await EbayInstance.getAuction(0);

        assert.equal(auction.bestOfferIdAddress, account2, "bid did not occur");

    });

    it("should try to make a bid without covering spread", async () => {

        await EbayInstance.bidOn(0, {from: account2, value: web3.utils.toWei("5", "finney")});
        
        await truffleAssert.reverts(
            EbayInstance.bidOn(0, {from: account3, value: web3.utils.toWei("5", "finney")}),
            "did not cover spread"
        );

    });

    it("should try to double bid and fail", async () => {

        await EbayInstance.bidOn(0, {from: account2, value: web3.utils.toWei("5", "finney")});
        
        await truffleAssert.reverts(
            EbayInstance.bidOn(0, {from: account2, value: web3.utils.toWei("8", "finney")}),
            "cannot double bid"
        );

    });

    it("should make a greater bid and check balance change for last bidder", async () => {

        await EbayInstance.bidOn(0, { from: account7, value: web3.utils.toWei("10", "finney") });
    
        const before = await web3.eth.getBalance(account7);
    
        await EbayInstance.bidOn(0, { from: account4, value: web3.utils.toWei("20", "finney") });
    
        const after = await web3.eth.getBalance(account7);
    
        const auction = await EbayInstance.getAuction(0);
    
        assert(after > before, "did not receive back its amount");
        assert.equal(auction.bestOfferIdAddress, account4, "bid address changed");
    
    });
    
    it("should try to bid after auction end", async () => {

        await EbayInstance.bidOn(0, {from: account7, value: web3.utils.toWei("10", "finney")});

        await increaseTime(7300);

        const auction = await EbayInstance.getAuction(0);

        await truffleAssert.reverts(
            EbayInstance.bidOn(0, {from: account4, value: web3.utils.toWei("20", "finney")}),
            "auction has ended"
        );

        assert.equal(auction.bestOfferIdAddress, account7, "bid address changed");

    });

    it("should try to bid on invalid auction", async () => {

        await truffleAssert.reverts(
            EbayInstance.bidOn(10, {from: account4, value: web3.utils.toWei("20", "finney")}),
            "auction does not exist"
        );

    });

    it("should bid and see change bid counter", async () => {

        let auction = await EbayInstance.getAuction(0);

        assert.equal(auction.offersMadeCounter, 0, "counter did not change");

        await EbayInstance.bidOn(0, {from: account7, value: web3.utils.toWei("5", "finney")});

        auction = await EbayInstance.getAuction(0);

        assert.equal(auction.offersMadeCounter, 1, "counter did not change");

    });

    it("should make a bid and see change in user's mappings", async () => {

        await EbayInstance.bidOn(0, {from: account7, value: web3.utils.toWei("5", "finney")});

        await EbayInstance.bidOn(1, {from: account7, value: web3.utils.toWei("30", "finney")});

        let allBids = await EbayInstance.getMyBids({from: account7});

        let aBid = await EbayInstance.getMyBidAmount(0, {from: account7});

        assert.equal(allBids.toString(), "0,1", "did not get all auction ids");
        assert.equal(aBid.toString(), web3.utils.toWei("5", "finney"), "did not get exact bid amount");

    });

    it("should make a trade after auction end", async () => {

        const before = web3.utils.toBN(await web3.eth.getBalance(account6));

        await EbayInstance.bidOn(0, {from: account7, value: web3.utils.toWei("20", "finney")});

        let a = await EbayInstance.getAuction(0);
        assert.equal(a.bestOfferIdAddress, account7, "address did not change");

        await increaseTime(7300);

        await EbayInstance.trade(0, {from: account7});

        const after = web3.utils.toBN(await web3.eth.getBalance(account6));

        const auction = await EbayInstance.getAuction(0);
        assert(auction.concluded, "trade was not made correctly");

        assert(after.gt(before), "creator of auction was not payed");

    });

    it("should make a trade for invalid auction and fail", async () => {

        await EbayInstance.bidOn(0, {from: account7, value: web3.utils.toWei("15", "finney")});

        await increaseTime(7300);

        await truffleAssert.reverts(
            EbayInstance.trade(10, {from: account1}),
            "auction does not exist"
        );

    });

    it("should make a trade from unauthorized trader and fail", async () => {

        await EbayInstance.bidOn(0, {from: account7, value: web3.utils.toWei("15", "finney")});

        await increaseTime(7300);

        await truffleAssert.reverts(
            EbayInstance.trade(0, {from: account8}),
            "not authorized trader"
        );
        
    });

    it("should remake the trade of concluded auction and fail", async () => {

        await EbayInstance.bidOn(0, {from: account7, value: web3.utils.toWei("15", "finney")});

        await increaseTime(7300);

        await EbayInstance.trade(0, {from: account7});

        await truffleAssert.reverts(
            EbayInstance.trade(0, {from: account7}),
            "Auction is concluded"
        );

    });

});
