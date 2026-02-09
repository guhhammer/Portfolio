import { useState } from 'react';
import {
  Typography, Container, Box, Paper, Button,
  Dialog, DialogTitle, DialogContent, DialogActions,
  TextField, FormControl, InputLabel, Select, MenuItem
} from '@mui/material';
import InfoCard from '../components/InfoCard';
import userInfo from "../constants/userinfo.json";
import proposalsstored from "../constants/proposalsstored.json";
import { Link as RouterLink } from 'react-router-dom';
import { ethers } from 'ethers';
import DAOABI from '../../../backend/build/contracts/DAO.json';
import { useWallet } from '../context/WalletProvider';


const CONTRACT_ADDRESS = "0xD82e6568c135BfA2Fb5b754148759b54208b7f13";

function Me() {


  console.log(DAOABI.abi.find(f => f.name === 'contribute'));


  const { signer, address, isConnected } = useWallet();

  const [openContribute, setOpenContribute] = useState(false);
  const [amountContribute, setAmountContribute] = useState('');
  const [unitContribute, setUnitContribute] = useState('ether');

  const [openRedeem, setOpenRedeem] = useState(false);
  const [amountRedeem, setAmountRedeem] = useState('');
  const [openTransfer, setOpenTransfer] = useState(false);
  const [recipientAddress, setRecipientAddress] = useState('');
  const [amountTransfer, setAmountTransfer] = useState('');
  const [availableShares, setAvailableShares] = useState(''); // Replace with real value

  const handleOpenContribute = () => setOpenContribute(true);

  const handleCloseContribute = () => {
    setAmountContribute('');
    setUnitContribute('ether');
    setOpenContribute(false);
  };

  const handleContribute = async () => {
    if (!isConnected || !signer) {
      alert("Please connect your wallet first.");
      return;
    }

    try {
      const contract = new ethers.Contract(CONTRACT_ADDRESS, DAOABI.abi, signer);
         // Validate the function is there
    console.log("Has contribute:", typeof contract.contribute);
      console.log(contract);
      try {
  const gas = await contract.estimateGas.contribute({ value });
  console.log("Estimated gas:", gas.toString());
} catch(e) {
  console.error("EstimateGas error:", e);
}

      // Convert amountContribute (string) to BigNumber with proper units
      const value = ethers.parseUnits(amountContribute, unitContribute);

      // Send transaction directly calling contribute(), passing ETH via value
      const tx = await contract.contribute({value: value, gasLimit: 300000});


      console.log("Transaction sent:", tx.hash);
      await tx.wait();
      alert("✅ Contribution successful!");
    } catch (err) {
      console.error("Contribution error:", err);
      alert(`❌ Failed: ${err.reason || err.message || err}`);
    }
  };


  const handleOpenRedeem = async () => {
    setOpenRedeem(true);

    try {
      const contract = new ethers.Contract(CONTRACT_ADDRESS, DAOABI.abi, signer);
      const tx = await contract.shares(address);

      setAvailableShares(tx.toString());

      console.log("Transaction sent:", tx.hash);
      await tx.wait();
      alert("✅ Contribution successful!");
    } catch (err) {
      console.error("Contribution error:", err);
      alert(`❌ Failed: ${err.reason || err.message}`);
    }

  };

  const handleCloseRedeem = () => setOpenRedeem(false);
  const handleRedeem = async () => {

    const contract = new ethers.Contract(CONTRACT_ADDRESS, DAOABI.abi, signer);

    if (!isConnected || !signer) {
      alert("Please connect your wallet first.");
      return;
    }

    if (amountRedeem <= 0 || amountRedeem > userInfo[address].numberShares) {
      alert("Invalid amount to redeem.");
      return;

    }

    try {
      const tx = await contract.redeemShares(amountRedeem);

      console.log("Transaction sent:", tx.hash);
      await tx.wait();
      alert("✅ Contribution successful!");

    } catch (err) {
      console.error("Contribution error:", err);
      alert(`❌ Failed: ${err.reason || err.message}`);
    }
    handleCloseRedeem();
  };

  const handleOpenTransfer = async () => {
    setOpenTransfer(true);

    try {
      const contract = new ethers.Contract(CONTRACT_ADDRESS, DAOABI.abi, signer);
      const tx = await contract.shares(address);

      setAvailableShares(tx.toString());

      console.log("Transaction sent:", tx.hash);
      await tx.wait();
      alert("✅ Contribution successful!");
    } catch (err) {
      console.error("Contribution error:", err);
      alert(`❌ Failed: ${err.reason || err.message}`);
    }

  };
  const handleCloseTransfer = () => setOpenTransfer(false);
  const handleTransfer = async () => {
    if (!isConnected || !signer) {
      alert("Please connect your wallet first.");
      return;
    }

    if (recipientAddress === '') {
      alert("Please enter a recipient address.");
      return;
    }

    try {
      const tx = await contract.transferShares(recipientAddress, amountTransfer);

      console.log("Transaction sent:", tx.hash);
      await tx.wait();
      alert("✅ Transfer successful!");

    } catch (err) {
      console.error("Transfer error:", err);
      alert(`❌ Failed: ${err.reason || err.message}`);
    }
    handleCloseTransfer();
  };

  const user = "0x0000..0000";
  const inv = userInfo[user]?.isInvestor
    ? "✅ You are an investor"
    : "❌ You are not an investor";

  return (
    <>
      <InfoCard />

      <Container sx={{ mt: 4 }}>
        <Typography variant="h4" fontWeight="bold" gutterBottom>
          Your account: {user} {inv}
        </Typography>

        <Typography variant="h4" fontWeight="bold" mb={2} gutterBottom>
          Your shares: {userInfo[user]?.numberShares} ether
        </Typography>

        <Typography variant="h4" fontWeight="bold" gutterBottom>
          Your Votes
        </Typography>

        <Paper
          sx={{
            maxHeight: userInfo[user].votes.length > 0 ? 10 * 48 : 'auto',
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
              .map((pid) => (
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
                    {proposalsstored[pid].executed.toString() === "true" ? "✅ executed" : "❌ not executed"}
                  </Typography>
                  <Typography sx={{ flexGrow: 1, mx: 2 }}>
                    {userInfo[user].votes.includes(pid) ? "✅ voted" : "❌ not voted"}
                  </Typography>
                </Box>
              ))
          )}
        </Paper>

        {/* Buttons Inside Container */}
        <Box sx={{ mt: 4 }}>
          <Box
            sx={{
              display: 'grid',
              gap: 2,
              gridTemplateColumns: {
                xs: '1fr',
                sm: 'repeat(2, 1fr)',
                md: 'repeat(4, 1fr)',
              },
            }}
          >
            <Button fullWidth variant="contained" onClick={handleOpenContribute}>Contribute</Button>
            <Button fullWidth variant="contained" onClick={handleOpenRedeem}>Redeem Shares</Button>
            <Button fullWidth variant="contained" onClick={handleOpenTransfer}>Transfer Shares</Button>
            <Button fullWidth variant="contained" component={RouterLink} to="/create">Create Proposal</Button>
          </Box>
        </Box>

        {/* Dialog for Contribute */}
        <Dialog open={openContribute} onClose={handleCloseContribute}>
          <DialogTitle>Contribute to DAO</DialogTitle>
          <DialogContent sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <TextField
              label="Amount"
              type="number"
              value={amountContribute}
              onChange={(e) => setAmountContribute(e.target.value)}
              fullWidth
            />
            <FormControl fullWidth>
              <InputLabel>Unit</InputLabel>
              <Select
                value={unitContribute}
                label="Unit"
                onChange={(e) => setUnitContribute(e.target.value)}
              >
                <MenuItem value="ether">Ether</MenuItem>
                <MenuItem value="finney">Finney</MenuItem>
                <MenuItem value="gwei">Gwei</MenuItem>
                <MenuItem value="wei">Wei</MenuItem>
              </Select>
            </FormControl>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleCloseContribute}>Cancel</Button>
            <Button variant="contained" onClick={handleContribute}>
              Submit
            </Button>
          </DialogActions>
        </Dialog>

        {/* Dialog for Redeem */}
        <Dialog open={openRedeem} onClose={handleCloseRedeem}>
          <DialogTitle>Redeem Shares</DialogTitle>
          <DialogContent sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <TextField
              label="Available Shares"
              value={availableShares}
              disabled
              fullWidth
            />
            <TextField
              label="Amount to Redeem"
              type="number"
              value={amountRedeem}
              onChange={(e) => setAmountRedeem(e.target.value)}
              fullWidth
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleCloseRedeem}>Cancel</Button>
            <Button variant="contained" onClick={handleRedeem}>Submit</Button>
          </DialogActions>
        </Dialog>

        {/* Dialog for Transfer */}
        <Dialog open={openTransfer} onClose={handleCloseTransfer}>
          <DialogTitle>Transfer Shares</DialogTitle>
          <DialogContent sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <TextField
              label="Available Shares"
              value={availableShares}
              disabled
              fullWidth
            />
            <TextField
              label="Recipient Address"
              value={recipientAddress}
              onChange={(e) => setRecipientAddress(e.target.value)}
              fullWidth
            />
            <TextField
              label="Amount to Transfer"
              type="number"
              value={amountTransfer}
              onChange={(e) => setAmountTransfer(e.target.value)}
              fullWidth
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleCloseTransfer}>Cancel</Button>
            <Button variant="contained" onClick={handleTransfer}>Submit</Button>
          </DialogActions>
        </Dialog>
      </Container>
    </>
  );
}

export default Me;
