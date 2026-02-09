const ICO = artifacts.require("ICO");

module.exports = async function (deployer, _network, accounts) {
  
  await deployer.deploy(ICO, "Artemis", "ATS", 18, 100_000_000_000, {from: accounts[0]});

};