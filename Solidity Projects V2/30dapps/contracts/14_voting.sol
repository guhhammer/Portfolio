// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;
pragma experimental ABIEncoderV2;

contract Voting {

    struct Choice {

        uint256 id;
        string name;
        uint256 votes;

    }

    struct Ballot {

        uint256 id;
        string name;
        Choice[] choices;
        uint256 end;
    
    }

    mapping(address => bool) public voters;

    mapping(uint256 => Ballot) public ballots;

    mapping(address => mapping(uint256 => bool)) public hasVoted;
    
    uint256 public nextBallotId;

    address public admin;

    constructor() {

        admin = msg.sender;

    }

    modifier onlyAdmin() {

        require(msg.sender == admin, "Only admin can call this function");
        _;
    
    }

    function addVoters(address[] calldata _voters) external onlyAdmin {

        for(uint256 i = 0; i < _voters.length; i++) { voters[_voters[i]] = true; }

    }

    function createBallot(string memory name, string[] memory choice_names, uint offset) public onlyAdmin {

        require(choice_names.length > 0, "At least one choice is required");
        require(offset > 0, "Offset must be greater than 0");

        Ballot storage ballot = ballots[nextBallotId];
        ballot.id = nextBallotId;
        ballot.name = name;
        ballot.end = block.timestamp + offset;

        for (uint256 i = 0; i < choice_names.length; i++) {

            ballot.choices.push(Choice(i, choice_names[i], 0));

        }

        nextBallotId++;

    } 

    function getChoices(uint256 ballotId) external view returns (Choice[] memory) {

        return ballots[ballotId].choices;

    }

    function vote(uint256 ballotId, uint256 choiceId) external {

        require(voters[msg.sender], "You are not allowed to vote");
        require(block.timestamp < ballots[ballotId].end, "Voting has ended");
        require(!hasVoted[msg.sender][ballotId], "You have already voted");
        require(ballots[ballotId].choices.length > choiceId, "Invalid choice");

        hasVoted[msg.sender][ballotId] = true;

        ballots[ballotId].choices[choiceId].votes++;

    }

    function results(uint256 ballotId) external view returns (Choice[] memory) {

        require(block.timestamp > ballots[ballotId].end, "Voting is still ongoing");
     
        return this.getChoices(ballotId);

    }

}