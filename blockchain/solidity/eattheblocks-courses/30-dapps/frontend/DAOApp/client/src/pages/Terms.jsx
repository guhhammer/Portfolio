import { Typography, Container } from '@mui/material';

function Terms() {
  return (
    <Container sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        Terms & Conditions
      </Typography>

      <Typography sx={{ mb: 2 }}>
        By accessing and interacting with this DAO frontend, you agree to the following terms. These terms exist to ensure fairness, transparency, and responsible participation within our decentralized ecosystem.
      </Typography>

      <Typography variant="h6" gutterBottom>
        1. Decentralization & Autonomy
      </Typography>
      <Typography sx={{ mb: 2 }}>
        This platform operates as a decentralized autonomous organization (DAO). All governance and protocol-level decisions are made collectively through token-holder votes. No central party guarantees any outcome or feature.
      </Typography>

      <Typography variant="h6" gutterBottom>
        2. Open Participation
      </Typography>
      <Typography sx={{ mb: 2 }}>
        Any individual with a compatible wallet can participate in proposals, voting, or discussions. However, misuse of the platform, such as spam, manipulation, or exploiting smart contract vulnerabilities, may result in being flagged by the community.
      </Typography>

      <Typography variant="h6" gutterBottom>
        3. No Custody or Guarantees
      </Typography>
      <Typography sx={{ mb: 2 }}>
        The frontend does not custody funds or hold user data. All interactions are performed through smart contracts on the blockchain. You are solely responsible for your wallet security and transaction decisions.
      </Typography>

      <Typography variant="h6" gutterBottom>
        4. Use at Your Own Risk
      </Typography>
      <Typography sx={{ mb: 2 }}>
        This is experimental software. By using it, you acknowledge the risks of blockchain technology — including possible bugs, hacks, or unforeseen behaviors. The DAO and its contributors are not liable for any loss of funds, data, or access.
      </Typography>

      <Typography variant="h6" gutterBottom>
        5. Community & Conduct
      </Typography>
      <Typography sx={{ mb: 2 }}>
        We expect respectful, constructive behavior. Contributions that harm or mislead the community — including phishing attempts, sybil attacks, or malicious proposals — may result in social and/or on-chain penalties.
      </Typography>

      <Typography>
        By continuing to use this application, you affirm that you've read and agreed to these terms. Thank you for building a fairer, freer digital future with us.
      </Typography>
    </Container>
  );
}

export default Terms;
