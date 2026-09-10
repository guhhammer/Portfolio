const SimpleStorage = artifacts.require("SimpleStorage");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("SimpleStorage", () => {

    let SimpleStorageInstance;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        SimpleStorageInstance = await SimpleStorage.new();
    
    });

    it("should return an empty string on start ('').", async () => {

        assert.equal(await SimpleStorageInstance.get(), "", "The initial value should be an empty string.");

    });

    it("should set the value to 'novo'.", async () => {

        await SimpleStorageInstance.set("novo");
        assert.equal(await SimpleStorageInstance.get(), "novo", "The value should be 'novo'.");

    });

    it("should get the value on each fresh state ''.", async () => {

        assert.equal(await SimpleStorageInstance.get(), "", "The value should be ''.");

    });
});