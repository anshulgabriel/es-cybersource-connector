package au.com.escybersourceconnector.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FraudInfo {
    private String fraudType;
     String key;
    private  String userName;
    private  String secret;
    private int merchantCategoryCode;
    private int merchantCountryCode;
    private String acquirerCountry;
}
