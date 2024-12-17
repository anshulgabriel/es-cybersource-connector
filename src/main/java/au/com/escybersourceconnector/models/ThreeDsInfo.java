package au.com.escybersourceconnector.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThreeDsInfo {
    private String threeDSType;
    private String key;
    private String userName;
    private String secret;
    private Map<String, String> acquirerBin;
    private String acquirerName;
    private String merchantId;
    private  String merchantName;
}
