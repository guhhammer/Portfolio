const EventOrganizer = artifacts.require("EventOrganizer");

module.exports = async function (deployer, _network) {
  
  await deployer.deploy(EventOrganizer);

};
