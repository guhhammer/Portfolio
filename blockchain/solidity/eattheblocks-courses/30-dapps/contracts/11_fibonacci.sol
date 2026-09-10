// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract Fibonacci {
	
	function fib(uint n) pure external returns(uint) {
		
		if(n == 0) { return 0; }

		uint a = 1;
		uint b = 1;
	
		for(uint i = 2; i < n; i++) {
			
			(a, b) = (a+b, a);
			
		}

		return a;

	}

}
