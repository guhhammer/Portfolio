// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract SimpleStorage {

    string public data;

    function set(string memory _data) public { data = _data; }

    function get() public view returns (string memory) { return data; }

}