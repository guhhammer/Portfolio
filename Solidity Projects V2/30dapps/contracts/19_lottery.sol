// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract Lottery {

    enum State { IDLE, BETTING }

    uint256 public betCount;
    uint256 public betSize;
    uint256 public houseFee;
    address public admin;
    State public state = State.IDLE;

    address[] public players;
    address public lastWinner;

    constructor(uint256 fee) {

        require(fee > 1 && fee < 99, "fee should be between 1 and 99");

        houseFee = fee;
        admin = msg.sender;

    }

    modifier inState(State _state) {

        require(_state == state, "current state does not allow this");
        _;
    
    }

    modifier onlyAdmin() {

        require(msg.sender == admin, "only admin can execute");
        _;

    }

    function createBet(
        uint256 count,
        uint256 size
    )
        external
        payable
        inState(State.IDLE)
        onlyAdmin
    {

        betCount = count;
        betSize = size;
        state = State.BETTING;

    }

    function bet(

    ) 
        external 
        payable 
        inState(State.BETTING) 
    {

        require(msg.value == betSize, "can only bet exactly the bet size");

        players.push(msg.sender);

        if(players.length == betCount) {

            uint256 winner = _randomModulo(betCount);
            
            payable(players[winner]).transfer((betSize * betCount) * (100 - houseFee) / 100);

            lastWinner = players[winner];

            state = State.IDLE;

            delete players;

        }

    }

    function cancel(

    )
        external
        inState(State.BETTING)
        onlyAdmin
    {
        
        for(uint256 i = 0; i < players.length; i++) { payable(players[i]).transfer(betSize); }

        delete players; state = State.IDLE;
    
    }

    function length() external view returns(uint256) { return players.length; }

    function _randomModulo(
        uint modulo
    ) 
        internal 
        view 
        returns (uint256) 
    {
      
        return uint256(keccak256(abi.encodePacked(
            block.timestamp,       // current block timestamp
            block.prevrandao,      // replaces block.difficulty on newer EVMs
            msg.sender,            // caller's address
            block.number,          // current block number
            tx.gasprice,           // gas price of the transaction
            gasleft()              // remaining gas
        ))) % modulo;
    
    }

    
}