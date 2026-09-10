
pub const SIMULATED_TRANSACTIONS: [[u8; 2]; 100] = [
    
    [0, 5], [1, 5], [2, 5], [3, 5], [4, 5], [6, 5], [7, 5], [8, 5], [9, 5], [10, 5],
    [11, 5], [12, 5], [13, 5], [14, 5], [15, 5], [16, 5], [17, 5], [18, 5], [19, 5], [20, 5],
    [21, 5], [22, 5], [23, 5], [24, 5], [25, 5], [26, 5], [27, 5], [28, 5], [29, 5], [30, 5],

    [0, 6], [1, 6], [2, 6], [3, 6], [4, 6], [7, 6], [8, 6], [9, 6], [10, 6], [11, 6],
    [12, 6], [13, 6], [14, 6], [15, 6], [16, 6], [17, 6], [18, 6], [19, 6], [20, 6], [21, 6],

    [0, 7], [1, 7], [2, 7], [3, 7], [4, 7], [5, 7], [6, 7], [8, 7], [9, 7], [10, 7],
    [11, 7], [12, 7], [13, 7], [14, 7], [15, 7],

    [0, 8], [1, 8], [2, 8], [3, 8], [4, 8], [5, 8], [6, 8], [7, 8], [9, 8], [10, 8],

    [0, 9], [1, 9], [2, 9], [3, 9], [4, 9],

    [10, 11], [12, 13], [14, 15], [16, 17], [18, 19], [20, 21], [22, 23], [24, 25], [26, 27], [28, 29],
    [30, 31], [32, 33], [34, 35], [36, 37], [38, 39], [5, 20], [6, 21], [7, 22], [8, 23], [9, 24]

];

pub const SIMULATED_ADDRESSES: [(u8, &str); 40] = [
    (0, "0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266"),
    (1, "0x70997970C51812dc3A010C7d01b50e0d17dc79C8"),
    (2, "0x3C44CdDdB6a900fa2b585dd299e03d12FA4293BC"),
    (3, "0x90F79bf6EB2c4f870365E785982E1f101E93b906"),
    (4, "0x15d34AAf54267DB7D7c367839AAf71A00a2C6A65"),
    (5, "0x9965507D1a55bcC2695C58ba16FB37d819B0A4dc"),
    (6, "0x976EA74026E726554dB657fA54763abd0C3a0aa9"),
    (7, "0x14dC79964da2C08b23698B3D3cc7Ca32193d9955"),
    (8, "0x23618e81E3f5cdF7f54C3d65f7FBc0aBf5B21E8f"),
    (9, "0xa0Ee7A142d267C1f36714E4a8F75612F20a79720"),
    (10, "0xBcd4042DE499D14e55001CcbB24a551F3b954096"),
    (11, "0x71bE63f3384f5fb98995898A86B02Fb2426c5788"),
    (12, "0xFABB0ac9d68B0B445fB7357272Ff202C5651694a"),
    (13, "0x1CBd3b2770909D4e10f157cABC84C7264073C9Ec"),
    (14, "0xdF3e18d64BC6A983f673Ab319CCaE4f1a57C7097"),
    (15, "0xcd3B766CCDd6AE721141F452C550Ca635964ce71"),
    (16, "0x2546BcD3c84621e976D8185a91A922aE77ECEc30"),
    (17, "0xbDA5747bFD65F08deb54cb465eB87D40e51B197E"),
    (18, "0xdD2FD4581271e230360230F9337D5c0430Bf44C0"),
    (19, "0x8626f6940E2eb28930eFb4CeF49B2d1F2C9C1199"),
    (20, "0x7cB57B5A97eAbe94205C07890BE4cD0f7eB03E9E"),
    (21, "0xA77dD6E84b8A8D18e301Ba69B0c3cE6Fb9D6C6c0"),
    (22, "0xC71B9d6A86565f842405c07D329E5019726d64F5"),
    (23, "0xE1AB53794470e0b57ED10F2Acaa788E33e6C94A3"),
    (24, "0xF40C2F2aE8d1eDDeA5BD1943B3BFeF462D3F3B22"),
    (25, "0xD0214D91C36563bC1d7Df4315B16c4958d785003"),
    (26, "0xA3a2D9E896573D40A95F8BcBb6E506aA8229BFA3"),
    (27, "0x98bD47B7d8A3D6767A7A01E1Ac5e3bfcB90B0F65"),
    (28, "0x63D46c3B7B776c68f7F02E14D8e3C65f678C95E0"),
    (29, "0x8D2e05F4aeb6C6B45A6768D2E1B4BFE31B16cFd1"),
    (30, "0xBcA4bDB2F2fF3F0a8EAE3D75903BE77F678E5dA4"),
    (31, "0xD5A37aB2F14F3C6549E8C874C957AcE1B456e3F5"),
    (32, "0xFAb3aA94cFfFeD6b6CdD4A21b6F5F4623D7e9b83"),
    (33, "0xC6789e3A2D4bF472D7B67E6c94E3D2F55F4B7A91"),
    (34, "0xE3457bCd123F4B89D23C3dAa457E90B34D2fE670"),
    (35, "0xA456CdB234D43A8B6b6F5fA943B5F6A3E7D4B90C"),
    (36, "0xD6A3B6F4E7B3B90C2F3D457E9B12D6F34578AB92"),
    (37, "0xB9C3F4D67A3A8D2B12E7F3C5D4F9A457E6B3D8A2"),
    (38, "0xC3D457E9B12D6F34578AB92A3B6F4E7B3B90C2F3"),
    (39, "0xF7D6A3B5F6A3E7D4B90CD3F457E9B12D6F34578A"),
];

