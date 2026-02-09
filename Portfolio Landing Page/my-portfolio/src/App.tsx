import React from 'react';
import './App.css';

import Header from "./components/Header";
import Home from "./components/Home";
import Contact from './components/Contact';

function App() {
  return (
    <div className="bg-white dark:bg-black text-black dark:text-white">
      <Header />
      <Home />
      <Contact />
    </div>
  );
}

export default App;
