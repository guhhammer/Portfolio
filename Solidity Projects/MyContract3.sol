pragma solidity 0.5.1;

contract MyContract {
        
    string private constant myString = "myString"; // data will be stored in the blockchain; it's not really local to the contract.
 
    bool private myBool = true;
    
    int private myInt = -1;
    
    uint private myUint = 1;
    
    uint8 private myUint8 = uint8(50000);
    
    enum State { Waiting, Ready, Active }
    
    State private state;
    
    constructor() public {
        
        state = State.Waiting;
        
    }
    
    
    function activete() private {
        
        state = State.Active;
        
    }
    
    
    function isActive() private view returns(bool) { 
        
        return state == State.Active;
        
    }
    
    
    
    struct Person { 
        
        string _firstName;
        string _lastName;
        
    }
    
    
    Person[] public people;
    
    uint256 public peopleCount = 0;
    
    function addPerson(string memory _firstName, string memory _lastName) public {
        
        people.push(Person(_firstName, _lastName));
        peopleCount += 1;
    
    }
    
    
    
    mapping(uint => mPerson) public mPeople;
    uint256 public mPeopleCount = 0;
    
    struct mPerson { 
        
        uint _id;
        string _firstName;
        string _lastName;
        
    }
    
    function mAddPerson(string memory _firstName, string memory _lastName) public {
        
        mPeopleCount += 1;
        mPeople[mPeopleCount] = mPerson(mPeopleCount, _firstName, _lastName);

    }
    
       
}