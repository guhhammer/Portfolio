import { Typography, Box, Paper, Divider } from '@mui/material';
import daoInfo from "../constants/infocard.json";

function InfoCard() {
  return (
    <>
      {/* Floating Info Card */}
      <Paper
        elevation={6}
        sx={{
          position: 'fixed',
          left: 24,
          top: 100,
          width: 240,
          padding: 2,
          borderRadius: 3,
          backdropFilter: 'blur(10px)',
          backgroundColor: 'rgba(255,255,255,0.8)', // Crystal glass effect
          zIndex: 10,
          userSelect: 'none',
        }}
      >
        <Typography variant="h6" gutterBottom fontWeight="bold">
          DAO Info
        </Typography>
        <Divider sx={{ mb: 1 }} />

        <Box mb={1}>
          <Typography variant="body2" color="text.secondary">Total Shares:</Typography>
          <Typography variant="body1" id="feed-total-shares">0 Ether</Typography>
        </Box>

        <Box mb={1}>
          <Typography variant="body2" color="text.secondary">Available Funds:</Typography>
          <Typography variant="body1" id="feed-available-funds">0 Ether</Typography>
        </Box>

        <Box mb={1}>
          <Typography variant="body2" color="text.secondary">Contribution Limit:</Typography>
          <Typography variant="body1" id="feed-contribution-limit"> date (FIX) {daoInfo.contributionLimit} </Typography>
        </Box> {/* make timestamp into date */}

        <Box mb={1}>
          <Typography variant="body2" color="text.secondary">Next Proposal:</Typography>
          <Typography variant="body1" id="feed-next-proposal-id">ID #</Typography>
        </Box>

        <Box mb={1}>
          <Typography variant="body2" color="text.secondary">Admin:</Typography>
          <Typography variant="body1" id="feed-admin-addr"> {daoInfo.admin} </Typography>
        </Box>

        <Box mb={1}>
          <Typography variant="body2" color="text.secondary"> Quorum: </Typography>
          <Typography variant="body1" id="feed-quorum"> {daoInfo.quorum} </Typography>
        </Box>

        <Box>
          <Typography variant="body2" color="text.secondary"> Voting Period: </Typography>
          <Typography variant="body1" id="feed-voting-period"> {daoInfo.votingPeriod} </Typography>
        </Box>

      </Paper>
    </>
  );
}

export default InfoCard;

{/*
  
  uint256 public totalShares;
  uint256 public availableFunds;
  uint256 public contributionLimit;
  uint256 public nextProposalId;
  address public admin;


  uint256 public constant quorum = 50;
  uint256 public constant votingPeriod = 30 days;
  

*/}