package au.com.escybersourceconnector.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

import java.net.URI;

@Configuration
@Slf4j
public class AwsCognitoConfigs {

  @Bean
  public CognitoIdentityProviderClient cognitoUserManagementClient(AwsRegionProvider awsRegionProvider) {
    log.info("Creating cognito user management client");
    return CognitoIdentityProviderClient.builder()
                                        .region(Region.of("ap-southeast-2"))
            .endpointOverride(URI.create("http://localhost:4566"))
                                        .build();
  }
}
