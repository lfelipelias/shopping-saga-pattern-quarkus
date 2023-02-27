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
                title = "Order Service API",
                description = "This API allows operations on Order service",
                version = "1.0"
        )
)
public class OrderServiceApplication extends Application {}
