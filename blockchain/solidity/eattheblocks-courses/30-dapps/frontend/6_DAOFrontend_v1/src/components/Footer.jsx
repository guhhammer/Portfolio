import { Typography, Link, Container } from '@mui/material';

function Footer() {
  return (    
    <Container
      maxWidth="md"
      sx={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'row',
        gap: 0,           // No space between items
      }}
    >
      <Typography variant="body2" color="text.secondary" sx={{ mr: 1 }}>
        © {new Date().getFullYear()} DAO App.
      </Typography>
      <Link href="/terms" underline="hover" color="inherit" sx={{ mr: 1 }}>
        Terms
      </Link>
      <Link href="/about" underline="hover" color="inherit">
        About
      </Link>
    </Container>
  );
}

export default Footer;