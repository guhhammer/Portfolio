const StringManipulation = artifacts.require("StringManipulation");
const truffleAssert = require("truffle-assertions");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("StringManipulation", (accounts) => {

    let StringManipulationInstance;

    const [account1] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        StringManipulationInstance = await StringManipulation.new({from: account1});
        
    });

    it("should retrieve length 0 for empty string", async () => {
        
        assert.equal(await StringManipulationInstance.length("", {from: account1}), 0, "Should be 0");
    
    });

    it("should retrieve length 4 for string 'life'", async () => {
        
        assert.equal(await StringManipulationInstance.length("life", {from: account1}), 4, "Should be 4");
    
    });
    
    it("should concatenate str1 'life' with str2 ' is good'", async () => {

        assert.equal(await StringManipulationInstance.concatenate("life", " is good", {from: account1}), "life is good", "Should be 'life is good'");

    });

    it("should concatenate str1 'life' with str2 ''", async () => {

        assert.equal(await StringManipulationInstance.concatenate("life", "", {from: account1}), "life", "Should be 'life'");

    });

    it("should concatenate str1 '' with str2 'good'", async () => {

        assert.equal(await StringManipulationInstance.concatenate("", "good", {from: account1}), "good", "Should be 'good'");

    });
    
});