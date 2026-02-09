import { Typography, Container } from '@mui/material';

function About() {
  return (
    <Container sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        About Our DAO
      </Typography>
      <Typography paragraph>
        Welcome to the decentralized home of collective innovation. Our DAO is a community-driven protocol 
        where decisions are made by the people, for the people — no central authority, no gatekeepers.
      </Typography>
      <Typography paragraph>
        Powered by smart contracts and governed by token holders, this DAO enables members to propose initiatives, 
        vote on upgrades, and collaborate on shared goals. Whether you're a developer, artist, investor, or curious newcomer, 
        you have a voice here.
      </Typography>
      <Typography paragraph>
        Our mission is to fund and support open, transparent, and borderless projects that empower global communities. 
        From on-chain governance tools to real-world social impact, we believe in the power of code and coordination.
      </Typography>
      <Typography paragraph>
        Join us in shaping the future — your vote matters, your ideas matter. Welcome to the frontier of decentralized autonomy.
      </Typography>
    </Container>
  );
}

export default About;
