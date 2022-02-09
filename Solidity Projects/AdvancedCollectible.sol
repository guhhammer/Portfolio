pragma solidity ^0.8.0;


import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@chainlink/contracts/src/v0.6/VRFConsumerBase.sol";


contract AdvancedCollectible is ERC721, VRFConsumerBase{


	uint256 public tokencounter;
	
	enum Breed {PUG, SHIBA_INU, BRENARD}

	mapping (bytes32 => address) public requestIdToSender;
	mapping (bytes32 => string) public requestIdToTokenURI;
	mapping (uint256 => Breed) public tokenIdToBreed;
	mapping (bytes32 => uint256) public requestIdToTokenId;

	event requestedCollectible(bytes32 indexed requestId);

	bytes32 internal keyHash;
	uint256 internal fee;
	uint256 public randomResult;


	constructor(address _VRFCoordinator, address _LinkToken, bytes32 _keyHash) public 
	VRFConsumerBase(_VRFCoordinator, _LinkToken) 
	ERC721("Dogie", "DOG") {

		tokencounter = 0;
		keyHash = _keyHash;
		fee = 0.1 * 10 ** 10;


	}

	function createCollectible(string memory tokenURI, uint256 userProvidedSeed) public returns(bytes32) {


		bytes32 requestID = requestRandomness(keyHash, fee, userProvidedSeed);

		requestIdToSender[requestID] = msg.sender;

		requestIdToTokenURI[requestID] = tokenURI;

		emit requestedCollectible(requestID);

	}


	function fulfillRandomness(bytes32 requestID, uint256 randomNumber) internal override {


		address dogOwner = requestIdToSender[requestID];

		string memory tokenURI = requestIdToTokenURI(requestID);

		uint256 newItemId = tokencounter;

		_safeMint(dogOwner, newItemId);
		_setTokenURI(newItemId);

		Breed breed = Breed(randomNumber % 3);

		tokenIdToBreed[newItemId] = breed;

	}


}