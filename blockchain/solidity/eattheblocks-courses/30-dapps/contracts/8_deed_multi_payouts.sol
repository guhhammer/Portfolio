// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract DeedMultiPayouts {

	address public lawyer;
	address payable public beneficiary;
	uint public earliest;
	uint public amount;
	uint constant public PAYOUTS = 10; 
	uint constant public INTERVAL = 10;
	uint public paidPayouts;

	constructor(
		address _lawyer,
		address payable _beneficiary,
		uint fromNow
	) payable {
	
		lawyer = _lawyer;
		beneficiary = _beneficiary;
		earliest = block.timestamp + fromNow;
		amount = msg.value / PAYOUTS;
	
	}

	 function withdraw() public {
        require(msg.sender == beneficiary, 'beneficiary only');
        require(block.timestamp >= earliest + paidPayouts * INTERVAL, 'too early');  // Enforce 1 payout per interval
        require(paidPayouts < PAYOUTS, 'payouts are over');  // Ensure no more than PAYOUTS are made

        paidPayouts += 1;
        beneficiary.transfer(amount);  // Transfer the payout amount
    }

}
