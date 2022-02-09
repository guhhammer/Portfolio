pragma solidity ^0.4.24;

contract MyContract {
        
    string value; // data will be stored in the blockchain; it's not really local to the contract.
    
    constructor() public {
        
        value = "myValue";
        
    }
    
    // func  name  visibility no-change datatype
    function get() public view returns(string){
            
        return value;
        
    }
    
    function set(string _value) public {
        
        value = _value;
        
    }
    
    
}