package au.com.escybersourceconnector.models;

import au.com.es.paymentsassist.models.paymentinstrument.applepay.decoded.ApplePayAuthenticationResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplePayData {
    private String applicationPrimaryAccountNumber;
    private String applicationExpirationDate;
    private String currencyCode;
    private double transactionAmount;
    private String cardholderName;
    private String deviceManufacturerIdentifier;
    private String paymentDataType;
    private PaymentData paymentData;
    private List<ApplePayAuthenticationResponse> authenticationResponses;
    private String merchantTokenIdentifier;
    private MerchantTokenMetadata merchantTokenMetadata;
    private String network;
}
