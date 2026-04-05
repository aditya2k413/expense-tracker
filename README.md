# 💸 Expense Tracker & Budget Manager
A full-stack personal finance web application built with **Spring Boot**, **MySQL**, and **Google Gemini AI** — designed to help users track expenses, manage budgets, and get AI-powered financial insights.

---

## ✨ Features

- **Dashboard** — Overview of income, expenses, and savings at a glance
- **Expense & Income Tracking** — Add, view, and delete transactions with categories and dates
- **Category Breakdown** — Visual charts showing spending by category
- **Monthly Budget Limits** — Set budget caps and get notified when approaching them
- **Savings Goals** — Define and track progress toward savings targets
- **Custom Categories** — Create your own spending/income categories
- **AI Insights (Gemini)** — Monthly spending summary and smart budget suggestions powered by Google Gemini AI

---

## 🛠️ Tech Stack

| Layer       | Technology                        |
|-------------|-----------------------------------|
| Backend     | Java 17, Spring Boot 3.x          |
| Frontend    | Plain HTML, CSS, JavaScript       |
| Database    | MySQL, Spring Data JPA (Hibernate)|
| AI          | Google Gemini AI API              |
| Build Tool  | Maven                             |
| IDE         | IntelliJ IDEA                     |

---

## 📁 Project Structure

```
src/
└── main/
    ├── java/com/expensetracker/
    │   ├── model/          # JPA entities (Expense, Income, Category, etc.)
    │   ├── repository/     # Spring Data JPA repositories
    │   ├── service/        # Business logic layer
    │   └── controller/     # REST API controllers
    └── resources/
        ├── static/         # HTML, CSS, JS files
        └── application.properties
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 17+
- MySQL 8.x
- Maven 3.x
- A [Google Gemini API key](https://aistudio.google.com/app/apikey)

### 1. Clone the repository

```bash
git clone https://github.com/aditya2k413/expense-tracker.git
cd expense-tracker
```

### 2. Create the MySQL database

```sql
CREATE DATABASE expense_tracker;
```

### 3. Configure `application.properties`

Create or edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

gemini.api.key=your_gemini_api_key
```

> ⚠️ **Never commit your real credentials.** Use environment variables or a `.env` file in production.

### 4. Run the application

```bash
mvn spring-boot:run
```

Visit `http://localhost:8080` in your browser.

---

## 🤖 AI Features

The app integrates **Google Gemini AI** to provide:
- A natural language **monthly spending summary** based on your transaction data
- **Smart budget suggestions** tailored to your spending patterns

---

## 🔒 Security Note

`application.properties` is listed in `.gitignore` and should never be pushed with real credentials. Use the template above as a reference.

---

## 📌 Status

> 🚧 Actively in development — features being added incrementally.

---

## 👤 Author

**Aditya** — [@aditya2k413](https://github.com/aditya2k413)
