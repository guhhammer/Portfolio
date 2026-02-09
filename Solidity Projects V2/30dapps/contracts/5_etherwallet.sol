// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract EtherWallet {

    address public owner;

    event transaction(address indexed _owner, uint256 indexed _timestamp, string indexed _direction, uint256 _amount);

    constructor(address _owner) {
     
        owner = _owner;
    
    }

    modifier onlyOwner() {
        
        require(msg.sender == owner, "Not the contract owner");
        _;
    
    }

    function deposit() public payable {
        
        require(msg.value > 0, "Deposit amount must be greater than 0");
        emit transaction(msg.sender, block.timestamp, "Deposit", msg.value);
     
    }

    function withdraw(uint256 amount) public onlyOwner {
        
        require(address(this).balance >= amount, "Insufficient balance");
        payable(msg.sender).transfer(amount);
        emit transaction(msg.sender, block.timestamp, "Withdraw", amount);
    
    }
    
    function send(address payable to, uint amount) public onlyOwner {
    
        to.transfer(amount);
        emit transaction(msg.sender, block.timestamp, "Send", amount);

    }

    function getBalance() public view returns (uint256) {
        
        return address(this).balance;
    
    }

}