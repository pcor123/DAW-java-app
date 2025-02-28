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
import pack.aplicaciowebllibraria.Connexio; // Importar la clase Connexio

/**
 * Servlet para manejar la modificación de un libro en la base de datos.
 * Permite mostrar un formulario para modificar los detalles de un libro y actualizar los datos en la base de datos.
 */
public class ModificarLlibre extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Método POST que procesa la actualización de los datos de un libro.
     * Recibe los datos del formulario y los actualiza en la base de datos
     * usando una consulta SQL UPDATE.
     *
     * @param request La solicitud HTTP que contiene los parámetros del formulario para modificar el libro.
     * @param response La respuesta HTTP que redirige al usuario tras la actualización del libro.
     * @throws ServletException Si ocurre un error durante el procesamiento del servlet.
     * @throws IOException Si ocurre un error de entrada/salida durante la generación de la respuesta.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el parámetro 'id' de la solicitud y verificar su validez
        String idParam = request.getParameter("id");
        if (idParam == null || !idParam.matches("\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID no válido");
            return;
        }

        int id = Integer.parseInt(idParam);
        String titol = request.getParameter("titol");
        String isbn = request.getParameter("isbn");
        int anyPublicacio = Integer.parseInt(request.getParameter("any_publicacio"));
        int idEditorial = Integer.parseInt(request.getParameter("id_editorial"));

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Verificar si la conexión es nula antes de proceder
            conn = Connexio.getConnexio();
            if (conn == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la conexión con la base de datos.");
                return;
            }

            // Consulta SQL para actualizar los datos del libro
            String sql = "UPDATE llibres SET titol = ?, isbn = ?, any_publicacio = ?, id_editorial = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, titol);
            stmt.setString(2, isbn);
            stmt.setInt(3, anyPublicacio);
            stmt.setInt(4, idEditorial);
            stmt.setInt(5, id);
            int rowsUpdated = stmt.executeUpdate();

            // Verificar si se actualizó algún registro
            if (rowsUpdated > 0) {
                response.sendRedirect("llistaLlibres.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró el libro con el ID proporcionado.");
            }
        } catch (SQLException e) {
            // Imprimir detalles de la excepción en los logs y enviar mensaje de error al cliente
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los datos del libro: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            // Manejo de la excepción ClassNotFoundException (si hay problemas con la conexión)
            Logger.getLogger(ModificarLlibre.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener la clase de conexión: " + ex.getMessage());
        } finally {
            // Asegurarse de cerrar la conexión
            Connexio.tancarConnexio(conn, stmt, null);
        }
    }

    /**
     * Método GET que muestra el formulario HTML para modificar un libro.
     * El formulario es prellenado con los datos actuales del libro para su actualización.
     *
     * @param request La solicitud HTTP que contiene los parámetros del formulario para modificar el libro.
     * @param response La respuesta HTTP que contiene el formulario HTML prellenado con los datos del libro.
     * @throws ServletException Si ocurre un error durante el procesamiento del servlet.
     * @throws IOException Si ocurre un error de entrada/salida durante la generación de la respuesta.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID del libro de la solicitud y verificar su validez
        String idParam = request.getParameter("id");
        if (idParam == null || !idParam.matches("\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID no válido");
            return;
        }

        int id = Integer.parseInt(idParam);

        // Obtener los datos del libro y generar el formulario para modificar
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Connexio.getConnexio();
            if (conn == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la conexión con la base de datos.");
                return;
            }

            String sql = "SELECT titol, isbn, any_publicacio, id_editorial FROM llibres WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                String titol = rs.getString("titol");
                String isbn = rs.getString("isbn");
                int anyPublicacio = rs.getInt("any_publicacio");
                int idEditorial = rs.getInt("id_editorial");

                // Generar formulario con datos actuales del libro
                response.setContentType("text/html;charset=UTF-8");
                try (var out = response.getWriter()) {
                    out.println("<html><body>");
                    out.println("<h1>Formulario para Modificar el Libro</h1>");
                    out.println("<form action='modificarLlibre' method='POST'>");
                    out.println("<input type='hidden' name='id' value='" + id + "'>");
                    out.println("<label for='titol'>Título:</label>");
                    out.println("<input type='text' id='titol' name='titol' value='" + titol + "' required><br><br>");
                    out.println("<label for='isbn'>ISBN:</label>");
                    out.println("<input type='text' id='isbn' name='isbn' value='" + isbn + "' required><br><br>");
                    out.println("<label for='any_publicacio'>Año de Publicación:</label>");
                    out.println("<input type='number' id='any_publicacio' name='any_publicacio' value='" + anyPublicacio + "' required><br><br>");
                    out.println("<label for='id_editorial'>ID Editorial:</label>");
                    out.println("<input type='number' id='id_editorial' name='id_editorial' value='" + idEditorial + "' required><br><br>");
                    out.println("<button type='submit'>Actualizar Libro</button>");
                    out.println("</form>");
                    out.println("</body></html>");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró el libro con el ID proporcionado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los datos del libro: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModificarLlibre.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener la clase de conexión: " + ex.getMessage());
        } finally {
            Connexio.tancarConnexio(conn, stmt, null);
        }
    }
}
