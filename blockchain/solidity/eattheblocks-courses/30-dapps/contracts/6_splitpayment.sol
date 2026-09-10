// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract SplitPayment {

    function send(address payable[] memory to, uint[] memory amount) payable public {
      
        require(to.length == amount.length, "Length mismatch");
      
        uint total = 0;
      
        for (uint i = 0; i < amount.length; i++) {
            total += amount[i];
        }
      
        require(msg.value == total, "Value mismatch");
      
        for (uint i = 0; i < to.length; i++) {
            to[i].transfer(amount[i]);
        }
    
    }

}