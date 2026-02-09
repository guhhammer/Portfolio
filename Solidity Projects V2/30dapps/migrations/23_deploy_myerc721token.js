const MyERC721 = artifacts.require("MyERC721");

module.exports = async function (deployer, _network, accounts) {

  await deployer.deploy(MyERC721, "MyToken", "MTK", "https://example.com/token/", { from: accounts[0] });

};
