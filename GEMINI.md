# Gemini Code Development Guidelines

## 🌐 Project Overview

This is a simple Java project named "EjemploConexionJDBC" that demonstrates a basic connection to a MariaDB database using the JDBC driver. The application reads database credentials and connection details from an external `jdbc.properties` configuration file.

The main application logic is contained within `src/ejemploconexionjdbc/App.java`, which handles loading the configuration and establishing the database connection.

## ⚙️ Building and Running

### Dependencies

*   Java Development Kit (JDK)
*   MariaDB JDBC Driver (included in the `lib/` directory)

### Configuration

Before running the application, you must create a configuration file for the database connection.

1.  Copy the sample configuration file:
    ```bash
    cp config/jdbc.properties.sample config/jdbc.properties
    ```
2.  Edit `config/jdbc.properties` and fill in your actual database credentials (host, port, database name, user, and password).

### Compilation

To compile the Java source code, run the following command from the project root directory:

```bash
javac -cp "lib/*" src/ejemploconexionjdbc/App.java -d bin
```

This command compiles the `App.java` file and places the resulting `.class` files into the `bin/` directory.

### Execution

To run the application after compiling it, use the following command from the project root directory:

```bash
java -cp "bin;lib/*" ejemploconexionjdbc.App
```

The application will then attempt to connect to the database using the credentials from `config/jdbc.properties` and will print a success or failure message to the console.

## 🎯 Core Development Principles

*   **Configuration Management**: Keep sensitive data like database credentials out of the source code. The use of a `jdbc.properties` file, which is ignored by Git, is the standard convention for this project.
*   **Modularity**: The database configuration logic is separated into a dedicated `DbConfig` record and a `loadDbConfig` method, keeping the main method clean and focused on the connection logic.
*   **Dependency Management**: Dependencies like the JDBC driver are stored in the `lib/` directory and referenced via the classpath during compilation and execution.
