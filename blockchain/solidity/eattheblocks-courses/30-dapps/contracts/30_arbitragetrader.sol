// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;


contract Dex {

    mapping(string => uint) private prices;

    function getPrice(string calldata _ticker) external view returns(uint) { return prices[_ticker]; }

    function buy(string calldata _ticker, uint _amount, uint _price) external { /* Buy ERC20 token. */ }

    function sell(string calldata _ticker, uint _amount, uint _price) external { /* Sell ERC20 token. */ }

}


contract Oracle {


    struct Result { bool exist; uint payload; address[] approvedBy; }


    mapping(bytes32 => Result) private results;

    address[] public validators;


    modifier onlyValidators() {

        bool isV = false;

        for(uint i = 0; i < validators.length; i++) { if(validators[i] == msg.sender) { isV = true; } }

        require(isV, "only validator");

        _;

    }


    constructor(address[] memory _validators) { validators = _validators; }


    function feedData(bytes32 _dataKey, uint _payload) external onlyValidators {

        address[] memory approvedBy = new address[](1);

        approvedBy[0] = msg.sender;

        require(results[_dataKey].exist == false, "this data was already imported");

        results[_dataKey] = Result(true, _payload, approvedBy);

    }

    function approvedData(bytes32 _dataKey) external onlyValidators {

        Result storage result = results[_dataKey];

        require(result.exist, "can't approve non-existing data");

        for(uint i = 0; i < result.approvedBy.length; i++) {
            require(result.approvedBy[i] != msg.sender, "cannot approve same data twice");
        }

        result.approvedBy.push(msg.sender);

    }

    function getData(bytes32 _dataKey) external view returns(bool, uint, address[] memory) {

        return (results[_dataKey].exist, results[_dataKey].payload, results[_dataKey].approvedBy);

    }

}


contract ArbitrageTrader {

    
    address public admin;
    address public oracle;


    struct Asset {

        string name;
        address[] dexes;

    }


    mapping(string => Asset) public assets;


    modifier onlyAdmin() { require(msg.sender == admin, "only admin"); _; }


    constructor() { admin = msg.sender; }
    

    function configureOracle(address _oracle) external onlyAdmin { oracle = _oracle; }

    function configureAssets(string calldata _assetName, address[] calldata _dexes) external onlyAdmin {

        assets[_assetName].name = _assetName;
        assets[_assetName].dexes = _dexes;

    }

    function maybeTrade(string calldata _ticker, uint256 _date, uint8 _dexPos) external onlyAdmin {

        Asset storage asset = assets[_ticker];

        require(asset.dexes[_dexPos] != address(0), "this asset dex does not exist");

        // get latest price of asset from oracle.

        bytes32 dataKey = keccak256(abi.encodePacked(_ticker, _date, _dexPos));

        Oracle oracleContract = Oracle(oracle);

        (bool _exist, uint _payload, address[] memory _approvedBy) = oracleContract.getData(dataKey);

        require(_exist, "this result does not exist, cannot trade");

        require(_approvedBy.length == 1, "not enough approvals for this trade");

        // if there is a price, trade of the dex.

        Dex dexContract = Dex(asset.dexes[_dexPos]);

        uint price = dexContract.getPrice(_ticker);

        uint amount = 1 ether / price;

        if (price > _payload) { 

            dexContract.sell(_ticker, amount, (99 * price) / 100);

        } else if (price < _payload) {

            dexContract.buy(_ticker, amount, (101 * price) / 100);

        }

    } 

}

