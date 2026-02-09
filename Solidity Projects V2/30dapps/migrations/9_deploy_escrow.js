const Escrow = artifacts.require("Escrow");

module.exports = async function (deployer, _network, accounts) {
  await deployer.deploy(Escrow, accounts[1], accounts[2], web3.utils.toWei("5", "ether"), {from: accounts[0]});
};
