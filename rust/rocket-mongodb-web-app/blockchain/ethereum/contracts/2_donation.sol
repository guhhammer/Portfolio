// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

/**
 * @title SafeMath
 * @dev Math operations with safety checks that throw on error
 * 
 *  Imported from OpenZeppelin Foundation.
 */
library SafeMath {

    /**
     * @dev Adds two numbers, throws on overflow.
     */
    function add(uint256 a, uint256 b) internal pure returns (uint256 c) {
        c = a + b;
        assert(c >= a);
        return c;
    }

    /**
     * @dev Subtracts two numbers, throws on overflow (i.e. if subtrahend is greater than minuend).
     */
    function sub(uint256 a, uint256 b) internal pure returns (uint256) {
        assert(b <= a);
        return a - b;
    }

    /**
     * @dev Multiplies two numbers, throws on overflow.
     */
    function mul(uint256 a, uint256 b) internal pure returns (uint256 c) {
        if (a == 0) {
        return 0;
        }
        c = a * b;
        assert(c / a == b);
        return c;
    }

    /**
     * @dev Integer division of two numbers, truncating the quotient.
     */
    function div(uint256 a, uint256 b) internal pure returns (uint256) {
        // assert(b > 0); // Solidity automatically throws when dividing by 0
        // uint256 c = a / b;
        // assert(a == b * c + a % b); // There is no case in which this doesn't hold
        return a / b;
    }

}

