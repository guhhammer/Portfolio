// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;
pragma experimental ABIEncoderV2;


import "./20_myerc20token.sol";


/**
 * @title SafeMath
 * @dev Math operations with safety checks that throw on error
 * 
 *  Imported from OpenZeppelin Foundation.
 */
library SafeMath {

    /**
     * @dev Adds two numbers, throws on overflow.
     */
    function add(uint256 a, uint256 b) internal pure returns (uint256 c) {
        c = a + b;
        assert(c >= a);
        return c;
    }

    /**
     * @dev Subtracts two numbers, throws on overflow (i.e. if subtrahend is greater than minuend).
     */
    function sub(uint256 a, uint256 b) internal pure returns (uint256) {
        assert(b <= a);
        return a - b;
    }

    /**
     * @dev Multiplies two numbers, throws on overflow.
     */
    function mul(uint256 a, uint256 b) internal pure returns (uint256 c) {
        if (a == 0) {
        return 0;
        }
        c = a * b;
        assert(c / a == b);
        return c;
    }

    /**
     * @dev Integer division of two numbers, truncating the quotient.
     */
    function div(uint256 a, uint256 b) internal pure returns (uint256) {
        // assert(b > 0); // Solidity automatically throws when dividing by 0
        // uint256 c = a / b;
        // assert(a == b * c + a % b); // There is no case in which this doesn't hold
        return a / b;
    }

}


contract AAVE is MyERC20 { constructor() MyERC20("Aave Token", "AAVE", 18, 16_000_000) {} }
contract DAI is MyERC20 { constructor() MyERC20("Dai Stablecoin", "DAI", 18, 1000000000 ) {} }
contract LDO is MyERC20 { constructor() MyERC20("Lido DAO Token", "LDO", 18, 1_000_000_000) {} }
contract LINK is MyERC20 { constructor() MyERC20("ChainLink Token", "LINK", 18, 1_000_000_000) {} }
contract MKR is MyERC20 { constructor() MyERC20("Maker", "MKR", 18, 1_000_000) {} }
contract SHIB is MyERC20 { constructor() MyERC20("Shiba Inu", "SHIB", 18, 589_000_000_000_000) {} }
contract UNI is MyERC20 { constructor() MyERC20("Uniswap", "UNI", 18, 1_000_000_000) {} }
contract USDC is MyERC20 { constructor() MyERC20("USD Coin", "USDC", 6, 10_000_000_000) {} }
contract USDT is MyERC20 { constructor() MyERC20("Tether USD", "USDT", 6, 10_000_000_000) {} }
contract WETH is MyERC20 { constructor() MyERC20("Wrapped Ether", "WETH", 18, 5_000_000) {} }


