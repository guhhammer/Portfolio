const SplitPayment = artifacts.require("SplitPayment");

module.exports = async function (deployer) {
  await deployer.deploy(SplitPayment);
};
