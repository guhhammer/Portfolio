// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract MultiSigWallet {
    
    address[] public approvers;
    uint public quorum;

    struct Transfer {
        uint id;
        uint amount;
        address payable to;
        uint approvals;
        bool sent;
    }

    mapping(uint => Transfer) public transfers;
    uint public nextId;
    mapping(address => mapping(uint => bool)) public approvals;

    constructor(address[] memory _approvers, uint _quorum) payable {
       
        approvers = _approvers;
        quorum = _quorum;
    
    }

    function createTransfer(uint _amount, address payable _to) public onlyApprover {
        
        transfers[nextId] = Transfer({
            id: nextId,
            amount: _amount,
            to: _to,
            approvals: 0,
            sent: false
        });

        nextId++;
    
    }

    function sendTransfer(uint _id) external onlyApprover {

        require(transfers[_id].sent == false, "Transfer has already been sent");
        
        if(approvals[msg.sender][_id] == false) {
    
            approvals[msg.sender][_id] = true;
            transfers[_id].approvals++;
    
        }

        if(transfers[_id].approvals >= quorum) { 

            transfers[_id].sent = true;
            address payable to = transfers[_id].to;
            uint amount = transfers[_id].amount;
            to.transfer(amount);

        }

    }

    modifier onlyApprover() {

        bool allowed = false;

        for(uint i = 0; i < approvers.length; i++) {
        
            if(approvers[i] == msg.sender) { allowed = true; break; }
        
        }
        
        require(allowed, "Only approvers can call this function");
        _;

    }

    function getApprovers() public view returns (address[] memory) {
    
        return approvers;
    
    }

}