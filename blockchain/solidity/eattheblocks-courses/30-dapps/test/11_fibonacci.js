const Fibonacci = artifacts.require("Fibonacci");
const truffleAssert = require("truffle-assertions");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("Fibonacci", (accounts) => {

    let FibonacciInstance;

    const [account1] = accounts;

    let snapshotId;
    
    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        FibonacciInstance = await Fibonacci.new({from: account1});
        
    });

    it("should retrieve fib(0) = 0", async () => {
        
        assert.equal(await FibonacciInstance.fib(0, {from: account1}), 0, "Should be 0");
    
    });

    it("should retrieve fib(2) = 1", async () => {
        
        assert.equal(await FibonacciInstance.fib(1, {from: account1}), 1, "Should be 1");
    
    });

    it("should retrieve fib(2) = 1", async () => {
        
        assert.equal(await FibonacciInstance.fib(2, {from: account1}), 1, "Should be 1");
    
    });

    it("should retrieve fib(10) = 55", async () => {
        
        assert.equal(await FibonacciInstance.fib(10, {from: account1}), 55, "Should be 55");
    
    });

});