import { useState } from 'react';
import { Container, TextField, Button, Box, Typography } from '@mui/material';
import InfoCard from '../components/InfoCard';

function CreateProposal() {
  const [name, setName] = useState('');
  const [recipient, setRecipient] = useState('');
  const [amount, setAmount] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log({ name, recipient, amount });
    // Call your createProposal logic here
  };

  return (
    <> 
      <InfoCard /> 
      
      <Container
        maxWidth="sm"
        sx={{
          height: '100vh',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
        }}
      >
        <Box
          component="form"
          onSubmit={handleSubmit}
          sx={{
            width: '100%',
            display: 'flex',
            flexDirection: 'column',
            gap: 3,
            padding: 4,
            borderRadius: 4,
            backgroundColor: 'rgba(255,255,255,0.4)',
            backdropFilter: 'blur(10px)',
            boxShadow: '0 8px 32px rgba(0,0,0,0.25)',
          }}
        >
          <Typography variant="h5" fontWeight="bold" textAlign="center">
            Create Proposal
          </Typography>
          <TextField
            label="Proposal Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
          <TextField
            label="Recipient Address"
            value={recipient}
            onChange={(e) => setRecipient(e.target.value)}
            required
          />
          <TextField
            label="Amount (in Ether) maybe change metric"
            type="number"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            required
          />
          <Button type="submit" variant="contained">
            Submit Proposal
          </Button>
        </Box>
      </Container>
    </>
  );
}

export default CreateProposal;
