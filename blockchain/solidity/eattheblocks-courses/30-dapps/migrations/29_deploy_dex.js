const DAI = artifacts.require("DAI");
const AAVE = artifacts.require("AAVE");
const LDO = artifacts.require("LDO");
const LINK = artifacts.require("LINK");
const MKR = artifacts.require("MKR");
const SHIB = artifacts.require("SHIB");
const UNI = artifacts.require("UNI");
const USDC = artifacts.require("USDC");
const USDT = artifacts.require("USDT");
const WETH = artifacts.require("WETH");

const DEX = artifacts.require("DEX");

module.exports = async function (deployer, _network, accounts) {

    await deployer.deploy(DAI, { from: accounts[0] });
    await deployer.deploy(AAVE, { from: accounts[0] });
    await deployer.deploy(LDO, { from: accounts[0] });
    await deployer.deploy(LINK, { from: accounts[0] });
    await deployer.deploy(MKR, { from: accounts[0] });
    await deployer.deploy(SHIB, { from: accounts[0] });
    await deployer.deploy(UNI, { from: accounts[0] });
    await deployer.deploy(USDC, { from: accounts[0] });
    await deployer.deploy(USDT, { from: accounts[0] });
    await deployer.deploy(WETH, { from: accounts[0] });

    await deployer.deploy(DEX, { from: accounts[0] });

};
