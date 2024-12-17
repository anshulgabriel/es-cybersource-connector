package au.com.escybersourceconnector.models.request;

import au.com.escybersourceconnector.models.ClientReferenceInformation;
import au.com.escybersourceconnector.models.OrderInformation;
import au.com.escybersourceconnector.models.PaymentInformation;
import au.com.escybersourceconnector.models.ReversalInformation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CyberSourceRequest {
    public ReversalInformation reversalInformation;
    private ClientReferenceInformation clientReferenceInformation;
    private PaymentInformation paymentInformation;
    private OrderInformation orderInformation;
}
