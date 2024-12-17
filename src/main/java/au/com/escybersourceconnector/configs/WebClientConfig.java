package au.com.escybersourceconnector.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient cybersourceWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://apitest.cybersource.com")
                .defaultHeaders(headers -> {
                    headers.add("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                    headers.add("Content-Type", "application/json");
                })
                .build();
    }
}
