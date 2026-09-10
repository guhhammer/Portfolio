// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract ArmstrongNumber {


    function isArmstrongNumber(uint256 number) public pure returns (bool) {

        uint256 sum = 0; 
        uint256 originalNumber = number;
        uint256 digits = 0;

        while (number != 0) { number /= 10; digits++; }

        number = originalNumber;

        while (number != 0) {
            uint256 digit = number % 10;
            sum += digit ** digits;
            number /= 10;
        }

        return sum == originalNumber;

    }



}