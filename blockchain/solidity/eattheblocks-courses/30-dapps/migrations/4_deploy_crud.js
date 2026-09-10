const Crud = artifacts.require("Crud");

module.exports = async function (deployer) {
  await deployer.deploy(Crud);
};
