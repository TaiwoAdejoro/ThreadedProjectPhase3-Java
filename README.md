# 🏨 Travel Experts - JavaFX Desktop Application

**A project by Taiwo, Prince, Jonathan, Jenson, and Cantor.

---

## **Table of Contents**
1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Database Schema](#database-schema)
5. [Setup Instructions](#setup-instructions)
6. [Branches](#branches)
7. [Usage](#usage)
8. [Contributors](#contributors)
9. [Additional Notes](#additional-notes)

---

## **Project Overview**
The **Travel Experts - JavaFX Desktop Application** is a **Java-based prototype** of a **travel management system** that allows agents and managers to efficiently handle **travel packages, customers, bookings, suppliers, and agents**.

This project is an extension of the **C# prototype built in an earlier phase**, with a focus on **Java and Open-Source technology** using a **MySQL/PostgreSQL database**. The goal is to provide **a feature-rich, modern alternative** to the C# version and compare its capabilities.

---

## **Features**
### **For Agents:**
- 📦 **Manage Travel Packages**: Add, edit, delete, and view available travel packages.
- 👥 **Customer Management**: View, edit, and track customer details & past trips.
- 🏢 **Manage Suppliers**: Add and update travel suppliers.
- 📊 **Review Performance**: View **sales numbers and commissions** to date.
- 🏷 **Product Management**: Add, modify, and organize travel-related products.
- 🔍 **Search Functionalities**: Filter displayed results for packages, customers, and suppliers.

### **For Agent Managers:**
- 🏢 **Agent & Agency Management**: Perform CRUD operations on **agents & agencies**.
- 📊 **Performance Statistics**: Review **sales numbers by agent, agency, and customer**.
- 📈 **Data Visualization**: Generate charts for **sales and performance metrics**.

### **Other Functionalities:**
- 💬 **Customer Chat System**: Real-time communication between customers and agents.
- 📝 **Activity Logging**: Log all database changes & errors in `log.txt` with timestamps.
- ✅ **Data Validation**:
    - **Agency Commission** cannot be greater than the **Base Price**.
    - **Package End Date** must be later than the **Start Date**.
    - **Package Name & Description** 
    - More validations included as needed.

---

## **Technologies Used**
- **Java 21** (Programming Language)
- **JavaFX** (User Interface Framework)
- **Maven** (Dependency & Build Management)
- **MySQL / PostgreSQL** (Database)
- **JUnit 5** (Testing Framework)
- **Bootstrap** (for UI)
- **GitHub** (Version Control & Collaboration)


---

## **Database Schema**
The system consists of key tables to manage **agents, customers, bookings, sales, and travel data**:

- **Agents** – Stores agent information.
- **AgentSales** – Tracks agent commissions & sales numbers.
- **Agencies** – Stores agency details.
- **Bookings** – Manages trip reservations & payments.
- **Customers** – Stores customer records & past trips.
- **Packages** – List of available travel packages.
- **Products** – Travel-related services & products.
- **Suppliers** – Manages product suppliers.
- **SalesStatistics** – Stores sales & performance metrics for visualization.
- **UserLogs** – Logs system changes & caught exceptions in `log.txt`.

---

## **Setup Instructions**

### **Prerequisites**
- **IntelliJ IDEA** 
- **Java 21 SDK** 
- **MySQL**   

### **Steps to Set Up the Project**

#### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/TaiwoAdejoro/ThreadedProject_Phase3-Java.git
cd ThreadedProject_Phase3-Java
