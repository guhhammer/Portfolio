// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract Assembly {

    function ifSolidity(uint _data) external pure returns(uint) {

        if (_data == 1) { 

            return 10;

        } else if (_data == 2) {

            return 20;

        } else {

            return 100;

        }

    }

    function ifAssembly(uint _data) external pure returns(uint ret) {

        assembly {
            
            switch _data
            case 1 {
                ret := 10
            }
            case 2 {
                ret := 20
            }
            default {
                ret := 100
            }

        }

    }

    function sumSolidity(uint[] memory _data) pure public returns(uint sum) {

        for(uint i = 0; i < _data.length; i++) { sum += _data[i]; }

    }

    function sumAssembly(uint[] memory _data) pure public returns(uint sum) {

        assembly {
            
            let length := mload(_data)
            let data := add(_data, 0x20)

            for 
                { let end := add(data, mul(0x20, length)) } 
                lt(data, end) 
                { data := add(data, 0x20) } 
            {
                sum := add(sum, mload(data))
            }

        }

    }

    function isContract(address addr) external view returns(bool) {

        uint codeLength;

        assembly {
            
            codeLength := extcodesize(addr)

        }

        return codeLength != 0;

    }

}

contract Proxy {

    address public implementation;
    address public admin;

    constructor() { admin = msg.sender; }

    function update(address _implementation) external {

        implementation = _implementation;

    }

    receive() external payable {}

    fallback() external payable {

        require(implementation != address(0));

        address impl = implementation;

        assembly {

            let ptr := mload(0x40)
            
            calldatacopy(ptr, 0, calldatasize())
            
            let result := delegatecall(gas(), impl, ptr, calldatasize(), 0, 0)

            returndatacopy(ptr, 0, returndatasize())
            
            switch result
            case 0 { revert(ptr, returndatasize()) }
            default { return(ptr, returndatasize()) }
        
        }

    }

}

contract TargetA {

    function add(uint a, uint b) public pure returns(uint) { b; return a; }

}

contract TargetB {

    function add(uint a, uint b) public pure returns(uint) { return a + b; }

}


/*
assembly {
    let target := sload(implementation.slot)
    calldatacopy(0, 0, calldatasize())
    let result := delegatecall(gas(), target, 0, calldatasize(), 0, 0)
    returndatacopy(0, 0, returndatasize())
    switch result
    case 0 { revert(0, returndatasize()) }
    default { return(0, returndatasize()) }
}
 */