pragma solidity ^0.8.6;


contract Token {
        
    string public name = "My Token";
    
    string public symbol = "MTK";
    
    uint256 public decimals = 18;
    
    uint256 public totalSupply = 1000000000000000000000000;
    
    mapping (address => uint256) public balanceOf;
    
    event Transfer(address indexed from, address indexed to, uint256 value);
    
    
    modifier hasValue(uint256 value) {
        require(balanceOf[msg.sender] >= value);
        _;
    }
    
    
    constructor () public {
            
        balanceOf[msg.sender] = totalSupply;
        
    }
    
    function transfer(address _to, uint256 _value) external hasValue(_value) returns (bool success) {
        
        balanceOf[msg.sender] = balanceOf[msg.sender] - (_value);
        
        balanceOf[_to] = balanceOf[_to] + (_value);
        
        emit Transfer(msg.sender, _to, _value);
        
        return true;
        
    } 
       
}