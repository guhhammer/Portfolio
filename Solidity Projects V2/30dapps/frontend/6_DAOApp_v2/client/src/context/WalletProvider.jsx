import React, { createContext, useContext, useState, useEffect } from "react";
import { ethers } from "ethers";

// Create context
const WalletContext = createContext();

// Custom hook for easy access
export const useWallet = () => useContext(WalletContext);

// Provider component
export const WalletProvider = ({ children }) => {
  const [address, setAddress] = useState(null);
  const [signer, setSigner] = useState(null);
  const [provider, setProvider] = useState(null);
  const [network, setNetwork] = useState(null);
  const [isConnected, setIsConnected] = useState(false);

  // Connect to MetaMask
  const connectWallet = async () => {
    try {
      if (!window.ethereum) throw new Error("MetaMask not found");

      const ethProvider = new ethers.BrowserProvider(window.ethereum);
      const accounts = await ethProvider.send("eth_requestAccounts", []);
      const signer = await ethProvider.getSigner();
      const network = await ethProvider.getNetwork();

      setProvider(ethProvider);
      setSigner(signer);
      setAddress(accounts[0]);
      setNetwork(network);
      setIsConnected(true);
    } catch (err) {
      console.error("Wallet connection failed:", err.message);
    }
  };

  // Handle account or network change
  useEffect(() => {
    if (!window.ethereum) return;

    const handleAccountsChanged = (accounts) => {
      if (accounts.length > 0) {
        setAddress(accounts[0]);
        setIsConnected(true);
      } else {
        setAddress(null);
        setIsConnected(false);
      }
    };

    const handleChainChanged = () => {
      window.location.reload();
    };

    window.ethereum.on("accountsChanged", handleAccountsChanged);
    window.ethereum.on("chainChanged", handleChainChanged);

    return () => {
      window.ethereum.removeListener("accountsChanged", handleAccountsChanged);
      window.ethereum.removeListener("chainChanged", handleChainChanged);
    };
  }, []);

  return (
    <WalletContext.Provider
      value={{
        address,
        signer,
        provider,
        network,
        isConnected,
        connectWallet,
      }}
    >
      {children}
    </WalletContext.Provider>
  );
};
