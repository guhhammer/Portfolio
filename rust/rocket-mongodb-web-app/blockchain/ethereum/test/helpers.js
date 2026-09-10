async function increaseTime(duration) {
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

const provider = web3.currentProvider;

async function takeSnapshot() {
  return new Promise((resolve, reject) => {
    provider.send(
      { jsonrpc: "2.0", method: "evm_snapshot", id: new Date().getTime() },
      (err, result) => {
        if (err) return reject(err);
        resolve(result.result);
      }
    );
  });
}

async function revertToSnapshot(id) {
  return new Promise((resolve, reject) => {
    provider.send(
      { jsonrpc: "2.0", method: "evm_revert", params: [id], id: new Date().getTime() },
      (err, result) => {
        if (err) return reject(err);
        resolve(result.result);
      }
    );
  });
}

const ZERO_ADDRESS = '0x0000000000000000000000000000000000000000';

module.exports = {
    increaseTime,
    takeSnapshot,
    revertToSnapshot,
    ZERO_ADDRESS,
};                                                                                           
