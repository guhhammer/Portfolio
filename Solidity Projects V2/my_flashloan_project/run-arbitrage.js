require('dotenv').config();
const Web3 = require('web3');
const { ChainId, Token , TokenAmount, Pair } = require('@uniswap/sdk');
const abis = require('./abis');
const { mainnet: addresses } = require('./addresses');

const web3 = new Web3(new Web3.providers.WebsocketProvider(process.env.INFURA_WSS));

web3.eth.accounts.wallet.add(process.env.PRIVATE_KEY);


const kyber = new web3.eth.Contract(
	abis.kyber.kyberNetworkProxy,
	addresses.kyber.kyberNetworkProxy
);

const AMOUNT_ETH = 100;

const RECENT_ETH_PRICE = 1000 ; // need to update to recent price regularly.

const AMOUNT_ETH_WEI = web3.utils.toWei(AMOUNT_ETH.toString());
const AMOUNT_DAI_WEI = web3.utils.toWei((AMOUNT_ETH * RECENT_ETH_PRICE).toString());

// Subscription test
const init = async () => {
  const [dai, weth] = await Promise.all(
    [addresses.tokens.dai, addresses.tokens.weth].map(tokenAddress => Token.fetchData(ChainId.MAINNET, tokenAddress)
  ));

  const daiWeth = await Pair.fetchData(dai, weth);

  web3.eth.subscribe('newBlockHeaders')
    .on('data', async block => {
    console.log('New block:', block.number);
    
    const kyberResults = await Promise.all([
          kyber
            .methods
            .getExpectedRate(
              addresses.tokens.dai, 
              '0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee', 
              AMOUNT_DAI_WEI
            ) 
            .call(),
          kyber
            .methods
            .getExpectedRate(
              '0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee', 
              addresses.tokens.dai, 
              AMOUNT_ETH_WEI
            ) 
            .call()
      ]);

      const kyberRates = { 
        buy: parseFloat(1 / (kyberResults[0].expectedRate / (10 ** 18))),
        sell: parseFloat(kyberResults[1].expectedRate / (10 ** 18))
      };
      console.log('Kyber ETH/DAI', kyberRates);

      const uniswapResults = await Promise.all([
        daiWeth.getOutputAmount(new TokenAmount(dai, AMOUNT_DAI_WEI)),
        daiWeth.getInputAmount(new TokenAmount(weth, AMOUNT_ETH_WEI))
      ]);

      const uniswapRates = { 
        buy: parseFloat(AMOUNT_DAI_WEI / (uniswapResults[0][0].toExact() * 10 ** 18)),
        sell: parseFloat(uniswapResults[1][0].toExact() / AMOUNT_ETH)
      };

      console.log('Uniswap ETH/DAI', uniswapRates);

      const gasPrice = await web3.eth.getGasPrice();
      const txCost = 200000 * parseInt(gasPrice);
      const currentEthPrice = (uniswapRates.buy + uniswapRates.sell) / 2;
      const profit1 = (parseInt(AMOUNT_ETH_WEI) / 10 ** 18 ) * (uniswapRates.sell - kyberRates.buy) - (txCost / 10 ** 18) * currentEthPrice;
      const profit2 = (parseInt(AMOUNT_DAI_WEI) / 10 ** 18 ) * (kyberRates.buy - uniswapRates.sell) - (txCost / 10 ** 18) * currentEthPrice;

      if(profit1 > 0) {
        console.log('Arbitrage opportunity found: Buy on Uniswap, Sell on Kyber');
        console.log('Uniswap buy price:', uniswapRates.buy);
        console.log('Kyber sell price:', kyberRates.sell);
        console.log('Profit:', profit1);

      } 
      
      if(profit2 > 0) {
        console.log('Arbitrage opportunity found: Buy on Kyber, Sell on Uniswap');
        console.log('Kyber buy price:', kyberRates.buy);
        console.log('Uniswap sell price:', uniswapRates.sell);
        console.log('Profit:', profit2);
      }

    })
    .on('error', err => console.error('Sub error:', err));
  
  }

init().catch(err => {
  console.error('Error in init:', err);
  process.exit(1);
}); 
