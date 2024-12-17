package au.com.escybersourceconnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class EsCybersourceConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsCybersourceConnectorApplication.class, args);
	}
}

