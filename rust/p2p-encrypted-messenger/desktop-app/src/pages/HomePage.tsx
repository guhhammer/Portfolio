import { useEffect, useState } from "react";
import { invoke } from "@tauri-apps/api/core";
import { useNavigate } from "react-router-dom";

type Message = {
  instance_name: string;
  timestamp: number;
  action: string;
  changes?: string;
  state?: string;
};

export default function App() {
  const navigate = useNavigate();

  const [messages, setMessages] = useState<Message[]>([]);
  const [input, setInput] = useState("");

  const fetchMessages = async () => {
    const msgs: Message[] = await invoke("get_messages");
    setMessages(msgs);
  };

  const handleSend = async () => {
    if (!input) return;
    await invoke("send_message", { msg: input });
    setInput("");
  };

  useEffect(() => {
    const interval = setInterval(() => {
      fetchMessages();
    }, 3000);

    return () => clearInterval(interval);
  }, []);

  return (
    <main className="container">
      <h1>Test Messaging for now: </h1>
      <div style={{ marginBottom: 10 }}>
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder="Type a message"
        />
        <button onClick={handleSend} style={{ marginLeft: 10 }}>
          Send
        </button>
      </div>
      <br />
      <div
        style={{
          border: "2px solid rgba(0,0,0,0.4)", // soft black border
          borderRadius: "12px", // rounded corners
          padding: "10px",
          height: "200px",
          width: "400px",
          display: "flex",
          alignItems: "flex-start",
          flexDirection: "column",
          justifyContent: "flex-start",
          backgroundColor: "#1a1a1a", // dark background (blacker than Tauri hello gray)
          color: "#f0f0f0", // light text for contrast
          overflowY: "auto", // scroll vertically if needed
          margin: "0 auto", // center horizontally
        }}
      >
        {messages.map((m, idx) => (
          <p key={idx} style={{color: "#ffffff"}}>
            [{new Date(m.timestamp * 1000).toLocaleTimeString()}]{" "}
            <b>{m.instance_name}</b>: {m.changes}
          </p>
        ))}
      </div>
      <button onClick={() => { navigate("/"); } }>Back </button>

    </main>
  );
}
