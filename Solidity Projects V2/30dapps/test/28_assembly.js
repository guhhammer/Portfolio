const Assembly = artifacts.require("Assembly");
const Proxy = artifacts.require("Proxy");
const TargetA = artifacts.require("TargetA");
const TargetB = artifacts.require("TargetB");
const truffleAssert = require("truffle-assertions");

const { takeSnapshot, revertToSnapshot } = require("./helpers");

contract("Assembly", (accounts) => {

    let AssemblyInstance;

    const [account1] = accounts;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
        
        AssemblyInstance = await Assembly.new({ from: account1 });

    });

    it("should check if contract is deployed", async () => {

        assert(AssemblyInstance.address !== "", "Contract not deployed");

    });

    it("should check IFS work right in solidity and assembly", async () => {

        const s1 = await AssemblyInstance.ifSolidity(1);
        const s2 = await AssemblyInstance.ifSolidity(2);
        const s3 = await AssemblyInstance.ifSolidity(3);

        const a1 = await AssemblyInstance.ifAssembly(1);
        const a2 = await AssemblyInstance.ifAssembly(2);
        const a3 = await AssemblyInstance.ifAssembly(3);

        assert(s1.toString() === "10", "value retrieved is wrong");
        assert(s2.toString() === "20", "value retrieved is wrong");
        assert(s3.toString() === "100", "value retrieved is wrong");

        assert(a1.toString() === "10", "value retrieved is wrong");
        assert(a2.toString() === "20", "value retrieved is wrong");
        assert(a3.toString() === "100", "value retrieved is wrong");

    });

    it("should check FORS work right in solidity and assembly", async () => {

        const arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

        const sf = await AssemblyInstance.sumSolidity(arr);

        const af = await AssemblyInstance.sumAssembly(arr);

        assert(sf.toString() === "55", "sum value is wrong");
        assert(af.toString() === "55", "sum value is wrong");

    });

    it("should check if address is contract", async () => {

        const a = await AssemblyInstance.isContract(account1);

        const b = await AssemblyInstance.isContract(AssemblyInstance.address);

        assert(a == false && b == true, "function is not working properly");

    });

});

contract("TargetA", (accounts) => {

    let TargetAInstance;

    const [account1] = accounts;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();

        TargetAInstance = await TargetA.new({ from: account1 });

    });

    it("should check if contract is deployed", async () => {

        assert(TargetAInstance.address !== "", "Contract not deployed");

    });

    it("should check if add(a, b) returns a value only", async () => {

        const res = await TargetAInstance.add(5, 7);

        assert(res.toString() === "5", "Contract logic not as defined");

    });

});

contract("TargetB", (accounts) => {

    let TargetBInstance;

    const [account1] = accounts;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();

        TargetBInstance = await TargetB.new({ from: account1 });

    });

    it("should check if contract is deployed", async () => {

        assert(TargetBInstance.address !== "", "Contract not deployed");

    });

    it("should check if add(a, b) returns a + b value only", async () => {

        const res = await TargetBInstance.add(5, 7);

        assert(res.toString() === "12", "Contract logic not as defined");

    });

});

contract("Proxy", (accounts) => {

    contract("Proxy", (accounts) => {

        let ProxyInstance;

        const [account1] = accounts;

        afterEach(async () => {
        
            await revertToSnapshot();
            
        });
        
        beforeEach(async () => {
        
            snapshotId = await takeSnapshot();
            
            ProxyInstance = await Proxy.new({ from: account1 });
        });

        it("should check if contract is deployed", async () => {
            assert(ProxyInstance.address !== "", "Contract not deployed");
        });

        it("should check if contract admin is right", async () => {
            const admin = await ProxyInstance.admin();
            assert.equal(admin, account1, "Contract admin is wrong");
        });

        it("should update implementation address", async () => {
            const TargetAInstance = await TargetA.new({ from: account1 });
            await ProxyInstance.update(TargetAInstance.address);
            const impl = await ProxyInstance.implementation();
            assert.equal(impl, TargetAInstance.address, "did not update implementation");
        });

        it("should proxy call methods of target a and target b", async () => {
            const TargetAInstance = await TargetA.new({ from: account1 });
            const TargetBInstance = await TargetB.new({ from: account1 });

            await ProxyInstance.update(TargetAInstance.address);
            const proxyAsA = await TargetA.at(ProxyInstance.address);
            const ta = await proxyAsA.add(5, 7);
            assert.equal(ta.toString(), "5", "TargetA should return 1st argument");

            await ProxyInstance.update(TargetBInstance.address);
            const proxyAsB = await TargetB.at(ProxyInstance.address);
            const tb = await proxyAsB.add(5, 7);
            assert.equal(tb.toString(), "12", "TargetB should return sum");
        });

        it("should delegate call to target and store result", async () => {
            const TargetBInstance = await TargetB.new({ from: account1 });

            await ProxyInstance.update(TargetBInstance.address);

            const proxyAsB = await TargetB.at(ProxyInstance.address);
            const result = await proxyAsB.add(2, 3);
            assert.equal(result.toString(), "5", "Expected result to be 5");

            // If TargetB stores `lastResult`, the proxy must be storage-compatible
            if (typeof proxyAsB.lastResult === "function") {
                const lastResult = await proxyAsB.lastResult();
                assert.equal(lastResult.toString(), "5", "lastResult should be 5");
            }
        });

    });

});

