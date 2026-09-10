const Crud = artifacts.require("Crud");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("Crud", () => {

    let CrudInstance;
    
    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        CrudInstance = await Crud.new();
    
    });

    it("should make user id=0 equal to adam", async () => {
        
        await CrudInstance.create("adam");
        let name = await CrudInstance.read(0);

        assert.equal(name, "adam", "Should set User (id=0) to adam"); 

    });

    it("should return number of users as 0 at start", async () => {

        assert.equal(await CrudInstance.nextId(), 0, "There are some users set."); 
    
    });

    it("should make 2 users and update the second's name", async () => {

        await CrudInstance.create("adam");
        await CrudInstance.create("eve");

        assert.equal(await CrudInstance.read(0), "adam", "Should set User (id=0) to adam");
        assert.equal(await CrudInstance.read(1), "eve", "Should set User (id=1) to eve");

        await CrudInstance.update(1, "eve2");
        assert.equal(await CrudInstance.read(1), "eve2", "Should set User (id=1) to eve2");
    
    });

    it("should make 3 users and destroy the second one + nextID remains as 3", async () => {

        await CrudInstance.create("adam");
        await CrudInstance.create("eve");
        await CrudInstance.create("john");

        assert.equal(await CrudInstance.read(0), "adam", "Should set User (id=0) to adam");
        assert.equal(await CrudInstance.read(1), "eve", "Should set User (id=1) to eve");
        assert.equal(await CrudInstance.read(2), "john", "Should set User (id=2) to john");
        
        await CrudInstance.destroy(1);
        assert.equal(await CrudInstance.read(1), "", "Should be empty after delete");
 
        assert.equal(await CrudInstance.nextId(), 3, "Should be 3 after delete");

    });

});