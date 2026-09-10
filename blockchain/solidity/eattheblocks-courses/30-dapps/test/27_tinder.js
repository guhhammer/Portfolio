const Tinder = artifacts.require("Tinder");
const truffleAssert = require("truffle-assertions");

const { takeSnapshot, revertToSnapshot } = require("./helpers");

contract("Tinder", (accounts) => {

    let TinderInstance;

    const [account1, account2, account3, account4, account5, account6, account7, account8, account9] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        
        TinderInstance = await Tinder.new({ from: account1 });
        
        await TinderInstance.register("max", "new york", 0, 20, "https://ipfs.io/max", { from: account2 });
        await TinderInstance.register("mike", "new york", 0, 25, "https://ipfs.io/mike", { from: account3 });
        await TinderInstance.register("jane", "new york", 1, 21, "https://ipfs.io/jane", { from: account5 });
        await TinderInstance.register("emma", "new york", 1, 23, "https://ipfs.io/emma", { from: account6 });
        await TinderInstance.register("sophia", "new york", 1, 24, "https://ipfs.io/sophia", { from: account7 });
        await TinderInstance.register("chris", "miami", 0, 26, "https://ipfs.io/chris", { from: account8 });
        await TinderInstance.register("gaby", "miami", 1, 28, "https://ipfs.io/gaby", { from: account9 });

    });

    it("should check if contract is deployed", async () => {

        assert(TinderInstance.address !== "", "Contract not deployed");

    });

    it("should check if contract admin is right", async () => {

        assert(await TinderInstance.admin() == account1, "Contract admin is wrong");

    });

    it("should revert if user is already registered", async () => {
        await truffleAssert.reverts(
            TinderInstance.register("john", "new york", 0, 20, "https://ipfs.io/john", { from: account2 }),
            "user is registered already"
        );
    });

    it("should revert if age is less than 18", async () => {
        await truffleAssert.reverts(
            TinderInstance.register("john", "new york", 0, 17, "https://ipfs.io/john", { from: account4 }),
            "restricted by age"
        );
    });

    it("should revert if name length is less than 2", async () => {
        await truffleAssert.reverts(
            TinderInstance.register("j", "new york", 0, 20, "https://ipfs.io/john", { from: account4 }),
            "must define name"
        );
    });

    it("should revert if city length is less than 2", async () => {
        await truffleAssert.reverts(
            TinderInstance.register("john", "n", 0, 20, "https://ipfs.io/john", { from: account4 }),
            "city cannot be empty"
        );
    });

    it("should revert if picURL length is less than 2", async () => {
        await truffleAssert.reverts(
            TinderInstance.register("john", "new york", 0, 20, "h", { from: account4 }),
            "picURL cannot be empty"
        );
    });

    it("should revert if gender is not valid", async () => {
        await truffleAssert.reverts(
            TinderInstance.register("john", "new york", 2, 20, "https://ipfs.io/john", { from: account4 }),
            "gender not valid"
        );
    });

    it("should register a user successfully with valid inputs", async () => {
        await TinderInstance.register("john", "new york", 0, 20, "https://ipfs.io/john", { from: account4 });

        const u = await TinderInstance.getUser(account4, { from: account1 });

        assert(u.name === "john", "User registration failed");
    });

    it("should get the names of the first and last registered users", async () => {
        const firstUserAddress = await TinderInstance.getListUsers(0, { from: account1 });
        const lastUserAddress = await TinderInstance.getListUsers(6, { from: account1 });

        const firstUser = await TinderInstance.getUser(firstUserAddress, { from: account1 });
        const lastUser = await TinderInstance.getUser(lastUserAddress, { from: account1 });

        assert(firstUser.name === "max", "First user's name is incorrect");
        assert(lastUser.name === "gaby", "Last user's name is incorrect");
    });

    it("should retrieve all women in New York", async () => {
        const womenInNewYork = await TinderInstance.getUserIdsByCity("new york", 1, { from: account1 });
        assert(womenInNewYork.length === 3, "Incorrect number of women retrieved");
        assert(womenInNewYork.includes(account5), "Jane is not included");
        assert(womenInNewYork.includes(account6), "Emma is not included");
        assert(womenInNewYork.includes(account7), "Sophia is not included");
    });

    it("should revert when calling getMatchableUsers if user is not registered", async () => {
        await truffleAssert.reverts(
            TinderInstance.getMatchableUsers({ from: account4 }),
            "user is not registered"
        );
    });

    it("should retrieve all matchable women in New York for max", async () => {
        const matchableUsers = await TinderInstance.getMatchableUsers({ from: account2 });
        assert(matchableUsers.length === 3, "Incorrect number of matchable users retrieved");
        assert(matchableUsers[0].name === "jane", "First matchable user's name is incorrect");
        assert(matchableUsers[1].name === "emma", "Second matchable user's name is incorrect");
        assert(matchableUsers[2].name === "sophia", "Third matchable user's name is incorrect");
    });

    it("should swipe twice for the first match and fail", async () => {
        const matchableUsers = await TinderInstance.getMatchableUsers({ from: account2 });
        const firstMatch = matchableUsers[0];

        // First swipe should succeed
        await TinderInstance.swipe(1, firstMatch.lookup, { from: account2 });

        // Second swipe should fail
        await truffleAssert.reverts(
            TinderInstance.swipe(1, firstMatch.lookup, { from: account2 }),
            "cannot swipe same person twice"
        );
    });

    it("should swipe like, swipe dislike, swipe like and check first and last events emitted", async () => {
        const matchableUsers = await TinderInstance.getMatchableUsers({ from: account2 });
        const firstMatch = matchableUsers[0].lookup;
        const secondMatch = matchableUsers[1].lookup;
        const thirdMatch = matchableUsers[2].lookup;

        await TinderInstance.swipe(1, firstMatch, { from: account2 });
        await TinderInstance.swipe(2, secondMatch, { from: account2 });
        await TinderInstance.swipe(1, thirdMatch, { from: account2 });

        // Retrieve swipes for account2
        const s1 = await TinderInstance.getSwipeStatus(account2, firstMatch, { from: account1 });
        const s2 = await TinderInstance.getSwipeStatus(account2, secondMatch, { from: account1 });
        const s3 = await TinderInstance.getSwipeStatus(account2, thirdMatch, { from: account1 });
        
        assert(s1.toString() === "1", "First match swipe status is incorrect");
        assert(s2.toString() === "2", "Second match swipe status is incorrect");
        assert(s3.toString() === "1", "Third match swipe status is incorrect");
    });

    it("should swipe like, swipe dislike, swipe like, swipe like for the third match and check first and last events emitted", async () => {
        const matchableUsers = await TinderInstance.getMatchableUsers({ from: account2 });
        const firstMatch = matchableUsers[0].lookup;
        const secondMatch = matchableUsers[1].lookup;
        const thirdMatch = matchableUsers[2].lookup;

        // Swipe like for the first match
        await TinderInstance.swipe(1, firstMatch, { from: account2 });
        await TinderInstance.swipe(2, secondMatch, { from: account2 });
        await TinderInstance.swipe(1, thirdMatch, { from: account2 });

        const likeBackTx = await TinderInstance.swipe(1, account2, { from: firstMatch });
        truffleAssert.eventEmitted(likeBackTx, "NewMatch", (ev) => {
            return ev._from === firstMatch && ev._to === account2;
        });

        // Attempt to send a message to account7 (thirdMatch) without mutual like
        await truffleAssert.reverts(
            TinderInstance.sendMessage(thirdMatch, "Hello!", { from: account2 }),
            "both users have to like each other to send messages"
        );

        // Max sends a message to the first match
        const messageTx = await TinderInstance.sendMessage(firstMatch, "Hi Jane!", { from: account2 });
        truffleAssert.eventEmitted(messageTx, "MessageSent", (ev) => {
            return ev._from === account2 && ev._to === firstMatch && ev._content === "Hi Jane!";
        });

        const c = await TinderInstance.getConversation(account2, firstMatch, {from: account1});

        assert(c[0].content == "Hi Jane!", "Message content is incorrect");

    });

    it("should try to swipe an invalid user address and fail", async () => {
                
        await truffleAssert.reverts(
            TinderInstance.swipe(1, account4, { from: account2 }),
            "user is not registered"
        );

    });

    it("should try to send message to invalid user address and fail", async () => {
                
        await truffleAssert.reverts(
            TinderInstance.sendMessage(account4, "hey, anon", { from: account2 }),
            "user is not registered"
        );

    });

});
