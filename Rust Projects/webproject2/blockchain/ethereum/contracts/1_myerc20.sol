// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

interface IERC20 {

    event Transfer(address indexed from, address indexed to, uint256 value);

    event Approval(address indexed owner, address indexed spender, uint256 value);

    function totalSupply() external view returns (uint256);

    function balanceOf(address account) external view returns (uint256);

    function transfer(address to, uint256 value) external returns (bool);

    function allowance(address owner, address spender) external view returns (uint256);

    function approve(address spender, uint256 value) external returns (bool);

    function transferFrom(address from, address to, uint256 value) external returns (bool);

}

contract MyERC20 is IERC20 {

    uint256 public total_supply;
    string public name;
    string public symbol;
    uint8 public decimals;

    mapping(address => uint) public balances;
    mapping(address => mapping(address => uint)) public allowed;

    constructor(
        string memory _name,
        string memory _symbol,
        uint8 _decimals,
        uint256 _totalSupply
    ) 
    {
        
        name = _name;
        symbol = _symbol;
        decimals = _decimals;
        total_supply = _totalSupply * (10 **_decimals);
        balances[msg.sender] = _totalSupply * (10 ** _decimals);

    }

    function transfer(address to, uint256 value) external returns(bool) {

        require(balances[msg.sender] >= value);
        
        balances[msg.sender] -= value;

        balances[to] += value;

        emit Transfer(msg.sender, to, value);

        return true;

    }

    function transferFrom(address from, address to, uint256 value) external returns (bool) {
    
        uint _allowance = allowed[from][msg.sender];

        require(balances[from] >= value, "Insufficient balance of 'from'");
        require(_allowance >= value, "Allowance too low");

        allowed[from][msg.sender] -= value;
        balances[from] -= value;
        balances[to] += value;

        emit Transfer(from, to, value);
        return true;
        
    }

    function approve(address spender, uint256 value) external returns(bool) {

        require(spender != msg.sender);

        allowed[msg.sender][spender] = value;

        emit Approval(msg.sender, spender, value);

        return true;

    }

    function allowance(address owner, address spender) external view returns(uint256) {

        return allowed[owner][spender];

    }

    function balanceOf(address account) external view returns(uint256) {

        return balances[account];

    }

    function totalSupply() external view returns(uint256) {

        return total_supply;

    }

}