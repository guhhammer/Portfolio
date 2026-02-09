// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;
pragma experimental ABIEncoderV2;

contract Twitter {

    
    event MessageSent(uint256 _id, string _content, address _from, address _to, uint256 _createdAt);
    
    event TweetSent(uint256 _id, address _author, string _content, uint256 _createdAt);
    

    uint256 private nextTweetID;
    uint256 private nextMessageID;
    
    address public admin;


    struct Tweet {

        uint256 id;
        uint256 createdAt;

        address author;
        string content;

    }

    struct Message {

        uint256 id;
        uint256 createdAt;

        address from;
        address to;

        string content;

    }


    mapping(uint256 => Tweet) private tweets;
    mapping(address => uint256[]) private tweetsOf;
    mapping(uint256 => Message[]) private conversations;
    mapping(address => address[]) private following;
    mapping(address => mapping(address => bool)) private operators;


    modifier authOperator(address _from) { require(operators[_from][msg.sender] || msg.sender == _from, "operator not authorized"); _; }


    constructor() { admin = msg.sender; }


    function tweet(string calldata _content) external { _tweet(msg.sender, _content); }
    
    function tweetFrom(address _from, string calldata _content) external { _tweet(_from, _content); }

    function sendMessage(string calldata _content, address _to) external { _sendMessage(_content, msg.sender, _to); }

    function sendMessageFrom(string calldata _content, address _from, address _to) external { _sendMessage(_content, _from, _to); }

    function follow(address _followed) external { following[msg.sender].push(_followed); }

    function retrieveFollowing() external view returns(address[] memory) { return following[msg.sender]; }

    function getLatestTweets(uint8 batch, uint8 page) external view returns(Tweet[] memory) {

        require(batch >= 1 && batch <= 25, "invalid batch size");

        // How many tweets exist
        uint total = nextTweetID;

        // The first index to start this batch from (latest first)
        uint start = total > batch * page ? total - (batch * page) : 0;

        // The last index to stop at (but not include)
        uint end = start >= batch ? start - batch : 0;

        // Actual number of tweets we can return
        uint actualBatchSize = start - end;

        Tweet[] memory arr_t = new Tweet[](actualBatchSize);
        uint bi = 0;

        // Fill array from newest to oldest in this batch
        for (uint i = start; i > end; i--) {
            Tweet storage t = tweets[i - 1]; // tweets are 0-indexed
            arr_t[bi] = Tweet(t.id, t.createdAt, t.author, t.content);
            bi++;
        }

        return arr_t;

    }

    function getLatestMessages(address with) external view returns(Message[] memory) { 
        
        uint256 conversationId = uint256(keccak256(abi.encodePacked(with, msg.sender)));

        return conversations[conversationId]; 
        
    }

    function getTweetsOf(address _user, uint8 batch, uint8 page) external view returns(Tweet[] memory) {

        require(batch <= 25 && batch >= 1, "invalid batch size");

        uint[] storage tweetIds = tweetsOf[_user];

        // How many tweets exist
        uint total = tweetIds.length;

        // The first index to start this batch from (latest first)
        uint start = total > batch * page ? total - (batch * page) : 0;

        // The last index to stop at (but not include)
        uint end = start >= batch ? start - batch : 0;

        // Actual number of tweets we can return
        uint actualBatchSize = start - end;

        Tweet[] memory arr_t = new Tweet[](actualBatchSize);

        uint bi = 0;

        // Fill array from newest to oldest in this batch
        for (uint i = start; i > end; i--) {
            Tweet storage t = tweets[tweetIds[ i - 1 ]]; // tweets are 0-indexed
            arr_t[bi] = Tweet(t.id, t.createdAt, t.author, t.content);
            bi++;
        }

        return arr_t;

    }

    function userAuthenticateOperator(address _operator) external { operators[msg.sender][_operator] = true; }

    function _tweet(address _from, string memory _content) internal authOperator(_from) {

        tweets[nextTweetID] = Tweet({

            id: nextTweetID,
            createdAt: block.timestamp,

            author: _from,
            content: _content

        });

        tweetsOf[_from].push(nextTweetID);

        emit TweetSent(nextTweetID, _from, _content, block.timestamp);

        nextTweetID++;

    }

    function _sendMessage(string memory _content, address _from, address _to) internal authOperator(_from) {

        uint256 conversationId = uint256(keccak256(abi.encodePacked(_from, _to)));

        conversations[conversationId].push(Message({

            id: nextMessageID,
            createdAt: block.timestamp, 
            from: _from,
            to: _to,
            content: _content

        }));

        emit MessageSent(nextMessageID, _content, _from, _to, block.timestamp);

        nextMessageID++;

    }

}