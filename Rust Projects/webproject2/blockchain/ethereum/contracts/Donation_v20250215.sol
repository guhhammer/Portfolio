// SPDX-License-Id.entifier: MIT
pragma solidity ^0.8.28;


contract Donation {

    // Contract Owner Address.
    address public owner; 

    uint32 private percentage;

    struct Users {

        address userAddress;

        uint32 percent;

    }

    mapping (uint256 => Users) recipients;

    event FundsAdded(address sender, uint256 total, uint256 percentageReceived, uint256 recipientSent);

    constructor(uint32 percent) {

        owner = msg.sender;
        percentage = percent;

    }

    // Set Contract To Receive Funds To it.
    receive() external payable {}    

    // Get Contract Balance.
    function getBalance() public view returns (uint256) {

        return address(this).balance;

    }

    // Owner Gets Balance Received From.
    function withdraw() public {

        require(msg.sender == owner, "Not Owner");
        payable(owner).transfer(address(this).balance); 

    }

    // Value Sent Is Defined On Transaction Body.
    function addToBalance(address recipient) payable public {
        
        require(msg.value > 0, "Must send ETH");
        require(recipient != address(0), "Invalid Recipient");

        uint256 contractPercent = (msg.value * percentage) / 100;
        uint256 remainingAmount = msg.value - contractPercent;

        // Ensure any remainder goes to the remaining part
        if (msg.value % 100 != 0) {
            remainingAmount += msg.value % 100;
        }

        payable(recipient).transfer(remainingAmount);

        emit FundsAdded(msg.sender, msg.value, contractPercent, remainingAmount);
        
    }

}


