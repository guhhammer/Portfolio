import { BrowserRouter, Routes, Route } from "react-router-dom";
import OtherPage from "./pages/OtherPage";
import HelloPage from "./pages/HelloPage";
import DistributeTest from "./pages/DistributedTest";
import "./App.css";

export default function App() {
  return (
    <BrowserRouter>  
      <Routes>
        <Route path="/" element={<DistributeTest />} />
        <Route path="/hellopage" element={<HelloPage />} />
        <Route path="/other" element={<OtherPage />} />
      </Routes>
    </BrowserRouter>
  )
};
