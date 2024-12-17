package au.com.escybersourceconnector.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GatewaySettings {
    private String gatewayType;
    private String clientId;
    private String secret;
    private String username;
    private String password;
    private int settlementTime;
    private String merchantCategoryCode;
    private String customerName;
    private String customerCountry;
    private String customerLocation;
    private String postCode;
    private String sli;
    private String currency;
    private String cardAcceptorTerminalCode;
    private String cardAcceptorTerminalId;
    private String cardAcceptorMerchantId;
    private String acquiringInstitution;
    private String gatewayRoute;
    private Map<String, String> specificInfo;
}
