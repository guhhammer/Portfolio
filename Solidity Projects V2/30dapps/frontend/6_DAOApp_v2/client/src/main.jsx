import React, { useState, useMemo, useEffect } from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { ThemeProvider, CssBaseline, createTheme } from '@mui/material';
import { WalletProvider } from './context/WalletProvider'; 
import defaultUserInfo from './constants/userinfo.json';

function Main() {
  const [mode, setMode] = useState('light');

  //implement this shit later, changes will need a server to route file and changes.
  
/*
  const USERINFO_KEY = 'daoUserInfo';
  const connectedWallet = "0x0000..0000"; // Simulate the connected wallet

  // Load user info from localStorage or fallback to json
  const loadUserInfo = () => {
    const stored = localStorage.getItem(USERINFO_KEY);
    return stored ? JSON.parse(stored) : { ...defaultUserInfo };
  };

  // Save user info to localStorage
  const saveUserInfo = (info) => {
    localStorage.setItem(USERINFO_KEY, JSON.stringify(info));
  };

  useEffect(() => {
    const userInfo = loadUserInfo();

    if (!userInfo[connectedWallet]) {
      userInfo[connectedWallet] = { themePreference: 'light' };
      saveUserInfo(userInfo);
    }

    setMode(userInfo[connectedWallet].themePreference || 'light');
  }, [connectedWallet]);

  useEffect(() => {
    const userInfo = loadUserInfo();

    userInfo[connectedWallet] = {
      ...userInfo[connectedWallet],
      themePreference: mode,
    };

    saveUserInfo(userInfo);
  }, [mode, connectedWallet]);*/

  const theme = useMemo(() => createTheme({
    palette: {
      mode,
      ...(mode === 'light'
        ? {
            primary: {
              main: '#1976d2',
              light: '#63a4ff',
              dark: '#004ba0',
              contrastText: '#ffffff',
            },
            secondary: {
              main: '#9c27b0',
              light: '#d05ce3',
              dark: '#6a0080',
              contrastText: '#ffffff',
            },
            background: {
              default: '#f5f5f5',
              paper: '#ffffff',
            },
            text: {
              primary: '#1a1a1a',
              secondary: '#4f4f4f',
              disabled: '#a0a0a0',
            },
            error: {
              main: '#d32f2f',
              light: '#ff6659',
              dark: '#9a0007',
            },
            warning: {
              main: '#ed6c02',
              light: '#ff9800',
              dark: '#e65100',
            },
            info: {
              main: '#0288d1',
              light: '#03a9f4',
              dark: '#01579b',
            },
            success: {
              main: '#2e7d32',
              light: '#4caf50',
              dark: '#1b5e20',
            },
            divider: '#e0e0e0',
            neutral: {
              main: '#64748B',
              light: '#94a3b8',
              dark: '#475569',
              contrastText: '#ffffff',
            },
          }
        : {
            primary: {
              main: '#90caf9',
              light: '#e3f2fd',
              dark: '#42a5f5',
              contrastText: '#0d1117',
            },
            secondary: {
              main: '#ce93d8',
              light: '#f3e5f5',
              dark: '#ab47bc',
              contrastText: '#0d1117',
            },
            background: {
              default: '#121212',
              paper: '#1e1e1e',
            },
            text: {
              primary: '#ffffff',
              secondary: '#bdbdbd',
              disabled: '#666666',
            },
            error: {
              main: '#f44336',
              light: '#e57373',
              dark: '#d32f2f',
            },
            warning: {
              main: '#ffa726',
              light: '#ffb74d',
              dark: '#f57c00',
            },
            info: {
              main: '#29b6f6',
              light: '#4fc3f7',
              dark: '#0288d1',
            },
            success: {
              main: '#66bb6a',
              light: '#81c784',
              dark: '#388e3c',
            },
            divider: '#333333',
            neutral: {
              main: '#94a3b8',
              light: '#cbd5e1',
              dark: '#475569',
              contrastText: '#0d1117',
            },
          }),
    },
    typography: {
      fontFamily: 'Roboto, Arial, sans-serif',
    },
  }), [mode]);

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <App mode={mode} setMode={setMode} />
    </ThemeProvider>
  );
}

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <WalletProvider> 
      <Main />
    </WalletProvider>
  </React.StrictMode>
);

{/*
  make bind to middleware 
  make middleware to trigger calls and store data atomically.
*/}
{/* ADD THIS LOGIC TO MIDDLEWARE SAVING.

event Contributor(address indexed _contributor, uint256 _amount, string indexed _action);

event ExecutedProposal(address indexed _caller, uint256 indexed _proposalId, uint256 _percentage, 
                                  uint256 _amount, address indexed _recipient, uint256 _timestamp);

event FallbackTriggered(address indexed _sender, uint256 _amount);

event ProposalCreated(uint256 indexed _id, string indexed _name, address _recipient, uint256 _amount);

event Transfer(address indexed _sender, address indexed _receiver, uint256 _amount);

event Voted(address indexed _voter, uint256 _proposalId, uint256 _votes);

*/}