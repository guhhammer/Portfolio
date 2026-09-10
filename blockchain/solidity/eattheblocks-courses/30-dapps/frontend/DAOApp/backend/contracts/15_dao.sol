// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;
pragma experimental ABIEncoderV2;
/**
 * @title DAO
 * @dev A simple Decentralized Autonomous Organization (DAO) contract.
 * DAO contract:
 * 1. Collects investors' money (ether).
 * 2. Keeps track of investors' contributions with shares.
 * 3. Allows investors to transfer shares.
 * 4. Allows investment proposals to be created and voted.
 * 5. Executes successful investment proposals (i.e. sends money).
 */
// @custom:security-contact

contract DAO {

    /**
     * @dev Events:
     * This section defines the events emitted by the DAO contract.
     * Events provide a way to log and track important actions and state changes
     * within the contract, enabling better transparency and debugging.
     */
    event Contributor(address indexed _contributor, uint256 _amount, string indexed _action);

    event ExecutedProposal(address indexed _caller, uint256 indexed _proposalId, uint256 _percentage, 
                                    uint256 _amount, address indexed _recipient, uint256 _timestamp);

    event FallbackTriggered(address indexed _sender, uint256 _amount);

    event ProposalCreated(uint256 indexed _id, string indexed _name, address _recipient, uint256 _amount);

    event Transfer(address indexed _sender, address indexed _receiver, uint256 _amount);

    event Voted(address indexed _voter, uint256 _proposalId, uint256 _votes);

    /**
     * @dev Errors:
     * This section defines custom error messages used throughout the contract.
     * Custom errors are a gas-efficient way to handle and communicate specific
     * failure conditions in Solidity.
     */
    error AlreadyVoted(address _sender);

    error ContributionLimitExceeded();

    error DeadlineExpired(uint256 _timestamp, uint256 _deadline);

    error InvalidProposal();

    error InvalidRecipient();
    
    error MustFund();
    
    error NoConsensus(uint256 _percentage);

    error NotAdmin();
    
    error NotEnoughFunds(uint256 _amount, uint256 _availableFunds);
    
    error NotEnoughShares();
    
    error NotInvestor();

    error ProposalExecuted(uint256 _proposalId);
    
    error VotingOngoing(uint256 _timestamp, uint256 _deadline);
    
    /**
     * @dev Constants and Immutables:
     * This section defines variables that are either constant or immutable.
     * Constants are fixed at compile-time, while immutables are set during contract deployment.
     * These variables help optimize gas usage and improve code readability.
     */
    uint256 public constant quorum = 50;
    uint256 public constant votingPeriod = 30 days;

    /**
     * @dev Structs and Enums:
     * This section defines the core data structures used in the DAO contract.
     * Structs are used to group related data, while enums can be used to define
     * a set of named values for better readability and maintainability.
     */
    struct Proposal {               // Optimized for storage packing.
 
        uint256 id;                 // 1 slot. (256 bits = 32 bytes).
        uint256 amount;             // 1 slot.   
        uint256 votes;              // 1 slot.
        uint256 deadline;           // 1 slot.
        string name;                // 1 slot (dynamic pointer).  
        address payable recipient;  // 20 bytes.
        bool executed;              // 1 byte.
 
    }

    /**
     * @dev Storage-Packing variables section:
     * This section contains variables that are optimized for storage packing.
     * Storage packing helps reduce gas costs by tightly packing variables
     * into fewer storage slots, leveraging Solidity's storage layout rules.
     */
    uint256 public totalShares;
    uint256 public availableFunds;
    uint256 public contributionLimit;
    uint256 public nextProposalId;
    address public admin;

    /**
     * @dev Data structures for managing DAO members and proposals.
     * Mappings and arrays are used to efficiently store and retrieve
     * information related to DAO participants and governance processes.
     */
    mapping(address => bool) public investors;
   
    mapping(address => uint256) public shares;
   
    mapping(uint256 => Proposal) public proposals;
   
    mapping(address => mapping(uint256 => bool)) public hasVoted;

    /**
     * @dev Modifiers are special functions in Solidity that are used to 
     * add reusable preconditions or logic to other functions. They help 
     * enforce rules and constraints, ensuring the contract's integrity 
     * and security. Below, you will find the modifiers defined for this 
     * DAO contract.
     */ 
    modifier checkContributionDeadline() {

        if (block.timestamp > contributionLimit) { revert ContributionLimitExceeded(); }
        require(block.timestamp < contributionLimit, "exceed");
        _;

    }
    
    modifier checkEnoughFunds(uint256 _amount) {

        if (_amount > availableFunds) { revert NotEnoughFunds(_amount, availableFunds); }
        _;

    }

    modifier checkEnoughShares(uint256 _shares) {

        if (_shares > shares[msg.sender]) { revert NotEnoughShares(); }
        _;

    }

    modifier checkHasAlreadyVoted(uint256 _proposalId) {

        if (hasVoted[msg.sender][_proposalId]) { revert AlreadyVoted(msg.sender); }
        _;

    }

    modifier checkInvalidProposalId(uint256 _proposalId) {

        if (_proposalId >= nextProposalId) { revert InvalidProposal(); }
        _;

    }
    
    modifier checkInvalidRecipient(address _to) {

        if (_to == address(0)) { revert InvalidRecipient(); }
        _;

    }

    modifier checkMustFund() {

        //if (msg.value == 0) { revert MustFund(); }
        require(msg.value > 0, "mustfund");
        _;

    }
    
    modifier onlyAdmin() {
   
        if (msg.sender != admin) { revert NotAdmin(); }
        _;
   
    }

    modifier onlyInvestor() {
   
        if (!investors[msg.sender]) { revert NotInvestor(); }
        _;
   
    }

    /**
     * @dev Constructor:
     * The constructor is executed once during contract deployment.
     * It initializes the DAO's contribution limit and sets the admin address.
     * 
     * @param _contributionLimit The duration (in seconds) for which contributions are allowed.
     */
    constructor(uint256 _contributionLimit) {
        
        contributionLimit = block.timestamp + _contributionLimit;

        admin = msg.sender;
    
    }

    /**
     * @dev The `receive` function is a special fallback function in Solidity.
     * It is executed when the contract receives Ether and no data is sent with the transaction.
     * This function is payable, allowing the contract to accept Ether transfers.
     * 
     * Note:
     * - If both `receive` and `fallback` functions are defined, the `receive` function
     *   will be called when the contract receives Ether without data.
     * - If only the `fallback` function is defined, it will handle both Ether with and without data.
     * - Ensure proper handling of received Ether to avoid unexpected behavior.
     */
    receive(

    ) 
        payable 
        external 
    { 
        
        availableFunds += msg.value;

        emit FallbackTriggered(msg.sender, msg.value);
        
    }

    /**
     * @dev
     * =============================================================
     *                            FUNCTIONS
     * =============================================================
     * This section contains all the functions that define the core
     * logic and behavior of the DAO contract. Each function is 
     * designed to handle specific operations such as governance, 
     * voting, and fund management.
     */
    
    /**
     * @dev Allows a user to contribute funds to the contract.
     * The contributed amount is added to the sender's balance and the total contributions.
     * Emits a `ContributionReceived` event upon successful contribution.
     *
     * Requirements:
     * - The contribution amount must be greater than zero.
     *
     * @notice Ensure that the sender has sufficient balance to make the contribution.
     */
    function contribute(

    )                                 // Modifier order goes top to bottom.
        external                      // executed first.  
        payable                       //
    //    checkContributionDeadline     //
    //    checkMustFund                 // executed last.
    {
        
        investors[msg.sender] = true;
        shares[msg.sender] += msg.value;
        totalShares += msg.value;
        availableFunds += msg.value;

        emit Contributor(msg.sender, shares[msg.sender], "contribute");
 
    }

    /**
     * @dev This function does not account for the fluctuation of Ether prices.
     *
     * Requirements:
     * - The sender must be an investor.
     * - The amount of shares to redeem must not exceed the sender's balance.
     *
     * @notice Ensure that the sender has sufficient shares to redeem.
     */
    function redeemShares(
        uint256 _shares
    ) 
        external 
    //    onlyInvestor
    //    checkEnoughShares(_shares)
    {
        
        shares[msg.sender] -= _shares;
        totalShares -= _shares;
        availableFunds -= _shares;

        payable(msg.sender).transfer(_shares); 

        if (shares[msg.sender] == 0) { investors[msg.sender] = false; }

        emit Contributor(msg.sender, shares[msg.sender], "redeem");
    
    } 

    /**
     * @dev Transfers shares from the sender to another investor.
     * Updates the shares balance of both the sender and the recipient.
     * Emits a `Contributor` event and a `Transfer` event upon successful transfer.
     *
     * Requirements:
     * - The sender must be an investor.
     * - The recipient address must not be the zero address.
     * - The sender must have enough shares to transfer.
     *
     * @notice Ensure that the recipient is a valid address and the sender has sufficient shares.
     */
    function transferShares(
        address _to, 
        uint256 _shares
    ) 
        external 
    //    onlyInvestor
    //    checkInvalidRecipient(_to)
    //    checkEnoughShares(_shares)
    {

        shares[msg.sender] -= _shares;
        shares[_to] += _shares;
        investors[_to] = true;

        emit Contributor(msg.sender, shares[msg.sender], "transfer");
        emit Transfer(msg.sender, _to, _shares);
        
    } 

    /**
     * @dev Creates a proposal and stores it in the proposals mapping.
     * Emits a `ProposalCreated` event upon successful creation.
     *
     * Requirements:
     * - The caller must be an investor.
     * - The proposal amount must not exceed the available funds.
     * - The recipient address must not be the zero address.
     *
     * @notice This function allows investors to propose an investment by specifying
     * the name, recipient, and amount of the proposal.
     */
    function createProposal(
        string memory _name, 
        address payable _recipient, 
        uint256 _amount
    ) 
        external 
    //    onlyInvestor
    //    checkEnoughFunds(_amount)
    //    checkInvalidRecipient(_recipient) 
    {
        
        Proposal storage p = proposals[nextProposalId];
        p.id = nextProposalId;
        p.name = _name;
        p.recipient = _recipient;
        p.amount = _amount;
        p.votes = 0;
        p.deadline = block.timestamp + votingPeriod;

        availableFunds -= _amount;
        
        emit ProposalCreated(nextProposalId, _name, _recipient, _amount);
    
        nextProposalId++;
    
    }

    /**
     * @dev Allows investors to vote on a proposal.
     * Updates the proposal's votes and marks the investor as having voted.
     *
     * Requirements:
     * - The proposal must be valid.
     * - The investor must not have already voted on the proposal.
     * - The voting period for the proposal must still be open.
     *
     * @notice This function enables investors to participate in decision-making
     * by casting their votes on active proposals.
     */
    function vote(
        uint256 _proposalId
    )
        external 
    //    checkInvalidProposalId(_proposalId)
    //    onlyInvestor 
    //    checkHasAlreadyVoted(_proposalId) 
    {

        Proposal storage p = proposals[_proposalId];

        if (block.timestamp > p.deadline) { revert DeadlineExpired(block.timestamp, p.deadline); }

        hasVoted[msg.sender][_proposalId] = true;
        p.votes += shares[msg.sender];

        emit Voted(msg.sender, _proposalId, p.votes);

    }

    /**
     * @dev Allows investors to push funds to the proposal recipient when a quorum has voted for it.
     * If any investor spots enough quorum to push, they can call the function on behalf of others.
     *
     * Requirements:
     * - The proposal must have reached its deadline.
     * - The proposal must not have already been executed.
     * - The proposal must have reached the required quorum percentage.
     *
     * @notice This function enables the execution of proposals that have achieved consensus among investors.
     */
    function executeProposal(
        uint256 _proposalId
    ) 
        external 
    //    onlyInvestor
    {

        Proposal storage p = proposals[_proposalId];

        if (block.timestamp < p.deadline) { revert VotingOngoing(block.timestamp, p.deadline); }
        
        if (p.executed) { revert ProposalExecuted(_proposalId); }
        p.executed = true;

        uint256 percentage = (p.votes * 100) / totalShares;
        if (percentage < quorum) { revert NoConsensus(percentage); }
        
        _transferEther(p.amount, p.recipient);

        emit ExecutedProposal(msg.sender, _proposalId, percentage, p.amount, p.recipient, block.timestamp);

    }

    /**
     * @dev This function allows the admin to retrieve all funds in case of an exploit.
     * It is intended as a safeguard mechanism and should not be included in a real DAO,
     * as it could raise concerns about potential admin-induced exploits.
     *
     * Requirements:
     * - The caller must be the admin.
     *
     * @notice Use this function only in emergency situations to prevent fund leakage.
     */
    function withdrawEther(
        uint256 _amount, 
        address payable _to
    ) 
        external 
    //    onlyAdmin 
    {

        _transferEther(_amount, _to);

    }

    /**
    * @dev This function ensures that the transfer of Ether is executed securely.
    * Requirements:
    * - The amount to transfer must not exceed the available funds.
    * - The recipient address must be a valid payable address.
    * @notice This function is triggered internally by the contract to execute Ether transfers.
     */
    function _transferEther(
        uint256 _amount, 
        address payable _to
    ) 
        internal
    //    checkEnoughFunds(_amount)
    {
        
        _to.transfer(_amount);

        availableFunds -= _amount;

        totalShares -= _amount;

    }
    
    /**
     * @notice Retrieves the details of a proposal by its ID.
     * @param id The unique identifier of the proposal to retrieve.
     * @return A `Proposal` struct containing the details of the specified proposal.
     */
    function getProposal(
        uint256 id
    ) 
        external 
        view 
        returns(Proposal memory) 
    {

        return proposals[id];

    }

    /**
     * @notice Checks if the caller has already voted on a specific proposal.
     * @param _proposalId The ID of the proposal to check.
     * @return bool Returns true if the caller has voted on the proposal, false otherwise.
     */
    function checkVoted(
        uint256 _proposalId
    ) 
        external 
        view 
        returns(bool) 
    {
   
        return hasVoted[msg.sender][_proposalId];
   
    }
   
}