const MyERC721 = artifacts.require("MyERC721");
const truffleAssert = require("truffle-assertions");

const { takeSnapshot, revertToSnapshot } = require("./helpers");

contract("MyERC721", (accounts) => {

    let myERC721Instance;

    const [account1, account2, account3] = accounts;
    const name = "MyToken";
    const symbol = "MTK";
    const baseURI = "https://example.com/token/";

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        myERC721Instance = await MyERC721.new(name, symbol, baseURI, {from: account1});
    
    });

    it("should check if contract is deployed", async () => {
        assert(myERC721Instance.address !== "", "Contract not deployed");
    });

    it("should return correct name, symbol and base URI", async () => {
        const actualName = await myERC721Instance.name();
        const actualSymbol = await myERC721Instance.symbol();
        const actualBaseURI = await myERC721Instance.tokenURIBase();

        assert.equal(actualName, name, "Incorrect name");
        assert.equal(actualSymbol, symbol, "Incorrect symbol");
        assert.equal(actualBaseURI, baseURI, "Incorrect base URI");
    });

});