/* SIMULATING TRANSACTION INBETWEEN THESE ACCOUNTS:

account 0  -> account 5  
account 1  -> account 5  
account 2  -> account 5  
account 3  -> account 5  
account 4  -> account 5  
account 6  -> account 5  
account 7  -> account 5  
account 8  -> account 5  
account 9  -> account 5  
account 10 -> account 5  
account 11 -> account 5  
account 12 -> account 5  
account 13 -> account 5  
account 14 -> account 5  
account 15 -> account 5  
account 16 -> account 5  
account 17 -> account 5  
account 18 -> account 5  
account 19 -> account 5  
account 20 -> account 5  
account 21 -> account 5  
account 22 -> account 5  
account 23 -> account 5  
account 24 -> account 5  
account 25 -> account 5  
account 26 -> account 5  
account 27 -> account 5  
account 28 -> account 5  
account 29 -> account 5  
account 30 -> account 5  

account 0  -> account 6  
account 1  -> account 6  
account 2  -> account 6  
account 3  -> account 6  
account 4  -> account 6  
account 7  -> account 6  
account 8  -> account 6  
account 9  -> account 6  
account 10 -> account 6  
account 11 -> account 6  
account 12 -> account 6  
account 13 -> account 6  
account 14 -> account 6  
account 15 -> account 6  
account 16 -> account 6  
account 17 -> account 6  
account 18 -> account 6  
account 19 -> account 6  
account 20 -> account 6  
account 21 -> account 6  

account 0  -> account 7  
account 1  -> account 7  
account 2  -> account 7  
account 3  -> account 7  
account 4  -> account 7  
account 5  -> account 7  
account 6  -> account 7  
account 8  -> account 7  
account 9  -> account 7  
account 10 -> account 7  
account 11 -> account 7  
account 12 -> account 7  
account 13 -> account 7  
account 14 -> account 7  
account 15 -> account 7  

account 0  -> account 8  
account 1  -> account 8  
account 2  -> account 8  
account 3  -> account 8  
account 4  -> account 8  
account 5  -> account 8  
account 6  -> account 8  
account 7  -> account 8  
account 9  -> account 8  
account 10 -> account 8  

account 0  -> account 9  
account 1  -> account 9  
account 2  -> account 9  
account 3  -> account 9  
account 4  -> account 9  

account 10 -> account 11  
account 12 -> account 13  
account 14 -> account 15  
account 16 -> account 17  
account 18 -> account 19  
account 20 -> account 21  
account 22 -> account 23  
account 24 -> account 25  
account 26 -> account 27  
account 28 -> account 29  
account 30 -> account 31  
account 32 -> account 33  
account 34 -> account 35  
account 36 -> account 37  
account 38 -> account 39  
account 5  -> account 20  
account 6  -> account 21  
account 7  -> account 22  
account 8  -> account 23  
account 9  -> account 24  

*/

