const EmptyContract = artifacts.require("EmptyContract");
const truffleAssert = require("truffle-assertions");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("EmptyContract", (accounts) => {

    let EmptyContractInstance;

    const [account1] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        EmptyContractInstance = await EmptyContract.new({from: account1});

    });

    it("should check if contract is deployed", async () => {
       
        assert(EmptyContractInstance.address !== "", "Contract not deployed");
    
    });

});