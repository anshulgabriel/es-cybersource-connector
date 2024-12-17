package au.com.escybersourceconnector.models.response;

import au.com.escybersourceconnector.models.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CyberSourceResponse {

    @JsonProperty("_links")
    private Links links;
    private ClientReferenceInformation clientReferenceInformation;
    private String id;
    private OrderInformation orderInformation;
    private PaymentAccountInformation paymentAccountInformation;
    private PaymentInformation paymentInformation;
    private PointOfSaleInformation pointOfSaleInformation;
    private ProcessorInformation processorInformation;
    private String reconciliationId;
    private String status;
    private String submitTimeUtc;
    private String reason;
    private String message;
    private String resultCode;
    private ErrorInformation errorInformation;
    private List<Details> details;
}
