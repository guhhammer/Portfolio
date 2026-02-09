const ArmstrongNumber = artifacts.require("ArmstrongNumber");

module.exports = async function (deployer, _network) {
  await deployer.deploy(ArmstrongNumber);
};
