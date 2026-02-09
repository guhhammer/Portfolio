import { Typography, Container, Box, Paper, Button } from '@mui/material';
import InfoCard from '../components/InfoCard';
import userInfo from "../constants/userinfo.json";
import proposalsstored from "../constants/proposalsstored.json";
import { Link as RouterLink } from 'react-router-dom';

function Me() {
  
  const user = "0x0000..0000";

  const inv = JSON.stringify(userInfo[user].isInvestor) === 'true' 
            ? "✅ You are an investor" : "❌ You are not an investor";
            
  return (
    <>
      <InfoCard />

      <Container sx={{ mt: 4 }}>    
        <Typography variant="h4" fontWeight="bold" gutterBottom>
          Your account: {user} {inv}
        </Typography>

        <Typography variant="h4" fontWeight="bold" mb={2} gutterBottom>
          Your shares: {userInfo[user].numberShares} ether
        </Typography>

        <Typography variant="h4" fontWeight="bold" gutterBottom>
          Your Votes
        </Typography>

        <Paper
          sx={{
            maxHeight: userInfo[user].votes.length > 0 ? 10 * 48 : 'auto', // 48px per row approx
            minHeight: 'auto',
            overflowY: 'auto',
            padding: 2,
            backgroundColor: 'background.paper',
            transition: 'max-height 0.3s ease-in-out',
          }}
          elevation={3}
        >
          {Object.keys(proposalsstored).length === 1 ? (
            <Typography>No votes found for your wallet.</Typography>
          ) : (
            Object.entries(proposalsstored)
            .map((pid) => pid[0])
            .filter((pid) => pid !== "null")
            .sort((a, b) => b - a)
            .map((pid) => {               
              return (
                <Box
                key={pid}
                sx={{
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    paddingY: 1,
                    borderBottom: '1px solid',
                    borderColor: 'divider',
                  }}
                  >
                  <Typography sx={{ width: 80 }}>#{pid}</Typography>
                  <Typography sx={{ flexGrow: 1, mx: 2 }}>{proposalsstored[pid].name}</Typography>
                  <Typography sx={{ flexGrow: 1, mx: 2 }}>
                    {(proposalsstored[pid].executed.toString() === "true") ? "✅ executed" : "❌ not executed" }
                  </Typography>
                  <Typography sx={{ flexGrow: 1, mx: 2 }}>
                    
                    {(userInfo[user].votes.includes(pid)) ? "✅ voted" : "❌ not voted"}

                  </Typography>
                </Box>
              );
            })
          )}
        </Paper>

        {/* Buttons Inside Container */}
        <Box sx={{ mt: 4 }}>
          <Box
            sx={{
              display: 'grid',
              gap: 2,
              gridTemplateColumns: {
                xs: '1fr',             // mobile: 1 column
                sm: 'repeat(2, 1fr)',  // small screen: 2x2
                md: 'repeat(4, 1fr)',  // desktop: 1x4 row
              },
            }}
          >
            <Button fullWidth variant="contained">Contribute</Button>
            <Button fullWidth variant="contained">Redeem Shares</Button>
            <Button fullWidth variant="contained">Transfer Shares</Button>
            <Button fullWidth variant="contained" component={RouterLink} to="/create">Create Proposal</Button>
          </Box>
        </Box>
      </Container>
    </>
  );
}

export default Me;
