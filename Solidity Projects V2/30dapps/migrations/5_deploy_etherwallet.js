const EtherWallet = artifacts.require("EtherWallet");

module.exports = async function (deployer, _network, accounts) {
  const owner = accounts[0];
  await deployer.deploy(EtherWallet, owner);
};
