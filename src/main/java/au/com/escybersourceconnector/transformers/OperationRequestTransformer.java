package au.com.escybersourceconnector.transformers;

import au.com.es.paymentsassist.enums.Operation;
import au.com.es.salad.transform.Transformer;
import au.com.escybersourceconnector.models.*;
import au.com.escybersourceconnector.models.context.ExchangeContext;
import au.com.escybersourceconnector.models.request.CyberSourceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class OperationRequestTransformer {

    private final Map<Operation, Function<ExchangeContext, CyberSourceRequest>> operationConsumerMap =
            Map.of(Operation.PURCHASE, createPurchaseOrCreditTransformer(), Operation.REVERSAL,
                    createReversalTransformer(), Operation.REVERSAL_TIMEOUT, createReversalTransformer(),
                    Operation.REFUND, createRefundTransformer(), Operation.CAPTURE, createRefundTransformer(),
                    Operation.CREDIT, createPurchaseOrCreditTransformer(), Operation.VOID_PURCHASE,
                    createVoidTransformer(), Operation.VOID_CAPTURE, createVoidTransformer(),
                    Operation.VOID_REFUND, createVoidTransformer());

    public CyberSourceRequest transform(final AtomicReference<ExchangeContext> atomicExchangeContext) {
        ExchangeContext exchangeContext = atomicExchangeContext.get();
        log.info("Transforming request: requestId={}, traceId={}", exchangeContext.getRequestId(), exchangeContext.getTraceId());

        Operation operation = exchangeContext.getEntityOperation().operation();

        if (!operationConsumerMap.containsKey(operation)) {
            log.error("Unsupported operation: {}", operation);
            throw new IllegalArgumentException("Unsupported operation: " + operation);
        }

        return operationConsumerMap.get(operation).apply(exchangeContext);
    }

    private Function<ExchangeContext, CyberSourceRequest> createPurchaseOrCreditTransformer() {
        return exchangeContext -> {
            CyberSourceRequest request = new CyberSourceRequest();
            request.setClientReferenceInformation(createClientReferenceInformation(exchangeContext));
            request.setPaymentInformation(createPaymentInformation(exchangeContext));
            request.setOrderInformation(createOrderInformation(exchangeContext));
            return request;
        };
    }

    private Function<ExchangeContext, CyberSourceRequest> createReversalTransformer() {
        return exchangeContext -> {
            CyberSourceRequest request = new CyberSourceRequest();
            request.setClientReferenceInformation(createClientReferenceInformation(exchangeContext));
            request.setReversalInformation(createReversalInformation(exchangeContext));
            return request;
        };
    }

    private Function<ExchangeContext, CyberSourceRequest> createRefundTransformer() {
        return exchangeContext -> {
            CyberSourceRequest request = new CyberSourceRequest();
            request.setClientReferenceInformation(createClientReferenceInformation(exchangeContext));
            request.setOrderInformation(createOrderInformation(exchangeContext));
            return request;
        };
    }

    private Function<ExchangeContext, CyberSourceRequest> createVoidTransformer() {
        return exchangeContext -> {
            CyberSourceRequest request =  new CyberSourceRequest();
            request.setClientReferenceInformation(createClientReferenceInformation(exchangeContext));
            return request;
        };
    }

    // --- Helper Methods ---

    private ClientReferenceInformation createClientReferenceInformation(ExchangeContext exchangeContext) {
        String reference = exchangeContext.getRequestModel().getMerchReference();
        return new ClientReferenceInformation(reference);
    }

    private PaymentInformation createPaymentInformation(ExchangeContext exchangeContext) {
        Card card = new Card();
        PaymentInstrumentDecoded instrumentDecoded = exchangeContext.getRequestModel().getPaymentInstrumentDecoded();

        if (instrumentDecoded != null) {
            InstrumentData instrumentData = instrumentDecoded.getInstrumentData();
            if (instrumentData != null) {
                card.setNumber(instrumentData.getPanData().getPan());
                card.setExpirationMonth(instrumentData.getInstrumentMetaData().getExpiryMonth());
                card.setExpirationYear(instrumentData.getInstrumentMetaData().getExpiryYear());
            }
        }

        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setCard(card);
        return paymentInformation;
    }

    private OrderInformation createOrderInformation(ExchangeContext exchangeContext) {
        AmountDetails amountDetails = new AmountDetails();
        amountDetails.setCurrency(exchangeContext.getRequestModel().getCurrency());
        amountDetails.setTotalAmount(String.valueOf(exchangeContext.getRequestModel().getAmount() / 100));

        BillTo billTo = createBillTo(exchangeContext.getRequestModel());

        return new OrderInformation(amountDetails, billTo);
    }

    private BillTo createBillTo(RequestModel requestModel) {
        BillTo billTo = new BillTo();
        String cardHolderName = requestModel.getPaymentInstrumentDecoded()
                .getInstrumentData().getInstrumentMetaData().getCardHolderName();
        if (cardHolderName != null && !cardHolderName.isEmpty()) {
            String[] nameParts = cardHolderName.split("\\s+");
            billTo.setFirstName(nameParts[0]);
            billTo.setLastName(nameParts.length > 1 ? String.join(" ",
                    Arrays.copyOfRange(nameParts, 1, nameParts.length)) : "");
        }
        billTo.setEmail(requestModel.getMetadata().getEmail());
        return billTo;
    }

    private ReversalInformation createReversalInformation(ExchangeContext exchangeContext) {
        AmountDetails amountDetails = new AmountDetails();
        amountDetails.setTotalAmount(String.valueOf(exchangeContext.getRequestModel().getAmount() / 100));
        return ReversalInformation.builder().amountDetails(amountDetails).reason("timeout/transaction failure reversal").build();
    }
}
