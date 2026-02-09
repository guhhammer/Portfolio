const Lottery = artifacts.require("Lottery");

module.exports = async function (deployer, _network, accounts) {
  
  await deployer.deploy(Lottery, 5, {from: accounts[0]});

};