contract DEX {


    using SafeMath for uint256;

    bytes32 constant DAI_ = bytes32('DAI');
    
    uint256 public nextUsers;
    uint256 public nextOrderId;
    uint256 public nextTradeId;

    address public admin;

    uint8 public actualFeePerThousand;


    event AddedToken(
        
        uint256 indexed _timestamp,
        address indexed _byAdmin, 
        bytes32 _ticker, 
        address _tokenAddress
    
    );

    event Deposit(
       
        uint256 indexed _timestamp, 
        address indexed _user, 
        string _username, 
        uint256 _amount
    
    );

    event NewTrade(
    
        uint256 _tradeId, 
        uint256 _orderId, 
        bytes32 indexed _ticker, 
        address indexed _trader1,
        address indexed _trader2,
        uint256 _amount,
        uint256 _price,
        uint256 _date
    
    );

    event Withdraw( 
    
        uint256 indexed _timestamp, 
        address indexed _user, 
        string _username, 
        uint256 _amount
    
    );


    enum Side { BUY, SELL }

    struct Order {

        uint256 id;
        uint256 date;

        uint256 amount;
        uint256 filled;
        
        uint256 price;
        
        Side side;
        address trader;

        bytes32 ticker;

    }

    struct TokenERC20 {

        uint256 indexID;
        
        address tokenAddress;
        
        bytes32 ticker;

    }

    struct User {

        uint256 dateJoined; // first time registered, user may not define itself.

        address addr;

        string username;

        uint128 deposits;
        uint128 withdraws;

    }


    mapping(address => User) private users; // maybe mark private;

    mapping(bytes32 => TokenERC20) private listTokens;

    mapping(address => mapping(bytes32 => uint256)) private traderBalances;

    mapping(bytes32 => mapping(uint8 => Order[])) public orderBook;

    mapping(bytes32 => uint256[2]) public totalLiquidity;


    address[] private listUsers; 
    bytes32[] private tickersToTokenERC20; 


    modifier onlyAdmin() { require(msg.sender == admin, "only admin"); _; }

    modifier tokenExists(bytes32 _ticker) { require(listTokens[_ticker].tokenAddress != address(0), "token does not exist"); _;}

    modifier tokenIsNotDai(bytes32 _ticker) { require(_ticker != DAI_, "cannot trade DAI"); _; }


    constructor () { admin = msg.sender; actualFeePerThousand = 4; }


    function updateAdmin(address _admin) external onlyAdmin { admin = _admin; } 

    function changeFeesTo(uint8 fees) external { require(fees < 99, "cannot be >98"); actualFeePerThousand = fees; }


    function addToken(bytes32 _ticker, address _tokenAddress) external onlyAdmin {

        require(listTokens[_ticker].tokenAddress == address(0), "token ticker is registered");

        uint256 l = tickersToTokenERC20.length;

        listTokens[_ticker] = TokenERC20({

            indexID: l,
            tokenAddress: _tokenAddress,
            ticker: _ticker
        
        });

        tickersToTokenERC20.push(_ticker);

        emit AddedToken(block.timestamp, msg.sender, _ticker, _tokenAddress);

    }
    
    function deposit(bytes32 _ticker, uint256 _amount) external tokenExists(_ticker) {

        IERC20(listTokens[_ticker].tokenAddress).transferFrom(msg.sender, address(this), _amount);

        traderBalances[msg.sender][_ticker] = traderBalances[msg.sender][_ticker].add(_amount);

        _register(msg.sender);

        _increment(0);

        emit Deposit(block.timestamp, msg.sender, users[msg.sender].username, _amount);

    }

    function withdraw(bytes32 _ticker, uint256 _amount) external tokenExists(_ticker) {

        require(traderBalances[msg.sender][_ticker] >= _amount, "not enough funds");

        // fee = (_amount * fee_per_thousand) / 1000
        uint256 dexFee = (_amount.mul(actualFeePerThousand)) / 1000;
        uint256 uAmount = _amount.sub(dexFee);

        traderBalances[address(this)][_ticker] = traderBalances[address(this)][_ticker].add(dexFee);
        traderBalances[msg.sender][_ticker] = traderBalances[msg.sender][_ticker].sub(_amount);

        IERC20(listTokens[_ticker].tokenAddress).transfer(msg.sender, uAmount);

        _increment(1);

        emit Withdraw(block.timestamp, msg.sender, users[msg.sender].username, _amount);

    }

    function adminWithdraw(bytes32 _ticker, uint256 _amount, address _to) external onlyAdmin {

        require(traderBalances[address(this)][_ticker] >= _amount, "not enough funds");

        traderBalances[address(this)][_ticker] = traderBalances[address(this)][_ticker].sub(_amount);

        IERC20(listTokens[_ticker].tokenAddress).transfer(_to, _amount);

        emit Withdraw(block.timestamp, msg.sender, "_TO", _amount);

    }

    function getDeposits(bytes32 _ticker) external view returns(uint256) {

        return traderBalances[ (msg.sender == admin) ? address(this) : msg.sender ][_ticker];

    }

    function getTokens() external view returns(TokenERC20[] memory _tokens) {

        _tokens = new TokenERC20[](tickersToTokenERC20.length);

        for(uint i = 0; i < tickersToTokenERC20.length; i++) {

            TokenERC20 storage t = listTokens[tickersToTokenERC20[i]];
            _tokens[i] = TokenERC20( t.indexID, t.tokenAddress, t.ticker );

        }

    }

    function getOrders(bytes32 _ticker, Side _side) external view returns(Order[] memory) { return orderBook[_ticker][uint8(_side)]; }

    function createLimitOrder(
        bytes32 _ticker, 
        uint256 _amount,
        uint256 _price,
        Side _side
    ) 
        public
        tokenExists(_ticker) 
        tokenIsNotDai(_ticker)
    {

        uint8 decimals = MyERC20(listTokens[_ticker].tokenAddress).decimals();
        uint256 denominator = 10 ** uint256(decimals);

        if (_side == Side.SELL) {

            require(traderBalances[msg.sender][_ticker] >= _amount, "not enough funds");

        } else {

            require(traderBalances[msg.sender][DAI_] >= _amount.mul(_price).div(denominator), "not enough DAI");

        } 

        Order[] storage orders = orderBook[_ticker][uint8(_side)];

        Order[] storage against = orderBook[_ticker][uint8(_side == Side.SELL ? 0 : 1)];

        if (against.length != 0) {
            
            uint256 bestAsk = against[0].price;                   // lowest sell
            uint256 bestBid = against[against.length - 1].price;     // highest buy

            if (_side == Side.BUY) {
                // A buy must not pay *more* than the best ask
                require(_price < bestAsk, "limit buy crosses best ask");
            } else {
                // A sell must not ask *less* than the best bid
                require(_price > bestBid, "limit sell crosses best bid");
            }

        }

        Order memory newOrder = Order({

            id: nextOrderId,
            date: block.timestamp,

            amount: _amount,
            filled: 0,
        
            price: _price,
        
            side: _side,
            trader: msg.sender,
            ticker: _ticker

        });

        uint pos = _insertSorted(orders, _price);

        orders[pos] = newOrder; 

        nextOrderId = nextOrderId.add(1);

        totalLiquidity[_ticker][ (Side.SELL == _side ? 1 : 0) ] += _amount;

    }

    function createMarketSellOrder(
        bytes32 _ticker, 
        uint256 _amount, 
        Side _side
    ) 
        external
        tokenExists(_ticker)
        tokenIsNotDai(_ticker) 
    {

        require(_side == Side.SELL, "wrong call");

        require(traderBalances[msg.sender][_ticker] >= _amount, "not enough funds");

        uint256 max = totalLiquidity[_ticker][0] * 20;

        require(_amount <= (max / 100), "over 20% liquidity");

        Order[] storage orders = orderBook[_ticker][uint8(Side.BUY)];

        uint256 denominator = _denominator(_ticker);

        uint remaining = _amount;
        uint lastFillPrice;
        for (uint i = orders.length; i > 0 && remaining > 0; ) {
            i--;

            uint matched = _matcher(orders, remaining, i);

            if (matched > 0) { lastFillPrice = orders[i].price; }
 
            remaining = remaining.sub(matched); 

            totalLiquidity[_ticker][0] = totalLiquidity[_ticker][0].sub(matched);

            uint256 cost = matched.mul(orders[i].price).div(denominator);

            _executeMatcher(_side, _ticker, orders[i], msg.sender, matched, cost);
        
        }
      
        if (remaining > 0) {
            // convert unfilled portion into a limit order @ lastFillPrice
            createLimitOrder(_ticker, remaining, lastFillPrice, Side.SELL);
        }

        _shiftByFilled(0, orders.length, orders);

    }

    function createMarketBuyOrder(
        bytes32 _ticker, 
        uint256 _amount, 
        Side _side
    ) 
        external
        tokenExists(_ticker)
        tokenIsNotDai(_ticker) 
    {

        require(_side == Side.BUY, "wrong call");
        
        Order[] storage orders = orderBook[_ticker][uint8(Side.SELL)];

        uint256 max = totalLiquidity[_ticker][1] * 20;

        require(_amount <= (max / 100), "over 20% liquidity");

        uint256 denominator = _denominator(_ticker);

        uint remaining = _amount;
        uint lastFillPrice;
        for (uint i = 0; i < orders.length && remaining > 0; i++) {

            uint matched = _matcher(orders, remaining, i);

            if (matched > 0) { lastFillPrice = orders[i].price; }

            remaining = remaining.sub(matched); 

            totalLiquidity[_ticker][1] = totalLiquidity[_ticker][1].sub(matched);

            uint256 cost = matched.mul(orders[i].price).div(denominator);

            _executeMatcher(_side, _ticker, orders[i], msg.sender, matched, cost);
        
        }

        if (remaining > 0) {
            // convert unfilled portion into a limit order @ lastFillPrice
            createLimitOrder(_ticker, remaining, lastFillPrice, Side.BUY);
        }

        _shiftByFilled(0, orders.length, orders);

    }

    function _denominator(bytes32 _ticker) internal view returns(uint256) {

        return 10 ** uint256( MyERC20(listTokens[_ticker].tokenAddress).decimals());

    }

    function _matcher(Order[] storage orders, uint256 remaining, uint i) internal returns(uint) {

        uint available = orders[i].amount.sub(orders[i].filled);

        uint matched = (remaining > available) ? available : remaining;

        orders[i].filled = orders[i].filled.add(matched);

        return matched;

    }

    function _executeMatcher(Side _side, bytes32 _ticker, Order memory _o, address caller, uint256 matched, uint256 cost) internal {

        emit NewTrade(nextTradeId, _o.id, _ticker, _o.trader, msg.sender, matched, _o.price, block.timestamp );

        if (_side == Side.SELL) {

            traderBalances[caller][_ticker] = traderBalances[caller][_ticker].sub(matched);
            traderBalances[caller][DAI_] = traderBalances[caller][DAI_].add(cost);

            traderBalances[_o.trader][_ticker] = traderBalances[_o.trader][_ticker].add(matched);
            traderBalances[_o.trader][DAI_] = traderBalances[_o.trader][DAI_].sub(cost);

        } else {
            
            require(traderBalances[msg.sender][DAI_] >= cost, "not enough DAI");

            traderBalances[caller][_ticker] = traderBalances[caller][_ticker].add(matched);
            traderBalances[caller][DAI_] = traderBalances[caller][DAI_].sub(cost);

            traderBalances[_o.trader][_ticker] = traderBalances[_o.trader][_ticker].sub(matched);
            traderBalances[_o.trader][DAI_] = traderBalances[_o.trader][DAI_].add(cost);

        }

        nextTradeId = nextTradeId.add(1);

    }

    function updateName(string memory _username) external { users[msg.sender].username = _username; }

    function _increment(uint8 _dw) internal { _dw == 0 ? users[msg.sender].deposits++ : users[msg.sender].withdraws++; }

    function _insertSorted(Order[] storage orders, uint256 _price) internal returns(uint) {

        uint i = 0;
        uint l = orders.length;

        if (l == 0) { orders.push(); return 0; }

        while (i < l && orders[i].price < _price) { i++; } // get pos.

        orders.push(); // empty-slot.

        for (uint j = l; j > i; j--) { orders[j] = orders[j - i]; } // order.

        return i;

    }
    
    function _register(address _addr) internal {

        if (users[msg.sender].addr == address(0)) {

            users[msg.sender] = User(block.timestamp, _addr, "", 1, 0);

            nextUsers++;

        }

    }

    function _shiftByFilled(uint i, uint l, Order[] storage orders) internal {

        i = 0;
        while(i < l && orders[i].filled == orders[i].amount) { i++; } // filled up to i.

        for(uint j = i; j < l; j++) { orders[j-i] = orders[j]; } // shift head.

        for (uint k = 0; k < i; k++) { orders.pop(); } // resize.

    }
    
}

