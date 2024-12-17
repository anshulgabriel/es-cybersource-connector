package au.com.escybersourceconnector.routehandlers.component;

import au.com.es.paymentsassist.enums.Operation;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("cyberSourceEndpoints")
@Slf4j
public class CyberSourceEndpoints {

    @Value("${CYBERSOURCE_ENDPOINT}:/pts/v2/payments")
    private String cyberSourcePurchaseEndpoint;

    @Value("${CYBERSOURCE_ENDPOINT}:/pts/v2/payments/{id}/reversal")
    private String cyberSourceReversalEndpoint;
    @Value("${CYBERSOURCE_ENDPOINT}:/pts/v2/reversals")
    private String cyberSourceReversalTimeoutEndpoint;

    @Value("${CYBERSOURCE_ENDPOINT}:/pts/v2/captures/{id}/refunds")
    private String cyberSourceRefundCaptureEndpoint;

    @Value("${CYBERSOURCE_ENDPOINT}:/pts/v2/payments/{id}/refund")
    private String cyberSourceRefundPaymentEndpoint;

    @Value("${CYBERSOURCE_ENDPOINT}:/pts/v2/credits")
    private String cyberSourceCreditEndpoint;

    @Value("${CYBERSOURCE_ENDPOINT}:/pts/v2/payments/{id}/voids")
    private String cyberSourcePaymentVoidEndPoint;

    @Value("${CYBERSOURCE_ENDPOINT}:/pts/v2/captures/{id}/voids")
    private String cyberSourceCaptureVoidEndPoint;

    @Value("${CYBERSOURCE_ENDPOINT}:/pts/v2/refunds/{id}/voids")
    private String cyberSourceRefundVoidEndPoint;

    private Map<Operation, String> resourceEndpoints;

    @PostConstruct
    public void mapEndpoints() {
        log.debug("----- Using CYBERSOURCE endpoints -----");
        log.debug("CYBERSOURCE_PAYMENT_ENDPOINT={}", cyberSourcePurchaseEndpoint);
        log.debug("CYBERSOURCE_REVERSAL_ENDPOINT={}", cyberSourceReversalEndpoint);
        log.debug("CYBERSOURCE_REVERSAL_TIMEOUT_ENDPOINT={}", cyberSourceReversalTimeoutEndpoint);
        log.debug("CYBERSOURCE_REFUND_PAYMENT_ENDPOINT={}", cyberSourceRefundPaymentEndpoint);
        log.debug("CYBERSOURCE_REFUND_CAPTURES_ENDPOINT={}", cyberSourceRefundCaptureEndpoint);
        log.debug("CYBERSOURCE_CREDIT_ENDPOINT={}", cyberSourceCreditEndpoint);
        log.debug("CYBERSOURCE_VOID_PURCHASE_ENDPOINT={}", cyberSourcePaymentVoidEndPoint);
        log.debug("CYBERSOURCE_VOID_CAPTURE_ENDPOINT={}", cyberSourceCaptureVoidEndPoint);
        log.debug("CYBERSOURCE_VOID_REFUND_ENDPOINT={}", cyberSourceRefundVoidEndPoint);
        log.debug("----------------------------------");

        resourceEndpoints = Map.of(
                Operation.PURCHASE,cyberSourcePurchaseEndpoint,
                Operation.REVERSAL,cyberSourceReversalEndpoint,
                Operation.REVERSAL_TIMEOUT,cyberSourceReversalTimeoutEndpoint,
                Operation.REFUND,cyberSourceRefundPaymentEndpoint,
                Operation.CAPTURE,cyberSourceRefundCaptureEndpoint,
                Operation.CREDIT,cyberSourceCreditEndpoint,
                Operation.VOID_PURCHASE,cyberSourcePaymentVoidEndPoint,
                Operation.VOID_CAPTURE,cyberSourceCaptureVoidEndPoint,
                Operation.VOID_REFUND,cyberSourceRefundVoidEndPoint
        );

    }

    public String get(Operation operation) {
        return resourceEndpoints.get(operation);
    }

}


