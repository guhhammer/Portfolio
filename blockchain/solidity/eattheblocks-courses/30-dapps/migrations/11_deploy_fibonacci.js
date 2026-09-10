const Fibonacci = artifacts.require("Fibonacci");

module.exports = async function (deployer, _network) {
  await deployer.deploy(Fibonacci);
};
