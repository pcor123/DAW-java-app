package pack.aplicaciowebllibreria.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet de prueba que muestra un mensaje básico con la ruta de contexto de la aplicación.
 * Responde tanto a solicitudes HTTP GET como POST.
 */
@WebServlet(name = "ServletProva", urlPatterns = {"/ServletProva"})
public class ServletProva extends HttpServlet {

    /**
     * Método auxiliar que maneja las solicitudes HTTP para los métodos GET y POST.
     * Este método genera una página HTML con un mensaje que muestra la ruta de contexto de la aplicación.
     *
     * @param request La solicitud HTTP recibida por el servlet.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error específico de servlet.
     * @throws IOException Si ocurre un error de entrada/salida durante la escritura de la respuesta.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletProva</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletProva at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Maneja el método HTTP GET.
     * Llama al método <code>processRequest</code> para generar la respuesta HTML.
     *
     * @param request La solicitud HTTP GET recibida por el servlet.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error específico de servlet.
     * @throws IOException Si ocurre un error de entrada/salida durante la escritura de la respuesta.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja el método HTTP POST.
     * Llama al método <code>processRequest</code> para generar la respuesta HTML.
     *
     * @param request La solicitud HTTP POST recibida por el servlet.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error específico de servlet.
     * @throws IOException Si ocurre un error de entrada/salida durante la escritura de la respuesta.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Devuelve una breve descripción del servlet.
     *
     * @return Una cadena que contiene la descripción del servlet.
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
