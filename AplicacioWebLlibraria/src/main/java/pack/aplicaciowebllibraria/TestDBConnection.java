package pack.aplicaciowebllibraria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase prueba la conexión a una base de datos MySQL.
 * Utiliza el controlador JDBC para establecer una conexión con la base de datos
 * especificada y verifica si la conexión se ha establecido correctamente.
 * 
 * <p>El método {@link #main(String[])} se encarga de configurar la conexión,
 * establecerla y manejar posibles errores en el proceso de conexión.</p>
 * 
 * @author User
 */
public class TestDBConnection {
    
    /**
     * Punto de entrada principal de la aplicación que intenta conectar con la base de datos.
     * 
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        
        // Configuración de la conexión a la base de datos
        String url = "jdbc:mysql://192.168.48.100:3306/llibreria";  // Asegúrate de usar la IP correcta y el nombre de la base de datos
        String user = "root";  // Nombre de usuario
        String password = "";  // Contraseña

        // Intentar establecer la conexión
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Verificar si la conexión fue exitosa
            if (connection != null) {
                System.out.println("¡Conexión establecida correctamente!");
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            // En caso de error, se imprime el mensaje de error
            System.out.println("Error al conectar a la base de datos:");
            e.printStackTrace();
        }
    }
}
