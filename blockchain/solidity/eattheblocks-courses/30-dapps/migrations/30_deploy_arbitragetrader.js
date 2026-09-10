const dex = artifacts.require("Dex");
const oracle = artifacts.require("Oracle");
const arb = artifacts.require("ArbitrageTrader");

module.exports = async function (deployer, _network, accounts) {

    await deployer.deploy(dex, { from: accounts[0] });
    await deployer.deploy(oracle, [accounts[0]], { from: accounts[0] });
    await deployer.deploy(arb, { from: accounts[0] });

};
