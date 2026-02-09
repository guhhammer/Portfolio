// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract LoanStateMachine {
    
    enum State { PENDING, ACTIVE, CLOSED }

    State public state = State.PENDING;

    uint256 public amount;
    uint256 public interest;
    uint256 public end;

    address payable public borrower;
    address payable public lender;

    constructor(
        uint256 _amount,
        uint256 _interest,
        uint256 _duration,
        address payable _borrower,
        address payable _lender
    ) {

        amount = _amount;
        interest = _interest;
        end = block.timestamp + _duration;
        borrower = _borrower;
        lender = _lender;

    }

    function fund() payable external {
        
        require(msg.sender == lender, 'only lender can lend');
        require(msg.value <= amount, 'cannot lend more than amount');

        _transitionTo(State.ACTIVE);
        borrower.transfer(amount);

    }

    function reimburse() payable external {

        require(msg.sender == borrower, 'only borrower can reimburse');
        require(msg.value == amount + interest, 'borrower need to reimburse exactly amount + interest');

        _transitionTo(State.CLOSED);
        lender.transfer(amount + interest);

    }

    function _transitionTo(State to) internal {

        require(to != State.PENDING, 'cannot go back to pending state');
        require(to != state, 'do not transition to current state');

        if (to == State.ACTIVE) {

            require(state == State.PENDING, 'can only transition from pending to active');
            state = State.ACTIVE;

        }

        if (to == State.CLOSED) {

            require(state == State.ACTIVE, 'can only transition from active to pending');
            require(block.timestamp >= end, "loan hasn't matured yet");
            state = State.CLOSED;

        }

    }

}