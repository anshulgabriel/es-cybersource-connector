package au.com.escybersourceconnector;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;


@IfProfileValue(name = "spring.profiles.active", values = {"integration"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = EsCybersourceConnectorApplication.class)
@Slf4j
@Disabled
class EsCybersourceConnectorApplicationTests {

	@Test
	void contextLoads() {
	}

}
