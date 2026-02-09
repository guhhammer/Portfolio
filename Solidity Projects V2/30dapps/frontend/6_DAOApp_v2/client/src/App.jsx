import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Box } from '@mui/material';
import Header from './components/Header';
import Footer from './components/Footer';
import Home from './pages/Home';
import Me from './pages/Me';
import Events from './pages/Events';
import Proposal from './pages/Proposal';
import About from './pages/About';
import Terms from './pages/Terms';
import CreateProposal from './pages/CreateProposal';
import { useWallet } from './context/WalletProvider';

function App({mode, setMode}) {
  
  const { signer, address, isConnected, connectWallet } = useWallet();

  return (
    
    <BrowserRouter>
      <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
        
        <Header mode={mode} setMode={setMode} />
        
        <Box component="main" sx={{ flexGrow: 1 }}>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/me" element={<Me />} />
            <Route path="/events" element={<Events />} />
            <Route path="/proposal" element={<Proposal />} />
            <Route path="/about" element={<About />} />
            <Route path="/terms" element={<Terms />} />
            <Route path="/create" element={<CreateProposal />} />
          </Routes>
        </Box>
        
        <Footer />

      </Box>
    </BrowserRouter>
  );
}

export default App;

/*

Private Keys
==================
(0) 0x6d2ebe5e96e2bb3a6782f1762b46d16d8ffddc516e4845107595b7a52276c27a
(1) 0xccbbe9ff3235fa49e679d7b96af961edea2c11a62ad8ea6d73a3ceeccfab31c4
(2) 0x72f2a46988d0428bfe9148341b20336be60d05b2744bfc25ab4e91d91c59b2cb
(3) 0x41e87c6b8695428791daf4546e77e988245f6a4311bab2f03a5c679f96b533e3
(4) 0x245130ab7708f99b3eb0d23a1d012f23e6acdc15a56601e747a665570150b761
(5) 0xacca21109a4424070e1db048d5173a6d3949ac2e4435f25c7004da86c237731a
(6) 0x66aef8e15938934a06ff9155ad85bb3778c08f6375c9f12a60be8661952ab6ad
(7) 0xf79dc5dff36ef5be96e85b35c55047b073a0fe4e82b82a3baa9537439fc09758
(8) 0x293e6131b3a811365176e72c57cff1c05c0f11d4f1dc6b243eb4115bc0cb08ae
(9) 0xa83c0e8aa8c37c54eefa1884a0cf8905ec2528946b525e2b3ec280714ef2fd46


*/