/* SIMULATED ADDRESSES (THE FIRST 20 ARE EXAMPLES GIVEN BY HARDHAT TESTING TOOLS)

Account #0:  0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266 (10000 ETH)
Account #1:  0x70997970C51812dc3A010C7d01b50e0d17dc79C8 (10000 ETH)
Account #2:  0x3C44CdDdB6a900fa2b585dd299e03d12FA4293BC (10000 ETH)
Account #3:  0x90F79bf6EB2c4f870365E785982E1f101E93b906 (10000 ETH)
Account #4:  0x15d34AAf54267DB7D7c367839AAf71A00a2C6A65 (10000 ETH)
Account #5:  0x9965507D1a55bcC2695C58ba16FB37d819B0A4dc (10000 ETH)
Account #6:  0x976EA74026E726554dB657fA54763abd0C3a0aa9 (10000 ETH)
Account #7:  0x14dC79964da2C08b23698B3D3cc7Ca32193d9955 (10000 ETH)
Account #8:  0x23618e81E3f5cdF7f54C3d65f7FBc0aBf5B21E8f (10000 ETH)
Account #9:  0xa0Ee7A142d267C1f36714E4a8F75612F20a79720 (10000 ETH)
Account #10: 0xBcd4042DE499D14e55001CcbB24a551F3b954096 (10000 ETH)
Account #11: 0x71bE63f3384f5fb98995898A86B02Fb2426c5788 (10000 ETH)
Account #12: 0xFABB0ac9d68B0B445fB7357272Ff202C5651694a (10000 ETH)
Account #13: 0x1CBd3b2770909D4e10f157cABC84C7264073C9Ec (10000 ETH)
Account #14: 0xdF3e18d64BC6A983f673Ab319CCaE4f1a57C7097 (10000 ETH)
Account #15: 0xcd3B766CCDd6AE721141F452C550Ca635964ce71 (10000 ETH)
Account #16: 0x2546BcD3c84621e976D8185a91A922aE77ECEc30 (10000 ETH)
Account #17: 0xbDA5747bFD65F08deb54cb465eB87D40e51B197E (10000 ETH)
Account #18: 0xdD2FD4581271e230360230F9337D5c0430Bf44C0 (10000 ETH)
Account #19: 0x8626f6940E2eb28930eFb4CeF49B2d1F2C9C1199 (10000 ETH)
Account #20: 0xA1B2c3D4E5F6078901234567890abcdef1234567  (10000 ETH)
Account #21: 0xB2C3D4E5F607890A1234567890abcdef12345678  (10000 ETH)
Account #22: 0xC3D4E5F607890A1B234567890abcdef123456789  (10000 ETH)
Account #23: 0xD4E5F607890A1B2C34567890abcdef1234567890  (10000 ETH)
Account #24: 0xE5F607890A1B2C3D4567890abcdef12345678901  (10000 ETH)
Account #25: 0xF607890A1B2C3D4E567890abcdef123456789012  (10000 ETH)
Account #26: 0x07890A1B2C3D4E5F67890abcdef1234567890123  (10000 ETH)
Account #27: 0x890A1B2C3D4E5F607890abcdef12345678901234  (10000 ETH)
Account #28: 0x90A1B2C3D4E5F607890Aabcdef123456789012345  (10000 ETH)
Account #29: 0x0A1B2C3D4E5F607890ABabcdef1234567890123456  (10000 ETH)
Account #30: 0x1B2C3D4E5F607890ABCabcdef12345678901234567  (10000 ETH)
Account #31: 0x2C3D4E5F607890ABCDabcdef123456789012345678  (10000 ETH)
Account #32: 0x3D4E5F607890ABCDEabcdef1234567890123456789  (10000 ETH)
Account #33: 0x4E5F607890ABCDEFabcdef12345678901234567890  (10000 ETH)
Account #34: 0x5F607890ABCDEF0abcdef123456789012345678901  (10000 ETH)
Account #35: 0x607890ABCDEF01abcdef1234567890123456789012  (10000 ETH)
Account #36: 0x07890ABCDEF012abcdef12345678901234567890123  (10000 ETH)
Account #37: 0x7890ABCDEF0123abcdef123456789012345678901234  (10000 ETH)
Account #38: 0x890ABCDEF01234abcdef1234567890123456789012345  (10000 ETH)
Account #39: 0x90ABCDEF012345abcdef12345678901234567890123456  (10000 ETH)

*/