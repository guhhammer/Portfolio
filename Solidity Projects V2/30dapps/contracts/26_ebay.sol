// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;
pragma experimental ABIEncoderV2;

contract Ebay {

    uint256 public nextAuctionId;

    address public admin;


    struct Auction {
        
        uint256 id;
        uint256 minBid;
        uint256 end;
        
        uint256 bestOfferAmount;
        uint256 nextOfferCoverSpread;
        uint256 offersMadeCounter;

        address bestOfferIdAddress;
        
        address seller;

        string name;
        string description;

        bool concluded;

    }


    mapping(uint256 => Auction) private auctions;

    mapping(address => uint256[]) private myBids;
    mapping(address => mapping(uint256 => uint256)) private bidAmount;


    modifier authTrader(uint256 a) { 
        
        Auction storage _a = auctions[a];
        require(msg.sender == _a.seller || msg.sender == _a.bestOfferIdAddress 
                            || msg.sender == admin, "not authorized trader");
        _; 
    
    }

    modifier doubleBid(uint256 a) { 
        
        Auction storage _a = auctions[a];
        require(msg.sender != _a.bestOfferIdAddress, "cannot double bid");
        _; 
    
    }

    modifier isConcluded(uint256 a) { 
        
        Auction storage _a = auctions[a];
        require(!_a.concluded, "Auction is concluded");
        _;
        
    }

    modifier isValidAuction(uint256 a) { 
        
        require(a < nextAuctionId, "auction does not exist");
        _; 
        
    }


    constructor() { admin = msg.sender; }


    function createAuction(
        string calldata _name,
        string calldata _description,
        uint256 _minBid,
        uint256 _duration,
        uint256 _spreadOnEachBid
    ) 
        external 
    {

        require(_minBid > 0, "invalid min bid");
        require(_spreadOnEachBid < _minBid && _spreadOnEachBid * 1000 >= _minBid, "invalid spread on each bid, must be one-thousandth");
        require(_duration >= 7200, "invalid auction 2-hour duration limit");

        auctions[nextAuctionId] = Auction({
           
            id: nextAuctionId,
            minBid: _minBid,
            end: block.timestamp + _duration,
            
            offersMadeCounter: 0,
            bestOfferAmount: _minBid,
            nextOfferCoverSpread: _spreadOnEachBid,
            
            bestOfferIdAddress: address(0),
            seller: msg.sender,

            name: _name,
            description: _description,

            concluded: false
     
        });

        nextAuctionId++;

    }

    function bidOn(uint256 _auctionId) external payable isValidAuction(_auctionId) doubleBid(_auctionId) {

        Auction storage _auction = auctions[_auctionId];

        require(_auction.end > block.timestamp, "auction has ended");

        uint256 spread = _auction.offersMadeCounter > 0 ? _auction.nextOfferCoverSpread : 0; 
        

        require(msg.value >= _auction.minBid, "bid too low");

        uint256 shouldCover = _auction.bestOfferAmount + spread;

        require(msg.value >= shouldCover, "did not cover spread");

        myBids[msg.sender].push(_auctionId);
        bidAmount[msg.sender][_auctionId] = msg.value;


        if (_auction.bestOfferIdAddress != address(0)) { payable(_auction.bestOfferIdAddress).transfer(_auction.bestOfferAmount); }

        _auction.bestOfferAmount = msg.value;
        _auction.bestOfferIdAddress = msg.sender;
        _auction.offersMadeCounter += 1;
            
    }

    function trade(uint256 _auctionId) external isValidAuction(_auctionId) authTrader(_auctionId) isConcluded(_auctionId) {
        
        Auction storage a = auctions[_auctionId];

        if ( a.bestOfferIdAddress != address(0) ) { payable(a.seller).transfer(a.bestOfferAmount); }

        a.concluded = true;

    }

    function getAuction(uint256 _auctionId) external view isValidAuction(_auctionId) returns(Auction memory) { return auctions[_auctionId]; }

    function getMyBids() external view returns(uint256[] memory) { return myBids[msg.sender]; }

    function getMyBidAmount(uint256 _auctionId) external view isValidAuction(_auctionId) returns(uint256) { return bidAmount[msg.sender][_auctionId]; }

}