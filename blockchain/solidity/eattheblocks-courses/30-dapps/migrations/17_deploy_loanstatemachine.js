const LoanStateMachine = artifacts.require("LoanStateMachine");

module.exports = async function (deployer, _network, accounts) {
  
  await deployer.deploy(LoanStateMachine, 
                        web3.utils.toWei("5", "ether"),
                        web3.utils.toWei("800", "finney"), 
                        3600,
                        accounts[1],
                        accounts[0],
                        {from: accounts[0]});

};
