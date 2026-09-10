// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

import "./20_myerc20token.sol";

contract ICO {
    
    address immutable public admin;
    address immutable public token;

    uint256 public minPurchase;
    uint256 public maxPurchase;

    uint256 public end;
    uint256 public price;

    uint256 public availableTokens;
    bool public released;


    struct Sale { address investor; uint256 quantity; }

    mapping(address => bool) public investors;

    Sale[] public sales;


    modifier hasIcoEnded() { require(end <= block.timestamp || availableTokens == 0, "ICO must have ended"); _; }

    modifier icoActive() { require(end > block.timestamp, 'ICO should not be active'); _; }
    
    modifier isAcceptableBid(uint256 b) { require(minPurchase <= (b/price) && (b/price) <= maxPurchase, "max >= b > min"); _; }

    modifier isAvailableAMount(uint256 a, address t) { 
        
        uint256 _totalSupply = MyERC20(t).totalSupply();
        require(a > 0 && a <= _totalSupply, "totalSupply should be in range 0..totalSupply"); 
        _; 
        
    }
    
    modifier isValidDuration(uint256 d) { require(d > 0, "Duration is not acceptable"); _; }

    modifier notEnoughTokens(uint256 v) { require((v / price) <= availableTokens, "Not Enough tokens left for sale"); _; }

    modifier notOutOfTokens() { require(availableTokens > 0, 'No available tokens'); _; }

    modifier onlyAdmin() { require(msg.sender == admin, "Only admin"); _; }

    modifier onlyInvestor() { require(investors[msg.sender], "msg.sender is not investor"); _; }

    modifier okMinPurchase(uint256 m) { require( m > 0, "_minPurchase should be > 0"); _; }

    modifier okMaxPurchase(uint256 m, uint256 a) { require(m > 0 && m <= a, "_maxPurchase should be 0 < X <= _availableTokens"); _; } 

    modifier releasedTokens() { require(released == true, "tokens not released"); _; }

    modifier rightSharesFrom(uint256 a) { require(a % price == 0, "cannot rightly share by price"); _; }

    modifier unreleasedTokens() { require(released == false, "tokens released"); _; }

    constructor(
        string memory _name,
        string memory _symbol,
        uint8 _decimals,
        uint256 _totalSupply
    ) 
    {  

        token = address(new MyERC20(_name, _symbol, _decimals, _totalSupply));
        admin = msg.sender;
    
    }

    function start(
        uint256 duration,
        uint256 _price,
        uint256 _availableTokens,
        uint256 _minPurchase,
        uint256 _maxPurchase
    )
        external
        onlyAdmin
        isValidDuration(duration)
        isAvailableAMount(_availableTokens, token)
        okMinPurchase(_minPurchase)
        okMaxPurchase(_maxPurchase, _availableTokens)
    {
        
        end = block.timestamp + duration;
        price = _price;
        availableTokens = _availableTokens;
        minPurchase = _minPurchase;
        maxPurchase = _maxPurchase;
        
    }

    function whitelist(address investor) external onlyAdmin { investors[investor] = true; }

    function buy(

    ) 
        external 
        payable 
        onlyInvestor 
        icoActive 
        notOutOfTokens 
        rightSharesFrom(msg.value)
        isAcceptableBid(msg.value)
        notEnoughTokens(msg.value)
    {

        sales.push(Sale({investor: msg.sender, quantity: msg.value / price}));

        availableTokens -= msg.value / price;

    }

    function release(

    )
        external
        onlyAdmin
        hasIcoEnded
    {

        MyERC20 tInstance = MyERC20(token);

        for(uint256 i = 0; i < sales.length; i++) {

            Sale storage s = sales[i];

            tInstance.transfer(s.investor, s.quantity);

        }

        released = true;
        
    }

    function withdraw(address payable to) external onlyAdmin hasIcoEnded releasedTokens { 
        
        to.transfer(address(this).balance);

    }

    function name() public view returns(string memory) {
      
        MyERC20 t = MyERC20(token);
        return t.name();
    
    }

    function symbol() public view returns(string memory) {
    
        MyERC20 t = MyERC20(token);
        return t.symbol();
    
    }

    function decimals() public view returns(uint8) {
    
        MyERC20 t = MyERC20(token);
        return t.decimals();
    
    }

    function totalSupply() public view returns(uint256) {
    
        MyERC20 t = MyERC20(token);
        return t.totalSupply();
    
    }

    function balanceOf(address account) external view returns(uint256) {

        MyERC20 t = MyERC20(token);
        return t.balanceOf(account);

    }

}

