const Ebay = artifacts.require("Ebay");

module.exports = async function (deployer, _network, accounts) {

  await deployer.deploy(Ebay, { from: accounts[0] });

};
