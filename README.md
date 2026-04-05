# Finance Dashboard

A full-stack finance management application built with Spring Boot (Backend) and React (Frontend). Manage your income/expenses with role-based access control.

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen)
![React](https://img.shields.io/badge/React-19-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)

---

## 🏗️ Architecture

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   React     │────▶│  Spring     │────▶│ PostgreSQL  │
│   Frontend  │     │  Boot API   │     │  Database   │
│   (Nginx)   │     │   (Java)    │     │             │
└─────────────┘     └─────────────┘     └─────────────┘
```

---

## ✨ Features

- **User Authentication** - JWT-based secure login/registration
- **Role-Based Access Control**
  - `ADMIN` - Full access (Create, Read, Update, Delete)
  - `ANALYST` - Create & Read access
  - `VIEWER` - Read-only access
- **Transaction Management** - Add, edit, delete income/expenses
- **Dashboard Analytics** - Summary, category-wise totals, monthly trends
- **Soft Delete** - Transactions are soft-deleted (recoverable)

---

## 🛠️ Tech Stack

### Backend
| Technology | Purpose |
|------------|---------|
| Java 17 | Runtime |
| Spring Boot 3.5 | Framework |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | Database access |
| PostgreSQL 16 | Database |
| JWT | Token-based auth |
| Lombok | Code generation |

### Frontend
| Technology | Purpose |
|------------|---------|
| React 19 | UI Library |
| Tailwind CSS | Styling |
| Nginx | Production server |

---

## 🚀 Quick Start

### Prerequisites
- Docker & Docker Compose
- Java 17 (for local development)
- PostgreSQL 16 (for local development)

### Using Docker (Recommended)

```bash
# Clone the repository
git clone https://github.com/PavanBobade01/Zorvyn-backend-assignment.git
cd Zorvyn-backend-assignment

# Start all services
docker-compose up --build
```

Access the application:
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080

### Default Admin Account
```
Email:    pavan@test.com
Password: 123456
```

---

## 📦 Docker Images

Pull from Docker Hub:

```bash
docker pull pavanbobade01/finance-backend:v1.0.0
docker pull pavanbobade01/finance-frontend:v1.0.0
docker pull postgres:16-alpine
```

---

## 🔌 API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login (returns JWT) |

### Transactions
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/transactions` | List all (paginated) |
| POST | `/api/transactions` | Create transaction |
| PATCH | `/api/transactions/{id}` | Update transaction |
| DELETE | `/api/transactions/{id}` | Soft delete transaction |

### Dashboard
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/dashboard/summary` | Income/Expense summary |
| GET | `/api/dashboard/categories` | Category-wise totals |
| GET | `/api/dashboard/trends` | Monthly trends |

### Users (Admin only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | List all users |
| PATCH | `/api/users/{id}/role` | Update user role |
| DELETE | `/api/users/{id}` | Deactivate user |

---

## 📂 Project Structure

```
├── finance/                    # Backend (Spring Boot)
│   ├── src/main/java/
│   │   └── io/github/pavanbobade01/finance/
│   │       ├── config/         # Security, CORS config
│   │       ├── module/         # Business modules
│   │       │   ├── auth/       # Authentication
│   │       │   ├── user/      # User management
│   │       │   ├── transaction/ # Transactions
│   │       │   └── dashboard/  # Analytics
│   │       ├── security/       # JWT & filters
│   │       └── exception/      # Error handling
│   ├── Dockerfile
│   └── pom.xml
│
├── finance-frontend/           # Frontend (React)
│   ├── src/
│   │   ├── Dashboard.js       # Main dashboard
│   │   ├── Login.js           # Login page
│   │   ├── Register.js        # Registration page
│   │   └── api.js             # API calls
│   ├── nginx.conf             # Nginx config
│   └── Dockerfile
│
├── docker-compose.yml          # Orchestrates all services
└── README.md
```

---

## ⚙️ Environment Variables

### Backend
| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://postgres:5432/finance_db` | DB URL |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | DB Username |
| `SPRING_DATASOURCE_PASSWORD` | `P@van01` | DB Password |

---

## 🧪 Development

### Backend (Local)

```bash
cd finance
./mvnw spring-boot:run
```

### Frontend (Local)

```bash
cd finance-frontend
npm install
npm start
```

---

## 📝 License

MIT License - feel free to use this project for learning or commercial purposes.

---

## 👨‍💻 Author

**Pavan Bobade**  
GitHub: [PavanBobade01](https://github.com/PavanBobade01)