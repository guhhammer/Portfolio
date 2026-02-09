pragma solidity 0.5.1;

contract MyContract {
        
    string value; // data will be stored in the blockchain; it's not really local to the contract.
    
    constructor() public {
        
        value = "myValue";
        
    }
    
    // func  name  visibility no-change datatype
    function get() public view returns(string memory){
            
        return value;
        
    }
    
    function set(string memory _value) public {
        
        value = _value;
        
    }
    
    
}