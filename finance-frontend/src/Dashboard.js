import React, { useEffect, useState } from "react";
import { getSummary, getTransactions } from "./api";
import { getUserRole } from "./utils";

function Dashboard({ token }) {
  const [summary, setSummary] = useState({});
  const [transactions, setTransactions] = useState([]);
  const [role, setRole] = useState(null);

  useEffect(() => {
    const load = async () => {
      const s = await getSummary(token);
      const t = await getTransactions(token);

      if (s.success) setSummary(s.data);
      if (t.success) setTransactions(t.data.content || t.data);

      setRole(getUserRole(token)); // 🔥 decode role
    };
    load();
  }, [token]);

  // 🔥 DELETE
  const handleDelete = async (id) => {
    await fetch(`http://localhost:8080/api/transactions/${id}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    setTransactions(transactions.filter((t) => t.id !== id));
  };

  return (
    <div className="min-h-screen bg-gray-950 text-white p-6">

      <h1 className="text-3xl font-bold mb-6">💰 Finance Dashboard</h1>

      {/* SUMMARY */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div className="bg-green-600 p-5 rounded-2xl">
          <p>Income</p>
          <h2 className="text-2xl font-bold">₹ {summary.totalIncome || 0}</h2>
        </div>

        <div className="bg-red-600 p-5 rounded-2xl">
          <p>Expense</p>
          <h2 className="text-2xl font-bold">₹ {summary.totalExpense || 0}</h2>
        </div>

        <div className="bg-blue-600 p-5 rounded-2xl">
          <p>Balance</p>
          <h2 className="text-2xl font-bold">₹ {summary.netBalance || 0}</h2>
        </div>
      </div>

      {/* TRANSACTIONS */}
      <div className="bg-gray-900 p-6 rounded-2xl">
        <h2 className="text-xl mb-4">Transactions</h2>

        {transactions.map((t) => (
          <div
            key={t.id}
            className="flex justify-between items-center p-3 border-b border-gray-700"
          >
            <div>
              <p>{t.category}</p>
              <p className="text-sm text-gray-400">{t.date}</p>
            </div>

            <div className="flex items-center gap-4">
              <span
                className={
                  t.type === "INCOME"
                    ? "text-green-400"
                    : "text-red-400"
                }
              >
                ₹ {t.amount}
              </span>

              {/* 🔥 ADMIN ONLY BUTTONS */}
              {role === "ADMIN" && (
                <>
                  <button className="text-yellow-400 hover:underline">
                    Edit
                  </button>

                  <button
                    onClick={() => handleDelete(t.id)}
                    className="text-red-400 hover:underline"
                  >
                    Delete
                  </button>
                </>
              )}
            </div>
          </div>
        ))}
      </div>

    </div>
  );
}

export default Dashboard;