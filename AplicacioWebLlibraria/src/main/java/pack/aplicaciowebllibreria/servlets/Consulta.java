package pack.aplicaciowebllibreria.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pack.aplicaciowebllibraria.Connexio; // Importar la clase Connexio

/**
 * Este servlet maneja las solicitudes HTTP GET para consultar los libros disponibles
 * en la base de datos y mostrar los resultados en formato HTML.
 * 
 * <p>Cuando se accede a la URL "/consulta", este servlet ejecuta una consulta SQL para obtener 
 * los detalles de los libros (ID, título, ISBN, año de publicación, y ID de editorial) 
 * y los presenta en una tabla HTML.</p>
 * 
 * @author User
 */
@WebServlet("/consulta")
public class Consulta extends HttpServlet {

    /**
     * Maneja las solicitudes HTTP GET realizadas a la URL "/consulta".
     * 
     * <p>Este método establece una conexión con la base de datos, ejecuta una consulta para 
     * obtener información sobre los libros disponibles, y genera una página HTML con los resultados 
     * en forma de una tabla.</p>
     * 
     * @param request El objeto {@link HttpServletRequest} que contiene la solicitud del cliente.
     * @param response El objeto {@link HttpServletResponse} que contiene la respuesta al cliente.
     * @throws ServletException Si ocurre un error en la ejecución del servlet.
     * @throws IOException Si ocurre un error de entrada/salida durante el procesamiento de la respuesta.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Usar PrintWriter para enviar la respuesta al cliente
        try (PrintWriter out = response.getWriter()) {
            
            // Conexión a la base de datos
            try (Connection connection = Connexio.getConnexio()) {
                
                // Crear la declaración y ejecutar la consulta SQL
                Statement statement = connection.createStatement();
                String query = "SELECT id, titol, isbn, any_publicacio, id_editorial FROM llibres";
                ResultSet resultSet = statement.executeQuery(query);

                // Generar la salida HTML con los resultados
                out.println("<html><body>");
                out.println("<h1>Libros Disponibles</h1>");
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Título</th><th>ISBN</th><th>Año Publicación</th><th>ID Editorial</th></tr>");

                // Recorrer los resultados y agregar los datos a la tabla
                while (resultSet.next()) {
                    out.println("<tr>");
                    out.println("<td>" + resultSet.getInt("id") + "</td>");
                    out.println("<td>" + resultSet.getString("titol") + "</td>");
                    out.println("<td>" + resultSet.getString("isbn") + "</td>");
                    out.println("<td>" + resultSet.getInt("any_publicacio") + "</td>");
                    out.println("<td>" + resultSet.getInt("id_editorial") + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
                out.println("</body></html>");
            } catch (Exception e) {
                // Manejo de excepciones en caso de error en la base de datos
                e.printStackTrace(out);
            }
        }
    }
}
