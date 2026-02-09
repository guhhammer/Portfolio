// SPDX-License-Identifier: UNLICENSED 
pragma solidity ^0.8.21;

contract AdvancedStorage {

    mapping (address => string) private data;

    function setData (string memory _data) public {
        
        data[msg.sender] = _data;


    }

    function getData () public view returns (string memory) {
        
        return data[msg.sender];
    
    }

}