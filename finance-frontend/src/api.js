const BASE_URL = "http://localhost:8080";

export const login = async (email, password) => {
  const res = await fetch(`${BASE_URL}/api/auth/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ email, password })
  });
  return res.json();
};

export const getSummary = async (token) => {
  const res = await fetch(`${BASE_URL}/api/dashboard/summary`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
  return res.json();
};

export const getTransactions = async (token) => {
  const res = await fetch(`${BASE_URL}/api/transactions`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
  return res.json();
};