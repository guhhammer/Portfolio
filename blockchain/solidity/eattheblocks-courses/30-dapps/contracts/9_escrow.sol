// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract Escrow {
    
    address payable public payer;
    address payable public payee;
    address public lawyer;
    uint public amount;

    constructor(
        address payable _payer,
        address payable _payee,
        uint _amount
    ) {
        payer = _payer;
        payee = _payee;
        lawyer = msg.sender;
        amount = _amount;
    }
    
    function deposit() public payable {
        
        require(msg.sender == payer, "Only payer can deposit");
    
    }

    function release() public {
      
        require(msg.sender == lawyer, "Only lawyer can release funds");
        require(address(this).balance >= amount, "Insufficient balance");

        if (address(this).balance > amount) {
            
            uint _refund = address(this).balance - amount;

            payable(payer).transfer(_refund);

        }

        payee.transfer(amount);

    }

    function balanceOf() public view returns (uint) {
    
        return address(this).balance;
    
    }

    function refund() public {
   
        require(msg.sender == lawyer, "Only lawyer can refund");
        require(address(this).balance < amount, "Cannot refund if balance is sufficient");
   
        payable(payer).transfer(address(this).balance);
   
    }

}