// ALL IMPLEMENTED BESIDES TOPIC 4: DUE TO OFFCHAIN CODE IMPLEMENTATION NEEDED.

/*

Potential improvements & edge cases
Our Dex is great, but there are a couple of improvements we can make.

Feel free to implement some of these as an exercise.

Disallow limit orders that cross the book
Currently, it's possible for a limit order (buy or sell) to "cross" the book, i.e:

for a limit buy order, it means a limit price above the best price (i.e lowest price) of limit sell orders
for a limit sell order, it means a limit price below the best price (i.e highest price) of limit buy orders
An orderbook is not supposed to be in this state.

To avoid this, we can disallow limit orders that cross the book. That means:

limit buy orders must have a limit price below the lowest price of sell orders
limit sell orders must have a limit price above the highest price of buy orders
Limit size of market orders
Currently, it's possible for a whale (i.e a big investor) to take out all the liquidity of the market by creating a huge market order.

This would have a huge impact of price, and leave many traders unable to trade. We want to avoid this.

The solution is to limit the size of market orders. For example, we could enforce a rule that says that all market orders can only have a size of up to 20pct of the total liquidity of the market.

As a practical example, if the total size of limit sell orders is 1000 tokens, that means that a market buy order can be for 200 tokens maximum.

Disallow partially filled market orders
With the current implementation, a market order can be partially filled. Worst, the unfilled portion will never be filled.

To solve this we could either:

Disallow partially filled market orders. The size of a market order need to be equal or less than the total liquidity of the market
Create limit orders for the unfilled portion, at the price at which the initial portion of the market order was filled
NOTE: if you implement the previous improvement "Limit size of market orders", it will implicitly guarantee that there won't be any partially filled market orders and solve this problem.



Move the orderbook off-chain
An on-chain orderbook has several disadvantages:

It's slow (traders need to wait their transaction to be confirmed by the Blockchain before successfully placing an order)
It's expensive (have to pay for gas costs everytime you place a transaction - can be expensive if you place lots of order)
It lacks of privacy (everybody can see who placed which order - if you are a known whale, people will monitor your orders)
For this reason, a lot of decentralized exchanges moved their orderbook off-chain. Here is how it work:

When traders send their order, they don't create Ethereum transactions. Instead, they use the private key of their wallet to sign their intention to create orders with all the details (token, amount, etc...).
The off-chain orderbook (basically a standard web API) stores the limit orders in an internal database (like Mysql, Postgres or Mongodb)
when a market orders arrive, the API matches it against limit orders, create trades, and send these trades to the smart contract, along with the signatures of traders.
The smart contract checks that the signatures of the traders are correct, and then settles the trade.
You can read this article for more detailed explanations, including diagrams, as well as a walkthrough of the smart contract of EtherDelta, a decentralized exchange that implements this pattern. 
 | 
 |
 V
https://eattheblocks.com/etherdelta-smart-contract-walkthrough/

 */