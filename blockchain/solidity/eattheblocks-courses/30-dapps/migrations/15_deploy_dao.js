const DAO = artifacts.require("DAO");

module.exports = async function (deployer, _network, accounts) {
  
  await deployer.deploy(DAO, 1000, {from: accounts[0]});

};
