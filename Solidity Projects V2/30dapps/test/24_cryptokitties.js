const CryptoKitties = artifacts.require("CryptoKitties");
const truffleAssert = require("truffle-assertions");

const { takeSnapshot, revertToSnapshot } = require("./helpers");

contract("CryptoKitties", (accounts) => {

    let CryptoKittiesInstance;

    const [account1, account2] = accounts;
    const name = "MyToken";
    const symbol = "MTK";
    const baseURI = "https://example.com/token/";

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        CryptoKittiesInstance = await CryptoKitties.new(name, symbol, baseURI, {from: account1});
    
    });

    it("should check if contract is deployed", async () => {
        assert(CryptoKittiesInstance.address !== "", "Contract not deployed");
    });

    it("should return correct name, symbol and base URI", async () => {
        const actualName = await CryptoKittiesInstance.name();
        const actualSymbol = await CryptoKittiesInstance.symbol();
        const actualBaseURI = await CryptoKittiesInstance.tokenURIBase();

        assert.equal(actualName, name, "Incorrect name");
        assert.equal(actualSymbol, symbol, "Incorrect symbol");
        assert.equal(actualBaseURI, baseURI, "Incorrect base URI");
    });

    it("should mint a new kitty", async () => { 

        await CryptoKittiesInstance.mint({from: account1});

        const kitty = await CryptoKittiesInstance.getKitty(0, {from: account1});

        assert(kitty[0], 0, "id is not 0");
        assert(kitty[1], 1, "generation is not 1");
        assert(kitty[2] < 10, "random gene a is not (0..9)");
        assert(kitty[3] < 10, "random gene b is not (0..9)");

    });

    it("should breed a new kitty", async () => { 

        await CryptoKittiesInstance.mint({from: account1});
        await CryptoKittiesInstance.mint({from: account1});
        await CryptoKittiesInstance.mint({from: account1});
        await CryptoKittiesInstance.mint({from: account1});

        await CryptoKittiesInstance.breed(0, 1, {from: account1, gas: 5000000 });

        const breeded = await CryptoKittiesInstance.getKitty(4);

        assert(breeded[0], 4, "id is not 0");
        assert(breeded[1], 2, "generation is not 1");
        assert(breeded[2] < 10, "random gene a is not (0..9)");
        assert(breeded[3] < 10, "random gene b is not (0..9)");

    });

    it("should try to mint as non-admin and fail", async () => { 

        await truffleAssert.reverts(
            CryptoKittiesInstance.mint({from: account2}),
            "only admin"
        );

    });

    it("should try to breed with non existent id and fail", async () => {

        await CryptoKittiesInstance.mint({from: account1});
        await CryptoKittiesInstance.mint({from: account1});

        await truffleAssert.reverts(
            CryptoKittiesInstance.breed(0, 5, {from: account1}),
            "the 2 kitties must exist"
        );

    });

    it("should try to breed with other address's kitty and fail", async () => {

        await CryptoKittiesInstance.mint({from: account1});
        await CryptoKittiesInstance.mint({from: account1});

        await CryptoKittiesInstance.safeTransferFrom(account1, account2, 1);

        await truffleAssert.reverts(
            CryptoKittiesInstance.breed(0, 1, {from: account1}),
            "msg.sender must own the 2 kitties"
        );

    });

});
