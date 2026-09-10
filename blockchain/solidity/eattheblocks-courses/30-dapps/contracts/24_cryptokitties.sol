// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

import "./23_myerc721token.sol";

contract CryptoKitties is MyERC721 {

    enum Hair { WHITE, BLACK }

    struct Kitty {

        uint256 id;
        uint256 generation;
        
        uint256 geneA;
        uint256 geneB;

    }

    mapping(uint256 => Kitty) private kitties;
    uint256 public nextId;

    address public admin;

    constructor(
        string memory _name, 
        string memory _symbol, 
        string memory _tokenURIBase
    )
        MyERC721(_name, _symbol, _tokenURIBase) 
    {

        admin = msg.sender;

    }

    function breed(uint256 kittyId1, uint256 kittyId2) external {

        require(kittyId1 < nextId && kittyId2 < nextId, "the 2 kitties must exist");
        
        require(idToOwner[kittyId1] == msg.sender // check if it works with this. 
                && idToOwner[kittyId2] == msg.sender, "msg.sender must own the 2 kitties");

        Kitty storage kitty1 = kitties[kittyId1];
        Kitty storage kitty2 = kitties[kittyId2];


        uint256 maxGen = kitty1.generation > kitty2.generation ? kitty1.generation : kitty2.generation;

        uint256 r = _random(5);

        uint256 geneA = r > 3 ? kitty1.geneA : kitty2.geneA;
        uint256 geneB = r > 3 ? kitty1.geneB : kitty2.geneB;

        kitties[nextId] = Kitty(nextId, maxGen+1, geneA, geneB);

        _mint(nextId, msg.sender);
        nextId++;

    }

    function mint() external {

        require(msg.sender == admin, "only admin");

        uint256 r = _random(10);
    
        kitties[nextId] = Kitty(nextId, 1, r, r);
        _mint(nextId, msg.sender);

        nextId++;

    } 

    function _random(uint256 max) internal view returns(uint256) {

        return uint256(keccak256(abi.encodePacked(block.timestamp, block.prevrandao))) % max;

    }

    function getKitty(uint256 _kittyId) external view returns(uint256, uint256, uint256, uint256) {
        
        Kitty storage k = kitties[_kittyId];

        return (k.id, k.generation, k.geneA, k.geneB);

    }

}
