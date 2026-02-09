const MultiSigWallet = artifacts.require("MultiSigWallet");

module.exports = async function (deployer, _network, accounts) {
  
  await deployer.deploy(MultiSigWallet, [ accounts[0], accounts[1], accounts[2] ], 2, {from: accounts[0]});

};
