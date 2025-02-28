import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase prueba la conexión a una base de datos MySQL.
 * Utiliza el controlador JDBC para establecer una conexión con la base de datos
 * especificada y verifica si la conexión se ha establecido correctamente.
 */
public class TestDBConnection {
    
    /**
     * El punto de entrada principal para probar la conexión a la base de datos.
     * 
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        
        // Configuración de la conexión a la base de datos
        String url = "jdbc:mysql://192.168.48.100:3306/llibreria"; // Cambia la IP y la BD si es necesario
        String user = "root"; // Nombre de usuario para la conexión
        String password = "1234"; // Contraseña para la conexión

        // Intenta establecer la conexión
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            
            // Verifica si la conexión se ha establecido correctamente
            if (connection != null) {
                System.out.println("¡Conexión establecida correctamente!");
            } else {
                System.out.println("No se ha podido establecer la conexión.");
            }
        } catch (SQLException e) {
            // En caso de error en la conexión, imprime el mensaje de error
            System.out.println("Error al conectarse a la base de datos:");
            e.printStackTrace();
        }
    }
}
