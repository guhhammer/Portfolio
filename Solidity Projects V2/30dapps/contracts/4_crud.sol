// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract Crud {

    struct User {
        uint id;
        string name;
    }

    User[] public users;
    uint public nextId = 0;

    function create(string memory name) public {
  
        users.push(User(nextId, name));
        nextId++;
  
    }

    function read(uint id) public view returns (string memory) {
       
        return users[id].name;

    }

    function update(uint id, string memory _name) public {
       
        users[id].name = _name;       

    }

    function destroy(uint id) public {
       
        delete users[id];
        
    } 

}