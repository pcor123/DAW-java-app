package pack.aplicaciowebllibraria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Esta clase proporciona métodos para gestionar la conexión a una base de datos MySQL.
 * Incluye métodos para obtener una conexión y para cerrar los recursos utilizados en la conexión.
 * 
 * <p>Esta clase se utiliza para facilitar la interacción con la base de datos en una aplicación web.
 * La configuración de la conexión (URL, usuario, y contraseña) se realiza de forma centralizada
 * en esta clase.</p>
 * 
 * @author User
 */
public class Connexio {

    // Configuración de la conexión
    private static final String URL = "jdbc:mysql://192.168.0.100:3306/llibreria"; // URL de la base de datos
    private static final String USER = "root"; // Usuario para la conexión
    private static final String PASSWORD = "jakepollo360"; // Contraseña para la conexión

    /**
     * Obtiene una nueva conexión a la base de datos MySQL utilizando los parámetros configurados.
     * 
     * <p>Este método registra el driver JDBC de MySQL si es necesario y devuelve una conexión
     * válida a la base de datos.</p>
     * 
     * @return La conexión establecida a la base de datos.
     * @throws SQLException Si ocurre un error al obtener la conexión.
     * @throws ClassNotFoundException Si no se encuentra el driver de MySQL.
     */
    public static Connection getConnexio() throws SQLException, ClassNotFoundException {
        // Registrar el driver si es necesario
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Cierra la conexión a la base de datos y otros recursos utilizados como el statement y resultSet.
     * 
     * <p>Este método asegura que todos los recursos abiertos (conexión, statement, resultSet)
     * sean correctamente cerrados para evitar fugas de memoria o conexiones abiertas.</p>
     * 
     * @param conn La conexión a la base de datos a cerrar.
     * @param stmt El statement a cerrar.
     * @param rs El resultSet a cerrar.
     */
    public static void tancarConnexio(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
