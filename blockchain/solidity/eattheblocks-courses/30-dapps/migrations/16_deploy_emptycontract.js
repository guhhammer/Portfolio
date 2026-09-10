const EmptyContract = artifacts.require("EmptyContract");

module.exports = async function (deployer) {
  await deployer.deploy(EmptyContract);
};
