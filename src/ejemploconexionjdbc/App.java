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

/**
 * Clase principal que demuestra una conexión a una base de datos mediante JDBC.
 *
 * <p>
 * Esta aplicación carga la configuración de la base de datos desde un archivo de
 * propiedades,
 * establece una conexión, ejecuta una consulta de prueba y muestra el
 * resultado.
 * </p>
 */
public class App {
    /**
     * Ruta al archivo de configuración que contiene los detalles de la conexión a la
     * base de datos.
     */
    private static final String CONFIG_FILE_PATH = "config/jdbc.properties";

    /**
     * Representa la configuración de la base de datos cargada desde el archivo de
     * propiedades.
     *
     * @param url         La URL de conexión JDBC para la base de datos.
     * @param user        El nombre de usuario para la autenticación.
     * @param password    La contraseña para la autenticación.
     * @param testQuery   Una consulta SQL simple para verificar la conexión.
     * @param driverClass El nombre completo de la clase del controlador JDBC.
     */
    private record DbConfig(String url, String user, String password, String testQuery, String driverClass) {
    }

    /**
     * Punto de entrada principal de la aplicación.
     *
     * Carga la configuración, ejecuta la demostración de conexión y maneja las
     * excepciones
     * que puedan ocurrir durante el proceso, imprimiendo mensajes de error
     * apropiados.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en esta
     *             aplicación).
     */
    public static void main(String[] args) {
        try {
            // Carga la configuración desde el archivo jdbc.properties
            DbConfig config = loadConfiguration();
            // Ejecuta la lógica de conexión y consulta
            runConnectionDemo(config);
        } catch (IOException exception) {
            System.err.println("Error al leer la configuración: " + exception.getMessage());
        } catch (SQLException exception) {
            System.err.println("Error al conectar con la base de datos: " + exception.getMessage());
        } catch (ClassNotFoundException exception) {
            System.err.println("Error: No se encontró la clase del controlador de la base de datos: " + exception.getMessage());
        }
    }

    /**
     * Establece una conexión a la base de datos y ejecuta una consulta de prueba.
     *
     * @param config El objeto de configuración de la base de datos.
     * @throws SQLException           Si ocurre un error de acceso a la base de
     *                                datos.
     * @throws ClassNotFoundException Si no se encuentra la clase del controlador
     *                                JDBC.
     */
    private static void runConnectionDemo(DbConfig config) throws SQLException, ClassNotFoundException {
        // Carga dinámicamente la clase del controlador JDBC.
        // Aunque con JDBC 4.0+ no siempre es necesario, es una buena práctica para
        // asegurar la carga del driver.
        Class.forName(config.driverClass());

        // Utiliza un bloque try-with-resources para asegurar que la conexión,
        // el PreparedStatement y el ResultSet se cierren automáticamente.
        try (Connection connection = DriverManager.getConnection(config.url(), config.user(), config.password());
                PreparedStatement statement = connection.prepareStatement(config.testQuery());
                ResultSet resultSet = statement.executeQuery()) {

            if (!resultSet.next()) {
                System.out.println("La conexión se realizó, pero la consulta de prueba no devolvió resultados.");
                return;
            }

            // Asumiendo que la consulta de prueba devuelve una columna llamada
            // "id_producto".
            String result = resultSet.getString("id_producto");
            System.out.printf("Conexión exitosa. Resultado de la consulta de prueba: %s%n", result);
        }
    }

    /**
     * Carga la configuración de la base de datos desde el archivo
     * {@code jdbc.properties}.
     *
     * @return Un objeto {@link DbConfig} con la configuración cargada.
     * @throws IOException Si ocurre un error al leer el archivo de propiedades o
     *                     si falta una propiedad requerida.
     */
    private static DbConfig loadConfiguration() throws IOException {
        Properties properties = new Properties();
        Path path = Path.of(CONFIG_FILE_PATH);

        // Carga las propiedades desde el archivo.
        try (InputStream inputStream = Files.newInputStream(path)) {
            properties.load(inputStream);
        }

        // Lee cada propiedad requerida del archivo, lanzando una excepción si falta
        // alguna.
        String url = requireProperty(properties, "db.url");
        String user = requireProperty(properties, "db.user");
        String password = requireProperty(properties, "db.password");
        String driverClass = requireProperty(properties, "db.driver");
        // La consulta de prueba es opcional y tiene un valor por defecto.
        String testQuery = properties.getProperty("db.testQuery", "SELECT 1");

        return new DbConfig(url, user, password, testQuery, driverClass);
    }

    /**
     * Obtiene una propiedad del objeto {@link Properties} y asegura que no sea nula
     * o vacía.
     *
     * @param properties El objeto de propiedades a consultar.
     * @param key        La clave de la propiedad a obtener.
     * @return El valor de la propiedad si existe y no está vacío.
     * @throws IOException Si la propiedad no se encuentra o está vacía.
     */
    private static String requireProperty(Properties properties, String key) throws IOException {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) { // Usar isBlank() para cubrir espacios en blanco.
            throw new IOException("La propiedad '" + key + "' no está definida o está vacía en " + CONFIG_FILE_PATH);
        }

        return value;
    }
}
