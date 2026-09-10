// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract RockPaperScissors {

    enum State { INEXISTENT, CREATED, JOINED, COMMITED, REVEALED }

    struct Game {

        uint256 id;
        uint256 bet;
        address payable player0;
        address payable player1;
        State state;

    }

    struct Move {

        bytes32 hash_;
        uint256 value;

    }

    mapping(uint256 => Game) public games;
    mapping(uint256 => mapping(address => Move)) public moves;
    mapping(uint => uint) public winningMoves;
    uint256 public gameId;

    constructor() {

        //1. Rock;
        //2. Paper;
        //3. Scissors;
        winningMoves[1] = 3;
        winningMoves[2] = 1;
        winningMoves[3] = 2;

    }


    function createGame(address payable participant) external payable {
        
        require(msg.value > 0, "need to send some ether");

        games[gameId] = Game(gameId, msg.value, payable(msg.sender), participant, State.CREATED);

        gameId++;

    }

    function joinGame(uint _gameId) external payable {

        Game storage game = games[_gameId];

        require(game.state == State.CREATED, "must be in CREATED state");
        require(game.player1 == msg.sender, "sender must be second player");
        require(game.bet <= msg.value, 'not enough ether sent');

        if(msg.value > game.bet) {

            payable(msg.sender).transfer(msg.value - game.bet);

        }

        game.state = State.JOINED;

    }

    function commitMove(uint256 _gameId, uint256 moveId, uint256 salt) external {

        Game storage game = games[_gameId];

        require(game.state == State.JOINED, "game must be in JOINED state");
        require(game.player0 == msg.sender || game.player1 == msg.sender, "can only be called by one of the players");
        require(moveId == 1 || moveId == 2 || moveId == 3, "move must be either 1,2,3");
        require(moves[_gameId][msg.sender].hash_ == 0, 'move already made');

        moves[_gameId][msg.sender] = Move(keccak256(abi.encodePacked(moveId, salt)), 0);

        if (moves[_gameId][game.player0].hash_ != 0 && moves[_gameId][game.player1].hash_ != 0) {
            
            game.state = State.COMMITED;

        }

    }

    function revealMove(uint256 _gameId, uint256 moveId, uint256 salt) external {

        Game storage game = games[_gameId];

        Move storage move1 = moves[_gameId][game.player0];

        Move storage move2 = moves[_gameId][game.player1];

        Move storage moveSender = moves[_gameId][msg.sender];

        require(game.state == State.COMMITED, 'game must be in commited state');
        require(game.player0 == msg.sender || game.player1 == msg.sender, "can only be called by one of the players");
        require(moveSender.hash_ == keccak256(abi.encodePacked(moveId, salt)), 'moveId does not match commitment');

        moveSender.value = moveId;

        if (move1.value != 0 && move2.value != 0) {

            if (move1.value == move2.value) {

                game.player0.transfer(game.bet);
                game.player1.transfer(game.bet);
                game.state = State.REVEALED;
                return;
            }

            address payable winner;

            winner = winningMoves[move1.value] == move2.value ? game.player0 : game.player1;

            winner.transfer(2 * game.bet);

            game.state = State.REVEALED;
            
        }

    }

}