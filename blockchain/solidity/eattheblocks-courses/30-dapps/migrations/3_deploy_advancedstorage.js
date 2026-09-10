const AdvancedStorage = artifacts.require("AdvancedStorage");

module.exports = async function (deployer) {
  await deployer.deploy(AdvancedStorage);
};
