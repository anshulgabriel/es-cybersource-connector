package au.com.escybersourceconnector.models;

import au.com.es.paymentsassist.models.internalpaymentinstrument.azupay.AzuPayData;
import au.com.es.paymentsassist.models.paymentinstrument.googlepay.GPayData;
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
public class PaymentInstrumentDecoded {
    private  String type;
    private  String subType;
    private InstrumentData instrumentData;
    private PanData panData;
    private AzuPayData azuPayData;
    private GPayData gpayData;
    private ApplePayData applePayData;
    private CardTokenData cardTokenData;
    private PaytoData payToData;
}
