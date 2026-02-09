import { Container } from '@mui/material';
import InfoCard from '../components/InfoCard';
import ProposalCard from '../components/ProposalCard';
import stored from '../constants/proposalsstored.json';
import { Grid } from '@mui/material';

function Home() {
  return (
    <Container sx={{ mt: 4 }}>

      <InfoCard />

      <Grid container spacing={2} justifyContent="center" mb={2}>
        {Object.keys(stored).sort((a, b) => b - a).map((key) => (
          <Grid item key={key}>
            <ProposalCard proposal={stored[key]} />
          </Grid>
        ))}
      </Grid>

    </Container>
  );
}

export default Home;