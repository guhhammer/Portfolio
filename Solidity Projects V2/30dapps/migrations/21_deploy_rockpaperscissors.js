const RockPaperScissors = artifacts.require("RockPaperScissors");

module.exports = async function (deployer, _network, _accounts) {
  
  await deployer.deploy(RockPaperScissors);

};