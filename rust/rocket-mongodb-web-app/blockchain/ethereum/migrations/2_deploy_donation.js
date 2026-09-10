const Donation = artifacts.require("Donation");

module.exports = async function (deployer, _network, accounts) {
  
  await deployer.deploy(Donation, {from: accounts[0]});

};
              