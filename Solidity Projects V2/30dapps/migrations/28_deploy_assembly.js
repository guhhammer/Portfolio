const Assembly = artifacts.require("Assembly");
const Proxy = artifacts.require("Proxy");
const TargetA = artifacts.require("TargetA");
const TargetB = artifacts.require("TargetB");

module.exports = async function (deployer, _network, accounts) {

  await deployer.deploy(Assembly, { from: accounts[0] });
  await deployer.deploy(Proxy, { from: accounts[0] });
  await deployer.deploy(TargetA, { from: accounts[0] });
  await deployer.deploy(TargetB, { from: accounts[0] });

};
