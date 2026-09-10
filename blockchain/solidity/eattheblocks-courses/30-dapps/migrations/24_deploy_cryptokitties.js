const CryptoKitties = artifacts.require("CryptoKitties");

module.exports = async function (deployer, _network, accounts) {

  await deployer.deploy(CryptoKitties, "MyToken", "MTK", "https://example.com/token/", { from: accounts[0] });

};
