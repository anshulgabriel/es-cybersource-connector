package au.com.escybersourceconnector.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;

@Configuration
@ConditionalOnProperty(prefix = "cybersource", name = "exchange.csrf.enabled", havingValue = "true")
public class CsrfConfigurations {

  Customizer<CsrfSpec> csrfCustomizer() {
    return CsrfSpec::disable;
  }
}
