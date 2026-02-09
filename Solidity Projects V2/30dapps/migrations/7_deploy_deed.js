const Deed = artifacts.require("Deed");

module.exports = async function (deployer, _network, accounts) {
  const currentTimestamp = 10; // 10 seconds from now.
  await deployer.deploy(Deed, accounts[0], accounts[1], currentTimestamp);
};
