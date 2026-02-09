// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.21;

contract EventOrganizer {

    struct Event {

        uint256 date;
        uint256 price;
        uint256 ticketCount;
        uint256 ticketRemaining;

        string name;
        address admin;

    }

    uint256 public nextId;
    
    mapping(uint256 => Event) public events;
    mapping(address => mapping(uint256 => uint256)) public tickets;

    modifier eventExist(uint256 id) {

        require(id < nextId, "this event doesn't exist");
        _;

    }

    modifier eventActive(uint256 id) {

        require(block.timestamp < events[id].date, 'this event is not active anymore');
        _;

    }
    
    function createEvent(
        string calldata _name,
        uint256 _date,
        uint256 _price,
        uint256 _ticketCount 
    ) 
        external 
    {

        require(_date > 0, 'event can only be organized in the future');
        require(_ticketCount > 0, 'can only create event with at least 1 ticket available');

        events[nextId] = Event({
            date: block.timestamp + _date,
            price: _price,
            ticketCount: _ticketCount,
            ticketRemaining: _ticketCount,
            name: _name, 
            admin: msg.sender
        });

        nextId++;

    }

    function buyTicket(
        uint256 id, 
        uint256 quantity
    ) 
        payable 
        external
        eventExist(id)
        eventActive(id) 
    {

        Event storage e = events[id];

        require(e.ticketRemaining >= quantity, 'not enough ticket left');
        require(msg.value == (e.price * quantity), 'not enough ether sent');

        e.ticketRemaining -= quantity;
        tickets[msg.sender][id] += quantity;
    
    }

    function transferTicket(
        uint256 eventId, 
        uint256 quantity, 
        address to
    ) 
        external
        eventExist(eventId)
        eventActive(eventId)
    {
        
        require(tickets[msg.sender][eventId] >= quantity, 'not enough tickets');
        
        tickets[msg.sender][eventId] -= quantity;
        tickets[to][eventId] += quantity;

    }

    function has(address _buyer, uint256 _eventId) external view returns(uint256) {

        return tickets[_buyer][_eventId];

    }
}