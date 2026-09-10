const Twitter = artifacts.require("Twitter");

module.exports = async function (deployer, _network, accounts) {

  await deployer.deploy(Twitter, { from: accounts[0] });

};
