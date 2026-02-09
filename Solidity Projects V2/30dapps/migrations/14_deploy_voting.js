const Voting = artifacts.require("Voting");

module.exports = async function (deployer, _network, accounts) {
  
  await deployer.deploy(Voting, {from: accounts[0]});

};
