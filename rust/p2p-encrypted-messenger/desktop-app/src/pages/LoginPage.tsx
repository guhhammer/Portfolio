import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = () => {
    if (username && password) {
      navigate("/home");
    } else {
      alert("Please enter username and password");
    }
  };

  return (
    <div
      className="
        border-2 border-gray-500
        w-2/5 max-w-[400px] h-4/5 max-h-[300px] 
        aspect-[4/3] bg-secondary
        rounded-xl shadow-lg hover:shadow-2xl
        absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2
        
grid grid-rows-3 gap-2 p-6 justify-center
      content-center
      place-items-center

      "
    >
      {/* Paragraph aligned left */}
      <p className=" text-[50px] text-background font-serif ">
        Login
      </p>

      {/* Inputs */}
      <div className="h-full grid grid-rows-w content-between">

      <input
        type="text"
        placeholder="Usuário"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        className="w-4/5 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-accent"
        
        />
      <input
        type="password"
        placeholder="Senha"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        className="w-4/5 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-accent"
        />
      </div>

      {/* Button */}
      <button 
        onClick={handleLogin}
        className="w-4/5 secondary-foreground  bg-secondary text-white py-2 rounded-md hover:bg-secondary/90 transition-colors"
        >
        Entrar
      </button>
    </div>
  );
}
