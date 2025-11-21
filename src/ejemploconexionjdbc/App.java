package ejemploconexionjdbc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class App {
    private static final String CONFIG_FILE_PATH = "config/jdbc.properties";

    private record DbConfig(String url, String user, String password, String testQuery) {
    }

    public static void main(String[] args) {
        try {
            DbConfig config = loadConfiguration();
            runConnectionDemo(config);
        } catch (IOException exception) {
            System.err.println("Error al leer la configuración: " + exception.getMessage());
        } catch (SQLException exception) {
            System.err.println("Error al conectar con la base de datos: " + exception.getMessage());
        }
    }

    private static void runConnectionDemo(DbConfig config) throws SQLException {
        try (Connection connection = DriverManager.getConnection(config.url(), config.user(), config.password());
                PreparedStatement statement = connection.prepareStatement(config.testQuery());
                ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                System.out.println("La conexión se realizó, pero la consulta de prueba no devolvió resultados.");
                return;
            }

            String result = resultSet.getString("id_producto");
            System.out.printf("Conexión exitosa. Resultado de la consulta de prueba: %s%n", result);
        }
    }

    private static DbConfig loadConfiguration() throws IOException {
        Properties properties = new Properties();
        Path path = Path.of(CONFIG_FILE_PATH);

        try (InputStream inputStream = Files.newInputStream(path)) {
            properties.load(inputStream);
        }

        String url = requireProperty(properties, "db.url");
        String user = requireProperty(properties, "db.user");
        String password = requireProperty(properties, "db.password");
        String testQuery = properties.getProperty("db.testQuery", "SELECT 1");

        return new DbConfig(url, user, password, testQuery);
    }

    private static String requireProperty(Properties properties, String key) throws IOException {
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            throw new IOException("La propiedad " + key + " no está definida en " + CONFIG_FILE_PATH);
        }

        return value;
    }
}
