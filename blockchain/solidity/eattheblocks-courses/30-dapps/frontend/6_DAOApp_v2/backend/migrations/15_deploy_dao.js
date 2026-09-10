const DAO = artifacts.require("DAO");

module.exports = async function (deployer, _network, accounts) {
  
  await deployer.deploy(DAO, 60*60*24, {from: accounts[0]});

};
