import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import proposalsJSON from "../constants/proposalsstored.json"; // adjust path if needed
import { Box, Paper, Typography, Divider, Button } from "@mui/material";
import FiberManualRecordIcon from '@mui/icons-material/FiberManualRecord';
import InfoCard from "../components/InfoCard";
import CardPopup from "../components/CardPopup";

// convert object to array once:
const proposals = Object.values(proposalsJSON);

function Proposal() {
  const location = useLocation();
  const navigate = useNavigate();
  const queryParams = new URLSearchParams(location.search);
  const id = queryParams.get("id");

  const [proposal, setProposal] = useState(null);
  const [actionInfo, setActionInfo] = useState("");

  useEffect(() => {
    if (!id) {
      navigate("/");
      return;
    }
    const proposalId = parseInt(id, 10);

    const lastProposalId = Math.max(...proposals.map((p) => p.id));

    if (isNaN(proposalId) || proposalId > lastProposalId) {
      navigate("/");
      return;
    }

    const found = proposals.find((p) => p.id === proposalId);
    if (!found) {
      navigate("/");
      return;
    }
    setProposal(found);
  }, [id, navigate]);

  const vote = () => setActionInfo("Vote: Not implemented");
  const executeProposal = () => setActionInfo("Execute Proposal: Not implemented");
  const checkVoted = () => setActionInfo("Check If Voted: Not implemented");

  if (!proposal) {
    return (
      <Box
        sx={{
          minHeight: "100vh",
          bgcolor: "background.default",
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          p: 4,
        }}
      >
        <Typography variant="h6">Loading proposal data...</Typography>
      </Box>
    );
  }

  return (
    <>

      <InfoCard />

      <Box
        sx={{
          minHeight: "80vh",
          bgcolor: "background.default",
          display: "flex",          // fix: use flex instead of 'center'
          gap: 1,
          p: 2,
          justifyContent: "center",
          alignItems: "start",
          width: '100%',
        }}
      >
        {/* Left: Proposal Card */}
        <Paper
          elevation={8}
          sx={{
            flex: 1,
            maxWidth: 600,
            borderRadius: 4,
            p: 4,
            bgcolor: "background.paper",
            backdropFilter: "blur(10px)",
            boxShadow: 8,
          }}
        >

          <Typography variant="h4" fontWeight="bold" gutterBottom>
            Proposal #{proposal.id}
          </Typography>

          <Divider sx={{ mb: 4 }} />

          <Info label="Name" value={proposal.name} />
          <Info label="Deadline" value={proposal.deadline} />
          <Info label="Recipient" value={proposal.recipient} />
          <Info label="Amount" value={proposal.amount} />
          <Info label="Votes" value={proposal.votes} />

          <Box mb={4}>
            <Typography variant="body2" color="text.secondary">
              Status
            </Typography>
            <Box display="flex" alignItems="center" gap={1} mt={0.5}>
              <FiberManualRecordIcon
                sx={{
                  color: proposal.executed ? "success.main" : "error.main",
                  fontSize: 18,
                }}
              />
              <Typography variant="body1" sx={{ fontWeight: "medium" }}>
                {proposal.executed ? "Already Executed" : "Not Executed Yet"}
              </Typography>
            </Box>
          </Box>

          <Box display="flex" flexDirection="column" gap={3}>
            <Button variant="contained" color="primary" fullWidth onClick={vote} size="large">
              Vote
            </Button>

            <Button
              variant="contained"
              color="primary"
              fullWidth
              onClick={checkVoted}
              size="large"
            >
              Check If Voted
            </Button>

            <Button
              variant="contained"
              color="error"
              fullWidth
              onClick={executeProposal}
              size="large"
            >
              Execute Proposal
            </Button>
          </Box>
        </Paper>
      </Box>

      {actionInfo && 
        <CardPopup text={actionInfo} onClose={() => setActionInfo(null)} />
      }
    
    </>
  );
}

const Info = ({ label, value }) => (
  <Box mb={3}>
    <Typography variant="body2" color="text.secondary" sx={{ mb: 0.5 }}>
      {label}
    </Typography>
    <Typography variant="h6">{value}</Typography>
  </Box>
);

export default Proposal;
