import React, { useState } from "react";

function Register({ onRegister, switchToLogin }) {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async () => {
    const res = await fetch("http://localhost:8080/api/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ name, email, password })
    });

    const data = await res.json();

    if (data.success) {
      onRegister(data.data.token);
    } else {
      alert(data.message || "Registration failed");
    }
  };

  return (
    <div className="h-screen flex items-center justify-center bg-gradient-to-br from-black to-gray-900 text-white">
      <div className="bg-gray-800 p-8 rounded-2xl shadow-2xl w-96">

        <h2 className="text-2xl font-bold mb-6 text-center">
          Create Account
        </h2>

        <input
          className="w-full mb-4 p-3 rounded bg-gray-700 outline-none focus:ring-2 focus:ring-blue-500"
          placeholder="Full Name"
          onChange={(e) => setName(e.target.value)}
        />

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
          onClick={handleRegister}
          className="w-full bg-green-600 hover:bg-green-700 transition p-3 rounded-lg font-semibold"
        >
          Register
        </button>

        {/* 🔥 Toggle */}
        <p className="text-center text-sm text-gray-400 mt-4">
          Already have an account?{" "}
          <button
            onClick={switchToLogin}
            className="text-blue-400 hover:underline"
          >
            Login
          </button>
        </p>

      </div>
    </div>
  );
}

export default Register;