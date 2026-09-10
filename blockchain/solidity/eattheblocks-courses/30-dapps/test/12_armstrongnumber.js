const ArmstrongNumber = artifacts.require("ArmstrongNumber");
const truffleAssert = require("truffle-assertions");

const { takeSnapshot, revertToSnapshot } = require("./helpers");

contract("ArmstrongNumber", (accounts) => {

    let ArmstrongNumberInstance;

    const [account1] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        ArmstrongNumberInstance = await ArmstrongNumber.new({from: account1});
        
    });

    it("should return true for number 153", async () => {
        
        assert.equal(await ArmstrongNumberInstance.isArmstrongNumber(153, {from: account1}), true, "Should be true");
    
    });

    it("should return true for number 370", async () => {
        
        assert.equal(await ArmstrongNumberInstance.isArmstrongNumber(370, {from: account1}), true, "Should be true");
    
    });

    it("should return false for 42", async () => {
        
        assert.equal(await ArmstrongNumberInstance.isArmstrongNumber(42, {from: account1}), false, "Should be false");

    });

});