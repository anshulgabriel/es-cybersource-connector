package au.com.escybersourceconnector.models.response;

import au.com.escybersourceconnector.models.Details;
import au.com.escybersourceconnector.models.Metadata;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DownStreamResponse {
    private HttpStatusCode code;
    private List<String> errors;
    private String description;
    private List<Details> details;
    private String rawResponse;
    private int txnType;
    private CyberSourceResponse cyberSourceResponse;
    private String customerId;
    private Metadata metadata;
    private String transactionDate;
    private String maskedPan;
    private String preAuthId;
    private Long amount;
    private String orderId;
    private String cardHolderName;
    private String cardToken;
    private String currency;
    private String orderReference;
    private int ecm;
    private String requestId;
    private String message;
    private String channel;
    private String dynamicDescriptor;
    private String gatewaySettingId;
    private String resultCode;
    private String verifyId;
    private String threeDSID;
    private Map LFSData;
    private String identifiedCardBrand;
    private String identifiedBinCountry;
}
