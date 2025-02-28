package pack.aplicaciowebllibraria.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Este recurso proporciona un punto de acceso a través de un servicio RESTful en Jakarta EE.
 * <p>La clase expone un endpoint que responde con un mensaje de texto simple cuando se hace una solicitud GET.</p>
 * 
 * @author User
 */
@Path("jakartaee10")
public class JakartaEE10Resource {
    
    /**
     * Método que maneja las solicitudes HTTP GET para el recurso {@code jakartaee10}.
     * <p>Este método devuelve una respuesta con el texto "ping Jakarta EE" para indicar
     * que el servicio está activo y responde correctamente.</p>
     * 
     * @return Una respuesta HTTP con el mensaje "ping Jakarta EE".
     */
    @GET
    public Response ping() {
        return Response
                .ok("ping Jakarta EE")  // Devuelve una respuesta HTTP 200 OK con el mensaje
                .build();  // Construye la respuesta
    }
}
