// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract Deed {

    address public lawyer;
    address payable public beneficiary;
    uint public amount;
    uint public earliest;

    constructor(address _lawyer, address payable _beneficiary, uint fromNow) payable {
 
        lawyer = _lawyer;
        beneficiary = _beneficiary;
        amount = msg.value;
        earliest = block.timestamp + fromNow;
 
    }

    function withdraw() public {

        require(msg.sender == lawyer, "Only lawyer can withdraw");
        require(block.timestamp >= earliest, "Too early to withdraw");

        beneficiary.transfer(address(this).balance);

    }

}