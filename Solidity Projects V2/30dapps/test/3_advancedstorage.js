const AdvancedStorage = artifacts.require("AdvancedStorage");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("AdvancedStorage", (accounts) => {

    let AdvancedStorageInstance;
    const [account1, account2, account3] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        AdvancedStorageInstance = await AdvancedStorage.new();
    
    });

    it("should allow different accounts to set and get data", async () => {
       
        await AdvancedStorageInstance.setData("Data from Account 1", { from: account1 });
        await AdvancedStorageInstance.setData("Data from Account 2", { from: account2 });

        const data1 = await AdvancedStorageInstance.getData({ from: account1 });
        const data2 = await AdvancedStorageInstance.getData({ from: account2 });

        assert.equal(data1, "Data from Account 1", "Account 1 data mismatch");
        assert.equal(data2, "Data from Account 2", "Account 2 data mismatch");
   
    });

    it("should return empty string if no data is set", async () => {
   
        const data3 = await AdvancedStorageInstance.getData({ from: account3 });
        assert.equal(data3, "", "Account 3 should have no data");
   
    });
 
});