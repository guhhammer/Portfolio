const DeedMultiPayouts = artifacts.require("DeedMultiPayouts");
const truffleAssert = require("truffle-assertions");

const {takeSnapshot, revertToSnapshot} = require("./helpers");

contract("DeedMultiPayouts", (accounts) => {

    let DeedMultiPayoutsInstance;

    const [account1, account2, account3] = accounts;

    const _10secondsFromNow = 10;

    let snapshotId;

    afterEach(async () => {
        
        await revertToSnapshot();
        
    });
    
    beforeEach(async () => {
    
        snapshotId = await takeSnapshot();
    
        
        DeedMultiPayoutsInstance = await DeedMultiPayouts.new(account1, account2, _10secondsFromNow, {
            from: account1,
            value: web3.utils.toWei("2", "ether"),
        });

    });

    it("should revert when non-beneficary tries to withdraw", async () => {

        // Try to withdraw from a non-beneficiary account (account3)
        await truffleAssert.reverts(DeedMultiPayoutsInstance.withdraw({ from: account3 }), "beneficiary only");

        // Try to withdraw from a lawyer account (account1)
        await truffleAssert.reverts(DeedMultiPayoutsInstance.withdraw({ from: account1 }), "beneficiary only");

    });

    it("should revert if beneficiary tries to send it to early", async () => {

        // Try to withdraw from a non-lawyer account (account2)
        await truffleAssert.reverts(DeedMultiPayoutsInstance.withdraw({ from: account2 }), "too early");

    });

    it("should revert once all payouts have been withdrawn", async () => {

        // safely parse the BN to a JS number
        const _payouts = parseInt((await DeedMultiPayoutsInstance.PAYOUTS()).toString(), 10);

        // 1) Empty out all payouts
        for (let i = 0; i < _payouts; i++) {
            await increaseTime(30);
            await DeedMultiPayoutsInstance.withdraw({ from: account2 });
        }

        await truffleAssert.reverts(DeedMultiPayoutsInstance.withdraw({ from: account2 }), "payouts are over");

    });

    it("should give correct balance to beneficiary after multiple withdraws", async () => {

        const startBalance = web3.utils.toBN(await web3.eth.getBalance(account2));
        const payoutAmount = web3.utils.toWei("2", "ether") / 10;
        const _payouts = parseInt((await DeedMultiPayoutsInstance.PAYOUTS()).toString(), 10);

        // Ensure payouts become available
        await increaseTime(1000); // Enough time for all payouts

        // Perform withdrawals
        for (let i = 0; i < _payouts; i++) {
            await DeedMultiPayoutsInstance.withdraw({ from: account2 });
            await increaseTime(10); // Advance for next payout
        }

        const endBalance = web3.utils.toBN(await web3.eth.getBalance(account2));
        const received = endBalance.sub(startBalance);

        const expected = web3.utils.toBN(payoutAmount).mul(web3.utils.toBN(_payouts));

        const tolerance = web3.utils.toBN(web3.utils.toWei("0.01", "ether")); // 0.01 ETH

        assert(
            received.sub(expected).abs().lte(tolerance),
            `Beneficiary's received balance is incorrect: got ${received}, expected ${expected} ± ${tolerance}`
        );

    });


});

const increaseTime = async (duration) => {
    await web3.currentProvider.send(
        {
            jsonrpc: '2.0',
            method: 'evm_increaseTime',
            params: [duration],
            id: new Date().getTime(),
        },
        () => { }
    );

    await web3.currentProvider.send(
        {
            jsonrpc: '2.0',
            method: 'evm_mine',
            id: new Date().getTime() + 1,
        },
        () => { }
    );
};
