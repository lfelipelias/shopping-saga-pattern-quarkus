import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Felipe.Elias
 */
@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(
                title = "Product Service API",
                description = "This API allows operations on Product service",
                version = "1.0"
        )
)
public class ProductServiceApplication extends Application {}
