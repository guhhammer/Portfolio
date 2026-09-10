const Tinder = artifacts.require("Tinder");

module.exports = async function (deployer, _network, accounts) {

  await deployer.deploy(Tinder, { from: accounts[0] });

};
