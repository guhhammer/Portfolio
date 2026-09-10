const Twitter = artifacts.require("Twitter");
const truffleAssert = require("truffle-assertions");

const { takeSnapshot, revertToSnapshot } = require("./helpers");

contract("Twitter", (accounts) => {

    let TwitterInstance;

    const [account1, account2, account3, account4, account5] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        TwitterInstance = await Twitter.new({from: account1});
    
    });

    it("should check if contract is deployed", async () => {
        assert(TwitterInstance.address !== "", "Contract not deployed");
    });

    it("should check if contract admin is right", async () => {
        assert(await TwitterInstance.admin() == account1, "Contract admin is wrong");
    });

    it("should check if user can tweet", async () => {

        const tx = await TwitterInstance.tweet( ("hey, tweeted!"), { from: account2 } );

        const event = tx.logs.find(log => log.event === "TweetSent");

        assert.equal(event.args._id, 0, "Tweet id is incorrect");
        assert.equal(event.args._author, account2, "Author is incorrect");
        assert.equal(event.args._content, ("hey, tweeted!"), "Content is incorrect");
        assert(event.args._createdAt !== 0, "Created at is invalid");

        
    });

    it("should check if operator can tweet for user", async () => {

        await TwitterInstance.userAuthenticateOperator(account3, {from: account2});

        const tx = await TwitterInstance.tweetFrom(account2, ("hey, operator found it!"), { from: account3 } );

        const event = tx.logs.find(log => log.event === "TweetSent");

        assert.equal(event.args._id, 0, "Tweet id is incorrect");
        assert.equal(event.args._author, account2, "Author is incorrect");
        assert.equal(event.args._content, ("hey, operator found it!"), "Content is incorrect");
        assert(event.args._createdAt !== 0, "Created at is invalid");

    });

    it("should check if non-operator can tweet for user", async () => {

        await truffleAssert.reverts(
            TwitterInstance.tweetFrom(account2, ("hey, operator found it!"), { from: account3 } ),
            "operator not authorized"
        );

    });

    it("should check if non-operator can send message from an user to an other user", async () => {

        await truffleAssert.reverts(
            TwitterInstance.sendMessageFrom(("hey, operator found it!"), account2, account3, { from: account4 } ),
            "operator not authorized"
        );

    });

    it("should check if operator can send message from an user to an other user", async () => {

        await TwitterInstance.userAuthenticateOperator(account3, {from: account2});

        const tx = await TwitterInstance.sendMessageFrom(("hey, operator found it!"), account2, account4, { from: account3 } );

        const event = tx.logs.find(log => log.event === "MessageSent");

        assert.equal(event.args._id, 0, "Tweet id is incorrect");
        assert.equal(event.args._content, ("hey, operator found it!"), "Content is incorrect");
        assert.equal(event.args._from, account2, "Sender is incorrect");
        assert.equal(event.args._to, account4, "Receiver is incorrect");
        assert(event.args._createdAt !== 0, "Created at is invalid");

    });

    it("should check if user can send a message", async () => {

        const tx = await TwitterInstance.sendMessage( ("hey, tweeted!"), account3, { from: account2 } );

        const event = tx.logs.find(log => log.event === "MessageSent");

        assert.equal(event.args._id, 0, "Tweet id is incorrect");
        assert.equal(event.args._content, ("hey, tweeted!"), "Content is incorrect");
        assert.equal(event.args._from, account2, "Sender is incorrect");
        assert.equal(event.args._to, account3, "Receiver is incorrect");
        assert(event.args._createdAt !== 0, "Created at is invalid");

    });

    it("should check if user can follow someone", async () => {

        await TwitterInstance.follow( account3, { from: account2 } );

        const t = await TwitterInstance.retrieveFollowing( { from: account2} );

        assert.equal(t[0], account3, "followed wrong user" );

    });

    it("should check if user can get latest tweets", async () => {

        // Populate with different tweets:

        let users = [account2, account3, account4, account5];
    
        for(let i = 0; i < 50; i++) {

            await TwitterInstance.tweet( ("hey, tweet #"+i+"!"), { from: users[i % 4] } );
        
        }

        const lt = await TwitterInstance.getLatestTweets(20, 0, { from: account2 } );

        assert.equal(lt.length, 20, "wrong batch size or paging exceeded");

    });

    it("should check if user can get latest tweets of someone", async () => {

        // Populate with different tweets:

        let users = [account2, account3, account4, account5];
    
        for(let i = 0; i < 50; i++) {

            await TwitterInstance.tweet( ("hey, tweet #"+i+"!"), { from: users[i % 4] } );
        
        }

        const to = await TwitterInstance.getTweetsOf(account2, 5, 0, { from: account2 } );

        assert.equal(to.length, 5, "wrong batch size or paging exceeded");

    });

});
