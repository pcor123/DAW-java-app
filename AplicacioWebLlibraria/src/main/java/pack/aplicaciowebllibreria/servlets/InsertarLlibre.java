package pack.aplicaciowebllibreria.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import pack.aplicaciowebllibraria.Connexio;

/**
 * Servlet que maneja la inserción de un nuevo libro en la base de datos.
 * Permite mostrar un formulario HTML para capturar los datos del libro
 * y procesar la inserción en la base de datos a través de una solicitud POST.
 */
public class InsertarLlibre extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Método GET que muestra un formulario HTML para insertar un libro.
     * El formulario permite al usuario ingresar el título, ISBN, año de publicación,
     * y un ID editorial opcional.
     *
     * @param request La solicitud HTTP que contiene los parámetros de entrada.
     * @param response La respuesta HTTP que contiene el formulario HTML.
     * @throws ServletException Si ocurre un error durante el procesamiento del servlet.
     * @throws IOException Si ocurre un error de entrada/salida durante la generación de la respuesta.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (var out = response.getWriter()) {
            out.println("<html><body>");
            out.println("<h1>Formulario para Insertar un Libro</h1>");
            out.println("<form action='InsertarLlibre' method='POST'>");
            out.println("<label for='titol'>Título:</label>");
            out.println("<input type='text' id='titol' name='titol' required><br><br>");
            out.println("<label for='isbn'>ISBN:</label>");
            out.println("<input type='text' id='isbn' name='isbn' required><br><br>");
            out.println("<label for='any_publicacio'>Año de Publicación:</label>");
            out.println("<input type='number' id='any_publicacio' name='any_publicacio' required><br><br>");
            out.println("<label for='id_editorial'>ID Editorial (opcional, default 0):</label>");
            out.println("<input type='number' id='id_editorial' name='id_editorial'><br><br>");
            out.println("<button type='submit'>Insertar Libro</button>");
            out.println("</form>");
            out.println("</body></html>");
        }
    }

    /**
     * Método POST que procesa la inserción de un libro en la base de datos.
     * Extrae los parámetros del formulario y los inserta en la tabla 'llibres' de la base de datos.
     * Si no se proporciona un ID editorial, se inserta NULL en el campo correspondiente.
     *
     * @param request La solicitud HTTP que contiene los parámetros del formulario para insertar el libro.
     * @param response La respuesta HTTP que redirige al usuario tras la inserción del libro.
     * @throws ServletException Si ocurre un error durante el procesamiento del servlet.
     * @throws IOException Si ocurre un error de entrada/salida durante la generación de la respuesta.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titol = request.getParameter("titol");
        String isbn = request.getParameter("isbn");
        int anyPublicacio = Integer.parseInt(request.getParameter("any_publicacio"));
        
        // El campo id_editorial es opcional, si no se proporciona se maneja como NULL
        Integer idEditorial = null;
        if (!request.getParameter("id_editorial").isEmpty()) {
            idEditorial = Integer.parseInt(request.getParameter("id_editorial"));
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Obtener conexión a la base de datos
            conn = Connexio.getConnexio(); // Usa el método getConnection de la clase Connexio
            String sql = "INSERT INTO llibres (titol, isbn, any_publicacio, id_editorial) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, titol);
            stmt.setString(2, isbn);
            stmt.setInt(3, anyPublicacio);

            // Si idEditorial no es NULL, se inserta, sino se deja el valor por defecto (NULL)
            if (idEditorial != null) {
                stmt.setInt(4, idEditorial);  // Inserta el valor proporcionado por el usuario
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER); // Deja el valor NULL si no se proporciona
            }
            
            stmt.executeUpdate();

            // Redirigir a la lista de libros (u otra página de tu elección)
            response.sendRedirect("llistallibres.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error insertando el libro.");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InsertarLlibre.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Connexio.tancarConnexio(conn, stmt, null); // Cerrar la conexión
        }
    }
}
