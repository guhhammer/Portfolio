const defi = artifacts.require("DeFi");

module.exports = async function (deployer, _network, accounts) {

    await deployer.deploy(defi, { from: accounts[0] });

};
