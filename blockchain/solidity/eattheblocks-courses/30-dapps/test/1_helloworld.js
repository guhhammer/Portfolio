const HelloWorld = artifacts.require("HelloWorld");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("HelloWorld", () => {
    
    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
    
    });

    it("should return 'Hello, World!'", async () => {
        const helloWorldInstance = await HelloWorld.deployed();
        const message = await helloWorldInstance.hello();
        assert.equal(message, "Hello, World!", "The message should be 'Hello, World!'");
    });
});