contract Donation {

    using SafeMath for uint256;

    bytes32 constant private USER = keccak256(abi.encodePacked("USER"));

    event ApproveAddress(address indexed _previous, address indexed _current, uint256 indexed _timestamp);

    event DonationRetrieved(address indexed _recepient, uint256 indexed _timestamp, uint256 _amount);

    event NewTransaction(address indexed _from, address indexed _to, uint256 indexed _timestamp, uint256 _amount);

    event NewUser(address indexed _recipient, uint256 indexed _timestamp);

    uint256 public nextTransaction;

    uint256 public nextUser;

    address public admin;

    address[] public approvedAddresses = new address[](5);

    uint256 public contractFunds;

    uint16 public feePerThousand;

    struct DonationTransaction {

        uint256 amount; uint256 date;

        address from; address to;

        uint256 receive_at; bool complete;

    }

    struct User {

        address donate_address; string profile_name;

        string tier; string categories;

        uint256 retrieve_at; uint256 dt_pointer;
        
        bool exist; bool user_mode;

    }

    mapping(uint256 => address) public allDonationAddresses;

    mapping(address => User) public donationAddresses;

    mapping(uint256 => DonationTransaction) public donationTransactions;

    mapping(address => uint256[]) public userDonations;

    modifier notZeroAddress(address _to) { require(_to != address(0), "invalid address"); _; }

    modifier onlyAdmin() { require(msg.sender == admin, "only admin"); _; }

    modifier onlyAdminOrApproved() { 
        
        bool isV = false;
        for(uint8 i = 0; i < 5; i++) { if (approvedAddresses[i] == msg.sender && msg.sender != address(0)) { isV = true; } }
        require(isV || msg.sender == admin, "address is not approved");
        _; 
        
    }

    modifier validDonation(address _to) { require(donationAddresses[_to].exist, "address cannot receive donations"); _; }

    constructor() { admin = msg.sender; feePerThousand = 10; }

    receive() external payable { contractFunds = contractFunds.add(msg.value); }

    function makeMyselfUser(string calldata _profile_name, string calldata _categories) external {

        donationAddresses[msg.sender] = User(msg.sender, _profile_name, "USER", _categories, 30 days, 0, true, true);

        allDonationAddresses[nextUser] = msg.sender; nextUser++;

        emit NewUser(msg.sender, block.timestamp);

    }

    function makeNewUser(address _recipient, string calldata _profile_name, string calldata _tier, string calldata _categories) external onlyAdminOrApproved {

        require(_recipient != address(0), "invalid recipient");

        donationAddresses[_recipient] = User(_recipient, _profile_name, _tier, _categories, 30 days, 0, true, false);

        allDonationAddresses[nextUser] = _recipient; nextUser++;

        emit NewUser(_recipient, block.timestamp);

    }
    
    function changeUserTier(address _recipient, string calldata _tier) external onlyAdminOrApproved {

        donationAddresses[_recipient].tier = _tier;

        if (keccak256(abi.encodePacked(_tier)) == USER) { donationAddresses[_recipient].user_mode = true; }
    
    }

    function changeUserCategories(address _recipient, string calldata _categories) external onlyAdminOrApproved {

        donationAddresses[_recipient].categories = _categories;
    
    }

    function changeUserRetrievePeriod(address _recipient, uint256 _retrieve_at) external onlyAdminOrApproved {

        donationAddresses[_recipient].retrieve_at = _retrieve_at;
    
    }

    function makeDonate(address _to) external payable notZeroAddress(_to) validDonation(_to) {

        require(msg.value > 0, "cannot make donation of 0");

        contractFunds = contractFunds.add(msg.value);

        uint256 receive_on = donationAddresses[_to].retrieve_at + block.timestamp;

        donationTransactions[nextTransaction] = DonationTransaction(msg.value, block.timestamp, msg.sender, _to, receive_on, false);
        
        userDonations[_to].push(nextTransaction); nextTransaction++;

        emit NewTransaction(msg.sender, _to, block.timestamp, msg.value);

    }

    function receiveDonation() external validDonation(msg.sender) {
        
        uint256 end = userDonations[msg.sender].length;
        uint256 user_dt_pointer = donationAddresses[msg.sender].dt_pointer;

        require(user_dt_pointer < end, "nothing to retrieve now");

        uint256 total = 0;
        uint256 new_pointer = user_dt_pointer;  // track how far we processed

        for (uint i = user_dt_pointer; i < end; i++) {
            uint256 j = userDonations[msg.sender][i];
            DonationTransaction storage dt = donationTransactions[j];

            if (!dt.complete && block.timestamp > dt.receive_at) {
                total = total.add(dt.amount);
                dt.complete = true;
                emit DonationRetrieved(msg.sender, block.timestamp, (dt.amount * (1000 - feePerThousand)) / 1000);
                new_pointer = i + 1; // move pointer forward after processing this donation
            } else if (dt.receive_at > block.timestamp) {
                // Stop processing if this donation is not ready yet
                break;
            }
        }

        require(new_pointer > user_dt_pointer, "nothing to retrieve now"); // no donations processed

        donationAddresses[msg.sender].dt_pointer = new_pointer;

        uint256 feeAmount = (total * feePerThousand) / 1000;
        uint256 got = total.sub(feeAmount);

        payable(msg.sender).transfer(got);

        contractFunds = contractFunds.add(feeAmount);
    
    }

    function approveAddress(uint8 _slot, address _new) external onlyAdminOrApproved() notZeroAddress(_new) {

        emit ApproveAddress(approvedAddresses[_slot], _new, block.timestamp);

        approvedAddresses[_slot] = _new;

    }

    function unapproveAddress(uint8 _slot) external onlyAdminOrApproved() {

        emit ApproveAddress(approvedAddresses[_slot], address(0), block.timestamp);

        approvedAddresses[_slot] = address(0);

    }

    function changeFee(uint16 _new) external onlyAdmin {

        require(_new < 1001 && _new != feePerThousand, "cannot change fee");

        feePerThousand = _new;

    }

    function withdraw(address _to, uint256 _amount) external onlyAdmin notZeroAddress(_to) {

        require(contractFunds >= _amount, "not enough funds");

        contractFunds = contractFunds.sub(_amount);

        payable(_to).transfer(_amount);
        
    }

}

/*

CAN IMPLEMENT ERC20 TOKEN FOR GOVERNANCE AND REDEEM SHARES.

CAN MAKE MULTISIG WALLET FOR SOME FUNCTION CONFIRMATIONS.

AND AUDIT EVEN MORE.

STAKE CONTRACT FUNDS FOR SOME PERIOD PREDEFINED.

MAKE FUNCTIONS THAT SET A TIME LIMIT AND A PERCENT OF CONTRACT FUNDS THAT
USERS WITH CERTAIN BALANCES CAN PERCENTAGE-WISE REDEEM FROM THE CONTRACT
LIKE YIELDS.

*/

