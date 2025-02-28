package pack.aplicaciowebllibreria.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import pack.aplicaciowebllibraria.Connexio;

/**
 * Este servlet permite la eliminación de un libro de la base de datos a través de un formulario.
 * <p>El servlet proporciona un formulario para que el usuario ingrese el ID de un libro
 * y lo elimine de la base de datos al enviar una solicitud POST.</p>
 * 
 * @author User
 */
@WebServlet("/EliminarLlibre")
public class EliminarLlibre extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Muestra el formulario HTML para eliminar un libro.
     * <p>Este método responde a las solicitudes GET mostrando un formulario
     * donde el usuario puede ingresar el ID del libro que desea eliminar.</p>
     * 
     * @param request El objeto {@link HttpServletRequest} que contiene la solicitud del cliente.
     * @param response El objeto {@link HttpServletResponse} que contiene la respuesta al cliente.
     * @throws ServletException Si ocurre un error en la ejecución del servlet.
     * @throws IOException Si ocurre un error de entrada/salida durante la generación de la respuesta.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (var out = response.getWriter()) {
            out.println("<html><body>");
            out.println("<h1>Formulario para Eliminar un Libro</h1>");
            out.println("<form action='EliminarLlibre' method='POST'>");
            out.println("<label for='id'>ID del Libro a Eliminar:</label>");
            out.println("<input type='number' id='id' name='id' required><br><br>");
            out.println("<button type='submit'>Eliminar Libro</button>");
            out.println("</form>");
            out.println("</body></html>");
        }
    }

    /**
     * Elimina el libro de la base de datos basado en el ID proporcionado por el usuario.
     * <p>Este método responde a las solicitudes POST, obtiene el ID del libro desde el formulario,
     * y ejecuta una consulta SQL para eliminar el libro correspondiente de la base de datos.
     * Si la eliminación es exitosa, redirige al usuario a una lista de libros. En caso de error,
     * se muestra un mensaje de error detallado.</p>
     * 
     * @param request El objeto {@link HttpServletRequest} que contiene los parámetros enviados por el cliente.
     * @param response El objeto {@link HttpServletResponse} que contiene la respuesta al cliente.
     * @throws ServletException Si ocurre un error en la ejecución del servlet.
     * @throws IOException Si ocurre un error de entrada/salida durante la generación de la respuesta.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Obtener la conexión a la base de datos
            conn = Connexio.getConnexio();
            String sql = "DELETE FROM llibres WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);  // Establecer el ID del libro a eliminar

            // Ejecutar la actualización
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No se encontró el libro con ID: " + id);
            }

            // Redirigir al usuario a la lista de libros
            response.sendRedirect("llistallibres.jsp");

        } catch (SQLException e) {
            // Manejar los errores de SQL
            e.printStackTrace();
            Logger.getLogger(EliminarLlibre.class.getName()).log(Level.SEVERE, "Error eliminando el libro", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error eliminando el libro. Detalles: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            // Manejar excepciones relacionadas con la clase no encontrada
            Logger.getLogger(EliminarLlibre.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Cerrar la conexión y los recursos
            Connexio.tancarConnexio(conn, stmt, null);
        }
    }
}
