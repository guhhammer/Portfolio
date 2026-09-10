import { IconButton, Tooltip } from '@mui/material';
import Brightness7Icon from '@mui/icons-material/Brightness7'; // sun
import Brightness4Icon from '@mui/icons-material/Brightness4'; // moon

export default function DayNightToggle({ mode, setMode }) {
  const toggleMode = () => {
    setMode(prev => (prev === 'light' ? 'dark' : 'light'));

    
    // You can call your saveUserPreference function here
  };

  return (
    <Tooltip title={mode === 'light' ? "Switch to dark mode" : "Switch to light mode"}>
      <IconButton onClick={toggleMode} color="inherit">
        {mode === 'light' ? <Brightness4Icon /> : <Brightness7Icon />}
      </IconButton>
    </Tooltip>
  );
}
