import { useEffect, useState } from "react";
import reactLogo from "./assets/react.svg";
import { invoke } from "@tauri-apps/api/core";
import { useNavigate } from "react-router-dom";

type Message = {
  instance_name: string;
  timestamp: number;
  action: string;
  changes?: string;
  state?: string;
};

function App() {
  const [greetMsg, setGreetMsg] = useState("");
  const [name, setName] = useState("");
  const navigate = useNavigate();

  async function greet() {
    // Learn more about Tauri commands at https://tauri.app/develop/calling-rust/
    setGreetMsg(await invoke("greet", { name }));
  }

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
      <h1>Welcome to Tauri + React</h1>

      <div className="row">
        <a href="https://vite.dev" target="_blank">
          <img src="/vite.svg" className="logo vite" alt="Vite logo" />
        </a>
        <a href="https://tauri.app" target="_blank">
          <img src="/tauri.svg" className="logo tauri" alt="Tauri logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <p>Click on the Tauri, Vite, and React logos to learn more.</p>

      <form
        className="row"
        onSubmit={(e) => {
          e.preventDefault();
          greet();
        }}
      >
        <input
          id="greet-input"
          onChange={(e) => setName(e.currentTarget.value)}
          placeholder="Enter a name..."
        />
        <button type="submit">Greet</button>
      </form>
      <p>{greetMsg}</p>
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
          <p key={idx}>
            [{new Date(m.timestamp * 1000).toLocaleTimeString()}]{" "}
            <b>{m.instance_name}</b>: {m.changes}
          </p>
        ))}
      </div>
      <button onClick={() => { navigate("/"); } }></button>

    </main>
  );
}

export default App;
