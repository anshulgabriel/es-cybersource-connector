package au.com.escybersourceconnector.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import au.com.es.paymentsassist.enums.Entity;
import au.com.es.paymentsassist.enums.Operation;
import org.springframework.stereotype.Component;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface OperationComponent {

  Entity entity();

  Operation[] operation();

}
