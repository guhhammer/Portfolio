const StringManipulation = artifacts.require("StringManipulation");

module.exports = async function (deployer, _network) {
  await deployer.deploy(StringManipulation);
};
