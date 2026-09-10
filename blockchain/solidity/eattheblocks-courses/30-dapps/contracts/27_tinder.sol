// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;
pragma experimental ABIEncoderV2;

contract Tinder {


    event NewMatch(address indexed _from, address indexed _to, uint256 _date);

    event MessageSent(uint256 _id, string _content, address _from, address _to, uint256 _createdAt);


    uint256 public nextUserId;
    uint256 private nextMessageID;

    address public admin;


    enum Gender { MALE, FEMALE } 

    enum SwipeStatus { UNDEFINED, LIKE, DISLIKE }

    struct User {

        uint8 age;
        Gender gender;

        string name;
        string city;

        string picURL;

        address lookup;

    }

    struct SwipeSession {

        uint256 start;
        uint256 count;

    }

    struct Message {

        uint256 id;
        uint256 createdAt;

        address from;
        address to;

        string content;

    }


    mapping(uint256 => address) private listUsers;
    mapping(address => User) private users;

    mapping(address => SwipeSession) private swipeSessions;
    mapping(uint256 => Message[]) private conversations;

    mapping(address => mapping(address => SwipeStatus)) private swipes;
    mapping(bytes32 => mapping(uint8 => address[])) private userIdsByCity;


    modifier onlyAdmin() { require(msg.sender == admin, "Only admin can access this function"); _; }

    modifier userExists(address u) { require(users[u].age > 0, "user is not registered"); _; }


    constructor() { admin = msg.sender; }


    function register(
        string calldata _name,
        string calldata _city,
        uint8 _gender,
        uint8 _age,
        string calldata _picURL
    )
        external    
    {

        require(users[msg.sender].age == 0, "user is registered already");
        require(_age > 17, "restricted by age");
        require(_length(_name) > 1, "must define name");
        require(_length(_city) > 1, "city cannot be empty");
        require(_length(_picURL) > 1, "picURL cannot be empty");
        require(_gender < 2, "gender not valid");

        Gender select = 0 == _gender ? Gender.MALE : Gender.FEMALE;

        users[msg.sender] = User({
            
            age: _age,
            gender: select,
            name: _name,
            city: _city,
            picURL: _picURL,
            lookup: msg.sender

        });

        listUsers[nextUserId] = msg.sender;

        userIdsByCity[keccak256(abi.encodePacked(_city))][_gender].push(msg.sender);

        nextUserId++;

    }

    function getMatchableUsers() external view userExists(msg.sender) returns(User[] memory) { 

        User storage _user = users[msg.sender];
        uint8 oppositeGender = _user.gender == Gender.MALE ? 1 : 0;

        address[] storage userIds = userIdsByCity[keccak256(abi.encodePacked(_user.city))][oppositeGender];

        uint matchableUSerCount;

        for(uint i = 0; i < userIds.length; i++) {

            address userID = userIds[i];

            if(swipes[msg.sender][userID] == SwipeStatus.UNDEFINED) { matchableUSerCount++; }

        }

        User[] memory _users = new User[](matchableUSerCount);
        for(uint j = 0; j < matchableUSerCount; j++) {

            address userId = userIds[j];

            if(swipes[msg.sender][userId] == SwipeStatus.UNDEFINED) { 

                _users[j] = users[userId];

            }

        }

        return _users;

    }

    function swipe(uint8 _swipeStatus, address _userId) external userExists(msg.sender) userExists(_userId) {

        require(swipes[msg.sender][_userId] == SwipeStatus.UNDEFINED, "cannot swipe same person twice");

        SwipeSession storage _swipeSession = swipeSessions[msg.sender];

        if (_swipeSession.start + 86400 <= block.timestamp) { 
            
            _swipeSession.start = block.timestamp;
            _swipeSession.count = 0;
        
        } 

        require(_swipeSession.count <= 100, "you have already used all your swipes for today");

        _swipeSession.count++;

        if (_swipeStatus == 2) { swipes[msg.sender][_userId] = SwipeStatus.DISLIKE; return; }

        swipes[msg.sender][_userId] = SwipeStatus.LIKE;

        if (swipes[_userId][msg.sender] == SwipeStatus.LIKE) { emit NewMatch(msg.sender, _userId, block.timestamp); } 

    }

    function sendMessage(address _to, string calldata _content) external userExists(msg.sender) userExists(_to) {

        SwipeStatus a = swipes[msg.sender][_to];
        SwipeStatus b = swipes[_to][msg.sender];

        require(a == SwipeStatus.LIKE && b == SwipeStatus.LIKE, "both users have to like each other to send messages");

        uint256 conversationId = uint256(keccak256(abi.encodePacked(msg.sender, _to)));

        conversations[conversationId].push(Message({

            id: nextMessageID,
            createdAt: block.timestamp, 
            from: msg.sender,
            to: _to,
            content: _content

        }));

        emit MessageSent(nextMessageID, _content, msg.sender, _to, block.timestamp);

        nextMessageID++;

    } 

    function getListUsers(uint256 _id) external view onlyAdmin returns (address) {

        return listUsers[_id];

    }

    function getUser(address _user) external view onlyAdmin returns (User memory) {

        return users[_user];

    }

    function getSwipeSession(address _user) external view onlyAdmin returns (SwipeSession memory) {

        return swipeSessions[_user];

    }

    function getConversation(address _from, address _to) external view onlyAdmin returns (Message[] memory) {

        return conversations[uint256(keccak256(abi.encodePacked(_from, _to)))];

    }

    function getSwipeStatus(address _from, address _to) external view onlyAdmin returns (SwipeStatus) {

        return swipes[_from][_to];

    }

    function getUserIdsByCity(string calldata _cityName, uint8 _gender) external view onlyAdmin returns (address[] memory) {

        return userIdsByCity[keccak256(abi.encodePacked(_cityName))][_gender];

    }

    function _length(string memory str) internal pure returns(uint) {
		
		bytes memory str_bytes = bytes(str);

		return str_bytes.length;

	}

}