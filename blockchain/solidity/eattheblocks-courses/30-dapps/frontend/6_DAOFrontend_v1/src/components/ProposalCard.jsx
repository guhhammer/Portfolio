import { Typography, Box, Paper, Divider } from '@mui/material';
import FiberManualRecordIcon from '@mui/icons-material/FiberManualRecord';
import { useNavigate } from 'react-router-dom';
import { Button } from '@mui/material';

function ProposalCard({ proposal }) {

  const navigate = useNavigate();

  const handleClick = () => { navigate(`/proposal?id=${proposal.id}`); };

  return (
    <>
     {/* Floating Info Card */}
      <Paper
        elevation={6}
        sx={{
          position: 'center',
          width: 280,
          height: 430,
          padding: 4,
          borderRadius: 3,
          backdropFilter: 'blur(10px)',
          backgroundColor: 'rgba(255,255,255,0.6)', // Crystal glass effect
          zIndex: 1,
          userSelect: 'none',
        }}
        onClick={handleClick}
      >
        <Typography variant="h6" gutterBottom fontWeight="bold" id="feed-id">
          Proposal #{proposal.id}
        </Typography>
        <Divider sx={{ mb: 1 }} />
        
        <Box mb={1}>
          <Typography variant="body2" color="text.secondary">Name</Typography>
          <Typography variant="body1" id="feed-name">{proposal.name}</Typography>
        </Box>

        <Box mb={1}>
          <Typography variant="body2" color="text.secondary">Deadline</Typography>
          <Typography variant="body1" id="feed-deadline">{proposal.deadline}</Typography>
        </Box>

        <Box mb={1}>
          <Typography variant="body2" color="text.secondary">Recipient</Typography>
          <Typography variant="body1" id="feed-recipient">{proposal.recipient}</Typography>
        </Box>

        <Box mb={1}>
          <Typography variant="body2" color="text.secondary">Amount</Typography>
          <Typography variant="body1" id="feed-amount">{proposal.amount}</Typography>
        </Box>

        <Box mb={1}>
          <Typography variant="body2" color="text.secondary">Votes</Typography>
          <Typography variant="body1" id="feed-votes">{proposal.votes}</Typography>
        </Box>

        <Box mb={1}>
          <Typography variant="body1" id="feed-executed">
            {proposal.executed ? (
              <Box display="flex" alignItems="center" gap={1}>
                <FiberManualRecordIcon sx={{ color: 'green', fontSize: 16 }} />
                <span>Already Executed!</span>
              </Box>
            ) : (
              <Box display="flex" alignItems="center" gap={1}>
                <FiberManualRecordIcon sx={{ color: 'red', fontSize: 16 }} />
                <span>Not Executed Yet!</span>
              </Box>
            )}
          </Typography>
        </Box>

        <Box>
          <Button
            fullWidth
            variant="contained"
            color="primary"
            sx={{
              borderRadius: '8px',
              fontWeight: 'bold',
              paddingY: 1.25, // = 10px
            }}
          >
            View More
          </Button>
        </Box>

      </Paper>
    </>
  );
  
}

export default ProposalCard;

{/*

  struct Proposal {               // Optimized for storage packing.
 
      uint256 id;                 // 1 slot. (256 bits = 32 bytes).
      uint256 amount;             // 1 slot.   
      uint256 votes;              // 1 slot.
      uint256 deadline;           // 1 slot.
      string name;                // 1 slot (dynamic pointer).  
      address payable recipient;  // 20 bytes.
      bool executed;              // 1 byte.

  }

*/}
