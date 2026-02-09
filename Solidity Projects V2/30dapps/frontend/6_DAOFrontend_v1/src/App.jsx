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

function App({mode, setMode}) {
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