const MyERC20 = artifacts.require("MyERC20");

module.exports = async function (deployer, _network, accounts) {
  
  await deployer.deploy(MyERC20, "Artemis", "ATS", 18, 100_000_000_000, {from: accounts[0]});

};