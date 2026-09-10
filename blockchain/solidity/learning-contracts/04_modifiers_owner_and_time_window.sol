pragma solidity 0.5.1;

contract MyContract {
   
    
    mapping(uint => mPerson) public mPeople;
    uint256 public mPeopleCount = 0;
    
    
    address owner;
    uint256 openingTime = 1630346059; // epoch time -> https://www.epochconverter.com/
    
    modifier onlyOwner() {
        
        require(msg.sender == owner);
        _;
        
    }
    
    modifier onlyWhileOpen() {
        
        require(block.timestamp >= openingTime);
        _;        
    }
    
    struct mPerson { 
        
        uint _id;
        string _firstName;
        string _lastName;
        
    }
    
    constructor() public {
        
        owner = msg.sender;
        
    }
    
    function mAddPerson(string memory _firstName, string memory _lastName) public onlyOwner onlyWhileOpen{
        
        incrementCount();
        mPeople[mPeopleCount] = mPerson(mPeopleCount, _firstName, _lastName);

    }
    
    
    function incrementCount() internal {
        
        mPeopleCount += 1;
        
    }  
       
}