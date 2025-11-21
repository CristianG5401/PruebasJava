# Gemini Code Development Guidelines

## 🌐 Project Overview

This is a simple Java project named "EjemploConexionJDBC" that demonstrates how to connect to a MariaDB database and perform a full suite of CRUD (Create, Read, Update, Delete) operations. The application reads database credentials and connection details from an external `jdbc.properties` configuration file.

The main application logic is contained within `src/ejemploconexionjdbc/App.java`, which handles loading the configuration, establishing the database connection, and running the CRUD demonstration.

## ⚙️ Building and Running

### Dependencies

*   Java Development Kit (JDK)
*   MariaDB JDBC Driver (included in the `lib/` directory)

### 1. Database Setup

Before running the application, you must set up the database schema and initial data. Connect to your MariaDB instance and execute the script located at `db/create-populate-inventario-table.sql`. This will create the necessary `inventario` table and populate it with sample data.

### 2. Configuration

After setting up the database, you must create a configuration file for the database connection.

1.  Copy the sample configuration file:
    ```bash
    cp config/jdbc.properties.sample config/jdbc.properties
    ```
2.  Edit `config/jdbc.properties` and fill in your actual database credentials (host, port, database name, user, and password).

### 3. Compilation

To compile the Java source code, run the following command from the project root directory:

```bash
javac -cp "lib/*" src/ejemploconexionjdbc/App.java -d bin
```

This command compiles the `App.java` file and places the resulting `.class` files into the `bin/` directory.

### 4. Execution

To run the application after compiling it, use the following command from the project root directory:

```bash
java -cp "bin;lib/*" ejemploconexionjdbc.App
```

The application will then connect to the database, run the CRUD demonstration, and print a success or failure message to the console.

## 🎯 Core Development Principles

*   **Configuration Management**: Keep sensitive data like database credentials out of the source code. The use of a `jdbc.properties` file, which is ignored by Git, is the standard convention for this project.
*   **Modularity**: The database configuration logic is separated from the application logic.
*   **Dependency Management**: Dependencies like the JDBC driver are stored in the `lib/` directory and referenced via the classpath during compilation and execution.

## 🗃️ SQL Script Organization

Database-related SQL scripts are organized in the `db/` directory at the project root. This structure helps in separating database schema definitions from data manipulation operations.

*   **`db/ddl/`**: This directory is intended for Data Definition Language (DDL) scripts. These scripts are used to define, modify, or drop the database structure.
*   **`db/dml/`**: This directory is for Data Manipulation Language (DML) scripts. These scripts are used for inserting, updating, or deleting data. The main setup script `create-populate-inventario-table.sql` combines both for convenience.