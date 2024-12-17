package au.com.escybersourceconnector.transformers;

import au.com.escybersourceconnector.models.RequestModel;
import au.com.escybersourceconnector.models.context.ExchangeContext;
import au.com.escybersourceconnector.models.response.CyberSourceResponse;
import au.com.escybersourceconnector.models.response.DownStreamResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class OperationResponseTransformer {

    public DownStreamResponse mapToErrorResponse(ClientResponse clientResponse, CyberSourceResponse
            cybersourceResponse, ExchangeContext context) {
        HttpStatusCode status = clientResponse.statusCode();

        String description = "Unknown Error Occured";
        List<String> errors = new ArrayList<>();
        if (Objects.nonNull(cybersourceResponse.getReason())) {
            errors.add(cybersourceResponse.getReason());
            description = cybersourceResponse.getMessage();
        } else if (Objects.nonNull(cybersourceResponse.getErrorInformation())) {
            errors.add(cybersourceResponse.getErrorInformation().getReason());
            description = cybersourceResponse.getErrorInformation().getMessage();
        }
        RequestModel requestModel = context.getRequestModel();
        return DownStreamResponse.builder()
                .code(status)
                .errors(errors)
                .description(description)
                .details(Objects.nonNull(cybersourceResponse.getDetails())
                        ? cybersourceResponse.getDetails() : null)
                .rawResponse(convertToJson(cybersourceResponse, new ObjectMapper()))
                .txnType(requestModel.getTxnType())
                .customerId(requestModel.getCustomerId())
                .orderReference(requestModel.getOrderReference())
                .amount(requestModel.getAmount())
                .transactionDate(cybersourceResponse.getSubmitTimeUtc())
                .metadata(requestModel.getMetadata())
                .maskedPan(requestModel.getMaskedPan())
                .preAuthId(requestModel.getPreAuthId())
                .orderId(requestModel.getOrderId())
                .cardHolderName(requestModel.getCardHolderName())
                .cardToken(requestModel.getCardToken())
                .currency(requestModel.getCurrency())
                .ecm(requestModel.getEcm())
                .requestId(requestModel.getRequestId())
                .channel(requestModel.getChannel())
                .dynamicDescriptor(requestModel.getDynamicDescriptor())
                .gatewaySettingId(requestModel.getGatewaySettingId())
                .verifyId(requestModel.getVerifyId())
                .LFSData(requestModel.getLFSData())
                .threeDSID(requestModel.getThreeDSID())
                .identifiedCardBrand(requestModel.getIdentifiedCardBrand())
                .identifiedBinCountry(requestModel.getIdentifiedBinCountry())
                .errors(errors)
                .build();
    }

    public DownStreamResponse mapToSuccessResponse(ClientResponse clientResponse, CyberSourceResponse
            cybersourceResponse, ExchangeContext context) {
        HttpStatusCode status = clientResponse.statusCode();
        RequestModel requestModel = context.getRequestModel();
        return DownStreamResponse.builder()
                .code(status)
                .rawResponse(convertToJson(cybersourceResponse, new ObjectMapper()))
                .cyberSourceResponse(cybersourceResponse)
                .txnType(requestModel.getTxnType())
                .customerId(requestModel.getCustomerId())
                .orderReference(requestModel.getOrderReference())
                .amount(requestModel.getAmount())
                .transactionDate(cybersourceResponse.getSubmitTimeUtc())
                .metadata(requestModel.getMetadata())
                .maskedPan(requestModel.getMaskedPan())
                .preAuthId(requestModel.getPreAuthId())
                .orderId(requestModel.getOrderId())
                .cardHolderName(requestModel.getCardHolderName())
                .cardToken(requestModel.getCardToken())
                .currency(requestModel.getCurrency())
                .ecm(requestModel.getEcm())
                .requestId(requestModel.getRequestId())
                .channel(requestModel.getChannel())
                .dynamicDescriptor(requestModel.getDynamicDescriptor())
                .gatewaySettingId(requestModel.getGatewaySettingId())
                .verifyId(requestModel.getVerifyId())
                .LFSData(requestModel.getLFSData())
                .threeDSID(requestModel.getThreeDSID())
                .identifiedCardBrand(requestModel.getIdentifiedCardBrand())
                .identifiedBinCountry(requestModel.getIdentifiedBinCountry())
                .build();
    }

    public static String convertToJson(CyberSourceResponse cyberSourceResponse, ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(cyberSourceResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Error transforming to JSON\", \"message\": \"" + e.getMessage() + "\"}";
        }
    }
}
