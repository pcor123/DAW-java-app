package pack.aplicaciowebllibraria;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configura los servicios web RESTful de Jakarta para la aplicación.
 * <p>Esta clase extiende {@link jakarta.ws.rs.core.Application} y define el 
 * punto de entrada para los servicios RESTful mediante la anotación 
 * {@link jakarta.ws.rs.ApplicationPath}.</p>
 * 
 *
 */
@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {
    // Esta clase se deja vacía ya que su único propósito es configurar la ruta base para los recursos RESTful
}
