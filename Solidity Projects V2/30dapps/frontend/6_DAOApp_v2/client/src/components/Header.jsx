import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import HomeIcon from '@mui/icons-material/Home';
import Tooltip from '@mui/material/Tooltip';
import PersonIcon from '@mui/icons-material/Person';
import DayNightToggle from './DayNight';
import { useWallet } from '../context/WalletProvider';

function Header({mode, setMode}) {

  const { address, isConnected, connectWallet } = useWallet();

  return (
    <AppBar position="static" color="primary">
      <Toolbar>
        {/* DAO Name (left) */}
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          DAO App
        </Typography>

        {/* Day And Night Theme Toggle */}
        <DayNightToggle mode={mode} setMode={setMode} />

        {/* Me Button */}
        <Tooltip title="Me">
          <Button color="inherit" component={RouterLink} to="/me">
            <PersonIcon />
          </Button>
        </Tooltip>

        {/* Home Button */}
        <Tooltip title="Home">
          <Button color="inherit" component={RouterLink} to="/">
            <HomeIcon />
          </Button>
        </Tooltip>

        {/* Events Button */}
        <Button color="inherit" sx={{ ml: 2, textTransform: 'none' }} 
          component={RouterLink} to="/events">
          Events
        </Button>

        {/* Connect Wallet Button (non-functional for now) */}
        <Button
          variant="outlined"
          color="inherit"
          sx={{ ml: 2, textTransform: 'none' }}
          onClick={connectWallet}
        >
          {isConnected ? `${address.slice(0, 6)}...${address.slice(-4)}` : 'Connect Wallet'} 
        </Button>
      </Toolbar>
    </AppBar>
  );
}

export default Header;
