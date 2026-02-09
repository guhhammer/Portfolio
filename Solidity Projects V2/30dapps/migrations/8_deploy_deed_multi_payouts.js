const DeedMultiPayouts = artifacts.require("DeedMultiPayouts");

module.exports = async function (deployer, _network, accounts) {
  const currentTimestamp = 10; // 10 seconds from now.
  await deployer.deploy(DeedMultiPayouts, accounts[0], accounts[1], currentTimestamp);
};
