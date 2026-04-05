import React, { useState } from "react";
import { login } from "./api";

function Login({ onLogin, switchToRegister }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    const res = await login(email, password);
    if (res.success) {
      onLogin(res.data.token);
    } else {
      alert(res.message || "Login failed");
    }
  };

  return (
    <div className="h-screen flex items-center justify-center bg-gradient-to-br from-black to-gray-900 text-white">
      <div className="bg-gray-800 p-8 rounded-2xl shadow-2xl w-96">

        <h2 className="text-2xl font-bold mb-6 text-center">
          Finance Login
        </h2>

        <input
          className="w-full mb-4 p-3 rounded bg-gray-700 outline-none focus:ring-2 focus:ring-blue-500"
          placeholder="Email"
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          className="w-full mb-6 p-3 rounded bg-gray-700 outline-none focus:ring-2 focus:ring-blue-500"
          placeholder="Password"
          type="password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button
          onClick={handleLogin}
          className="w-full bg-blue-600 hover:bg-blue-700 transition p-3 rounded-lg font-semibold"
        >
          Login
        </button>

        {/* 🔥 Toggle */}
        <p className="text-center text-sm text-gray-400 mt-4">
          Don’t have an account?{" "}
          <button
            onClick={switchToRegister}
            className="text-blue-400 hover:underline"
          >
            Register
          </button>
        </p>

      </div>
    </div>
  );
}

export default Login;