import React, { useState } from "react";
import Login from "./Login";
import Register from "./Register";
import Dashboard from "./Dashboard";

function App() {
  const [token, setToken] = useState(null);
  const [isRegister, setIsRegister] = useState(false);

  if (token) {
    return <Dashboard token={token} />;
  }

  return (
    <>
      {isRegister ? (
        <Register
          onRegister={setToken}
          switchToLogin={() => setIsRegister(false)}
        />
      ) : (
        <Login
          onLogin={setToken}
          switchToRegister={() => setIsRegister(true)}
        />
      )}
    </>
  );
}

export default App;