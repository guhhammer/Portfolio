pragma solidity 0.5.1;


import "./SafeMath.sol";


contract MyContract {
    
    using SafeMath for uint256;
    
    uint256 public value;

    function calculate(uint _value1, uint _value2) public {
        
        value = _value1.div(_value2);
        
    }

}