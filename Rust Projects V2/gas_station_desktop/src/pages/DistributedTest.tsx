import { useState, useEffect } from "react";
import { invoke } from "@tauri-apps/api/core";

function DistributeTest() {
    const [messages, setMessages] = useState<string[]>([]);
    const [nodeId, setNodeId] = useState("NodeA");
    const [isDistributed, setIsDistributed] = useState(false);
    
    // Poll messages every second
    useEffect(() => {
        const interval = setInterval(() => {
            invoke<string[]>("get_messages")
            .then((msgs) => setMessages(msgs))
            .catch(console.error);
        }, 1000);
        
        return () => clearInterval(interval);
    }, []);
    
    // Start distributed node
    const startDistributed = () => {
        invoke("start_distributed", { myId: nodeId })
        .then(() => setIsDistributed(true))
        .catch(console.error);
    };
    
    return (
        <div style={{ padding: 20, fontFamily: "sans-serif" }}>
      <h1>Distributed Node</h1>

      <div style={{ marginBottom: 20 }}>
        <input
          value={nodeId}
          onChange={(e) => setNodeId(e.target.value)}
          placeholder="Node ID"
          />
        <button onClick={startDistributed} disabled={isDistributed}>
          {isDistributed ? "Running..." : "Start Node"}
        </button>
      </div>

      <div>
        <h2>Received Messages</h2>
        <ul>
          {messages.map((msg, idx) => (
              <li key={idx}>{msg}</li>
            ))}
        </ul>
      </div>
    </div>
  );
}

export default DistributeTest;